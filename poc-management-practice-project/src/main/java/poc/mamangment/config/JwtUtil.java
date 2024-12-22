package poc.mamangment.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import poc.mamangment.model.User;

@Component
public class JwtUtil {
	private static final String SECRET = "your-secret-key";
	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER = "Bearer";
	private static final long EXPIRATION_TIME = 864_000_000; // 10 days
	private final JwtParser jwtParser;

	public JwtUtil() {
		this.jwtParser = Jwts.parser().setSigningKey(SECRET);
	}

	public String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		Date createTokenTime = new Date();
		Date tokenValidity = new Date(createTokenTime.getTime() + TimeUnit.MILLISECONDS.toMillis(EXPIRATION_TIME));
		return Jwts.builder().setClaims(claims).setExpiration(tokenValidity).signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}

	private Claims parseJWTClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public Claims resolveClaims(HttpServletRequest request) {
		try {
			String token = resolveToken(request);
			if (token != null) {
				return parseJWTClaims(token);
			}
			return null;
		} catch (ExpiredJwtException e) {
			request.setAttribute("Expired", e.getMessage());
			throw e;
		} catch (Exception e) {
			request.setAttribute("invalid", e.getMessage());
			throw e;
		}
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(BEARER)) {
			return bearerToken.substring(BEARER.length());
		}
		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
			return claims.getExpiration().after(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public static String extractUsername(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
	}
}

package poc.mamangment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "pocapp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class POCAPPConfig {

	private String expiredStatus;
	private String approvedStatus;
	private String rejectedStatus;
}

package blockchain.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler {

	@ExceptionHandler(value = BlockChainAppException.class)
	protected ResponseEntity<?> handleConflict(BlockChainAppException ex) {
		return new ResponseEntity<ServiceException>(ex.getServiceException(), ex.getServiceException().getHttpStatus());
	}

	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<?> handleConflict(Exception ex) {
		ServiceException exception = new ServiceException("500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ServiceException>(exception, exception.getHttpStatus());
	}
}

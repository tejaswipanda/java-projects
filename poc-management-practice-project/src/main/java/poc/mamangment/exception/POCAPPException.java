package poc.mamangment.exception;

public class POCAPPException extends Exception {
	private ServiceException serviceException;

	public POCAPPException(ServiceException serviceException) {
		super();
		this.serviceException = serviceException;
	}

	public ServiceException getServiceException() {
		return serviceException;
	}

	public void setServiceException(ServiceException serviceException) {
		this.serviceException = serviceException;
	}

}

package blockchain.practice.exception;

public class BlockChainAppException extends Exception {
	private ServiceException serviceException;

	public BlockChainAppException(ServiceException serviceException) {
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

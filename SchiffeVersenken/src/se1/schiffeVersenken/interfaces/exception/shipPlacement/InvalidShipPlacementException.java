package se1.schiffeVersenken.interfaces.exception.shipPlacement;

public class InvalidShipPlacementException extends Exception {
	
	public InvalidShipPlacementException() {
	}
	
	public InvalidShipPlacementException(String message) {
		super(message);
	}
	
	public InvalidShipPlacementException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidShipPlacementException(Throwable cause) {
		super(cause);
	}
}

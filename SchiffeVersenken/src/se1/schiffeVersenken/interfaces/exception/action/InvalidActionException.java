package se1.schiffeVersenken.interfaces.exception.action;

/**
 * Thrown if someone wants to take a Turn but something is invalid.
 */
public class InvalidActionException extends Exception {
	
	public InvalidActionException() {
	}
	
	public InvalidActionException(String message) {
		super(message);
	}
	
	public InvalidActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InvalidActionException(Throwable cause) {
		super(cause);
	}
}

package se1.schiffeVersenken.interfaces.exception.action;

import se1.schiffeVersenken.interfaces.util.Position;

public class ActionPositionOutOfBoundsException extends InvalidActionException {
	
	private final Position pos;
	
	public ActionPositionOutOfBoundsException(Position pos) {
		super("Invalid Position: " + pos + "!");
		this.pos = pos;
	}
	
	public Position getPos() {
		return pos;
	}
}

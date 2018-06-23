package se1.schiffeVersenken.interfaces.exception.action;

import se1.schiffeVersenken.interfaces.util.Position;

public class AlreadyShotPositionException extends InvalidActionException {
	
	private final Position pos;
	
	public AlreadyShotPositionException(Position pos) {
		super("Shot already taken on position " + pos);
		this.pos = pos;
	}
	
	public Position getPos() {
		return pos;
	}
}

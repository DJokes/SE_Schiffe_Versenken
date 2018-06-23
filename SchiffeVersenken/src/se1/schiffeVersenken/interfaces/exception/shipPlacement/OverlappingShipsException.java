package se1.schiffeVersenken.interfaces.exception.shipPlacement;

import se1.schiffeVersenken.interfaces.Ship;
import se1.schiffeVersenken.interfaces.util.Position;

public class OverlappingShipsException extends InvalidShipPlacementException {
	
	private final Position pos;
	private final Ship ship1;
	private final Ship ship2;
	
	public OverlappingShipsException(Position pos, Ship ship1, Ship ship2) {
		super("Overlapping Ships " + ship1 + " and " + ship2 + " at position " + pos + "!");
		this.pos = pos;
		this.ship1 = ship1;
		this.ship2 = ship2;
	}
	
	public Position getPos() {
		return pos;
	}
	
	public Ship getShip1() {
		return ship1;
	}
	
	public Ship getShip2() {
		return ship2;
	}
}

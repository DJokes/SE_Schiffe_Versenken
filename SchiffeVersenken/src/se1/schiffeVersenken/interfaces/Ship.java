package se1.schiffeVersenken.interfaces;

import java.util.Arrays;
import java.util.Objects;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.util.Direction;
import se1.schiffeVersenken.interfaces.util.Position;

/**
 * The {@link Ship} is our main "Entity" in the Game. <br>
 * It has a position inside a 10x10 grid world, a direction ({@link Direction#HORIZONTAL HORIZONTAL}
 * or {@link Direction#VERTICAL VERTICAL}) and a length (at most 10).<br>
 * Methods:
 * <ul>
 * <li>{@link Ship#getHealth()} to get the current health of the ship</li>
 * <li>{@link Ship#getOccupiedSpaces()} to query all blocks of the world it is using.</li>
 * <li>{@link Ship#getEmptySpacesSurrounding(ShipBorderConditions)} to query all blocks around it which have to be water depending on {@link ShipBorderConditions}.</li>
 * </ul>
 */
public final class Ship {
	
	/**
	 * the left upper corner of the ship
	 */
	private final Position position;
	private final Direction direction;
	private final int length;
	private int health;
	private final Position[] occupiedSpaces;
	
	public Ship(Position position, Direction direction, int length) {
		this.position = position;
		this.direction = direction;
		this.length = length;
		this.health = length;
		
		occupiedSpaces = new Position[length];
		for (int i = 0; i < length; i++)
			occupiedSpaces[i] = position.add(direction.positive.multiply(i));
	}
	
	//getter
	public Position getPosition() {
		return position;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getHealth() {
		return health;
	}
	
	public boolean isSunk() {
		return health == 0;
	}
	
	public void takeHit() {
		health--;
	}
	
	/**
	 * gets all {@link Position Positions} which this ship occupies
	 */
	public Position[] getOccupiedSpaces() {
		return occupiedSpaces;
	}
	
	/**
	 * gets all {@link Position Positions} which this ship requires to be water depending on the borderConditions
	 *
	 * @param borderConditions the {@link ShipBorderConditions} on how the {@link Ship Ships} are allowed to touch
	 * @return an {@link Position}-Array containing all {@link Position Positions} which have to be {@link Tile#WATER}
	 */
	public Position[] getEmptySpacesSurrounding(ShipBorderConditions borderConditions) {
		switch (borderConditions) {
			case TOUCHING_ALLOWED:
				return new Position[0];
			case NO_DIRECT_TOUCH:
				Position[] ret = new Position[length * 2 + 2];
				ret[0] = position.add(direction.negative);
				ret[1] = position.add(direction.positive.multiply(length));
				for (int i = 0; i < length * 2; i++)
					ret[i + 2] = position.add(direction.positive.multiply(i / 2)).add(i % 2 == 0 ? direction.other().negative : direction.other().positive);
				return ret;
			case NO_DIRECT_AND_DIAGONAL_TOUCH:
				Position[] ret2 = new Position[length * 2 + 6];
				for (int i = 0; i < 3; i++)
					ret2[i] = position.add(direction.negative).add(direction.other().positive.multiply(i - 1));
				for (int i = 0; i < 3; i++)
					ret2[i + 3] = position.add(direction.positive.multiply(length)).add(direction.other().positive.multiply(i - 1));
				for (int i = 0; i < length * 2; i++)
					ret2[i + 6] = position.add(direction.positive.multiply(i / 2)).add(i % 2 == 0 ? direction.other().negative : direction.other().positive);
				return ret2;
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ship)) return false;
		Ship ship = (Ship) o;
		return length == ship.length &&
				Objects.equals(position, ship.position) &&
				direction == ship.direction;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(position, direction, length);
	}
	
	@Override
	public String toString() {
		return "Ship{" +
				"position=" + position +
				", direction=" + direction +
				", length=" + length +
				", health=" + health +
				", occupiedSpaces=" + Arrays.toString(occupiedSpaces) +
				'}';
	}
}

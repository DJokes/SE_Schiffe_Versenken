package se1.schiffeVersenken.interfaces;

import se1.schiffeVersenken.interfaces.util.Position;

import java.util.Arrays;

/**
 * An Object representing the Settings for a Game.<br>
 * You can create this Object with {@link GameSettingsBuilder}.<br>
 * <br>
 * You can query:
 * <ul>
 * <li>{@link GameSettings#getNumberOfShips(int)} get the number of {@link Ship Ships} for a certain size</li>
 * <li>{@link GameSettings#getShipBorderConditions()} gets the {@link ShipBorderConditions} for the current Game</li>
 * </ul>
 */
public class GameSettings {
	
	public static final int SIZE_OF_PLAYFIELD = 10;
	public static final Position SIZE_OF_PLAYFIELD_VECTOR = new Position(SIZE_OF_PLAYFIELD, SIZE_OF_PLAYFIELD);
	
	private final ShipBorderConditions shipBorderConditions;
	private final int[] numberOfShips;
	
	GameSettings(ShipBorderConditions shipBorderConditions, int[] numberOfShips) {
		this.shipBorderConditions = shipBorderConditions;
		this.numberOfShips = Arrays.copyOf(numberOfShips, SIZE_OF_PLAYFIELD);
	}
	
	//getter
	public ShipBorderConditions getShipBorderConditions() {
		return shipBorderConditions;
	}
	
	public int[] getNumberOfShips() {
		return numberOfShips;
	}
	
	public int getNumberOfShips(int shipLength) {
		return numberOfShips[shipLength - 1];
	}
	
	//enum
	public enum ShipBorderConditions {
		
		TOUCHING_ALLOWED,
		NO_DIRECT_TOUCH,
		NO_DIRECT_AND_DIAGONAL_TOUCH
		
	}
}

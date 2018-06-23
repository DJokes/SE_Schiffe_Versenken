package se1.schiffeVersenken.interfaces;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;

/**
 * A Builder for {@link GameSettings}<br>
 * <br>
 * <ul>
 * <li>{@link GameSettingsBuilder#setShipBorderConditions(ShipBorderConditions)} sets the {@link ShipBorderConditions}</li>
 * <li>{@link GameSettingsBuilder#setNumOfShips(int, int)} sets the count of ships for any shipLength</li>
 * </ul>
 * The Method {@link GameSettingsBuilder#createGameSettings()} creates the {@link GameSettings}. <br>
 */
public class GameSettingsBuilder {
	
	private ShipBorderConditions shipBorderConditions;
	private int[] numOfShips = new int[GameSettings.SIZE_OF_PLAYFIELD];
	
	public GameSettingsBuilder setShipBorderConditions(ShipBorderConditions shipBorderConditions) {
		this.shipBorderConditions = shipBorderConditions;
		return this;
	}
	
	public GameSettingsBuilder setNumOfShips(int shipLength, int count) {
		numOfShips[shipLength - 1] = count;
		return this;
	}
	
	public GameSettings createGameSettings() {
		return new GameSettings(shipBorderConditions, numOfShips);
	}
}
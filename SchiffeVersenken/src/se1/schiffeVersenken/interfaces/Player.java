package se1.schiffeVersenken.interfaces;

import se1.schiffeVersenken.interfaces.exception.action.InvalidActionException;
import se1.schiffeVersenken.interfaces.exception.shipPlacement.InvalidShipPlacementException;
import se1.schiffeVersenken.interfaces.util.Position;

/**
 * A {@link Player} is one of the two Players of the Game. <br>
 * <br>
 * The Lifecycle of a Player Object:
 * <ul>
 * <li>The Player Object is created by a {@link PlayerCreator#createPlayer(GameSettings, Class)}</li>
 * <li>The Method {@link Player#placeShips(ShipPlacer)} is called and the {@link Player} calls {@link ShipPlacer#setShips(Ship[])},
 * catching any {@link InvalidShipPlacementException}</li>
 * <li>Repeating:<ul>
 * <li>{@link Player#takeTurn(TurnAction)} is called and you take your turn by calling {@link TurnAction#shootTile(Position)},
 * which returns the type of {@link Tile} you hit or throws an {@link InvalidActionException}</li>
 * <li>{@link Player#onEnemyShot(Position, Tile, Ship)} tells you when the other player shoots one of your {@link Tile}
 * and tells you the position and what was hit</li>
 * </ul></li>
 * <li>When {@link Player#gameOver(boolean)} is called the game is over to do eg. state saving</li>
 * </ul>
 */
public interface Player {
	
	/**
	 * This Method is the first to get called after the {@link Player} is created.<br>
	 * You should call {@link ShipPlacer#setShips(Ship[])} to place your {@link Ship Ships}
	 * and catch any {@link InvalidShipPlacementException} thrown.
	 *
	 * @param placer the {@link ShipPlacer} to be given your ship placement.
	 */
	void placeShips(ShipPlacer placer);
	
	/**
	 * While the Game is running this Method is called every time you should take an action.
	 * You should call {@link TurnAction#shootTile(Position)} to shoot a {@link Tile}
	 * and it returns what was hit or throws an {@link InvalidActionException} is something went wrong.
	 *
	 * @param turnAction the {@link TurnAction} which allows you to shoot a {@link Tile}
	 */
	void takeTurn(TurnAction turnAction);
	
	/**
	 * While the Game is running this Method will inform you when the other {@link Player} shoots one of your {@link Tile Tiles}.
	 * It will give you where he shot and what it hit.
	 *
	 * @param position the {@link Position} the other {@link Player} shot
	 * @param tile     the {@link Tile} the other {@link Player} has hit, either {@link Tile#WATER}, {@link Tile#SHIP} or {@link Tile#SHIP_KILL}
	 * @param ship     the {@link Ship} the other {@link Player} has hit, may be <code>null</code> if no ship was damaged
	 */
	void onEnemyShot(Position position, Tile tile, Ship ship);
	
	/**
	 * Tells you when the Game is over and whether you have won.
	 *
	 * @param youHaveWon true if you have won
	 */
	void gameOver(boolean youHaveWon);
}

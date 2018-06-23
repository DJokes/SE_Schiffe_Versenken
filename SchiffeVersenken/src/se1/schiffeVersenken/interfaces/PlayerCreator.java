package se1.schiffeVersenken.interfaces;

/**
 * This is the "main" interface with the {@link PlayerCreator#createPlayer(GameSettings, Class)} Method initializing any {@link Player}.<br>
 * <br>
 * Sample usage:
 * <pre>
 * //add this {@link java.lang.annotation.Annotation Annotation} to "mark" your AIs to be found by our BotBattle
 * &#64;PlayableAI("My awesome Ai's fancy name")
 * public class MyPlayerCreator implements PlayerCreator {
 *
 *     public Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer) {
 *         return new AwesomePlayer(...);
 *     }
 *
 * }
 * </pre>
 */
@FunctionalInterface
public interface PlayerCreator {
	
	/**
	 * This is the "main" init Method of any {@link Player} or AI.
	 *
	 * @param settings    the {@link GameSettings} for the new Game
	 * @param otherPlayer the class of the other player playing, just informational if someone wants to use it
	 * @return an instance of {@link Player} which will be one of the players of the current game
	 */
	Player createPlayer(GameSettings settings, Class<? extends PlayerCreator> otherPlayer);
}

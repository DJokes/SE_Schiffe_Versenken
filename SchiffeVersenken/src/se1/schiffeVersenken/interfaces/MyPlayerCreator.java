package se1.schiffeVersenken.interfaces;

import se1.schiffeVersenken.interfaces.GameSettings.ShipBorderConditions;
import se1.schiffeVersenken.interfaces.GameSettings;
import se1.schiffeVersenken.interfaces.Player;
import se1.schiffeVersenken.interfaces.PlayerCreator;

public class MyPlayerCreator implements PlayerCreator{
	
	@Override
	public Player createPlayer (GameSettings settings, Class<? extends PlayerCreator> otherPlayer){
		
		ShipBorderConditions border = settings.getShipBorderConditions();
		int[] numberShips = settings.getNumberOfShips();
		
		Player superDuperPlayer = new MyPlayer(numberShips, border);
		
		return superDuperPlayer;
	}
}

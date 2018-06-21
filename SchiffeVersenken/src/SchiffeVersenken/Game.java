package SchiffeVersenken;

import java.awt.Color;

public class Game {
	private GUI gui = null;

	public Game(GUI gui) {
		this.gui = gui;

	}

	public int determineBeginner() {
		return (int) (Math.random() * 2) + 1;
	}

	public void shoot(int index) {
		if (gui.getRightGridFieldArray()[index].getBackground() == Color.blue) {
			gui.getRightGridFieldArray()[index].setBackground(Color.BLACK);
		}
		else if(gui.getRightGridFieldArray()[index].getBackground() == Color.GREEN) {
			gui.getRightGridFieldArray()[index].setBackground(Color.RED);
		}
	}

	public boolean isOver() {
		// TODO
		return true;
	}

	public void waitForEnemy() {
		// TODO

	}

}

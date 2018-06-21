package SchiffeVersenken;

import java.awt.Color;

public class Game {
	private GUI gui = null;
	private int hits = 30;

	public Game(GUI gui) {
		this.gui = gui;

	}

	public int determineBeginner() {
		return (int) (Math.random() * 2) + 1;
	}

	public void shoot(int index) {
		if (!isOver()) {
			if (gui.getRightGridFieldArray()[index].getBackground() == Color.blue) {
				gui.getRightGridFieldArray()[index].setBackground(Color.BLACK);
			} else if (gui.getRightGridFieldArray()[index].getBackground() == Color.GREEN) {
				gui.getRightGridFieldArray()[index].setBackground(Color.RED);
				hits--;
			}
		}
	}

	public boolean isOver() {
		if (hits == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void waitForEnemy() {
		// TODO

	}

}

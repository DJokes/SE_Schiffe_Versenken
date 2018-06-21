package SchiffeVersenken;

import java.awt.Color;

public class Game {
	private GUI gui = null;
	private int hits = 30;
	private boolean turn = determineBeginner();

	public Game(GUI gui) {
		this.gui = gui;

	}

	public boolean determineBeginner() {
		int beginner = (int) (Math.random() * 2) + 1;
		if (beginner == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void shoot(int index) {

		if (!isOver()) {
			if (turn) {
				if (gui.getRightGridFieldArray()[index].getBackground() == Color.blue) {
					gui.getRightGridFieldArray()[index].setBackground(Color.BLACK);
					turn = false;
				} else if (gui.getRightGridFieldArray()[index].getBackground() == Color.GREEN) {
					gui.getRightGridFieldArray()[index].setBackground(Color.RED);
					hits--;
				}
			}
			else {
				waitForEnemy();
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
		boolean enemyHasShot = false;
		//TODO
		if(enemyHasShot) {
			turn = true;
		}

	}

}

package SchiffeVersenken;

import java.awt.Color;

public class Game {
	private GUI gui = null;
	private int hits = 30;
	private boolean turn = determineBeginner();
	private AI enemy;
	private boolean[][] field;

	public Game(GUI gui) {
		this.gui = gui;
		enemy = new AI(new Spielfeld());
		// enemy.setShips();
		field = enemy.getField().getField();

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
		int x = index % 10;
		int y = index / 10;

		if (!isOver()) {
			if (turn) {
				if (field[x][y] == false) {
					gui.getRightGridFieldArray()[index].setBackground(Color.BLACK);
					turn = false;
				} else {
					gui.getRightGridFieldArray()[index].setBackground(Color.RED);
					hits--;
				}
			} else {
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
		enemy.takeTurn();
		int pos = enemy.getLastShot().getX() + enemy.getLastShot().getY() * 10;
		if (gui.getLeftGridFieldArray()[pos].getBackground() == Color.GREEN) {
			gui.getLeftGridFieldArray()[pos].setBackground(Color.RED);
		} else {
			gui.getLeftGridFieldArray()[pos].setBackground(Color.BLACK);
			enemyHasShot = true;
		}
		if (enemyHasShot) {
			turn = true;
		}

	}

}

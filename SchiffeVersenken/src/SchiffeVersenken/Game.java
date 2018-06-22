package SchiffeVersenken;

import java.awt.Color;

import javax.swing.JOptionPane;

public class Game {
	private GUI gui = null;
	private int hits = 30;
	private boolean turn = determineBeginner();
	private AI enemy;
	private boolean[][] field;

	public Game(GUI gui) {
		this.gui = gui;
		enemy = new AI(new Spielfeld());
		enemy.setShips();
		enemy.getField().printField();
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
				if (field[x][y] == false && gui.getRightGridFieldArray()[index].getBackground() != Color.BLACK) {
					gui.getRightGridFieldArray()[index].setBackground(Color.BLACK);
					turn = false;
				} else if (field[x][y] == true && gui.getRightGridFieldArray()[index].getBackground() != Color.RED){
					gui.getRightGridFieldArray()[index].setBackground(Color.RED);
					hits--;
					gui.setPlayerScore(5);
					gui.setOpponentScore(-2);
				}
				else {
					return;
				}
				
			} else {
				waitForEnemy();
			}
		}
	}

	public boolean isOver() {
		if (hits == 0) {
			JOptionPane.showMessageDialog(null, "Spiel Ende!");
			hits = 30;
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
			gui.setOpponentScore(5);
			gui.setPlayerScore(-2);
		} else {
			gui.getLeftGridFieldArray()[pos].setBackground(Color.BLACK);
			enemyHasShot = true;
		}
		if (enemyHasShot) {
			turn = true;
		}

	}

}

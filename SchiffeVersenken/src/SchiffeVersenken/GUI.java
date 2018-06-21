package SchiffeVersenken;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI {

	private Spiel game = null;
	private JFrame ruleFrame = null;
	private JFrame gameFrame = null;
	private static final int RULES_HEIGHT = 300;
	private static final int RULES_WIDTH = 300;
	private static final int GAME_HEIGHT = 500;
	private static final int GAME_WIDTH = 1000;
	private JButton opt1 = new JButton("Standard");
	private JButton opt2 = new JButton("Spezial");
	private JButton opt3 = new JButton("Wasser");
	private JButton opt4 = new JButton("Ecken");
	private JButton opt5 = new JButton("Ber\u00fchren");
	private JButton reset = new JButton("Reset");
	// private JButton ship_placement_ok = new JButton("Setzen");
	private JLabel leftGridScore = new JLabel("", SwingConstants.CENTER);
	private JLabel rightGridScore = new JLabel("", SwingConstants.CENTER);
	private JComboBox ship_combobox = null;
	private String ship_number[] = null;
	private JButton[] leftGridFieldArray = new JButton[100];
	private JButton[] rightGridFieldArray = new JButton[100];
	private boolean fieldArray[][] = new boolean[10][10];
	private JLabel ship_placement = new JLabel("Schiffe setzen:", SwingConstants.CENTER);
	private JPanel seperator_down = new JPanel();
	private JPanel seperator_up = new JPanel();
	private JPanel seperator = new JPanel();
	private boolean setzen = false;
	private int x = 0;
	private int y = 0;
	private int shipNormal[] = { 0, 0, 4, 3, 2, 1 };
	private int shipSpezial[] = new int[10];
	private int chosenSettings = 0;

	// Danke, Maze :D
	public GUI(Spiel game) {
		this.game = game;
	}

	public void openFirstRulesFrame() {

		ruleFrame = new JFrame("Einstellungen");
		ruleFrame.setLayout(new GridLayout(3, 1));

		JLabel headline = new JLabel("Spielregeln ausw\u00e4hlen (1):", SwingConstants.CENTER);
		headline.setFont(new Font("Serif", Font.PLAIN, 20));

		ruleFrame.add(headline);

		ruleFrame.add(opt1);
		ruleFrame.add(opt2);

		ruleFrame.setSize(RULES_WIDTH, RULES_HEIGHT);
		ruleFrame.setResizable(false);
		ruleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ruleFrame.setLocationRelativeTo(null);
		ruleFrame.setVisible(true);

		for (int i = 0; i < leftGridFieldArray.length; i++) {
			leftGridFieldArray[i] = new JButton();
			rightGridFieldArray[i] = new JButton();
		}

	}

	public void openSecondRulesFrame() {

		ruleFrame.setLayout(new GridLayout(4, 1));

		JLabel headline = new JLabel("Spielregeln ausw\u00e4hlen (2):", SwingConstants.CENTER);
		headline.setFont(new Font("Serif", Font.PLAIN, 20));

		ruleFrame.add(headline);

		ruleFrame.add(opt3);
		ruleFrame.add(opt4);
		ruleFrame.add(opt5);

		showRulesElements();

	}

	public void openGameFrame() {

		gameFrame = new JFrame("Schiffe versenken");
		gameFrame.setLayout(null);

		JLabel leftGridHeadline = new JLabel("Dein Spielfeld:", SwingConstants.CENTER);
		leftGridHeadline.setSize(250, 50);
		leftGridHeadline.setLocation(50, 50);
		leftGridHeadline.setFont(new Font("Serif", Font.PLAIN, 24));
		gameFrame.add(leftGridHeadline);

		JPanel leftGridField = new JPanel();
		leftGridField.setSize(250, 250);
		leftGridField.setLocation(50, 125);
		leftGridField.setLayout(new GridLayout(10, 10));
		gameFrame.add(leftGridField);

		for (int i = 0; i < leftGridFieldArray.length; i++) {
			leftGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white));
			leftGridFieldArray[i].setBackground(Color.blue);
			leftGridField.add(leftGridFieldArray[i]);
		}

		leftGridScore.setSize(250, 50);
		leftGridScore.setLocation(50, 400);
		leftGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
		gameFrame.add(leftGridScore);

		seperator.setSize(10, 500);
		seperator.setLocation((1000 / 2) - 5, 0);
		seperator.setBackground(Color.black);
		seperator.setVisible(false);
		gameFrame.add(seperator);

		seperator_up.setSize(10, 150);
		seperator_up.setLocation((1000 / 2) - 5, 0);
		seperator_up.setBackground(Color.black);
		gameFrame.add(seperator_up);

		ship_placement.setSize(200, 50);
		ship_placement.setLocation((1000 / 2) - 100, 180);
		ship_placement.setFont(new Font("Serif", Font.PLAIN, 20));
		gameFrame.add(ship_placement);

		setShipNumber(5);
		ship_combobox = new JComboBox(ship_number);
		ship_combobox.setSize(100, 50);
		ship_combobox.setLocation((1000 / 2) - 50, 215);
		gameFrame.add(ship_combobox);

		reset.setSize(80, 40);
		reset.setLocation((1000 / 2) - 40, 280);
		reset.setVisible(true);
		gameFrame.add(reset);

		// ship_placement_ok.setSize(100,35);
		// ship_placement_ok.setLocation((1000/2)-50, 260);
		// gameFrame.add(ship_placement_ok);

		seperator_down.setSize(10, 150);
		seperator_down.setLocation((1000 / 2) - 5, 330);
		seperator_down.setBackground(Color.black);
		gameFrame.add(seperator_down);

		JLabel rightGridHeadline = new JLabel("Gegner Spielfeld:", SwingConstants.CENTER);
		rightGridHeadline.setSize(250, 50);
		rightGridHeadline.setLocation(1000 - 50 - 250, 50);
		rightGridHeadline.setFont(new Font("Serif", Font.PLAIN, 24));
		gameFrame.add(rightGridHeadline);

		JPanel rightGridField = new JPanel();
		rightGridField.setSize(250, 250);
		rightGridField.setLocation(1000 - 50 - 250, 125);
		rightGridField.setLayout(new GridLayout(10, 10));
		gameFrame.add(rightGridField);

		for (int i = 0; i < rightGridFieldArray.length; i++) {
			rightGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white));
			rightGridFieldArray[i].setBackground(Color.blue);
			rightGridField.add(rightGridFieldArray[i]);
		}

		rightGridScore.setSize(250, 50);
		rightGridScore.setLocation(1000 - 50 - 250, 400);
		rightGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
		gameFrame.add(rightGridScore);

		// // Init left field
		// changeGrid(0, 1, 22);
		// changeGrid(0, 1, 23);
		// changeGrid(0, 1, 24);
		//
		// changeGrid(0, 1, 33);
		// changeGrid(0, 1, 42);
		// changeGrid(0, 1, 55);
		//
		// changeGrid(0, 1, 24);
		//
		// changeGrid(0, 1, 88);
		// changeGrid(0, 1, 89);
		//
		// setPlayerScore(100);
		//
		// // Init right field
		// changeGrid(1, 1, 22);
		// changeGrid(1, 1, 23);
		// changeGrid(1, 1, 24);
		//
		// changeGrid(1, 2, 33);
		// changeGrid(1, 2, 42);
		// changeGrid(1, 2, 55);
		//
		// changeGrid(1, 2, 24);
		//
		// changeGrid(1, 2, 88);
		// changeGrid(1, 2, 89);
		//
		setOpponentScore(0);

		gameFrame.setSize(GAME_WIDTH, GAME_HEIGHT);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);

	}

	// First param: leftGrid(0) rightGrid(1)
	// Second param: water(0) ship(1) shotShip(2) shotwater(3)
	// Third param: index of grid
	public void changeGrid(int grid, int art, int index) {

		if (grid == 0) {

			switch (art) {
			case 0:
				leftGridFieldArray[index].setBackground(Color.blue);
				break;
			case 1:
				leftGridFieldArray[index].setBackground(Color.black);
				break;
			case 2:
				leftGridFieldArray[index].setBackground(Color.red);
				break;
			case 3:
				leftGridFieldArray[index].setBackground(Color.yellow);
				break;
			default:
				System.exit(0);
			}

		} else if (grid == 1) {

			switch (art) {
			case 0:
				rightGridFieldArray[index].setBackground(Color.blue);
				break;
			case 1:
				rightGridFieldArray[index].setBackground(Color.black);
				break;
			case 2:
				rightGridFieldArray[index].setBackground(Color.red);
				break;
			case 3:
				rightGridFieldArray[index].setBackground(Color.yellow);
				break;
			default:
				System.exit(0);
			}

		} else {
			System.exit(0);
		}

	}

	private int getShipHitpoints(int[] shipArray) {
		int sum = 0;
		for (int i = 0; i < shipArray.length; i++) {
			sum = sum + i * shipArray[i];
		}
		return sum;
	}

	public boolean placeShip(int index) {

		int ship_size = Integer.parseInt((String) ship_combobox.getSelectedItem());

		// if(summe > 0) {
		if (setzen) {
			int diffX = index % 10 - x;
			int diffY = index / 10 - y;
			int firstX = x;
			int firstY = y;
			boolean isValid = false;

			if (Math.abs(diffX) < ship_size && Math.abs(diffY) < ship_size) {
				if (diffX == 0 ^ diffY == 0) {
					fieldArray[firstX][firstY] = false;

					isValid = true;
				}
			}

			if (chosenSettings == 1) {
				for (int i = 0; i < ship_size; i++) {
					if (!isFieldValidate(firstX + (diffX != 0 ? (int) Math.copySign(i, diffX) : 0),
							firstY + (diffY != 0 ? (int) Math.copySign(i, diffY) : 0))) {
						isValid = false;
						break;
					}
				}
			}

			if (isValid) {
				for (int i = 0; i < ship_size; i++) {
					fieldArray[firstX + (diffX != 0 ? (int) Math.copySign(i, diffX) : 0)][firstY
							+ (diffY != 0 ? (int) Math.copySign(i, diffY) : 0)] = true;
					leftGridFieldArray[firstX + (diffX != 0 ? (int) Math.copySign(i, diffX) : 0)
							+ (firstY + (diffY != 0 ? (int) Math.copySign(i, diffY) : 0)) * 10]
									.setBackground(Color.GREEN);
				}

				setzen = false;
				// summe -= ship_size;
				// updateShipLegend();
			} else {
				fieldArray[firstX][firstY] = true;
			}
		}

		else {
			x = index % 10;
			y = index / 10;

			if (isShipSizeValidate(x, y, ship_size)) {
				if (ship_size != 1) {
					setzen = true;
				}
				fieldArray[x][y] = true;
				leftGridFieldArray[x + (y * 10)].setBackground(Color.GREEN);

				return true;

			}
		}
		if (getShipHitpoints(shipNormal) == 0) {
			startGame();
		}

		return setzen;
	}

	private void startGame() {
		Game game = new Game();
		int nextPlayer = game.determineBeginner();
		while (!game.isOver()) {
			if (nextPlayer == 1) {
				game.shoot();
				nextPlayer = 2;
			} else {
				game.waitForEnemy();
				nextPlayer = 1;
			}
		}
	}

	public boolean isInField(int x, int y) {
		if (x >= 0 && x < fieldArray.length) {
			if (y >= 0 && y < fieldArray[0].length) {
				return true;
			}
		}

		return false;
	}

	public boolean isFieldValidate(int x, int y) {
		if (isInField(x, y) && !fieldArray[x][y]) {
			if (isInField(x - 1, y)) {
				if (fieldArray[x - 1][y]) {
					return false;
				}
			}
			if (isInField(x, y - 1)) {
				if (fieldArray[x][y - 1]) {
					return false;
				}
			}
			if (isInField(x + 1, y)) {
				if (fieldArray[x + 1][y]) {
					return false;
				}
			}
			if (isInField(x, y + 1)) {
				if (fieldArray[x][y + 1]) {
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}

	public boolean isShipSizeValidate(int x, int y, int size) {
		if (shipNormal[size] == 0) {
			return false;
		}
		if (isFieldValidate(x, y)) {
			// 1 da eigenes nicht mit gepr�ft werden muss
			int possibleFields = 1;

			// X-achse links
			for (int i = 1; i < size && isFieldValidate(x - i, y); i++) {
				possibleFields++;
			}

			// X-achse rechts
			for (int i = 1; i < size && isFieldValidate(x + i, y); i++) {
				possibleFields++;
			}

			if (possibleFields >= size) {
				shipNormal[size]--;
				return true;
			} else {
				// 1 da eigenes nicht mit gepr�ft werden muss
				possibleFields = 1;

				// Y-achse links
				for (int i = 1; i < size && isFieldValidate(x, y - i); i++) {
					possibleFields++;
				}

				// Y-achse rechts
				for (int i = 1; i < size && isFieldValidate(x, y + i); i++) {
					possibleFields++;
				}

				if (possibleFields >= size) {
					shipNormal[size]--;
					return true;
				}
			}
		}
		return false;
	}

	// private int getNextShipSize() {
	// if(availableSchlachtschiffe > 0) {
	// availableSchlachtschiffe--;
	// return 5;
	// } else if(availablekreuzer > 0) {
	// availablekreuzer--;
	// return 4;
	// } else if(availableZerstoerer > 0) {
	// availableZerstoerer--;
	// return 3;
	// } else if(availableUBoots > 0) {
	// availableUBoots--;
	// return 2;
	// }
	//
	// return 0;

	public void setChosenSettings(int choice) {
		chosenSettings = choice;
	}

	public void setPlayerScore(int score) {
		leftGridScore.setText("Dein Score: " + score);
	}

	public void setOpponentScore(int score) {
		rightGridScore.setText("Gegner Score: " + score);
	}

	public void setShipNumber(int number) {
		ship_number = new String[number];
		for (int i = 0; i < number; i++)
			ship_number[i] = "" + (i + 1);
	}

	public void removeRulesElements() {
		ruleFrame.getContentPane().removeAll();
		ruleFrame.getContentPane().revalidate();
	}

	public void showRulesElements() {
		ruleFrame.getContentPane().repaint();
	}

	public void removeRulesFrame() {
		ruleFrame.dispose();
		ruleFrame = null;
	}

	public void removeGameFrame() {
		gameFrame.dispose();
		gameFrame = null;
	}

	public void clearField() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				fieldArray[x][y] = false;
			}
		}
		shipNormal[2] = 4;
		shipNormal[3] = 3;
		shipNormal[4] = 2;
		shipNormal[5] = 1;

		x = 0;
		y = 0;
	}

	public JButton getOpt1() {
		return opt1;
	}

	public JButton getOpt2() {
		return opt2;
	}

	public JButton getOpt3() {

		return opt3;
	}

	public JButton getOpt4() {
		return opt4;
	}

	public JButton getOpt5() {
		return opt5;
	}

	public JButton getReset() {
		return reset;
	}

	public JButton[] getLeftGridFieldArray() {
		return leftGridFieldArray;
	}

	public JButton[] getRightGridFieldArray() {
		return rightGridFieldArray;
	}

	// public JButton getShip_placement_ok() {
	// return ship_placement_ok;
	// }

}

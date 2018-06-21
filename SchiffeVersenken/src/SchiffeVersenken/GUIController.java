package SchiffeVersenken;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUIController extends MouseAdapter implements ActionListener, MouseListener {

	private GUI gui = null;
	private JButton opt1 = null;
	private JButton opt2 = null;
	private JButton opt3 = null;
	private JButton opt4 = null;
	private JButton opt5 = null;
	private JButton ship_placement_ok = null;
	private JButton reset = null;
	private JButton[] leftGridFieldArray = null;
	private JPanel[] rightGridFieldArray = null;

	public GUIController(GUI gui) {
		this.gui = gui;
		prepare();
	}

	public void prepare() {

		opt1 = gui.getOpt1();
		opt1.addActionListener(this);

		opt2 = gui.getOpt2();
		opt2.addActionListener(this);

		opt3 = gui.getOpt3();
		opt3.addActionListener(this);

		opt4 = gui.getOpt4();
		opt4.addActionListener(this);

		opt5 = gui.getOpt5();
		opt5.addActionListener(this);

		reset = gui.getReset();
		reset.addActionListener(this);

		// ship_placement_ok = gui.getShip_placement_ok();
		// ship_placement_ok.addActionListener(this);

		leftGridFieldArray = gui.getLeftGridFieldArray();
		for (int i = 0; i < leftGridFieldArray.length; i++)
			leftGridFieldArray[i].addMouseListener(this);

		rightGridFieldArray = gui.getRightGridFieldArray();
		for (int i = 0; i < leftGridFieldArray.length; i++)
			rightGridFieldArray[i].addMouseListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == opt1) {

			gui.removeRulesElements();
			gui.openSecondRulesFrame();

		} else if (e.getSource() == opt2) {

			gui.removeRulesElements();
			gui.openSecondRulesFrame();

		} else if (e.getSource() == opt3) {

			gui.setChosenSettings(1);
			gui.removeRulesFrame();
			gui.openGameFrame();

		} else if (e.getSource() == opt4) {

			gui.setChosenSettings(2);
			gui.removeRulesFrame();
			gui.openGameFrame();

		} else if (e.getSource() == opt5) {

			gui.setChosenSettings(3);
			gui.removeRulesFrame();
			gui.openGameFrame();

		} else if (e.getSource() == ship_placement_ok) {
			for (int i = 0; i < leftGridFieldArray.length; i++) {
				// if (e.()==leftGridFieldArray[i])
				// gui.placeShip(i);
			}
			// gui.placeShip();

		} else if (e.getSource() == reset) {
			gui.clearField();
			gui.removeGameFrame();
			gui.openGameFrame();
		}

		else {
			System.exit(0);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {

		// gui.changeGrid(0, 2, 0);
		// gui.changeGrid(1, 2, 99);

		for (int i = 0; i < leftGridFieldArray.length; i++) {
			if (e.getSource() == leftGridFieldArray[i]) {
				System.out.println("Linkes Spielfeld, Index: " + i);
				gui.placeShip(i);
				break;
			}
		}

		for (int i = 0; i < rightGridFieldArray.length; i++) {
			if (e.getSource() == rightGridFieldArray[i]) {
				System.out.println("Rechtes Spielfeld, Index: " + i);
				break;
			}
		}

	}

}

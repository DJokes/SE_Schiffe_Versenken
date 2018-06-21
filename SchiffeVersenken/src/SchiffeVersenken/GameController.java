package SchiffeVersenken;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class GameController extends MouseAdapter implements ActionListener, MouseListener {
	private Game game = null;
	private JButton[] leftGridFieldArray = null;
	private JButton[] rightGridFieldArray = null;
	
	public GameController(Game game, JButton[] leftGridFieldArray, JButton[] rightGridFieldArray) {
		this.game = game;
		this.leftGridFieldArray = leftGridFieldArray;
		this.rightGridFieldArray = rightGridFieldArray;
		prepare();
	}
	
	public void prepare() {
		for (int i = 0; i < leftGridFieldArray.length; i++)
			leftGridFieldArray[i].addMouseListener(this);
		
		for (int i = 0; i < leftGridFieldArray.length; i++)
			rightGridFieldArray[i].addMouseListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
	
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < rightGridFieldArray.length; i++) {
			if (e.getSource() == rightGridFieldArray[i]) {
				System.out.println("Rechtes Spielfeld, Index: " + i);
				game.shoot(i);
				break;
			}
		}
	}

}

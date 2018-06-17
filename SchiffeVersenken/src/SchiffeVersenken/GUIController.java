package SchiffeVersenken;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GUIController implements ActionListener {
	
	private GUI gui = null;
	private JButton opt1 = null;
	private JButton opt2 = null;
	private JButton opt3 = null;
	private JButton opt4 = null;
	private JButton opt5 = null;
	
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == opt1) {
			
			 gui.removeRulesElements();
			 gui.openSecondRulesFrame();
			
		} else if(e.getSource() == opt2) {
		
			gui.removeRulesElements();
			gui.openSecondRulesFrame();
		
		} else if(e.getSource() == opt3) {
		
			gui.removeRulesFrame();
			gui.openGameFrame();
		
		} else if(e.getSource() == opt4) {
		
			gui.removeRulesFrame();
			gui.openGameFrame();
		
		} else if(e.getSource() == opt5) {
		
			gui.removeRulesFrame();
			gui.openGameFrame();
		
		} else {
			System.exit(0);
		}
	}

}

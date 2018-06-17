package SchiffeVersenken;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

	public GUI(Spiel game) {
		this.game = game;
	}
	
	public void openFirstRulesFrame() {
		
		ruleFrame = new JFrame("Einstellungen");
	    ruleFrame.setLayout(new GridLayout(3,1));
	    
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
	    
	}
	
	public void openSecondRulesFrame() {
		
		ruleFrame.setLayout(new GridLayout(4,1));
		
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
		leftGridHeadline.setSize(250,50);
		leftGridHeadline.setLocation(50, 50);
        leftGridHeadline.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(leftGridHeadline);
		
		JPanel leftGridField = new JPanel();
		leftGridField.setSize(250,250);
		leftGridField.setLocation(50, 125);
        leftGridField.setLayout(new GridLayout(10,10));
        gameFrame.add(leftGridField);
        
        JPanel[] leftGridFieldArray = new JPanel[100];
        for(int i = 0; i < leftGridFieldArray.length; i++) {
        	leftGridFieldArray[i] = new JPanel();
        	leftGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white)); 
        	leftGridFieldArray[i].setBackground(Color.blue);
        	leftGridField.add(leftGridFieldArray[i]);
        }
        
        JLabel leftGridScore = new JLabel("Dein Score: XXX", SwingConstants.CENTER);
        leftGridScore.setSize(250,50);
        leftGridScore.setLocation(50, 400);
        leftGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(leftGridScore);
        
        JPanel seperator_up = new JPanel();
        seperator_up.setSize(10, 150);
        seperator_up.setLocation((1000/2)-5, 0);
        seperator_up.setBackground(Color.black);
        gameFrame.add(seperator_up);
        
        JLabel ship_placement = new JLabel("Schiffe setzen:", SwingConstants.CENTER);
        ship_placement.setSize(200,50);
        ship_placement.setLocation((1000/2)-100, 180);
        ship_placement.setFont(new Font("Serif", Font.PLAIN, 20));
        gameFrame.add(ship_placement);
        
        String ship_sizes[] = {"1", "2", "3", "4", "5"};
        JComboBox ship_combobox = new JComboBox(ship_sizes);
        ship_combobox.setSize(100, 50);
        ship_combobox.setLocation((1000/2)-50, 215);
        gameFrame.add(ship_combobox);
        
        JButton ship_placement_ok = new JButton("Fertig");
        ship_placement_ok.setSize(100,35);
        ship_placement_ok.setLocation((1000/2)-50, 260);
        gameFrame.add(ship_placement_ok);
        
        JPanel seperator_down = new JPanel();
        seperator_down.setSize(10, 150);
        seperator_down.setLocation((1000/2)-5, 330);
        seperator_down.setBackground(Color.black);
        gameFrame.add(seperator_down);
        
        JLabel rightGridHeadline = new JLabel("Gegner Spielfeld:", SwingConstants.CENTER);
        rightGridHeadline.setSize(250,50);
        rightGridHeadline.setLocation(1000-50-250, 50);
        rightGridHeadline.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(rightGridHeadline);
		
		JPanel rightGridField = new JPanel();
		rightGridField.setSize(250,250);
		rightGridField.setLocation(1000-50-250, 125);
		rightGridField.setLayout(new GridLayout(10,10));
        gameFrame.add(rightGridField);
        
        JPanel[] rightGridFieldArray = new JPanel[100];
        for(int i = 0; i < rightGridFieldArray.length; i++) {
        	rightGridFieldArray[i] = new JPanel();
        	rightGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white)); 
        	rightGridFieldArray[i].setBackground(Color.blue);
        	rightGridField.add(rightGridFieldArray[i]);
        }
        
        JLabel rightGridScore = new JLabel("Gegner Score: XXX", SwingConstants.CENTER);
        rightGridScore.setSize(250,50);
        rightGridScore.setLocation(1000-50-250, 400);
        rightGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(rightGridScore);       
	    
		gameFrame.setSize(GAME_WIDTH, GAME_HEIGHT);
	    gameFrame.setResizable(false);
	    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gameFrame.setLocationRelativeTo(null);
	    gameFrame.setVisible(true);
	    
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
	

}

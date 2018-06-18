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
	private JButton ship_placement_ok = new JButton("Setzen");
	private JLabel leftGridScore = new JLabel("", SwingConstants.CENTER);
	private JLabel rightGridScore = new JLabel("", SwingConstants.CENTER);
	private JComboBox ship_combobox = null;
	private String ship_number[] = null;
	private JPanel[] leftGridFieldArray = new JPanel[100];		
	private JPanel[] rightGridFieldArray = new JPanel[100];
	private JLabel ship_placement = new JLabel("Schiffe setzen:", SwingConstants.CENTER);
	private JPanel seperator_down = new JPanel();
	private JPanel seperator_up = new JPanel();
	private JPanel seperator = new JPanel();
	

	// Danke, Maze :D
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
	    
	    for(int i = 0; i < leftGridFieldArray.length; i++) {
	    	leftGridFieldArray[i] = new JPanel();
	    	rightGridFieldArray[i] = new JPanel();
	    }
	    
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
        
        for(int i = 0; i < leftGridFieldArray.length; i++) {
        	leftGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white)); 
        	leftGridFieldArray[i].setBackground(Color.blue);
        	leftGridField.add(leftGridFieldArray[i]);
        }
        
        leftGridScore.setSize(250,50);
        leftGridScore.setLocation(50, 400);
        leftGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(leftGridScore);
        
        seperator.setSize(10, 500);
        seperator.setLocation((1000/2)-5, 0);
        seperator.setBackground(Color.black);
        seperator.setVisible(false);
        gameFrame.add(seperator);
        
        seperator_up.setSize(10, 150);
        seperator_up.setLocation((1000/2)-5, 0);
        seperator_up.setBackground(Color.black);
        gameFrame.add(seperator_up);
        
        ship_placement.setSize(200,50);
        ship_placement.setLocation((1000/2)-100, 180);
        ship_placement.setFont(new Font("Serif", Font.PLAIN, 20));
        gameFrame.add(ship_placement);
        
        setShipNumber(5);
        ship_combobox = new JComboBox(ship_number);
        ship_combobox.setSize(100, 50);
        ship_combobox.setLocation((1000/2)-50, 215);
        gameFrame.add(ship_combobox);
        
        ship_placement_ok.setSize(100,35);
        ship_placement_ok.setLocation((1000/2)-50, 260);
        gameFrame.add(ship_placement_ok);
        
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
        
        for(int i = 0; i < rightGridFieldArray.length; i++) {
        	rightGridFieldArray[i].setBorder(BorderFactory.createLineBorder(Color.white));
        	rightGridFieldArray[i].setBackground(Color.blue);
        	rightGridField.add(rightGridFieldArray[i]);
        }
        
        rightGridScore.setSize(250,50);
        rightGridScore.setLocation(1000-50-250, 400);
        rightGridScore.setFont(new Font("Serif", Font.PLAIN, 24));
        gameFrame.add(rightGridScore);
        
        // Init left field
        changeGrid(0, 0, 22);
        changeGrid(0, 0, 23);
        changeGrid(0, 0, 24);
        
        changeGrid(0, 2, 33);
        changeGrid(0, 2, 42);
        changeGrid(0, 2, 55);
        
        changeGrid(0, 3, 24);
        
        changeGrid(0, 4, 88);
        changeGrid(0, 4, 89);
        
        setPlayerScore(100);
        
        // Init right field
        changeGrid(1, 0, 22);
        changeGrid(1, 0, 23);
        changeGrid(1, 0, 24);
        
        changeGrid(1, 2, 33);
        changeGrid(1, 2, 42);
        changeGrid(1, 2, 55);
        
        changeGrid(1, 3, 24);
        
        changeGrid(1, 4, 88);
        changeGrid(1, 4, 89);
        
        setOpponentScore(0);
	    
		gameFrame.setSize(GAME_WIDTH, GAME_HEIGHT);
	    gameFrame.setResizable(false);
	    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    gameFrame.setLocationRelativeTo(null);
	    gameFrame.setVisible(true);
	    
	}
	
	// First param: leftGrid(0) rightGrid(1)
	// Second param: ship(0) water(1) shotWater(2) shotShip(3) shipKilled(4)
	// Third param: index of grid
	public void changeGrid(int grid, int art, int index) {
		
		if(grid == 0) {
			
			switch(art) {
			case 0: leftGridFieldArray[index].setBackground(Color.black);
				break;
			case 1: leftGridFieldArray[index].setBackground(Color.blue);
				break;
			case 2: leftGridFieldArray[index].setBackground(Color.green);
				break;
			case 3: leftGridFieldArray[index].setBackground(Color.yellow);
				break;
			case 4: leftGridFieldArray[index].setBackground(Color.red);
				break;
			default: 
				System.exit(0);
			}
			
		} else if(grid == 1) {
			
			switch(art) {
			case 0: rightGridFieldArray[index].setBackground(Color.black);
				break;
			case 1: rightGridFieldArray[index].setBackground(Color.blue);
				break;
			case 2: rightGridFieldArray[index].setBackground(Color.green);
				break;
			case 3: rightGridFieldArray[index].setBackground(Color.yellow);
				break;
			case 4: rightGridFieldArray[index].setBackground(Color.red);
				break;
			default: 
				System.exit(0);
			}
			
		} else {
			System.exit(0);
		}
		
	}
	
	public void placeShip() {
		
		int ship_size = Integer.parseInt((String)ship_combobox.getSelectedItem());
		
		JFrame popup = new JFrame("Vorbereitungen");
		popup.setLayout(new GridLayout(ship_size+2,1));
	    
	    JLabel headline = new JLabel("Schiff ("+ship_size+") setzen:", SwingConstants.CENTER);
	    headline.setFont(new Font("Serif", Font.PLAIN, 20));
	    popup.add(headline);
	    
	    JTextField[] textfields = new JTextField[ship_size];
	    for(int i = 0; i < ship_size; i++) {
	    	textfields[i] = new JTextField();
	    	popup.add(textfields[i]);
	    }
	    
	    JButton close = new JButton("Fertig");
	    close.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
				  
	    		  for(int i = 0; i < ship_size; i++) {
	    			  try {
	    				  int index = Integer.parseInt(textfields[i].getText());
	    				  
	    				  if(index <= 99 && index >= 0) {
	    					  changeGrid(0, 0, index);
	    				  }
	    				  
	    				  if(i == (ship_size-1)) {
	    					  ship_combobox.removeItem(""+ship_size);
	    					  if(ship_combobox.getItemCount() <= 0) {
	    						  
	    			    		  seperator_down.setVisible(false);
	    			    		  seperator_up.setVisible(false);
	    			    		  ship_combobox.setVisible(false);
	    			    		  ship_placement_ok.setVisible(false);
	    			    		  ship_placement.setVisible(false);
	    						  
	    						  seperator.setVisible(true);
	    						  
	    					  }
	    				  }

	    			  } catch(Exception e1) {
	    				  popup.dispose();
	    			  }
	    		  }
	    		  popup.dispose();
	    		  
	    	  } 
	    });
	    popup.add(close);
	    	   
	    popup.setUndecorated(true);
	    popup.setSize(RULES_WIDTH, RULES_HEIGHT);
	    popup.setResizable(false);
	    popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    popup.setLocationRelativeTo(null);
	    popup.setVisible(true);        
		
	}
	
	public void setPlayerScore(int score) {
		leftGridScore.setText("Dein Score: " + score);
	}
	
	public void setOpponentScore(int score) {
		rightGridScore.setText("Gegner Score: " + score);
	}
	
	public void setShipNumber(int number) {
		ship_number = new String[number];
		for(int i = 0; i < number; i++)
			ship_number[i] = "" + (i+1);
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
	
	public JPanel[] getLeftGridFieldArray() {
		return leftGridFieldArray;
	}
	
	public JPanel[] getRightGridFieldArray() {
		return rightGridFieldArray;
	}
	
	public JButton getShip_placement_ok() {
		return ship_placement_ok;
	}

}

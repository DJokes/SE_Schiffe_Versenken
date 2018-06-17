package SchiffeVersenken;

public class Spiel {
	
	private static GUI gui = null;
	private static GUIController controller = null;
	
    public static void main(String[] args) {
    	
    	// create bidirectional association
    	Spiel spiel = new Spiel();
        gui = new GUI(spiel);
        
        // call gui to display frame
        gui.openFirstRulesFrame();
        
        // call GUIController to register Listeners
        controller = new GUIController(gui);
    
    }

}

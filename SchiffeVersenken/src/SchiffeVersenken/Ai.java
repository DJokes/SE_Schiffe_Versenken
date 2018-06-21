package SchiffeVersenken;

public class Ai {
	
	private Spielfeld aiField;
	private Spielfeld enemyField;
	private Position position = new Position(0, 0);
	
	public Ai(Spielfeld aiField, Spielfeld enemyField){
		this.aiField = aiField;
		this.enemyField = enemyField;
		generateKoordinates(0, 9);
	}
	
	public void setShips() {
		//Shipset Regular 
		if (aiField.getShipType() == 0) {
			int ship = 1;
			while(ship <= 5){
				getStartPoint(ship);
				//0 = rigth 
				//1 = left 
				//2 = up
				//3 = down
				int direction = aiField.possibleDirection(new Position(position.getVertical(), position.getHorizontal()), ship);
				aiField.setShip(ship, new Position(position.getVertical(), position.getHorizontal()), direction);
				generateKoordinates(0, 9);
				ship++;
			}
		}
		// Shipset special
		else{
			
		}
	}
	
	private void getStartPoint(int ship){
		while(!aiField.possibleSet(new Position(position.getVertical(), position.getHorizontal()), ship)){
			generateKoordinates(0, 9);
		}
	}
	
	public void shoot(){
		while(enemyField.shootOfAi(position.getVertical(), position.getHorizontal())){
			generateKoordinates(0, 9);
		}
	}
	
	public void generateKoordinates(int minimumRange, int maximumRange){
		int x = (int) (Math.random() * maximumRange) + minimumRange;
		int y = (int) (Math.random() * maximumRange) + minimumRange;
		position = new Position(x, y);
	}

	public static void main(String[] args) {
		Spielfeld aiField = new Spielfeld(0, 2);
		Spielfeld enemyField = new Spielfeld(0, 2);
		enemyField.setShip(5, new Position(4,4), 0);
		Ai test = new Ai(aiField, enemyField);
		test.setShips();
		aiField.printField();
		test.shoot();
		enemyField.printField();;

	}

}

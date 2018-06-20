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
		if (aiField.getShipType() == 0) {
			int ship = 1;
			while(ship <= 5){
				getStartPoint(ship);
				int direction = aiField.possibleDirection(new Position(position.getX(), position.getY()), ship);
				aiField.setShip(ship, new Position(position.getX(), position.getY()), direction);
				generateKoordinates(0, 9);
				ship++;
			}
		}
	}
	
	private void getStartPoint(int ship){
		while(!aiField.possibleSet(new Position(position.getX(), position.getY()), ship)){
			generateKoordinates(0, 9);
		}
	}
	
	public void shoot(){
		
	}
	
	public void generateKoordinates(int minimumRange, int maximumRange){
		int x = (int) (Math.random() * 8) + 1;
		int y = (int) (Math.random() * 8) + 1;
		position = new Position(x, y);
	}

	public static void main(String[] args) {
		Spielfeld aiField = new Spielfeld(0, 2);
		Spielfeld enemyField = new Spielfeld(0, 2);
		Ai test = new Ai(aiField, enemyField);
		test.setShips();
		aiField.printField();

	}

}

package SchiffeVersenken;

public class Ai {
	
	private Spielfeld field;
	private MemoryField history;
	private Position lastShot;
	
	public Ai(Spielfeld aiField){
		this.field = aiField;
		this.history = new MemoryField();
		this.lastShot = new Position();
	}
	
	private Spielfeld getField(){
		return field;
	}
	
	private MemoryField getHistory(){
		return history;
	}
	
	public void setShips() {
		// int[] ships = aiField.getShips();
		int[] ships = new int[6];
		for (int i = 2; i < ships.length; i++) {
			ships[i] = 1;
		}
		int direction = 1;
		Position position = new Position(0,0);
		position.random(1, 8);
		for (int ship = ships.length - 1; ship >= 2; ship--) {
			for (int j = 0; j < ships[ship]; j++) {
				position = getStartPoint(ship);
				boolean shipIsNotSet = true;
				while (shipIsNotSet) {
					// Try setting the ship to the position
					if (field.setShip(ship, new Position(position.getVertical(), position.getHorizontal()),direction)) {
						shipIsNotSet = false;
					}
					// Invert direction
					else if (direction > 0) {
						direction = direction * -1;
					}
					// Change direction
					else {
						direction = changeDirection(direction);
					}
				}
				direction = changeDirection(direction);
				position.random(1, 8);
			}
		}
	}
	
	private static int changeDirection(int direction){
		if(direction == 1 || direction == -1){
			return 2;
		}
		else if(direction == 2 || direction == -2){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	private Position getStartPoint(int ship){
		Position candidate = new Position().random();
		while(!this.field.possibleSet(candidate, ship)){
			candidate = generateKoordinates(1, 8);
		}
		return candidate;
	}
	
	
	public Position takeTurn(){//random shots trying not to repeat
		Position candidate = new Position();
		int limit = 5;//5 turns tops to shoot
		int counter = 0;
		while(counter < limit) {
			candidate = new Position().random();
			if(this.field.checkBounds(candidate)){//in bounds
				if(!this.history.wasHit(candidate)){//not a repeat
					return candidate;
				}
			}
			counter++;
		}		
		return candidate; //TODO alternative when cant find unrepeated shots after limit tries
	}
	
	public void turnResult(int result){//just updating shot Tiles, no prediction
		this.history.hit(this.lastShot);
	}
	
	public Position generateKoordinates(int minimumRange, int maximumRange){
		int x = (int) (Math.random() * maximumRange) + minimumRange;
		int y = (int) (Math.random() * maximumRange) + minimumRange;
		return new Position(x, y);
	}

	public static void main(String[] args) {
		Ai test = new Ai(new Spielfeld(0, 2));
		test.setShips();
		System.out.println("Your Field");
		test.getField().printField();
		System.out.println("");
//		System.out.println("Your History");
//		System.out.println(test.getHistory());
//		System.out.println("");
//		test.takeTurn();
//		System.out.println("Your History after shot");
//		System.out.println(test.getHistory());
//		System.out.println("");

	}

}

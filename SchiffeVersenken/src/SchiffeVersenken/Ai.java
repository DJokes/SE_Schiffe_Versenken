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
	
	public void setShips() {
		//Shipset Regular 
		if (this.field.getShipType() == 0) {
			int ship = 5;
			while(ship >= 0){
				Position startPoint = getStartPoint(ship);
				//1 = right //deprecated, ignore it
				//-1 = left 
				//-2 = up
				//2 = down
				//0 error
				int direction = this.field.possibleDirection(startPoint, ship);
				this.field.setShip(ship, startPoint, direction);
				ship--;
			}
		}
		// Shipset special
		else{
			
			
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

	public void main(String[] args) {
		Ai test = new Ai(new Spielfeld(0, 2));
		test.setShips();
		System.out.println("Your Field");
		field.printField();
		System.out.println("");
		System.out.println("Your History");
		System.out.println(history);
		System.out.println("");
		test.takeTurn();
		System.out.println("Your History after shot");
		System.out.println(history);
		System.out.println("");

	}

}

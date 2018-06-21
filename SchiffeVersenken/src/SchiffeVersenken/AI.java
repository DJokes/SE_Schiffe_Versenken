package SchiffeVersenken;

public class AI {
	
	private Spielfeld field;
	private MemoryField history;
	private Position walker;
	private Position lastShot;
	
	public AI(){
		super();
		this.field = new Spielfeld();//default is 0, 0
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
	}
	
	public AI(int shipType, int positioning){
		super();
		this.field = new Spielfeld(shipType, positioning);
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
	}
	
	public AI(Spielfeld aiField){
		this.field = aiField;
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
	}
	
	private Spielfeld getField(){
		return this.field;
	}
	
	private MemoryField getHistory(){
		return this.history;
	}
	
	private void setLastShot(Position shot) {
		this.lastShot = shot;
	}
	
	public void setShips() {
		// int[] ships = aiField.getShips();
		int[] ships = new int[6];
		for (int i = 2; i < ships.length; i++) {
			ships[i] = 1;
		}
		int direction = 1;
		Position position = new Position().random(7);//0 to 7 on x and y
		position = position.add(1);//1 to 8 on x and y
		for (int ship = ships.length - 1; ship >= 2; ship--) {
			for (int j = 0; j < ships[ship]; j++) {
				position = getStartPoint(ship);
				boolean shipIsNotSet = true;
				int tries = 0;
				while (shipIsNotSet) {
					// Try setting the ship to the position
					if (field.setShip(ship, position,direction)) {
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
					if(tries == 4){
						position = getStartPoint(ship);
						tries = 0;
					}
					tries++;
				}
				direction = changeDirection(direction);
				position = new Position().random(7);//0 to 7 on x and y
				position = position.add(1);//1 to 8 on x and y
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
			candidate = new Position().random(7);//0 to 7 on x and y
			candidate = candidate.add(1);//1 to 8 on x and y
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
					beforeShot(candidate);
					return candidate;
				}
			}
			counter++;
		}
		candidate = this.walker;//if cant find new position to shoot in 5 turns, use the walker
		beforeShot(candidate);
		return candidate;
	}
	
	private void beforeShot(Position candidate) {//updates walker and lastShot
		setLastShot(candidate);
		if(this.walker.equals(candidate)) {
			walk();//move walker to next unshot position
		}
	}
	
	private boolean walk() {//move walker to next unshot position
		boolean stop = false;
		
		while(!stop) {
			this.walker = this.walker.right();
			if(this.field.checkBounds(walker)) {//check if in bounds
				stop = this.history.wasHit(this.walker);//check if already shot
			} else {//out of bounds
				this.walker = this.walker.cycle();//new line
				if(this.field.checkBounds(walker)) {//check if in bounds
					stop = this.history.wasHit(this.walker);//check if already shot
				} else {//out of bounds, we walked whole board
					return false;					
				}
			}
		}		
		
		return true;
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
		AI test = new AI(new Spielfeld(0, 2));
		test.setShips();
		System.out.println("Your Field");
		test.getField().printField();
		System.out.println("");
		System.out.println("Your History");
		System.out.println(test.getHistory());
		System.out.println("");
		for(int i = 0; i < 30; i++) {
			test.takeTurn();
			test.turnResult(0);
		}		
		System.out.println("Your History after 30 shots");
		System.out.print("" + test.getHistory().totalHits() + " hits\n");
		System.out.println(test.getHistory());
		System.out.println("");

	}

}

package SchiffeVersenken;

public class AI {
	/**
	 * Random shots for Beruhren
	 * Probing shots for Wasser and Ecken
	 * 		BUT ship-kill must be 2 (int) and be properly returned or you will keep shooting shot positions as a failsafe
	 * 
	 * expected sequence for shots
	 * 
	 * AI cpu = new AI(shipType, positioning);
	 * Position shoot = cpu.takeTurn();
	 * int result = player.beShot(shoot);//0 for water, 1 for ship, 2 for ship-kill
	 * cpu.turnResult(result);
	 * 
	 * if you want to use random shots always, uncomment endProbing() in the beginning of takeShot() 
	 * 
	 **/
	
	
	private Spielfeld field;
	private MemoryField history;
	private Position walker;
	private Position lastShot;
	private Probe probe;
	private int[] ships;
	
	public AI(){
		this(0, 0);
		ships = new int[]{0, 0, 4, 3, 2, 1};
	}
	
	public AI(int shipType, int positioning){
		super();
		this.field = new Spielfeld(shipType, positioning);
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
		this.probe = null;
	}
	
	public AI(Spielfeld aiField){
		this.field = aiField;
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
		this.probe = null;
		this.ships = new int[]{0, 0, 4, 3, 2, 1};
	}
	
	public AI(int shipType, int positioning, int[]ships){
		this.field = new Spielfeld(shipType, positioning);
		this.history = new MemoryField();
		this.walker = new Position();
		this.lastShot = new Position();
		this.probe = null;
		this.ships = ships;
	}
	
	public Spielfeld getField(){
		return this.field;
	}
	
	private MemoryField getHistory(){
		return this.history;
	}
	
	private void setLastShot(Position shot) {
		this.lastShot = shot;
	}
	
	public Position getLastShot(){
		return lastShot;
	}
	
	public void setShips() {
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
	
	
	public Position takeTurn(){//logic: random for beruhren and probe otherwise
		//endProbing();//always random logic TODO, just a marker
		
		
		Position candidate;
		switch(this.field.getPositioning()) {
		case 2://beruhren
			candidate = randomShot();
			break;
		default://wasser or ecken
			if(isProbing()) {
				candidate = probeShot();
			}else {
				candidate = randomShot();
			}
		}
		beforeShot(candidate);
		return candidate;
	}
	
	private Position randomShot() {
		Position candidate = new Position();
		int limit = 5;//5 turns tops to shoot
		int counter = 0;
		while(counter < limit) {
			candidate = new Position().random();
			if(this.field.checkBounds(candidate)){//in bounds
				if(!this.history.wasHit(candidate)){//not a repeat
					//System.out.println("Random shot: " + candidate);
					return candidate;
				}
			}
			counter++;
		}
		candidate = this.walker;//if cant find new position to shoot in 5 turns, use the walker
		//System.out.println("Random shot: " + candidate);
		return candidate;
	}
	
	private Position probeShot() {
		Position candidate = this.probe.probe();
		while(candidate!=null && !this.field.checkBounds(candidate)) {//out of bounds
			this.probe.checkResult(0, candidate);//say it hit water to stop probe in that direction
			candidate = this.probe.probe();//try again
		}
		//System.out.println("Probe shot: " + candidate);
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
	
	private boolean isProbing() {//true if AI is probing ship
		return (this.probe!=null);
	}
	
	private void endProbing() {
		this.probe = null;
	}
	
	public void turnResult(int result){//just updating shot Tiles, no prediction
		this.history.hit(this.lastShot);
		switch(result) {
		case 0://water
			if(isProbing()) {
				this.probe.checkResult(result, this.lastShot);
				//prediction changes
			}
			break;
		case 1://ship
			if(isProbing()) {
				this.probe.checkResult(result, this.lastShot);
				//prediction changes
				
			} else {//start probe
				this.probe = new Probe(this.lastShot);
			}
			break;
		case 2://ship-kill
			//prediction changes
			endProbing();
		default://weird stuff
		}
	}
	
	public Position generateKoordinates(int minimumRange, int maximumRange){
		int x = (int) (Math.random() * maximumRange) + minimumRange;
		int y = (int) (Math.random() * maximumRange) + minimumRange;
		return new Position(x, y);
	}

	public static void main(String[] args) {
//		AI test = new AI();
		AI test = new AI(new Spielfeld(0, 2));
		test.setShips();
		System.out.println("Your Field");
		test.getField().printField();
//		System.out.println("");
//		for(int i = 0; i < 30; i++) {
//			test.takeTurn();
//			
//			switch(i) {
//			case 1:
//				test.turnResult(0);//water
//				break;
//			case 5:
//				test.turnResult(2);//ship_kill
//				break;
//			default:
//				test.turnResult(1);//ship	
//			}			
//		}		
//		System.out.println("Your History after 30 shots");
//		System.out.print("" + test.getHistory().totalHits() + " hits\n");
//		System.out.println(test.getHistory());
//		System.out.println("");

	}

}

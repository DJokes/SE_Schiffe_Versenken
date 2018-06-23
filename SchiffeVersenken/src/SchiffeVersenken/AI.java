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
	
	public AI(int shipType, ShipBorderConditions positioning){
		//this is to help translate the special team's rules to ours
		super();
		switch(positioning) {
		case TOUCHING_ALLOWED://beruhren
			this.field = new Spielfeld(shipType, 2);
			break;
		case NO_DIRECT_TOUCH://ecken
			this.field = new Spielfeld(shipType, 1);
			break;
		case NO_DIRECT_AND_DIAGONAL_TOUCH://wasser
			this.field = new Spielfeld(shipType, 0);
			break;
		}
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
		//position = position.add(1);//1 to 8 on x and y
		for (int ship = ships.length - 1; ship >= 2; ship--) {
			for (int j = 0; j < ships[ship]; j++) {
				//position = getStartPoint(ship);
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
						position = new Position().random(9);//0 to 7 on x and y
						//position = position.add(1);//1 to 8 on x and y
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
	
//	private Position getStartPoint(int ship){
//		Position candidate = new Position().random();
//		while(!this.field.possibleSet(candidate, ship)){
//			candidate = new Position().random(7);//0 to 7 on x and y
//			candidate = candidate.add(1);//1 to 8 on x and y
//		}
//		return candidate;
//	}
	
	
	public Position takeTurn(){//logic: random for beruhren and probe otherwise
		//endProbing();//always random logic TODO, just a marker
				
		Position candidate;
		switch(this.field.getPositioning()) {
		case 2://beruhren
			if(isProbing()) {
				candidate = probeShot();
			}else {
				candidate = randomShot();
			}
			break;
		default://wasser or ecken
			if(isProbing()) {
				candidate = probeShot();
			}else {
				candidate = randomShot();
			}
		}
		beforeShot(candidate);
		System.out.println("Shot: " + candidate);
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
		if(this.probe.probingOver()) {
			endProbing();
			return randomShot();
		}
		Position candidate = this.probe.probe();//probe shouldnt return null because of previous check
		while(!this.field.checkBounds(candidate) || this.history.wasHit(candidate)) {
			//out of bounds or already hit
			this.probe.checkResult(0, candidate);//say it hit water to stop probe in that direction
			if(this.probe.probingOver()) {//make sure probe can go on
				endProbing();//it cant, go random
				return randomShot();
			}
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
				
				
				//PREDICTIONS
				if(this.field.getPositioning() == 0) {//wasser
					switch(this.probe.getProbing()-1) {//diagonal from previous is current horizontal/vertical
					case 0://up
						predictHorizontal(this.lastShot);
						break;
					case 1://right
						predictVertical(this.lastShot);
						break;
					case 2://down
						predictHorizontal(this.lastShot);
						break;
					case 3://left
						predictVertical(this.lastShot);
						break;
					default:
					}
				}				
			}
			break;
		case 1://ship
			if(isProbing()) {
				if((this.field.getPositioning() < 2) && (this.probe.getDirection() == null)) {
					//this shot will set direction, predict accordingly
					int dir = this.probe.getProbing();
					if(dir == 0 || dir == 2) {//vertical, so predict horizontal
						predictHorizontal(this.probe.getFirst());
					}else {//horizontal, predict vertical
						predictVertical(this.probe.getFirst());
					}					
				}
				
				this.probe.checkResult(result, this.lastShot);
				

				//PREDICTIONS
				if(this.field.getPositioning() < 2) {//wasser/ecken
					switch(this.probe.getDirection()) {
					case HORIZONTAL:
						predictVertical(this.lastShot);
						break;
					case VERTICAL:
						predictHorizontal(this.lastShot);
						break;
					default:
					}
				}
				
			} else {//start probe
				switch(this.field.getPositioning()) {
				case 2://beruhren gets a special probe
					this.probe = new SurroundingsProbe(this.lastShot);
					break;
				default://wasser and ecken
					this.probe = new Probe(this.lastShot);
				}
			}
			break;
		case 2://ship-kill
			if(isProbing()) {
				this.probe.checkResult(result, this.lastShot);
				
				Position first = this.probe.getFirst();
				Position last = this.probe.getLast();
				switch(this.field.getPositioning()) {
				case 2://beruhren
					this.probe.checkResult(result, this.lastShot);//this probe continues even after ship-kill
					//probeShot itself has the end for this probe
					break;
				case 1://ecken

					//PREDICTIONS				
					predictDirect(first);
					predictDirect(last);
					
					endProbing();
					break;
				default://wasser

					//PREDICTIONS				
					predictDirect(first);
					predictDiagonal(first);
					predictDirect(last);
					predictDiagonal(last);
					
					endProbing();
				}
			}			
		default://weird stuff happened
		}
	}
	
	public Position generateKoordinates(int minimumRange, int maximumRange){
		int x = (int) (Math.random() * maximumRange) + minimumRange;
		int y = (int) (Math.random() * maximumRange) + minimumRange;
		return new Position(x, y);
	}
	
	private void predictHorizontal(Position pos) {//predicts there's nothing left on pos's horizontal neighbours
		Position direct;
		
		direct = pos.left();
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
		direct = pos.right();
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
	}
	
	private void predictVertical(Position pos) {//predicts there's nothing left on pos's vertical neighbours
		Position direct;
		
		direct = pos.up();
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
		direct = pos.down();
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
	}
	
	private void predictDirect(Position pos) {//predicts there's nothing left on pos's direct neighbours
		predictHorizontal(pos);
		predictVertical(pos);
	}
	
	private void predictDiagonal(Position pos) {//predicts there's nothing left on pos's diagonal neighbours
		Position direct;
		
		direct = pos.add(-1, 1);//upper right
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
		direct = pos.add(1, 1);//lower right
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
		direct = pos.add(1, -1);//lower left
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
		direct = pos.add(-1, -1);//upper left
		if(this.field.checkBounds(direct)) {
			//System.out.println("Predicted: "+direct);
			this.history.hit(direct);
		}
	}
	
	
	public enum ShipBorderConditions {//this is from the special team, I'm trying to make things easier for us	
		TOUCHING_ALLOWED,
		NO_DIRECT_TOUCH,
		NO_DIRECT_AND_DIAGONAL_TOUCH		
	}

	public static void main(String[] args) {
		//AI test = new AI();
		AI test = new AI(new Spielfeld(0, 0));
		test.setShips();
		System.out.println("Your Field");
		test.getField().printField();
		//System.out.println("");
//		for(int i = 0; i < 10; i++) {
//			test.takeTurn();
//			if(i < 6) {
//				switch(i) {
//				case 1:
//					System.out.println("MISSED!");
//					test.turnResult(0);//water
//					
//					break;
//				case 5:
//					System.out.println("BLUB!");
//					test.turnResult(2);//ship_kill
//					
//					break;
//				default:
//					System.out.println("HIT!");
//					test.turnResult(1);//ship
//					
//				}
//			} else {
//				System.out.println("MISSED!");
//				test.turnResult(0);//water
//				
//			}
//			
//		}		
//		System.out.println("Your History after 10 shots");
//		System.out.print("" + test.getHistory().totalHits() + " hits\n");		
//		System.out.println(test.getHistory());
//		System.out.println("");

	}

}

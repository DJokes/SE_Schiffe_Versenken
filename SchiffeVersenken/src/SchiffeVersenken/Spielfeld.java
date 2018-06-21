package SchiffeVersenken;

public class Spielfeld {
	
	// 0 = water // 1 to 10 are ships their length // 100 is hit
	private int[][] field = new int[10][10];
	private int maxX = 10;
	private int maxY = 10;
	private int shipType;
	private int positioning;
	private int amountOfShips = 5;
	
	public Spielfeld(){//default rules
		this.shipType = 0;
		this.positioning = 0;
	}
	
	public Spielfeld(int shipType, int positioning){
		this.shipType = shipType;
		this.positioning = positioning;
	}
	
	public Spielfeld(int positioning){//regular ships, different placement rule
		this.shipType = 0;
		this.positioning = positioning;
	}
	
	public int getShipType(){
		return this.shipType;
	}
	
	public int getPositioning(){
		return this.positioning;
	}
	
	public int getTile(Position pos){//please make sure position is in bounds BEFORE calling this method
		return this.field[pos.getY()][pos.getX()];
	}
	
	public int getTile(int x, int y){//please make sure position is in bounds BEFORE calling this method
		return this.field[y][x];
	}
	
	public boolean setTile(Position pos, int value){//will check for bounds
		boolean bounds = checkBounds(pos);
		if(bounds){
			this.field[pos.getY()][pos.getX()] = value; 
		}
		return bounds;
	}
	
	public boolean setTile(int x, int y, int value){//will check for bounds
		boolean bounds = checkBounds(x, y);
		if(bounds){
			this.field[y][x] = value; 
		}
		return bounds;
	}
	
	public boolean checkBounds(Position pos){
		boolean inside = true;
		inside &= (pos.getX() > -1);
		inside &= (pos.getY() > -1);
		inside &= (pos.getX() < this.maxX);
		inside &= (pos.getY() < this.maxY);
		
		return inside;
	}
	
	public boolean checkBounds(int x, int y){
		boolean inside = true;
		inside &= (x > -1);
		inside &= (y > -1);
		inside &= (x < this.maxX);
		inside &= (y < this.maxY);
		
		return inside;
	}
	
	public boolean setShip(int ship, Position startPosition, int direction){//TODO unset partial ships
		if(isNotEnoughSpace(ship, startPosition, direction)){
			return false;
		}
		else{
		boolean set;
		Position current = startPosition;
		for(int i = 0; i < ship; i++){
			if(getTile(current) == 0){
				set = setTile(current, ship);// if set is false, you'll know the placement failed
				if(!set){
					return false;
				}
			} else{
				return false;
			}
			//to the right
			if(direction == 1){
				current = current.right();
			}
			//to the left
			else if(direction == -1){
				current = current.left();
			}
			//up 
			else if(direction == -2){
				current = current.up();
			}
			//down
			else if(direction == 2){
				current = current.down();
			}    
		}
		return true;
		}
	}
	
	private boolean isNotEnoughSpace(int ship, Position pos, int direction){
		Position inside = pos;
		for(int i = 0; i < ship; i++){
			if(!checkBounds(inside)){
				return true;
			}
			else if(getTile(inside) != 0){
				return true;
			}
			if(direction == 1){
				inside = inside.right();
			}
			else if(direction == -1){
				inside = inside.left();
			}
			else if(direction == 2){
				inside = inside.down();
			}
			else if(direction == -2){
				inside = inside.up();
			}
			else{
				return true;	
			}
		}
		return false;
		
	}
	
	public boolean possibleSet(Position pos, int delta){
		if(positioning == 2){
			return touchingPositioning(pos, delta);
		}
		else if(positioning == 1){
			return cornersTouchingPositioning(pos, delta);
		}
		else if(positioning == 0){
			return noTouchingPositioning(pos, delta);
		}
		return false;
	}
	
	private boolean touchingPositioning(Position pos, int delta){
		Position mover = pos;
		//Right 
		boolean rightIsPossible = true;
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && getTile(mover) != 0){
				rightIsPossible = false;
				break;
			}
			else{
				mover = mover.right();
			}
		}
		
		//Left 
		mover = pos;
		boolean leftIsPossible = true;
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && getTile(mover) != 0){
				leftIsPossible = false;
				break;
			}
			else{
				mover = mover.left();
			}
		}
		
		//Up 
		mover = pos;
		boolean upIsPossible = true;
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && getTile(mover) != 0){
				upIsPossible = false;
				break;
			}
			else{
				mover = mover.up();
			}
		}
		
		//Down
		mover = pos;
		boolean downIsPossible = true;
		for(int i = 0; i <= delta; i++){
			if(checkBounds(mover) && getTile(mover) != 0){
				downIsPossible = false;
				break;
			}
			else{
				mover = mover.down();
			}
		}
		if(rightIsPossible || leftIsPossible || upIsPossible || downIsPossible){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean cornersTouchingPositioning(Position pos, int delta){
		//Right 
		Position mover = pos;
		boolean rightIsPossible = (checkBounds(mover.left()) && getTile(mover.left()) == 0);
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && shipAround(mover)){
				rightIsPossible = false;
				break;
			}
			else{
				mover = mover.right();
			}
		}
		
		//Left 
		mover = pos;
		boolean leftIsPossible = (checkBounds(mover.right()) && getTile(mover.right()) == 0);
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && shipAround(mover)){
				leftIsPossible = false;
				break;
			}
			else{
				mover = mover.left();
			}
		}
		
		//Up 
		mover = pos;
		boolean upIsPossible = (checkBounds(mover.down()) && getTile(mover.down()) == 0);
		for(int i = 0; i < delta; i++){
			if(checkBounds(mover) && shipAround(pos)){
				upIsPossible = false;
				break;
			}
			else{
				mover = mover.up();
			}
		}
		
		//Down
		mover = pos;
		boolean downIsPossible = (checkBounds(mover.up()) && getTile(mover.up()) == 0);
		for(int i = 0; i <= delta; i++){
			if(checkBounds(mover) && shipAround(mover)){
				downIsPossible = false;
				break;
			}
			else{
				mover = mover.down();
			}
		}
		if(rightIsPossible || leftIsPossible || upIsPossible || downIsPossible){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean noTouchingPositioning(Position pos, int delta){
		//Right 
		//Set mover to one position before to check that there is no ship near
		Position mover = pos.left();
		boolean rightIsPossible = (checkBounds(mover) && shipAround(mover));
		for(int i = 0; i < delta && rightIsPossible; i++){
			if(checkBounds(mover) && shipAround(mover)){
				rightIsPossible = false;
			}
			else{
				mover = mover.right();
			}
		}
		
		//Left
		mover = pos.right();
		boolean leftIsPossible = (checkBounds(mover) && shipAround(mover));
		for(int i = 0; i < delta && leftIsPossible; i++){
			if(checkBounds(mover) && shipAround(mover)){
				rightIsPossible = false;
			}
			else{
				mover = mover.left();
			}
		}
		
		//Up 
		mover = pos.down();
		boolean upIsPossible = (checkBounds(mover) && shipAround(mover));
		for(int i = 0; i < delta && upIsPossible; i++){
			if(checkBounds(mover) && shipAround(mover)){
				rightIsPossible = false;
			}
			else{
				mover = mover.up();
			}
		}
		
		//Down
		mover = pos.up();
		boolean downIsPossible = (checkBounds(mover) && shipAround(mover));
		for(int i = 0; i < delta && downIsPossible; i++){
			if(checkBounds(mover) && shipAround(mover)){
				rightIsPossible = false;
			}
			else{
				mover = mover.down();
			}
		}
		
		if(rightIsPossible || leftIsPossible || upIsPossible || downIsPossible){
			return true;
		}
		else{
			return false;
		}		
	}
	
	private boolean shipAround(Position pos){
		Position up = pos.up();
		Position down = pos.down();
		boolean upIsFree = (checkBounds(up) && getTile(up) != 0);
		boolean downIsfree = (checkBounds(down) && getTile(down) != 0);
		
		if(!downIsfree || !upIsFree){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean shootOfAi(int x, int y){
		if(checkHit(x, y)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkHit(int vertical, int horizontal){
		if(field[vertical][horizontal] != 0 ){
			if(field[vertical][horizontal] >= 1 && field[vertical][horizontal] <= 10){
				field[vertical][horizontal] = 100;
				return true;
			}
			else if(field[vertical][horizontal] == 100){
				return false;
			}
		}
		else{
			field[vertical][horizontal] = -1;
		}
		return false;
	}
	
	public boolean gewonnen(){
		return amountOfShips == 0;
	}
	
	public boolean shipSunk(){
		return true;
	}
	
	public void printField(){
		for(int i = 0; i < field.length; i++){
			for(int j = 0; j < field.length; j++){
				System.out.print("[" + field[i][j] + "]");
			}
			System.out.println("");
		}
	}
	
//	public static void main(String[] args) {
//		Spielfeld test = new Spielfeld(0, 1);		
//		test.setShip(5, new Position(1, 0), 1);
//		test.printField();
//
//	}

}

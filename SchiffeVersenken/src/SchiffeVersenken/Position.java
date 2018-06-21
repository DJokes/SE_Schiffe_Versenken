package SchiffeVersenken;

public class Position {//doesn't check bounds

	private int x;
	private int y;
	
	public Position(){
		this.x = 0;
		this.y = 0;
	}
	
	public Position(int horizontal, int vertical){
		this.x = horizontal;
		this.y = vertical;
	}
	
	public Position(Position pos){
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getHorizontal(){
		return this.x;
	}
	
	public int getVertical(){
		return this.y;
	}
	
	public Position move(int deltaHorizontal, int deltaVertical){
		return new Position(this.x + deltaHorizontal, this.y + deltaVertical);
	}
	
	public Position move(Position pos){
		return this.add(pos);
	}
	
	public Position add(Position pos){
		return new Position(this.x + pos.getX(), this.y + pos.getY());
	}
	
	public Position add(int value){//add a value to both x and y
		return new Position(this.x + value, this.y + value);
	}
	
	public Position up(){//position above
		return new Position(this.x, this.y + 1);
	}
	
	public Position right(){//position to the right
		return new Position(this.x + 1, this.y);
	}
	
	public Position down(){//position below
		return new Position(this.x, this.y - 1);
	}
	
	public Position left(){//position to the left
		return new Position(this.x - 1, this.y);
	}
	
	public Position cycle(){//position at the beginning of next line
		return new Position(0, this.y + 1);
	}
}

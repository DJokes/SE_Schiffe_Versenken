package SchiffeVersenken;

public class MemoryField {
	boolean[][] field;
	int maxX;
	int maxY;
	
	public MemoryField(){
		super();
		this.field = new boolean[10][10];
		this.maxX = 10;
		this.maxY = 10;
	}
	
	public MemoryField(int x, int y){
		super();
		this.field = new boolean[y][x];
		this.maxX = x;
		this.maxY = y;
	}
	
	public boolean checkBounds(Position pos){
		boolean inside = true;
		inside &= (pos.getX() > -1);
		inside &= (pos.getY() > -1);
		inside &= (pos.getX() < this.maxX);
		inside &= (pos.getY() < this.maxY);
		
		return inside;
	}
	
	public boolean getValue(Position pos){//check bounds before
		 return this.field[pos.getY()][pos.getX()];
	}
	
	public boolean getValue(int x, int y){//check bounds before
		 return this.field[y][x];
	}

	public void setValue(Position pos, boolean value){//check bounds before
		 this.field[pos.getY()][pos.getX()] = value;
	}
	
	public boolean wasHit(Position pos){
		 if(checkBounds(pos)){
			 return getValue(pos);
		 } else {
			 return true;//out of bounds is considered hit so we don't do it
		 }
	}
	
	public int totalHits() {
		int counter = 0;
		for(int line=0; line < this.maxY; line++){
			for(int column=0; column < this.maxX; column++){
				if(getValue(line, column)){
					counter++;
				} 			
			}
		}
		return counter;
	}
	
	public void hit(Position pos){
		if(checkBounds(pos)){
			 setValue(pos, true);
		 }
	}
	
	public void hitCross(Position pos){//TODO
		if(checkBounds(pos)){
			 setValue(pos, true);
		 }
	}
	
	public void hitDiagonal(Position pos){//TODO
		if(checkBounds(pos)){
			 setValue(pos, true);
		 }
	}
	
	public String toString(){
		String fieldString = "";
		for(int line=0; line < this.maxY; line++){
			for(int column=0; column < this.maxX; column++){
				if(getValue(line, column)){
					fieldString += "[X]";//.concat(new String("[X]"));//X for true/hit
				} else{
					fieldString += "[0]";//.concat(new String("[0]"));//0 for false
				}				
			}
			fieldString += "\n";//.concat(new String("\n"));
		}
		
		return fieldString;
	}
	
}

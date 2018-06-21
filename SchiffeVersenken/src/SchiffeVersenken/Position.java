package SchiffeVersenken;

public class Position {

	private int vertical;
	private int horizontal;
	
	public Position(int horizontal, int vertical){
		this.vertical = vertical;
		this.horizontal = horizontal;
	}
	
	public int getVertical(){
		return vertical;
	}
	
	public int getHorizontal(){
		return horizontal;
	}
	
	public void verschiebe(int deltaHorizontal, int deltaVertical){
		vertical += deltaVertical;
		horizontal += deltaHorizontal;
	}
}

package SchiffeVersenken;

public class Spielfeld {
	
	// 0 = water // 1 to 10 are ships their length // 100 is hit
	private int[][] field = new int[10][10];
	private int shipType;
	private int positioning;
	private int amountOfShips = 5;
	
	public Spielfeld(int shipType, int positioning){
		this.shipType = shipType;
		this.positioning = positioning;
	}
	
	public int getShipType(){
		return shipType;
	}
	
	public int getPositioning(){
		return positioning;
	}
	
	public void setShip(int ship, Position startPosition, int direction){
		Position pos = startPosition;
		for(int i = 0; i < ship; i++){
			if(field[pos.getX()][pos.getY()] == 0)
				
				field[pos.getX()][pos.getY()] = ship;
			//to the right
			if(direction == 0){
				pos.verschiebe(-1, 0);
			}
			//to the left
			else if(direction == 1){
				pos.verschiebe(1, 0);
			}
			//up 
			else if(direction == 2){
				pos.verschiebe(0, -1);
			}
			//down
			else{
				pos.verschiebe(0, 1);
			}
		}
	}
	
	public boolean possibleSet(Position pos, int delta){
		if(positioning == 2){
			boolean xPossible = true;
			for(int i = 0; i <= delta; i++){
				if(pos.getX() > 0 && field[pos.getX()-1][pos.getY()] != 0){
					xPossible = false;
					break;
				}
				else if(pos.getX() < 9 && field[pos.getX()+1][pos.getY()] != 0){
					xPossible = false;
					break;
				}
			}
			boolean yPossible = true;
			for(int i = 0; i <= delta; i++){
				if(pos.getY() > 0 && field[pos.getX()][pos.getY()-1] != 0){
					yPossible = false;
					break;
				}
				else if(pos.getY() < 9 && field[pos.getX()][pos.getY()+1] != 0){
					yPossible = false;
					break;
				}
			}
			if(yPossible || xPossible){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	public int possibleDirection(Position pos, int delta){
		if(positioning == 2){
			int direction = 0;
			for(int i = 0; i <= delta; i++)
				if(pos.getX() > 0 && field[pos.getX()-1][pos.getY()] != 0){
					direction = 1;
				}
			for(int i = 0; i <= delta; i++)
				if(pos.getX() < 9 && field[pos.getX()+1][pos.getY()] != 0){
					direction = 2;
				}
			
			for(int i = 0; i <= delta; i++)
				if(pos.getY() > 0 && field[pos.getX()][pos.getY()-1] != 0){
					direction = 3;
				}
			for(int i = 0; i <= delta; i++)
				if(pos.getY() < 9 && field[pos.getX()][pos.getY()+1] != 0){
					direction = -1;
				}
			return direction;
		}
		return -1;
	}
	
	public boolean shootOfAi(int x, int y){
		if(checkHit(x, y)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkHit(int x, int y){
		if(field[x][y] != 0 ){
			if(field[x][y] >= 1 && field[x][y] <= 10){
				field[x][y] = 100;
				return true;
			}
			else if(field[x][y] == 100){
				return false;
			}
		}
		return false;
	}
	
	public boolean gewonnen(){
		return amountOfShips == 0;
	}
	
	public boolean versenkt(){
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
	
	public static void main(String[] args) {
		Spielfeld test = new Spielfeld(0, 2);
		test.setShip(5, new Position(4, 4), 0);
		test.printField();

	}

}

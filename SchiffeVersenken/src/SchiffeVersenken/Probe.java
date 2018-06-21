package SchiffeVersenken;

public class Probe {//only for Wasser or Ecken
	/*things taht shouldnt happen here:
	 * rule is Berühren
	 * probing is over 3
	 * probing persists after ship-kill
	 * */
	
	private Position first;
	private Position last;
	private int probing;//0 up, 1 right, 2 down, 3 left
	private Direction direction;
	
	public Probe() {
		this(new Position());		
	}
	
	public Probe(Position base) {
		this.first = base;
		this.last = base;
		this.probing = 0;
		this.direction = null;//there's a reason for this i swear
	}

	public Position probe() {//don't have a set direction yet, searching
		Position check;
		
		switch (this.probing){
		case 0://up
			check = this.first.up();
			break;
		case 1://right
			check = this.last.right();
			break;
		case 2://down
			check = this.last.down();
			break;
		case 3://left
			check = this.first.left();
			break;
		default://some error, shouldnt get here ever
			return this.first;//fail-safe, better to keep shooting shot position than getting exceptions or loops	
		}		
		
		return check;
	}
	
	public void checkResult(int result, Position pos) {
		switch (result){
		case 0://water
			if(isGuided()) {
				flipProbe();//look in other direction
			} else {
				this.probing++;//technically shouldn't reach 4
			}
			break;
		case 1://ship
			if(!isGuided()) {
				setDirection();//set direction
			}
			updateLimits(pos);//update limits
			//keep probing same direction
			break;
		default:
			return;//ship_kill, probe is over		
		}
	}
	
	private void updateLimits(Position pos) {//hit ship but didnt sink it yet
		switch (this.probing){
		case 0://up
			this.first = pos;
			break;
		case 1://right
			this.last = pos;
			break;
		case 2://down
			this.last = pos;
			break;
		case 3://left
			this.first = pos;
			break;
		default:
			return;//some error			
		}
	}
	
	private void setDirection() {//set ship orientation as direction
		switch (this.probing){
		case 0://up
			this.direction = Direction.VERTICAL;
			break;
		case 1://right
			this.direction = Direction.HORIZONTAL;
			break;
		case 2://down
			this.direction = Direction.VERTICAL;
			break;
		case 3://left
			this.direction = Direction.HORIZONTAL;
			break;
		default:
			return;//some error			
		}
	}
	
	private boolean isGuided() {//has a direction
		return (this.direction != null);
	}
	
	private void flipProbe() {//limit reached, try other side
		this.probing += 2;// 0[up] goes to 2[down], 1[right] goes to 3[left], 2 and 3 go over limit
		//but, again, this shouldn't happen ever
	}
	
	



	enum Direction{
		HORIZONTAL, VERTICAL
	}

}

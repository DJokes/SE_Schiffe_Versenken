package se1.schiffeVersenken.interfaces.util;

/**
 * A {@link Direction}, either {@link Direction#HORIZONTAL} or {@link Direction#VERTICAL}.
 */
public enum Direction {
	
	HORIZONTAL(new Position(1, 0)),
	VERTICAL(new Position(0, 1));
	
	public final Position positive;
	public final Position negative;
	
	Direction(Position positive) {
		this.positive = positive;
		this.negative = positive.negate();
	}
	
	/**
	 * returns the other direction
	 */
	public Direction other() {
		switch (this) {
			case HORIZONTAL:
				return VERTICAL;
			case VERTICAL:
				return HORIZONTAL;
		}
		throw new IllegalStateException();
	}
}

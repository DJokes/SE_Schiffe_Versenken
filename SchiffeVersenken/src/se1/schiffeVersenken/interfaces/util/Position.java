package se1.schiffeVersenken.interfaces.util;

import java.util.Objects;

/**
 * A Vector Utility class using <code>int</code>.<br>
 * Every Method creates a new Object, which may not be the most efficient way but it'll be easier for Beginners working on this.<br>
 * <br>
 * Any suggestions are welcome!
 */
public final class Position {
	
	public static final Position NULL_VECTOR = new Position(0, 0);
	
	public final int x;
	public final int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position add(Position vec) {
		return new Position(x + vec.x, y + vec.y);
	}
	
	public Position sub(Position vec) {
		return new Position(x - vec.x, y - vec.y);
	}
	
	public Position multiply(int scalar) {
		return new Position(x * scalar, y * scalar);
	}
	
	public Position divide(int scalar) {
		return new Position(x / scalar, y / scalar);
	}
	
	public Position negate() {
		return new Position(-x, -y);
	}
	
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean boundsCheck(Position min, Position max) {
		return min.x <= x && x < max.x && min.y <= y && y < max.y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Position)) return false;
		Position position = (Position) o;
		return x == position.x &&
				y == position.y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}
}

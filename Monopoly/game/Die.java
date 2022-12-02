package game;
/** @author Khushdip */

public class Die {
	
	//Constructor: default to 6 sided die
	public Die() {
		sides = 6;
	}
	
	/** Rolls a value between 1 and number of sides
	 * @return None
	 */
	public void roll() {
		min = 1;
		max = sides + 1;
		value = (int)(Math.random() * (max - min) + min);
	}
	
	/** Returns the die value
	 * @return integer value of roll
	 */
	public int getValue() {
		return value;
	}
	
	/** Outputs Die object with the die value
	 * @return String with die states
	 */
	public String toString() {
		return "You rolled a " + value;
	}
	
	//Private instance variables
	private int sides, value, min, max;
}
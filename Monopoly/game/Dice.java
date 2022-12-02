package game;
/** @author Khushdip */

public class Dice extends Die{
	
	//Constructor: create two six sided die objects
	public Dice() {
		d1 = new Die();
		d2 = new Die();
	}

	/** Roll the two die objects
	 * @return None
	 */
	public void roll() {
		d1.roll();
		d2.roll();
	}

	/** Returns the sum of both dice
	 * @return int total of rolls
	 */
	public int getSum() {
		sum = d1.getValue() + d2.getValue();
		return sum;
	}
	
	/** Returns value of first die rolled
	 * @return int single die value
	 */
	public int getD1() {
		return d1.getValue();
	}
	
	/** Returns value of second die rolled
	 * @return int single die value
	 */
	public int getD2() {
		return d2.getValue();
	}
	
	/** Outputs Dice object along with dice values and total sum
	 * @return String with dice states
	 */
	public String toString() {
		return "rolled a " + d1.getValue() + " and a " + d2.getValue() + ". Move " + getSum() + " spaces.\n";
	}

	//Private instance variables
	private Die d1, d2;
	private int sum;
}
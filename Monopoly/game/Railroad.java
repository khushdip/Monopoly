package game;
/** @author Khushdip */

public class Railroad extends Property{

	//Constructor
	public Railroad(String givenName) {
		name = givenName;
		type = "railroad";
		cost = 200;
		isOwned = false;
	}
	
	/**Getter method for railroad cost
	 * @param none
	 * @return int cost of railroad
	 */
	public int getCost() {
		return cost;
	}
	
	/**Getter method for property type
	 * @param none
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**Getter method for status of railroad, is it owned or not
	 * @param none
	 * @return Boolean owned status
	 */
	public boolean getOwnedStatus() {
		return isOwned;
	}
	
	/**Getter method of owner of railroad
	 * @param none
	 * @return Player owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	/**Identify buyer and change owned status accordingly
	 * @param Player buyer
	 * @return none
	 */
	public void buyProperty(Player buyer) {
		isOwned = true;
		owner = buyer;
	}
	
	public String toString() {
		return "///////////////////// " + name + " /////////////////////" + "\nCost: " + cost +
				"\nRent: " + rentOneR + "\nIf 2 railroads are owned: " + rentTwoR + 
				"\nIf 3 railroads are owned: " + rentThreeR + "\nIf 4 railroads are owned: " + rentFourR + "\n";
	}
	
	public static int rentOneR = 25;
	public static int rentTwoR = 50;
	public static int rentThreeR = 75;
	public static int rentFourR = 100;
}

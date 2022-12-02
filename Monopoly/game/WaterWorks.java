package game;
/** @author Khushdip */

public class WaterWorks extends Utility{

	//Constructor
	public WaterWorks() {
		name = "Water Works";
		type = "water works";
		cost = 150;
		isOwned = false;
	}
	
	/**Getter method for utility cost
	 * @param none
	 * @return int cost of utility
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
	
	/**Getter method for status of utility, is it owned or not
	 * @param none
	 * @return Boolean owned status
	 */
	public boolean getOwnedStatus() {
		return isOwned;
	}
	
	/**Getter method of owner of utility
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
}

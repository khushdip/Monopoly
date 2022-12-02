package game;
/** @author Khushdip */

public class BoardPosition {
	
	//no explicit constructor, unnecessary
	//Methods created here are largely for the purpose of being overridden in child classes
	//Overriding these methods is necessary for method calling in playMonopoly class
	
	/**To string method for BoardPosition type object
	 * @param
	 * @return
	 */
	public String toString() {
		return name;
	}
	
	/**Getter method for name
	 * @param none
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	//Method declared for Site class use
	public void initSiteInfo(int index) {
	}
	
	//overridden in property  class
	public boolean getOwnedStatus() {
		return false;
	}
	
	//Following methods overridden in multiple classes
	
	public String getType() {
		return type;
	}
	
	public int getCost() {
		return 0;
	}
	
	public int getRent() {
		return 0;
	}
	
	public Player getOwner() {
		return new Player();
	}
	
	public void buyProperty(Player buyer) {
	}
	
	//instance variables
	//type: chance, commChest, incomeTax, luxuryTax, etc
	//type necessary to identify BoardPosition objects in playMonoploy class
	//colour declared for Site class use
	public String name, type, colour;
	public int position;
}

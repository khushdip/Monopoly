package game;
/** @author Khushdip */

public class LuxuryTax extends BoardPosition{

	//Constructor
	public LuxuryTax() {
		name = "Luxury Tax";
	}
	
	/**Getter method for luxury tax ammount
	 * @param none
	 * @return int
	 */
	public static int getLuxuryTax() {
		return tax;
	}
	
	//Class variable for tax ammount
	static int tax = 200;
}

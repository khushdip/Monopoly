package game;
/** @author Khushdip */

public class IncomeTax extends BoardPosition{

	//Constructor
	public IncomeTax() {
		name = "Income Tax";
		type = "income tax";
	}
	
	/**Getter method for income tax ammount
	 * @param none
	 * @return int tax ammount
	 */
	public static int getIncomeTax() {
		return tax;
	}
	
	//class variable for tax ammount
	private static int tax = 200;
}

package game;
/** @author Khushdip */

public class Site extends Property{

	//Constructor
	public Site(int positionOnBoard) {
		position = positionOnBoard;
		name = "Not Named";
		type = "site";
		isOwned = false;
	}
	
	/**Initialize information of site according to siteInfo 2D array
	 * @param int index
	 * @return none
	 */
	public void initSiteInfo(int index) {
		//Cost Rent 1House 2Houses 3Houses 4Houses Hotel MortgageValue HousesCost HotelsCost
		cost = siteInfo[index][0];
		rent = siteInfo[index][1];
		rentOneH = siteInfo[index][2];
		rentTwoH = siteInfo[index][3];
		rentThreeH = siteInfo[index][4];
		rentFourH = siteInfo[index][5];
		rentHotel = siteInfo[index][6];
		mortgageValue = siteInfo[index][7];
		costHouse = siteInfo[index][8];
		costHotel = siteInfo[index][9];
	}
	
	/**Getter method for site cost
	 * @param none
	 * @return int cost of site
	 */
	public int getCost() {
		return cost;
	}
	
	/**Getter method for site rent
	 * @param none
	 * @return int rent of site
	 */
	public int getRent() {
		if (housesBuilt == 1) {
			return rentOneH;
		}
		if (housesBuilt == 2) {
			return rentTwoH;
		}
		if (housesBuilt == 3) {
			return rentThreeH;
		}
		if (housesBuilt == 4) {
			return rentFourH;
		}
		if (hotelsBuilt > 0) {
			return rentHotel;
		}
		return rent;
	}
	
	/**Getter method for property type
	 * @param none
	 * @return String type
	 */
	public String getType() {
		return type;
	}
	
	/**Getter method for status of site, is it owned or not
	 * @param none
	 * @return Boolean owned status
	 */
	public boolean getOwnedStatus() {
		return isOwned;
	}
	
	/**Getter method of owner of site
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
	
	/**To string method for a site, display its info
	 * @param none
	 * @return String site card information
	 */
	public String toString() {
		return "///////////////////// " + name + " /////////////////////" + colour
				+ "\nCOST: " + cost + "\n\tRENT: " + rent + "\n\twith 1 House: " + rentOneH
				+ "\n\twith 2 Houses: " + rentTwoH + "\n\twith 3 Houses: " + rentThreeH
				+ "\n\twith 4 Houses: " + rentFourH + "\n\twith Hotel: " + rentHotel
				+ "\nHouses Cost: " + costHouse + "Each\nHotels Cost: " + costHotel
				+ " each, plus 4 Houses\nMORTGAGE VALUE: " + mortgageValue + "\n";
	}

	//instance variables
	private int rentOneH, rentTwoH, rentThreeH, rentFourH, rentHotel, mortgageValue, costHouse, costHotel, housesBuilt, hotelsBuilt;
	//String arrays to store lines of text from the file
	public static int[][] siteInfo = new int[22][10];
	public static String[] siteNames = new String[22];
	public static String[] siteColours = new String[22];
}

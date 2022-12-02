package game;
import java.util.*;
/** @author Khushdip */

public class Player{

	//Constructor
	public Player() {
		positionOnBoard = 0;
		bankAccount = 1500;
	}

	/**Setter method for name
	 * @param playerName
	 * @return none
	 */
	public void setName(String playerName) {
		name = playerName;
	}

	/**Getter method for name
	 * @param none
	 * @return String name of player
	 */
	public String getName() {
		return name;
	}

	/**Move player on the board forward once
	 * @param none
	 * @return none
	 */
	public void movePlayer() {
		//increment position by single place each time method is called
		positionOnBoard++;
		//when position is maxed out, reset to zero; 0 and 40 are synonymous with the GO position
		if (positionOnBoard == 40) positionOnBoard = 0;
	}

	/**Move player on board backwards once
	 * @param none
	 * @return none
	 */
	public void movePlayerBackwards() {
		//decrement position by single place each time method is called
		positionOnBoard--;
		//transition Go from index 0 to index 40 to maintain circuit of the monopoly board
		if (positionOnBoard == 0) positionOnBoard = 40;
	}

	/**Getter method for player's position on board
	 * @param none
	 * @return none
	 */
	public int getPositionOnBoard() {
		return positionOnBoard;
	}

	/**Collect $200 when passing GO
	 * @param none
	 * @return none
	 */
	public void passGo() {
		bankAccount += 200;
	}

	/**Move player to jail and change their status to jailed
	 * @param none
	 * @return none
	 */
	public void goToJail() {
		isJailed = true;
		//10 is index of jail
		positionOnBoard = 10;
	}
	
	/**Change player's status to not jailed
	 * @param none
	 * @return none
	 */
	public void getOutOfJail() {
		isJailed = false;
	}

	/**Player pays taxed amount to the bank
	 * @param int taxed value
	 * @return none
	 */
	public void payTax(int tax) {
		bankAccount -= tax;
	}

	/**Player adheres to actions dictated by chance card message
	 * @param int messageIndex
	 * @param int numPlayers
	 * @return int relevant index value
	 */
	public int actionChance(int messageIndex, int numPlayers) {
		//index is in order of ChanceCards.txt file
		//when message index is returned, it signifies that there are still actions required in-game to fulfill the message
		if (messageIndex == 0) {
			//given get out of jail free card
			haveJailFreeCard = true;
		} else if (messageIndex == 1) {
			//go directly to jail
			goToJail();
		} else if (messageIndex == 2) {
			//chairman fee message
			bankAccount -= numPlayers * 50;
			return messageIndex;
		} else if (messageIndex == 3) {
			//speeding fine
			bankAccount -= 15;
		} else if (messageIndex == 4) {
			//make repairs
			bankAccount -= (numHouses * 25) + (numHotels * 100);
		} else if (messageIndex == 5) {
			//advance to go
			positionOnBoard = 0;
			passGo();
		} else if (messageIndex == 6) {
			//bank pays player dividend
			bankAccount += 50;
		} else if (messageIndex == 7) {
			//building loan matures
			bankAccount += 150;
		} else if (messageIndex == 8) {
			//move player back three spaces
			for (int i = 0; i < 3; i++) {
				movePlayerBackwards();
			}
			return messageIndex;
		} else if (messageIndex == 9) {
			//go to reading railroad
			if (positionOnBoard > 5) passGo();
			positionOnBoard = 5;
			return messageIndex;
		} else if (messageIndex == 10 || messageIndex == 12) {
			//go to nearest railroad
			//index 5 is reading railroad, 15 is Pennsylvania
			if (positionOnBoard >= 5 && positionOnBoard < 15) {
				positionOnBoard = 15;
			} else if (positionOnBoard >= 15 && positionOnBoard < 25) {
				positionOnBoard = 25;
				//index 25 is B. & O., 35 is short line
			} else if (positionOnBoard >= 25 && positionOnBoard < 35) {
				positionOnBoard = 35;
			} else positionOnBoard = 5;
			return messageIndex;
		} else if (messageIndex == 11) {
			//go to nearest utility
			//index 12 is electric company, 28 is water works
			if (positionOnBoard >= 12 && positionOnBoard < 28) {
				//if between electric company and water works, go to water works
				positionOnBoard = 28;
				//otherwise go to electric company
			} else positionOnBoard = 12;
			return messageIndex;
		} else if (messageIndex == 13) {
			//go to St. Charles place at index 11
			if (positionOnBoard > 11) passGo();
			positionOnBoard = 11;
			return messageIndex;
		} else if (messageIndex == 14) {
			//go to Illinois Avenue at index 24
			if (positionOnBoard > 24) passGo();
			positionOnBoard = 24;
			return messageIndex;
		} else if (messageIndex == 15) {
			//go to boardwalk at index 39
			positionOnBoard = 39;
			return messageIndex;
		}
		return -1;
	}

	/**Player adheres to actions dictated by community chest card message
	 * @param int messageIndex
	 * @param int numPlayers
	 */
	public void actionCommChest(int messageIndex, int numPlayers) {
		//index is in order of CommunityChest.txt file
		//no additional in-game actions required to fulfill card message
		//index 0 to 9, player receives money, except for index 8 in which they advance to go
		//index 10 gives player get out of jail free card
		//index 11 is street repairs
		//index 12 to 14 are fees
		//index 15 sends player directly to jail
		if (messageIndex == 0) {
			bankAccount += 20;
		} else if (messageIndex == 1) {
			bankAccount += 50;
		} else if (messageIndex == 2) {
			bankAccount += 200;
		} else if (messageIndex == 3) {
			bankAccount += 25;
		} else if (messageIndex == 4) {
			bankAccount += 100;
		} else if (messageIndex == 5) {
			bankAccount += 100;
		} else if (messageIndex == 6) {
			bankAccount += numPlayers * 10;
		} else if (messageIndex == 7) {
			bankAccount += 10;
		} else if (messageIndex == 8) {
			passGo();
			positionOnBoard = 0;
		} else if (messageIndex == 9) {
			bankAccount += 100;
		} else if (messageIndex == 10) {
			haveJailFreeCard = true;
		} else if (messageIndex == 11) {
			bankAccount -= (numHouses * 40) + (numHotels * 115);
		} else if (messageIndex == 12) {
			bankAccount -= 50;
		} else if (messageIndex == 13) {
			bankAccount -= 50;
		} else if (messageIndex == 14) {
			bankAccount -= 100;
		} else if (messageIndex == 15) {
			goToJail();
		}
	}

	/**Getter method for money the player has
	 * @param none
	 * @return int player's ammount of money
	 */
	public int getMoneyInBank() {
		return bankAccount;
	}

	/**Adds money to player's bank account
	 * @param int value
	 * @return none
	 */
	public void addToBankAccount(int value) {
		bankAccount += value;
	}

	/**Takes money from player's bank account
	 * @param int value
	 * @return none
	 */
	public void takeFromBankAccount(int value) {
		bankAccount -= value;
	}

	/**Players pays for the property and now owns it
	 * @param BoardPostion property
	 * @return none
	 */
	public void buyProperty(BoardPosition property) {
		//add property to array list
		propertiesOwned.add(property);
		//pay for property
		takeFromBankAccount(property.getCost());
		//if property is a railroad or utility, keep track of how many of this type are owned
		if ((property.getType()).equalsIgnoreCase("railroad")) {
			numRailroadsOwned++;
		} else if ((property.getType()).equalsIgnoreCase("water works") || (property.getType()).equalsIgnoreCase("electric company")) {
			numUtilsOwned++;
		}
	}
	
	/**Getter method for number of utilities the player owns
	 * @param none
	 * @return int number of utilities owned
	 */
	public int getNumUtilsOwned() {
		return numUtilsOwned;
	}
	
	/**Getter method for number of railroads the player owns
	 * @param none
	 * @return int number of railroads owned
	 */
	public int getNumRailroadsOwned() {
		return numRailroadsOwned;
	}
	
	public ArrayList<BoardPosition> getSites() {
		ArrayList<BoardPosition> sites = new ArrayList<BoardPosition>();
		ArrayList<BoardPosition> copyProperties = propertiesOwned;
		Iterator<BoardPosition> itr = copyProperties.iterator();
		while(itr.hasNext()) {
			if (((itr.next()).getType()).equalsIgnoreCase("site")) {
				sites.add(itr.next());
			} else itr.next();
		}
		return sites;
	}

	/**To string method for properties the player owns
	 * @param none
	 * @return String list of property names
	 */
	public String propertiesOwnedToString() {
		String str = "";
		if (propertiesOwned.size() > 0) {
			Iterator<BoardPosition> propItr = propertiesOwned.iterator();
			while(propItr.hasNext()) {
				str += (propItr.next()).getName();
				str += ", ";
			}
		}
		str += "...";
		return str;
	}

	/**To string method for player, output their financial record
	 * @param none
	 * @return none
	 */
	public String toString() {
		return name + " has $" + bankAccount + " in the bank. " + name + " owns " + propertiesOwnedToString();
	}

	//instance variables
	private String name;
	public boolean haveJailFreeCard = false;
	public boolean isJailed = false;
	private int positionOnBoard, bankAccount, numHouses, numHotels;
	private int numUtilsOwned = 0;
	private int numRailroadsOwned = 0;
	private ArrayList<BoardPosition> propertiesOwned = new ArrayList<BoardPosition>();
}

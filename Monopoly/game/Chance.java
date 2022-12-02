package game;
/** @author Khushdip */

import acm.util.RandomGenerator;

public class Chance extends BoardPosition{

	//Constructor
	public Chance() {
		name = "Chance";
		type = "chance";
	}

	/**Shuffles chance cards deck
	 * @param none
	 * @return none
	 */
	public static void shuffleChanceCards() {
		//declare new array to hold copy of original array
		String[] copyArray = new String [chanceCards.length];
		//declare array to hold randomized index values
		int[] index = new int [chanceCards.length];
		//initialize copyArray with original array values; index to -1
		for (int i = 0; i < index.length; i++) {
			//shuffled cards array allows chance cards array to remain ordered
			chanceCardsShuffled[i] = chanceCards[i];
			copyArray[i] = chanceCardsShuffled[i];
			index[i] = -1;
		}
		//assign randomized index values to index array
		for (int i = 0; i < chanceCards.length; i++) {
			//generate random value
			index[i] = rgen.nextInt(0, chanceCardsShuffled.length - 1);
			//if value is already stored, decrement i and reassign
			for (int j = 0; j < i; j++) {
				if (index[i] == index[j]) {
					i--;
					break;
				}
			}
		}
		//array is assigned randomized values
		for (int i = 0; i < chanceCardsShuffled.length; i++) {
			chanceCardsShuffled[i] = copyArray[index[i]];
		}
	}
	
	/**Returns the message written on the card dealt
	 * @param none
	 * @return String chance card message
	 */
	public static String dealCard() {
		//all cards have been dealt, start back from first card in the deck
		if (dealCount == 16) dealCount = 0;
		return chanceCardsShuffled[dealCount++];
	}
	
	/**Used to identify the index of a card relative to the ChanceCards.txt file
	 * Allows a player to act accordingly to what the message dictates
	 * @param none
	 * @return int index value
	 */
	public static int identifyCard(String cardMessage) {
		for (int i = 0; i < chanceCards.length; i++) {
			//go through each message in the ordered array until a match to the given message is found
			if (cardMessage.equalsIgnoreCase(chanceCards[i])) {
				return i;
			}
		}
		return -1;
	}
	
	/**Getter method for chairman fee
	 * @param none
	 * @return int chairman fee
	 */
	public static int getChairmanFee() {
		return chairmanFee;
	}
	
	//Class variables:
	
	// Create a random generator object
	private static RandomGenerator rgen = RandomGenerator.getInstance();
	//public and private String arrays to hold chance cards in order and shuffled
	public static String[] chanceCards = new String[16];
	private static String[] chanceCardsShuffled = new String[16];
	//private count value to keep track of when cards need to be dealt from top of the order
	//allows multiple get out of jail free cards
	private static int dealCount = 0;
	//private integer value for chairman fee that a chance card prompts a player to pay
	private static int chairmanFee = 50;
}

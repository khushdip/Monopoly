package game;
/** @author Khushdip */

import acm.util.RandomGenerator;

public class CommunityChest extends BoardPosition{

	//Constructor
	public CommunityChest() {
		name = "Community Chest";
		type = "community chest";
	}

	/**Shuffle community chest cards deck
	 * @param none
	 * @return none
	 */
	public static void shuffleCommChestCards() {
		//declare new array to hold copy of original array
		String[] copyArray = new String [communityChestCards.length];
		//declare array to hold randomized index values
		int[] index = new int [communityChestCards.length];
		//initialize copyArray with original array values; index to -1
		for (int i = 0; i < index.length; i++) {
			////shuffled cards array allows community chest cards array to remain ordered
			commChestCardsShuffled[i] = communityChestCards[i];
			copyArray[i] = commChestCardsShuffled[i];
			index[i] = -1;
		}
		//assign randomized index values to index array
		for (int i = 0; i < communityChestCards.length; i++) {
			//generate random value
			index[i] = rgen.nextInt(0, commChestCardsShuffled.length - 1);
			//if value is already stored, decrement i and reassign
			for (int j = 0; j < i; j++) {
				if (index[i] == index[j]) {
					i--;
					break;
				}
			}
		}
		//array is assigned randomized values
		for (int i = 0; i < communityChestCards.length; i++) {
			communityChestCards[i] = copyArray[index[i]];
		}
	}
	
	/**Returns the message written on the card dealt
	 * @param none
	 * @return String community chest card message
	 */
	public static String dealCard() {
		//all cards have been dealt, start back from first card in the deck
		if (dealCount == 16) dealCount = 0;
		return commChestCardsShuffled[dealCount++];
	}
	
	/**Used to identify the index of a card relative to the CommunityChest.txt file
	 * Allows a player to act accordingly to what the message dictates
	 * @param none
	 * @return int index value
	 */
	public static int identifyCard(String cardMessage) {
		for (int i = 0; i < communityChestCards.length; i++) {
			//go through each message in the ordered array until a match to the given message is found
			if (cardMessage.equalsIgnoreCase(communityChestCards[i])) {
				return i;
			}
		}
		return -1;
	}
	
	//Class variables:
	
	// Create a random generator object
	private static RandomGenerator rgen = RandomGenerator.getInstance();
	//public and private String arrays to hold community chest cards in order and shuffled
	public static String[] communityChestCards = new String[16];
	private static String[] commChestCardsShuffled = new String[16];
	//private count value to keep track of when cards need to be dealt from top of the order
	//allows multiple get out of jail free cards
	private static int dealCount = 0;
}

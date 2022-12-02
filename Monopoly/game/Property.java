package game;
/** @author Khushdip */

abstract class Property extends BoardPosition{
	
	//public instance variables
	public int cost, rent;
	public Player owner;
	public boolean isOwned;
	
	//template for getter method for owned status
	public abstract boolean getOwnedStatus();
	
	//template for buy property method
	public abstract void buyProperty(Player buyer);
	
	//template for get cost of property method
	public abstract int getCost();
	
}

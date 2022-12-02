package game;
/** @author Khushdip */

import acm.graphics.*;
import acm.program.*;
import acm.util.ErrorException;

import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*; 

public class playMonopoly extends ConsoleProgram{

	private static final long serialVersionUID = 1L;

	//Used to draw interactive GRects
	private static final int POSITION_WIDTH = 66;
	private static final int POSITION_HEIGHT = 105;
	private static final int ALTERNATE_POSITION_WIDTH = 105;
	//Used to draw player tokens
	private static final int TOKEN_DIMENSIONS = 20;
	//Fee to pay out of jail
	private static final int JAIL_FEE = 50;
	//40 spaces on playing board
	private static final int NUM_POSITIONS = 40;
	
	//index for each space on board that is not a property
	private static final int GO = 0;
	private static final int JAIL = 10;
	private static final int FREE_PARKING = 20;
	private static final int GO_TO_JAIL = 30;

	private static final int INCOME_TAX = 4;
	private static final int LUXURY_TAX = 38;

	private static final int CHANCE = 7;
	private static final int CHANCE_TWO = 22;
	private static final int CHANCE_THREE = 36;
	private static final int COMMUNITY_CHEST = 2;
	private static final int COMMUNITY_CHEST_TWO = 17;
	private static final int COMMUNITY_CHEST_THREE = 33;

	private static final int READING_RAILROAD = 5;
	private static final int PENNSYLVANIA_RAILROAD = 15;
	private static final int B_O_RAILROAD = 25;
	private static final int SHORT_LINE = 35;

	private static final int ELECTRIC_COMPANY = 12;
	private static final int WATER_WORKS = 28;
	
	//Used for mouse events
	private static final int LEFT_BUTTON = 1;

	public void init(){
		
		//Big window to make the monopoly board as attractive as possible
		setSize (1800,1000);
		setFont("Arial-14");
		//Set up console + canvas
		GridLayout grid = new GridLayout(1, 2);
		setLayout(grid);

		canvas = new GCanvas();
		add(canvas);

		//NAMEFIELD
		nameField = new JTextField(15);
		nameLabel = new JLabel ("Name");
		add(nameLabel, NORTH);
		add(nameField, NORTH);

		//STATS
		statsLabel = new JLabel("Stats");
		statsLabel.setVisible(false);
		add(statsLabel, NORTH);

		p1Button = new JButton("P1");
		p1Button.setVisible(false);
		add(p1Button, NORTH);

		p2Button = new JButton("P2");
		p2Button.setVisible(false);
		add(p2Button, NORTH);

		playerButton = new JButton("You");
		playerButton.setVisible(false);
		add(playerButton, NORTH);

		//ROLL DICE
		diceButton = new JButton("Roll Dice");
		diceButton.setVisible(false);
		add(diceButton, SOUTH);

		//BUY PROPERTY
		buyPropButton = new JButton("Buy Property");
		buyPropButton.setVisible(false);
		add(buyPropButton, SOUTH);

		//END TURN
		endTurnButton = new JButton("End Turn");
		endTurnButton.setVisible(false);
		add(endTurnButton, SOUTH);

		//PAY OUT OF JAIL
		payOutOfJailButton = new JButton("Pay $50");
		payOutOfJailButton.setVisible(false);
		add(payOutOfJailButton, SOUTH);

		//USE JAIL FREE CARD
		useJailFreeCardButton = new JButton("Use Get Out of Jail Free Card");
		useJailFreeCardButton.setVisible(false);
		add(useJailFreeCardButton, SOUTH);

		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);
		nameField.addActionListener(this);
		addActionListeners();
	}

	public void run() {

		//initialize to prepare the game

		drawBoard();

		initSiteNames();
		initSiteColours();

		initChanceCards();
		Chance.shuffleChanceCards();

		initCommunityChestCards();
		CommunityChest.shuffleCommChestCards();

		initSiteInfo();
		initPositions();

		p1.setName("P1");
		p2.setName("P2");

		playerToken.setFilled(true);
		playerToken.setFillColor(Color.RED);
		p1Token.setFilled(true);
		p1Token.setFillColor(Color.BLUE);
		p2Token.setFilled(true);
		p2Token.setFillColor(Color.GREEN);

		doublesCount = 0;

		displayInstructions();

		//cpus buy property
		//buy houses/ hotel
		//instruction display

		//while (true) {
		//	if (player.getMoneyInBank() <= 0) {
		//		diceButton.setVisible(false);
		//		endTurnButton.setVisible(false);
		//		println("Someone is broke!\nGAME OVER!");
		//		break;
		//	}
		//}

		//figure out get out of jail yes
		//make cpus buy properties
		//triple double lands in jail yes
		//pay utility rent
		//pay railroad rent

		//println(rectBoardPositions[6].getX());


		//roll dice and move
		//check landing
		//if landed on site, buy, add site name to array list
		//check every turn if have all sites of a colour, display build houses button
		//output bankAccount value at every turn


		//Test FileIO
		//for (int i = 0; i < Site.siteInfo.length; i++) {
		//	for (int j = 0; j < Site.siteInfo[i].length; j++) {
		//		print(Site.siteInfo[i][j] + " ");
		//	}
		//	println();
		//}
		//Test FileIO
		//for (int i = 0; i < 20; i++) {
		//	println(Chance.dealCard() + "\n");
		//}

		//canvas.add(site, ((getWidth() / 4)  - ((int)board.getWidth() / 2)) + 105, ((getHeight() / 2) - ((int)board.getHeight() / 2)));
		//initPositions();
		//drawBoard();

	}

	private GCanvas canvas;

	//Declare interactors

	private JLabel nameLabel;
	private JTextField nameField;

	private JButton diceButton;

	private JButton buyPropButton;

	private JButton useJailFreeCardButton;
	JButton payOutOfJailButton;

	private JButton endTurnButton;

	private JLabel statsLabel;
	private JButton playerButton;
	private JButton p1Button;
	private JButton p2Button;

	private GObject gobj; /* The object being clicked */ 
	private GPoint mouse_Pointer; /* The last mouse position */
	private int mouseButtonPressed;

	//Declare/assign monopoly board image
	GImage board = new GImage("images/board.gif");

	//Other universal variables and objects
	int boardWidth = (int)board.getWidth();
	int boardHeight = (int)board.getHeight();

	String nameFieldInput;
	//numPlayers not including user
	int numPlayers = 2;
	int doublesCount;

	Dice dice = new Dice();
	Player player = new Player();
	GOval playerToken = new GOval(TOKEN_DIMENSIONS, TOKEN_DIMENSIONS);
	Player p1 = new Player();
	GOval p1Token = new GOval(TOKEN_DIMENSIONS, TOKEN_DIMENSIONS);
	Player p2 = new Player();
	GOval p2Token = new GOval(TOKEN_DIMENSIONS, TOKEN_DIMENSIONS);

	GRect[] rectBoardPositions = new GRect[40];
	BoardPosition[] position = new BoardPosition[40];

	//Called whenever an action occurs
	public void actionPerformed(ActionEvent e) {
		//Entering name allows player to start
		if (e.getSource() == nameField) {
			nameFieldInput = nameField.getText();
			player.setName(nameFieldInput);
			nameLabel.setVisible(false);
			nameField.setVisible(false);
			diceButton.setVisible(true);
			statsLabel.setVisible(true);
			playerButton.setVisible(true);
			p1Button.setVisible(true);
			p2Button.setVisible(true);
		}
		//Output stats for following interactors
		if (e.getSource() == playerButton) {
			println(player + "\n");
		}
		if (e.getSource() == p1Button) {
			println(p1 + "\n");
		}
		if (e.getSource() == p2Button) {
			println(p2 + "\n");
		}
		//dice is rolled
		if (e.getSource() == diceButton) {
			dice.roll();
			if (player.isJailed) {
				tryGetOutOfJail(player);
			} else {
				payOutOfJailButton.setVisible(false);
				println("\n" + player.getName() + " " + dice);
				movePlayer(player);
				playerAction(player);
				//When a player rolls a double they get to go again
				if (checkForDoubles()) {
					if (player.isJailed) player.getOutOfJail();
					//roll doubles once, roll again
					println(player.getName() + " rolled doubles. Roll Again");
					dice.roll();
					println("\n" + player.getName() + " " + dice);
					movePlayer(player);
					playerAction(player);
					if (checkForDoubles()) {
						//roll doubles twice, roll again
						if (player.isJailed) player.getOutOfJail();
						println(player.getName() + " rolled doubles. Roll Again");
						dice.roll();
						println("\n" + player.getName() + " " + dice);
						//When a player rolls doubles three times they go to jail
						if (checkForDoubles()) {
							println("Oh no! You rolled doubles three times. Go to Jail!");
							player.goToJail();
						} else {
							movePlayer(player);
							playerAction(player);
						}
					}
				}
			}
			diceButton.setVisible(false);
			endTurnButton.setVisible(true);
		}
		//The player can buy the property they have landed on if this interactor is visible
		if (e.getSource() == buyPropButton) {
			buyPropButton.setVisible(false);
			BoardPosition prop = position[player.getPositionOnBoard()];
			String t = prop.getType();
			if (t.equalsIgnoreCase("site") || t.equalsIgnoreCase("railroad") || t.equalsIgnoreCase("electric company")
					|| t.equalsIgnoreCase("water works"))
			println(nameFieldInput + " has bought " + prop.getName() + "\n");
			//appropriate actions take place in the property class and player class
			prop.buyProperty(player);
			player.buyProperty(prop);
		}
		//Player chooses when to end turn and other players automatically have their turn
		if (e.getSource() == endTurnButton) {
			//take this moment to check for broke players
			//if game is not over after user's turn, next player goes
			if (!checkForGameOver()) {
				endTurnButton.setVisible(false);
				buyPropButton.setVisible(false);
				otherPlayerTurn(p1);
				//if game is not over after p1's turn continue
				if (!checkForGameOver()) {
					otherPlayerTurn(p2);
					//if game is not over after p2's turn, user's turn once again
					if (!checkForGameOver()) {
						diceButton.setVisible(true);
					}
				}
			}
		}
		//get out of jail free card is used
		if (e.getSource() == useJailFreeCardButton) {
			player.getOutOfJail();
		}
		//user pays out of jail
		if (e.getSource() == payOutOfJailButton) {
			player.takeFromBankAccount(JAIL_FEE);
			player.getOutOfJail();
			payOutOfJailButton.setVisible(false);
		}
		if (e.getSource() == useJailFreeCardButton) {
			player.haveJailFreeCard = false;
			player.getOutOfJail();
			payOutOfJailButton.setVisible(false);
		}
	}

	//Called when mouse is pressed
	public void mousePressed(MouseEvent e) {
		// Create GPoint that has X and Y coordinates of mouse cursor 
		mouse_Pointer = new GPoint(e.getPoint());

		// Determine which mouse button is pressed
		mouseButtonPressed = e.getButton();

		// Get the object at the mouse cursor position
		gobj = canvas.getElementAt(mouse_Pointer); 

		// prints the position that belongs to the object to the console
		for (int i = 0; i < NUM_POSITIONS; i ++) {
			if (gobj.equals(rectBoardPositions[i]) && mouseButtonPressed == LEFT_BUTTON) {
				println(position[i]);
			}
		}
	}


	/**Display the instructions of Monopoly for the user
	 * @param none
	 * @return none
	 */
	public void displayInstructions() {
		println("Hello! Let's play Monopoly!\n\n"
				+ "You will be the red token, P1 is the blue token, and P2 is the green token.\n"
				+ "You will be using interactors to play the game. Click any space on the board for more information about it and check player stats at the top of the screen.\n"
				+ "The rules of the game are:\n\n\t"
				+ "-You start at GO with $1500\n\t"
				+ "-You will play with two other players, P1 and P2\n\t"
				+ "-The game ends when any player goes broke\n\t"
				+ "-After landing on a space you can decide when to end your turn\n\t"
				+ "-When you land on a property, you can decide if you want to buy it or not\n\t"
				+ "-The rent for utilities is 4 times the dice roll if the owner owns only a single utility and 10 times it if they own both\n\t"
				+ "-You can get out of jail using a 'Get Out of Jail Free' card, paying $50, or rolling a double\n\t"
				+ "-If you roll a double three times consecutively, you will go to jail\n\t"
				+ "-This is a modified version of monopoly so there are a few changes:\n\t\t"
				+ "-There are no auctions, trading, or deals with other players\n\t\t"
				+ "-You cannot build houses or hotels\n\t\t"
				+ "-You are unable to mortgage properties\n\t\t"
				+ "\n\n"
				+ "Type in your name and press ENTER to start! :)");
	}

	/**Draw the monopoly board
	 * @param none
	 * @return none
	 */
	public void drawBoard() {
		//add image of board, centered on canvas
		int boardX = getWidth() / 4 - (boardWidth / 2);
		int boardY = getHeight() / 2 - (boardHeight / 2);
		canvas.add(board, boardX, boardY);

		//add GRect objects on each position
		initRectBoardPositions();
		drawRectBoardPositions();
	}

	/**Initialize the GRect objects that will make the board interactive
	 * @param none
	 * @return none
	 */
	public void initRectBoardPositions() {
		for (int i = 0; i < NUM_POSITIONS; i++) {
			//for corner positions, vertical positions, and horizontal positions respectively
			if (i == GO || i == JAIL || i == FREE_PARKING || i == GO_TO_JAIL) {
				rectBoardPositions[i] = new GRect(ALTERNATE_POSITION_WIDTH, POSITION_HEIGHT);
			} else if ((i > JAIL && i < FREE_PARKING) || (i > GO_TO_JAIL && i < NUM_POSITIONS)){
				rectBoardPositions[i] = new GRect(POSITION_HEIGHT, POSITION_WIDTH);
			} else rectBoardPositions[i] = new GRect(POSITION_WIDTH, POSITION_HEIGHT);
		}
	}

	/**Draw Grect objects over the board for interactive experience
	 * @param none
	 * @return none
	 */
	public void drawRectBoardPositions() {
		//South side of board
		for (int i = GO + 1; i < JAIL; i++) {
			canvas.add(rectBoardPositions[i], ((getWidth() / 4) + (boardWidth / 2) - ALTERNATE_POSITION_WIDTH) - (i * POSITION_WIDTH), (getHeight() / 2 + (boardHeight / 2) - POSITION_HEIGHT));
		}
		//West side
		for (int i = JAIL + 1; i < FREE_PARKING; i++) {
			canvas.add(rectBoardPositions[i], ((getWidth() / 4) - (boardWidth / 2)), (getHeight() / 2 + (boardHeight / 2) - POSITION_HEIGHT) - ((i - JAIL) * POSITION_WIDTH));
		}
		//North side
		for (int i = FREE_PARKING + 1; i < GO_TO_JAIL; i++) {
			canvas.add(rectBoardPositions[i], ((getWidth() / 4) - (boardWidth / 2) + ALTERNATE_POSITION_WIDTH) + ((i - FREE_PARKING - 1) * POSITION_WIDTH), (getHeight() / 2 - (boardHeight / 2)));
		}
		//East side
		for (int i = GO_TO_JAIL + 1; i < NUM_POSITIONS; i++) {
			canvas.add(rectBoardPositions[i], ((getWidth() / 4) + (boardWidth / 2) - POSITION_HEIGHT), (getHeight() / 2 - (boardHeight / 2) + POSITION_HEIGHT) + ((i - GO_TO_JAIL - 1) * POSITION_WIDTH));
		}
		//Go position
		canvas.add(rectBoardPositions[GO], (getWidth() / 4) + (boardWidth / 2) - ALTERNATE_POSITION_WIDTH, (getHeight() / 2 + (boardHeight / 2) - POSITION_HEIGHT));
		//Jail
		canvas.add(rectBoardPositions[JAIL], ((getWidth() / 4) - (boardWidth / 2)), (getHeight() / 2 + (boardHeight / 2) - POSITION_HEIGHT));
		//Free parking
		canvas.add(rectBoardPositions[FREE_PARKING], ((getWidth() / 4) - (boardWidth / 2)), (getHeight() / 2 - (boardHeight / 2)));
		//GO to jail
		canvas.add(rectBoardPositions[GO_TO_JAIL], ((getWidth() / 4) + (boardWidth / 2) - POSITION_HEIGHT), (getHeight() / 2 - (boardHeight / 2)));
		//make lines invisible
		for (int i = GO; i < NUM_POSITIONS; i++) {
			rectBoardPositions[i].setVisible(false);
		}
	}

	/**Initialize the site names by reading in the SiteNames.txt file
	 * @param none
	 * @return none
	 */
	private void initSiteNames() {
		try {
			// Open the file
			BufferedReader rd = new BufferedReader(new FileReader("SiteNames.txt"));
			Site.siteNames = readFileText(rd);

		} catch (IOException ex){ 
			println(ex.toString());
		}
	}

	/**Initialize the site colours by reading in the SiteColours.txt file
	 * @param none
	 * @return none
	 */
	public void initSiteColours() {
		try {
			// Open the file
			BufferedReader rd = new BufferedReader(new FileReader("SiteColours.txt"));
			Site.siteColours = readFileText(rd);

		} catch (IOException ex){ 
			println(ex.toString());
		}
	}

	/**Initialize the chance cards deck by reading in the ChanceCards.txt file
	 * @param none
	 * @return none
	 */
	public void initChanceCards() {
		try {
			// Open the file
			BufferedReader rd = new BufferedReader(new FileReader("ChanceCards.txt"));
			Chance.chanceCards = readFileText(rd);

		} catch (IOException ex){ 
			println(ex.toString());
		}
	}

	/**Initialize the community chest by reading in the CommunityChest.txt file
	 * @param none
	 * @return none
	 */
	public void initCommunityChestCards() {
		try {
			// Open the file
			BufferedReader rd = new BufferedReader(new FileReader("CommunityChest.txt"));
			CommunityChest.communityChestCards = readFileText(rd);

		} catch (IOException ex){ 
			println(ex.toString());
		}
	}

	/**Initialize the site info by reading in the Properties.txt file
	 * @param none
	 * @return none
	 */
	private void initSiteInfo() {
		// String array to store lines of text from the file
		String [] fileLinesOfText;

		try {
			// Open the file
			BufferedReader rd = new BufferedReader(new FileReader("Properties.txt"));
			fileLinesOfText = readFileText(rd);

			// Initialize the integer array with data from the text file
			for (int i = 0; i < Site.siteInfo.length; i++){
				Site.siteInfo[i] = convertTextToInt(fileLinesOfText[i]);
			}

		} catch(NumberFormatException ex) {// Catch non-integers characters in file
			println("Text file has non-numeric characters : \n" + ex.toString());
		} catch (IOException ex) { 
			println(ex.toString());
		}
	}

	/*
	 * Reads all lines of text from the specified reader and returns a String array
	 * containing those lines.  
	 */
	public static String[] readFileText (BufferedReader rd){
		// Use an ArrayList to dynamically and temporarily store each line of text
		ArrayList <String> lineText = new ArrayList<String>();
		try {
			while (true){
				String line = rd.readLine();
				if (line == null)
					break; // EOF
				lineText.add(line);
			}
			rd.close();
		} catch (IOException ex){
			// Throw all file I/O errors
			throw new ErrorException(ex);
		}
		// Move each line of text in the ArrayList into the String array
		String [] textArray = new String[lineText.size()];
		for (int i = 0; i < lineText.size(); i++) {
			textArray[i] = lineText.get(i);
		}
		// Return the constructed String array
		return textArray;
	}

	/*
	 * Convert each line of text into an integer value.
	 * Any text which is not a number throws an exception
	 */
	private int [] convertTextToInt (String line) {

		// Use StringTokenizer to break up each line of text into String tokens 
		StringTokenizer strTkn = new StringTokenizer(line," ");

		// Create an integer array with the same number of elements as tokens in the String
		int [] intArray = new int[strTkn.countTokens()];

		int i = 0;
		while (strTkn.hasMoreTokens()){
			/* Extract each String token from the input line, convert it
			 * to a String, then convert it to an Integer for the ArrayList
			 */
			intArray[i++] = Integer.parseInt(strTkn.nextToken());
		}

		return intArray;
	}

	/**Initialize all board positions
	 * @param none
	 * @return none
	 */
	public void initPositions() {
		int siteCount = 0;
		for (int i = 0; i < NUM_POSITIONS; i++) {
			if(i == GO) {
				position[i] = new Go();
			} else if (i == JAIL) {
				position[i] = new Jail();
			} else if (i == FREE_PARKING) {
				position[i] = new FreeParking();
			} else if (i == GO_TO_JAIL) {
				position[i] = new GoToJail();
			} else if (i == INCOME_TAX) {
				position[i] = new IncomeTax();
			} else if (i == LUXURY_TAX) {
				position[i] = new LuxuryTax();
			} else if (i == CHANCE || i == CHANCE_TWO || i == CHANCE_THREE) {
				position[i] = new Chance();
			} else if (i == COMMUNITY_CHEST || i == COMMUNITY_CHEST_TWO || i == COMMUNITY_CHEST_THREE) {
				position[i] = new CommunityChest();
			} else if (i == ELECTRIC_COMPANY) {
				position[i] = new ElectricCompany();
			} else if (i == WATER_WORKS) {
				position[i] = new WaterWorks();
			} else if (i == READING_RAILROAD) {
				position[i] = new Railroad("Reading Railroad");
			} else if (i == PENNSYLVANIA_RAILROAD) {
				position[i] = new Railroad("Pennsylvania Railroad");
			} else if (i == B_O_RAILROAD) {
				position[i] = new Railroad("B. & O. Railroad");
			} else if (i == SHORT_LINE) {
				position[i] = new Railroad("Short Line");
			} else {
				position[i] = new Site(i);
				//set names of sites and their colours
				position[i].name = Site.siteNames[siteCount];
				position[i].colour  = Site.siteColours[siteCount];
				//call initSiteInfo to initialize the site itself
				position[i].initSiteInfo(siteCount++);
			}
		}
	}

	/**Move player across board
	 * @param Player p
	 * @return none
	 */
	private void movePlayer(Player p) {
		int count = 0;
		while(count < dice.getSum()) {
			p.movePlayer();
			if (p.getPositionOnBoard() == GO) p.passGo();
			count++;
		}
		drawPlayer(p);
	}

	/**The player acts according to where they have landed
	 * @param Player p
	 * @return none
	 */
	private void playerAction(Player p) {
		//index the position that has been landed on
		int i = p.getPositionOnBoard();
		//name the position and let the user know where they are
		String posName = position[i].getName();
		println(p.getName() + " landed on " + posName + "\n");
		//check landing
		if (i == GO) {
			println("Pass Go!");
		} else if (i == JAIL) {
			if (p.isJailed) {
				tryGetOutOfJail(p);
			}
		} else if (i == FREE_PARKING) {
			println("Well... nothing happens here.");
		} else if (i == GO_TO_JAIL) {
			p.goToJail();
		} else if (i == INCOME_TAX) {
			p.payTax(IncomeTax.getIncomeTax());
		} else if (i == LUXURY_TAX) {
			p.payTax(LuxuryTax.getLuxuryTax());
		} else if (i == CHANCE || i == CHANCE_TWO || i == CHANCE_THREE) {
			//at any chance location a card is ealt, identified, and the appropriate actions take place
			String dealtCard = Chance.dealCard();
			println(dealtCard + "\n");
			int messageIndex = Chance.identifyCard(dealtCard);
			//when the actionChance method returns the messageIndex, the player has not completed what the card
			//has asked for
			if (p.actionChance(messageIndex, numPlayers) == messageIndex) {
				//player must pay chairman fee to all players
				if (messageIndex == 2) {
					//depending on whose turn it is, the method call has different parameters
					if ((p.getName()).equalsIgnoreCase(nameFieldInput)) {
						payUp(p1, p2, Chance.getChairmanFee());
					} else if ((p.getName()).equalsIgnoreCase("P1")) {
						payUp(player, p2, Chance.getChairmanFee());
					} else payUp(player, p1, Chance.getChairmanFee());
				}
				//player moves back three spaces and acts
				if (messageIndex == 8) {
					String t = position[i].getType();
					if (t.equalsIgnoreCase("site")) {
						buyOrPayRent(i, posName, p);
					} else if (t.equalsIgnoreCase("income tax")) {
						p.payTax(IncomeTax.getIncomeTax());
					} else if (t.equalsIgnoreCase("community chest")) {
						String dealtCardTwo = CommunityChest.dealCard();
						println(dealtCardTwo + "\n");
						int messageIndexTwo = CommunityChest.identifyCard(dealtCard);
						p.actionCommChest(messageIndexTwo, numPlayers);
					}
				}
				//subsequent chance cards require player to buy a property or pay its rent
				if (messageIndex == 9) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 10) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 11) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 12) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 13) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 14) {
					buyOrPayRent(i, posName, p);
				}
				if (messageIndex == 15) {
					buyOrPayRent(i, posName, p);
				}
			}
		} else if (i == COMMUNITY_CHEST || i == COMMUNITY_CHEST_TWO || i == COMMUNITY_CHEST_THREE) {
			//at any community chest location, a card is dealt, identified, and appropriate actions are taken
			String dealtCard = CommunityChest.dealCard();
			println(dealtCard + "\n");
			int messageIndex = CommunityChest.identifyCard(dealtCard);
			p.actionCommChest(messageIndex, numPlayers);
			//at any property the player must decide to buy or pay rent
		} else if (i == ELECTRIC_COMPANY || i == WATER_WORKS || i == READING_RAILROAD || i == PENNSYLVANIA_RAILROAD
				|| i == B_O_RAILROAD || i == SHORT_LINE || (position[i].getType()).equalsIgnoreCase("site"))
			buyOrPayRent(i, posName, p);
		//if player's position has changed, they are redrawn
		drawPlayer(p);
	}

	/**Player attempts to get out of jail
	 * @param Player p
	 * @return none
	 */
	public void tryGetOutOfJail(Player p) {
		//if the user is trying to get out of jail...
		if ((p.getName()).equalsIgnoreCase(nameFieldInput)) {
			//can pay to get out of jail
			payOutOfJailButton.setVisible(true);
			if (p.haveJailFreeCard) {
				//can use the card if possible
				useJailFreeCardButton.setVisible(true);
			} else {
				//try to roll doubles to leave jail, only get three tries as per rules
				println("\n" + player.getName() + " " + dice);
				if (checkForDoubles()) {
					println(p.getName() + " rolled doubles and can exit jail!");
					p.getOutOfJail();
					movePlayer(p);
				} else {
					doublesCount++;
					println("Aw! " + p.getName() + " need to roll a double to get out of jail!");
				}
				//after 3 tries they must pay the fee
				if (doublesCount == 3) {
					p.takeFromBankAccount(JAIL_FEE);
					p.getOutOfJail();
					doublesCount = 0;
				}
			}
		} else {
			//if another player is trying to get out of jail...
			//they use the card if possible
			if (p.haveJailFreeCard) {
				println(p.getName() + " used their get out of jail free card.");
				p.haveJailFreeCard = false;
			} else {
				//otherwise pay to get out of jail
				println(p.getName() + " payed $50 to get out of jail.");
				p.takeFromBankAccount(JAIL_FEE);
			}
			p.getOutOfJail();
		}
	}

	/**Check the rolled dice for doubles
	 * @param none
	 * @return boolean
	 */
	public boolean checkForDoubles() {
		if(dice.getD1() == dice.getD2()) return true;
		return false;
	}

	/**The player will buy the property they have landed on or pay the rent due
	 * @param int index
	 * @param String posName
	 * @param Player p
	 * @return none
	 */
	public void buyOrPayRent(int index, String posName, Player p) {
		//if position isn't owned, give user the oppurtunity to buy
		if (!(position[index].getOwnedStatus())) {
			if ((p.getName()).equalsIgnoreCase(nameFieldInput)) {
				buyPropButton.setVisible(true);
			} else {
				//other players will automatically buy the property if they have the money
				if (p.getMoneyInBank() > position[index].getCost()) {
					BoardPosition prop = position[index];
					String t = prop.getType();
					if (t.equalsIgnoreCase("site") || t.equalsIgnoreCase("railroad") || t.equalsIgnoreCase("electric company")
							|| t.equalsIgnoreCase("water works")) {
						println(p.getName() + " has bought " + prop.getName() + "\n");
						//appropriate actions take place in the property class and player class
						prop.buyProperty(p);
						p.buyProperty(prop);
					}
				}
			}
		} else {
			//otherwise determine the owner and pay the rent due
			Player owner = position[index].getOwner();
			if (!owner.equals(p)) {
				println(posName + " is owned by " + owner.getName() + ". Time to pay up!");
				payRent(p, owner, index);
			}
		}
	}

	/**Draw player's token on the monopoly board
	 * @param Player p
	 * @return none
	 */
	public void drawPlayer(Player p) {
		//according to the player, draw the appropriate token
		if ((p.getName()).equalsIgnoreCase(nameFieldInput)) {
			playerToken.setLocation(findTokenX(p), findTokenY(p));
			canvas.add(playerToken);
		}
		if ((p.getName()).equalsIgnoreCase("P1")) {
			p1Token.setLocation(findTokenX(p), findTokenY(p));
			canvas.add(p1Token);
		}
		if ((p.getName()).equalsIgnoreCase("P2")) {
			p2Token.setLocation(findTokenX(p), findTokenY(p));
			canvas.add(p2Token);
		}
	}

	/**Find x position to draw token
	 * @param Player p
	 * @return int x position of token
	 */
	public int findTokenX(Player p) {
		//get player's position and the coinciding GRect object
		int pos = p.getPositionOnBoard();
		GRect posRect = rectBoardPositions[pos];
		return (int) (posRect.getX() + (posRect.getWidth() / 2) - (TOKEN_DIMENSIONS / 2));
	}

	/**Find y position to draw token
	 * @param Player p
	 * @return int y position of token
	 */
	public int findTokenY(Player p) {
		//get player's position and the coinciding GRect object
		int pos = p.getPositionOnBoard();
		GRect posRect = rectBoardPositions[pos];
		return (int) (posRect.getY() + (posRect.getHeight() / 2) - (TOKEN_DIMENSIONS / 2));
	}

	/**Used to pay fees to all players in game (directly for the purpose of a chance card message)
	 * @param Player p1
	 * @param Player p2
	 * @param int value
	 * @return void
	 */
	public void payUp (Player p1, Player p2, int value) {
		p1.addToBankAccount(value);
		p2.addToBankAccount(value);
	}

	/**Rent is paid to the appropriate player
	 * @param Player payer
	 * @param Player payee
	 * @param int pos
	 * @return none
	 */
	public void payRent(Player payer, Player payee, int pos) {
		BoardPosition prop = position[pos];
		int amount;
		//get the correct rent amount for the property in question
		if ((prop.getType()).equalsIgnoreCase("site")) {
			amount = prop.getRent();
		} else if ((prop.getType()).equalsIgnoreCase("railroad")) {
			amount = getRailroadRent(payee);
		} else {
			amount = getUtilityRent(payee);
		}
		//transfer the money
		payer.takeFromBankAccount(amount);
		payee.addToBankAccount(amount);
	}

	/**Determines the rent that is due for a utility
	 * @param Player owner
	 * @return int rent for utility
	 */
	public int getUtilityRent(Player owner) {
		//if owner owns only one utility, rent is 4 times the dice roll
		//if they own two, the rent is 10 times the dice roll
		if (owner.getNumUtilsOwned() == 1) {
			return dice.getSum() * 4;
		} else return dice.getSum() * 10;
	}

	/**Determines the rent that is due for a railroad
	 * @param Player owner
	 * @return int rent for railroad
	 */
	public int getRailroadRent(Player owner) {
		//rent depends on the number of railroads the owner owns
		int railroadsOwned = owner.getNumRailroadsOwned();
		if (railroadsOwned == 1) {
			return Railroad.rentOneR;
		} else if (railroadsOwned == 2) {
			return Railroad.rentTwoR;
		} else if (railroadsOwned == 3) {
			return Railroad.rentThreeR;
		} else return Railroad.rentFourR;
	}

	/**The opponents of the user play their turns
	 * @param Player p
	 * @return none
	 */
	public void otherPlayerTurn(Player p) {
		dice.roll();
		if (p.isJailed) {
			tryGetOutOfJail(p);
		} else {
			println(p.getName() + " " + dice);
			movePlayer(p);
			playerAction(p);

			//check doubles
			if (checkForDoubles()) {
				if (p.isJailed) p.getOutOfJail();
				//roll doubles once, roll again
				println(p.getName() + " rolled doubles. Roll Again");
				dice.roll();
				println("\n" + p.getName() + " " + dice);
				movePlayer(p);
				playerAction(p);
				if (checkForDoubles()) {
					//roll doubles twice, roll again
					if (p.isJailed) p.getOutOfJail();
					println(p.getName() + " rolled doubles. Roll Again");
					dice.roll();
					println("\n" + p.getName() + " " + dice);
					//When a player rolls doubles three times they go to jail
					if (checkForDoubles()) {
						println("Oh no! " + p.getName() + " rolled doubles three times. Go to Jail!");
						p.goToJail();
					} else {
						movePlayer(p);
						playerAction(p);
					}
				}
			}
		}
	}

	/**Determines if a player is broke, if so the game is over
	 * @param none
	 * @return boolean
	 */
	public boolean checkForGameOver() {
		//if any of the players are broke
		if (player.getMoneyInBank() <= 0 || p1.getMoneyInBank() <= 0 || p2.getMoneyInBank() <= 0) {
			//remove interactors vital to gameplay, inform players that the game is over
			diceButton.setVisible(false);
			buyPropButton.setVisible(false);
			endTurnButton.setVisible(false);
			println("Someone is broke!\nGAME OVER!");
			return true;
		}
		return false;
	}
}
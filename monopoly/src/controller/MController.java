/**
 * CSCI 205 - Software Design and Engineering
 * Name: Arjuna Kankipati
 * Semester: Fall 2013
 * Work: FinalProject
 * Created: Dec 5, 2013, 8:00:47 PM
 */

package controller;

import monopoly.*;
import monopoly.tiles.Property;
import monopoly.tiles.TileType;

import gui.MMainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

/**
 * @author ajrk001
 *
 */
public class MController 
{
	//
	// Member Variables
	//
	
	private MMainFrame theView;
	private Monopoly theGame;
	
	private int numHours;
	private int numPlayers;
	private String[] playerNames;
	private String aiDifficulty;
	
	//
	// Constructors
	//
	
	public MController(MMainFrame theView, Monopoly theGame, int hours, int players, String[] names, String aiDiff, boolean createPlayers)
	{
		// save away the view and the model
		this.theView = theView;
		this.theGame = theGame;

		// set the number of hours
		this.numHours = hours;
		this.numPlayers = players;
		playerNames = names;
		
		aiDifficulty = aiDiff;
		
		if(createPlayers)
		{
			// create the number of players
			this.theGame.addPlayer(new Player(playerNames[0], numHours, theGame.getBoard().getNumTiles()));
		
		
			// create new AIPlayers
			// first set the difficulty/ratio to buying
			float propBuyRatio = 0;
			if(aiDifficulty.equals("Easy")) { propBuyRatio = 20; }
			else if(aiDifficulty.equals("Medium")) { propBuyRatio = 40; }
			else { propBuyRatio = 60; }
			
			for(int x = 1; x < numPlayers; x++)
			{
				this.theGame.addPlayer(new AIPlayer(propBuyRatio, playerNames[x], hours, theGame));
			}
		}
		
		setRollDiceFunction();
		setMortgageFunction();
		setBuyButtonFunction();
		
		populateBoardFieldsFromModel();
		setMenuBarFunction();		
	}
	
	//
	// Main Functions
	//
	
	private void setBuyButtonFunction()
	{
		theView.getProperties().getBtnBuy().addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				theGame.handleBuyRequest(true);
				Player player = theGame.getCurrentPlayer();
				double[] money = new double[theGame.getPlayers().length];
				for(int i = 0 ; i < theGame.getPlayers().length ; i++){
					money[i] = theGame.getPlayers()[i].getMoney();
				}
				//theView.getControl().setMoneyVals(money);
				theView.getProperties().changeBuyState(false);
				// Update model
			}
		});
	}
	
	private void setRollDiceFunction() {
		RollDiceAction actionListener = new RollDiceAction(theView, theGame);
		
		theView.getControl().setRollDiceAction(actionListener);
	}
	
	private void setMortgageFunction() {
		theView.getControl().setMortgageAction(new MortgageListener(theGame.getCurrentPlayer(), theView));
	}
	
	private void setMenuBarFunction()
	{
		MenuListener listen = new MenuListener(this);
		theView.getMMenuBar().setActionListener(listen);

	}
	
	public void populateBoardFieldsFromModel() {
		JTextArea[] props = theView.getTheBoard().getPropertyLabels();		// get all the property labels
		Board theBoard = theGame.getBoard();								// get the board itself
		int x = 0;															// need two separate incrementers to go through the props array
		for(int y = 0; y < theBoard.getNumTiles(); y++)						// parse through the board and tiles 
		{
			Property prop = theBoard.getPropertyAt(y);					// gets property or null if not property
			if(prop != null && prop.getTileType() == TileType.PROPERTY) // check if not null, and double check not a railroad or utility
			{
				props[x].setText(prop.getName()); // set text to name
				x++; // Increment props incrementer
			}
		}
	}

	/**
	 * @return the theView
	 */
	public MMainFrame getTheView() {
		return theView;
	}

	/**
	 * @return the theGame
	 */
	public Monopoly getTheGame() {
		return theGame;
	}

	/**
	 * @return the numHours
	 */
	public int getNumHours() {
		return numHours;
	}

	/**
	 * @return the numPlayers
	 */
	public int getNumPlayers() {
		return numPlayers;
	}

	/**
	 * @return the playerNames
	 */
	public String[] getPlayerNames() {
		return playerNames;
	}

	/**
	 * @return the propBuyRatio
	 */
	public String getAIDifficulty() {
		return aiDifficulty;
	}
}

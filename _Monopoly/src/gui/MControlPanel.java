/** 
* CSCI 205 - Software Design and Engineering
 * Name: Arjuna Kankipati
 * Semester: Fall 2013
 * Work: FinalProject
 * Created: Nov 15, 2013, 10:13:56 AM
 */
package gui;


import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * @author ajrk001
 * A JPanel that will contain properties of a game of monopoly
 */
public class MControlPanel extends JPanel
{
	
	JLabel[] moneyLabels;
	JLabel[] nameLabels;
	
	JButton btnRollDice;
	JButton btnMortgage;
	
	int numPlayers;
	
	Font boldFont = new Font("Tahoma", Font.BOLD, 11);
	
	public MControlPanel(int players) 
	{
		numPlayers = players;
		this.moneyLabels = new JLabel[numPlayers];
		this.nameLabels = new JLabel[numPlayers];
		this.setLayout(new GridLayout(numPlayers * 2 + 2, 1, 0, 0));		
		
		nameLabels[0] = createLabel("Your Hours:");
		moneyLabels[0] = createLabel("0");
		moneyLabels[0].setFont(boldFont);
		
		for(int x = 1; x < numPlayers; x++)
		{
			nameLabels[x] = createLabel("Player " + x + " Hours:");
			moneyLabels[x] = createLabel("0");
			moneyLabels[x].setFont(boldFont);
		}
		
		btnRollDice = new JButton("Roll Dice!");
		btnRollDice.setSize(50, 20);
		
		btnMortgage = new JButton("Mortgage!");
		btnMortgage.setActionCommand("StartMortgage");
		btnMortgage.setSize(50, 20);
		
		for(int x = 0; x < numPlayers; x++)
		{
			this.add(nameLabels[x]);
			this.add(moneyLabels[x]);
		}
		this.add(btnRollDice);
		this.add(btnMortgage);
	}
	
	/**
	 * function to remove a player from this panel, such that it will hide the data
	 * @param player
	 */
	public void removePlayer(int player)
	{
		JLabel[] tempArrNames = new JLabel[numPlayers];
		JLabel[] tempArrMoney = new JLabel[numPlayers];
		
		for(int x = 0; x < numPlayers; x++) 
		{
			tempArrNames[x] = nameLabels[x];
			tempArrMoney[x] = moneyLabels[x];
		}
		
		nameLabels = new JLabel[numPlayers-1];
		moneyLabels = new JLabel[numPlayers-1];
	}
	
	public void bankruptPlayer(int player)
	{
		moneyLabels[player].setText("Bankrupt!");
	}
	
	/**
	 * This function will create a new JLabel that has a horizontal alignment set so that
	 * repeated lines of code are not needed
	 * @param text - a String representing the text the JLabel should contain
	 * @return a new JLabel with a central alignment, with the text being the parameter passed
	 */
	public JLabel createLabel(String text)
	{
		JLabel temp = new JLabel(text);
		temp.setHorizontalAlignment(SwingConstants.CENTER);
		return temp;
	}
	
	/**
	 * This function will set the money label for a player at "index" to "money"
	 * @param player - The index of the player to change to
	 * @param money - A double array representing four players money
	 */
	public void setMoneyLabel(int player, double money)
	{
		if(!moneyLabels[player].getText().equals("Bankrupt!"))
		{
			moneyLabels[player].setText(Double.toString(money));
		}
	}
	
	/**
	 * This function will take in a array that represents four players name,
	 * and set the labels as needed 
	 * @param names - names represented as string in array form
	 */
	public void setNames(String[] names)
	{
		for(int x = 0; x < numPlayers; x++)
		{
			nameLabels[x].setText(names[x] + " hours!");
		}
	}
	
	public void setRollDiceAction(ActionListener al) {
		btnRollDice.addActionListener(al);
	}
	
	public void setMortgageAction(ActionListener al) {
		btnMortgage.addActionListener(al);
	}
	
	public JButton getBtnRollDice()
	{
		return btnRollDice;
	}


}

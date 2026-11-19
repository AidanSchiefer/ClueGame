package gameDisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import clueGame.*;

public class GameCardsPanel extends JPanel {
	// Member variables
	// The human player whose cards are displayed
	private Player _player;
	
	// One panel for each category of card
	private JPanel _peoplePanel;
	private JPanel _roomsPanel;
	private JPanel _weaponsPanel;
	
	// Seen card names for each category (kept in the order they were seen)
	private ArrayList<String> _seenPeople;
	private ArrayList<String> _seenRooms;
	private ArrayList<String> _seenWeapons;
	
	// Map of seen card name to the color of the player who holds it
	private HashMap<String, Color> _seenColors;
	
	// Constructor
	public GameCardsPanel(Player player) {
		_player = player;
		
		// Initialize the seen card lists
		_seenPeople = new ArrayList<String>();
		_seenRooms = new ArrayList<String>();
		_seenWeapons = new ArrayList<String>();
		_seenColors = new HashMap<String, Color>();
		
		// Set up the main panel, one row for each category
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder("Known Cards"));
		
		// Set the dimensions
		setPreferredSize(new Dimension(150, 25));
		
		// Create the category JPanels
		_peoplePanel = new JPanel();
		_peoplePanel.setBorder(new TitledBorder("People"));
		_roomsPanel = new JPanel();
		_roomsPanel.setBorder(new TitledBorder("Rooms"));
		_weaponsPanel = new JPanel();
		_weaponsPanel.setBorder(new TitledBorder("Weapons"));
		
		// Add the category panels, order matters
		add(_peoplePanel);
		add(_roomsPanel);
		add(_weaponsPanel);
		
		// Fill in the fields
		updatePanels();
	}
	
	// Method to update the panel
	public void updatePanels() {
		// Fill the people panel
		fillCategory(_peoplePanel, CardType.PERSON, _seenPeople);
		// Fill the room panel
		fillCategory(_roomsPanel, CardType.ROOM, _seenRooms);
		// Fill the weapons panel
		fillCategory(_weaponsPanel, CardType.WEAPON, _seenWeapons);
		
		// Redraw the panel
		revalidate();
		repaint();
	}
	
	// Method to update seen cards
	public void addSeenCard(Card card, Player player) {
		// Pick the list for this card's category
		ArrayList<String> seenList;
		// If the card is a person, the seen list becomes the seenPeople list
		if (card.getCardType() == CardType.PERSON) {
			seenList = _seenPeople;
		} 
		// If the card is a room, the seen list becomes the seenRooms list
		else if (card.getCardType() == CardType.ROOM) {
			seenList = _seenRooms;
		} 
		// Card type is weapon, the seen list becomes the seenWeapons list
		else {
			seenList = _seenWeapons;
		}
		
		// Only add the card if it has not been seen already
		if (!seenList.contains(card.getCardName())) {
			seenList.add(card.getCardName());
			_seenColors.put(card.getCardName(), player.getColor());
		}
		
		// Show the new card
		updatePanels();
	}
	
	// Helper method to rebuild one category panel
	private void fillCategory(JPanel panel, CardType type, ArrayList<String> seenList) {
		// Clear out the old fields
		panel.removeAll();
		panel.setLayout(new GridLayout(0, 1));
		
		// Hand section
		// Create a label for the in hand section
		panel.add(new JLabel("In Hand:"));
		
		// Tracker boolean to track if the hand is empty
		boolean handEmpty = true;
		
		// Loop through the players hand
		for (Card card: _player.getHand()) {
			// If the current card type matches the parameter card type, make a generic field and set handEmpty to false
			if (card.getCardType() == type) {
				panel.add(makeField(card.getCardName(), Color.white));
				handEmpty = false;
			}
		}
		// If hand is empty, create a generic empty field
		if (handEmpty) {
			panel.add(makeField("None", Color.white));
		}
		
		// Seen section
		panel.add(new JLabel("Seen:"));
		if (seenList.isEmpty()) {
			panel.add(makeField("None", Color.white));
		}
		for (String name: seenList) {
			panel.add(makeField(name, _seenColors.get(name)));
		}
	}
	
	// Helper method to create a read only text field
	private JTextField makeField(String text, Color color) {
		// Create a JTextField
		JTextField field = new JTextField(text);
		// Set editable to false
		field.setEditable(false);
		// Set the background to the input color
		field.setBackground(color);
		// Return the field
		return field;
	}
	
	// Main method to display the panel and test updating it
	public static void main(String[] args) {
		// Run all of the GUI code on the event dispatch thread
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create the players
				HumanPlayer human = new HumanPlayer("Santa", "Red", 0, 0);
				ComputerPlayer buddy = new ComputerPlayer("Buddy", "Green", 0, 0);
				ComputerPlayer rudolph = new ComputerPlayer("Rudolph", "Brown", 0, 0);
				ComputerPlayer elfy = new ComputerPlayer("Elfy", "Yellow", 0, 0);
				
				// Give the human some cards
				human.updateHand(new Card("Santa", CardType.PERSON));
				human.updateHand(new Card("Candy Cane", CardType.WEAPON));
				human.updateHand(new Card("Snowball", CardType.WEAPON));
				
				// Create the panel
				GameCardsPanel panel = new GameCardsPanel(human);
				
				// Display the panel in a frame
				JFrame frame = new JFrame("Known Cards");
				frame.setContentPane(panel);
				frame.setSize(250, 750);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
				// Update the data to show seen cards
				panel.addSeenCard(new Card("Buddy", CardType.PERSON), buddy);
				panel.addSeenCard(new Card("Rudolph", CardType.PERSON), rudolph);
				panel.addSeenCard(new Card("Elfy", CardType.PERSON), elfy);
				panel.addSeenCard(new Card("Sweets Room", CardType.ROOM), buddy);
				panel.addSeenCard(new Card("Reindeer Pen", CardType.ROOM), rudolph);
				panel.addSeenCard(new Card("Living Room", CardType.ROOM), elfy);
				panel.addSeenCard(new Card("Candle", CardType.WEAPON), buddy);
				panel.addSeenCard(new Card("Malet", CardType.WEAPON), elfy);
			}
		});
	}
}

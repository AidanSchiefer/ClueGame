package gameDisplay;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.*;

public class GameControlPanel extends JPanel{
	
	// Private attributes for the JPanel
	private JTextField _name;
	private JTextField _theGuess;
	private JTextField _theGuessResult;
	private JTextField _rowNum;
	
	// Control Panel Constructor
	public GameControlPanel() {
		// Create a layout with two rows
		setLayout(new GridLayout(2, 0));
		
		// Create the name panel, 1 row, 4 elements
		JPanel panel = createBottomTopPanel();
		
		// Add the panel to the frame
		add(panel);
	
		// Create a button panel
		panel = createBottomBottomPanel();
		
		// Add the panel to the frame
		add(panel);
		
		//makes text fields uneditable
		_name.setEditable(false);
		_rowNum.setEditable(false);
		_theGuess.setEditable(false);
		_theGuessResult.setEditable(false);
	}
	
	// Private method to create the top panel
	private JPanel createBottomTopPanel() {
		// Create a JPanel object
		JPanel panel = new JPanel();
		
		// Use a grid layout, 1 row, 4 elements (label, text)
		panel.setLayout(new GridLayout(1, 4));
		
		// Create subpanel 1, 2 rows, 1 element		
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout(new GridLayout(2, 1));
		
		// Create a JLabel
		JLabel nameLabel = new JLabel("Whose Turn?");
		
		// Create a name text field
		_name = new JTextField(20);
		
		// Add the name label and name to subPanel1
		subPanel1.add(nameLabel);
		subPanel1.add(_name);
		
		// Add subPanel1 to panel
		panel.add(subPanel1);
		
		// Create a second subpanel JPanel, 1 row 2 elements
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new GridLayout(1, 2));
		
		// Create a JLabel
		JLabel rowLabel = new JLabel("Roll:");
		
		// Create a JTextField
		_rowNum = new JTextField(5);
		
		// Add the row label and text field to subPanel2
		subPanel2.add(rowLabel);
		subPanel2.add(_rowNum);
		
		// Add subPanel2 to panel
		panel.add(subPanel2);
		
		// Create an accusation JButton
		JButton accusationButton = new JButton("Make Accusation");
		
		// Add the accusation button to the panel
		panel.add(accusationButton);
		
		// Create a next button
		JButton nextButton = new JButton("Next");
		
		// Add the next button to the panel
		panel.add(nextButton);
		
		// Return the panel
		return panel;
	}
	
	// Private method to create a button panel
	private JPanel createBottomBottomPanel() {
		// Create a new panel, 0 rows, 2 elements
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		// Create subPanel1, 1 row, 0 elements
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout(new GridLayout(1, 0));
		
		// Fill the guess label
		_theGuess = new JTextField(20);
		
		// Add the label to subPanel1, then set the border
		subPanel1.add(_theGuess);
		subPanel1.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		// Add subPanel1 to the panel
		panel.add(subPanel1);
		
		// Create subPanel2, 1 row, 0 elements
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new GridLayout(1, 0));
		
		// Fill the guess result label
		_theGuessResult = new JTextField(20);
		
		// Add the label to subPanel2, then set the border
		subPanel2.add(_theGuessResult);
		subPanel2.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		
		// Add subPanel2 to the panel
		panel.add(subPanel2);
		
		// Return the panel
		return panel;
	}
	
	// Setter method to set the guess field for the GUI
	public void setGuess(String guess) {
		_theGuess.setText(guess);
	}
	
	// Setter method to set the guess result field for the GUI
	public void setGuessResult(String guessResult) {
		_theGuessResult.setText(guessResult);
	}
	
	// Setter method for the player turn and roll
	public void setTurn(Player player, int roll) {
		_name.setText(player.getName());
		_name.setBackground(player.getColor());
		_rowNum.setText(Integer.toString(roll));
	}

	
	public static void main(String[] args) {
        // Create the JFrame with all the normal functionality
		JFrame frame = new JFrame();
		
		// Create the panel
		GameControlPanel panel = new GameControlPanel();
		
		// Add the panel to the frame
		frame.setContentPane(panel);
		
		// Size the frame
		frame.setSize(750, 180);
		
		// Allow the frame to close
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Make the frame visible
		frame.setVisible(true);
		
		// Test filling in the data
		panel.setTurn(new ComputerPlayer("Santa", "Red", 0, 0), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");
	}
}

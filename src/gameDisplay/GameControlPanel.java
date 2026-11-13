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

public class GameControlPanel extends JPanel{
	
	// Private attributes for the JPanel
	private JTextField name;
	
	// Control Panel Constructor
	public GameControlPanel() {
		// Create a layout with two rows
		setLayout(new GridLayout(2, 0));
		
		// Create the name panel
		JPanel panel = createNamePanel();
		
		// Add the panel to the frame
		add(panel);
		
		// Create a button pannel
		panel = createButtonPanel();
		
		// Add the panel to the frame
		add(panel);
	}
	
	// Private method to create a name panel
	private JPanel createNamePanel() {
		return null;
	}
	
	// Private method to create a button pannel
	private JPanel createButtonPanel() {
		return null;
	}

	
	public static void main(String[] args) {
        // Create the JFrame with all the normal functionality
		JFrame frame = new JFrame();
		
		// Set the default closing behavior
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the title of the GUI window
		frame.setTitle("Clue Game");
		
		// Set the size of the GUI window
		frame.setSize(350, 150);
		
		// Create a gui and add it to the JFrame
		GameControlPanel gui = new GameControlPanel();
		
		frame.add(gui, BorderLayout.CENTER);
		
		// View the gui 
		frame.setVisible(true);
    }
}

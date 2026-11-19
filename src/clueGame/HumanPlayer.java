package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class HumanPlayer extends Player{
	// Constructor
	public HumanPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	// Implementation of updateHand
	@Override
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	// Getter method for the name
	public String getName() {
		return _name;
	}
	
	// Getter method for the color
	public Color getColor() {
		return _color;
	}
	
	// Getter method for the row
	public int getRow() {
		return _row;
	}
	
	// Getter method for the column
	public int getColumn() {
		return _column;
	}
	
	// Getter method for the players hand
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	// Draw method
	public void draw(Graphics g, int width, int height) {
		super.draw(g, width, height);
	}
}

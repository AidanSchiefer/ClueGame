package clueGame;

public class Card {
	// Member variables
	// Variable to hold the card name
	private String _cardName;
	// Variable to hold the card type
	CardType _cardType;
	
	// Constructor
	public Card(String name, CardType cardType) {
		_cardName = name;
		_cardType = cardType;
	}
	
	// Equals method
	public boolean equals(Card target) {
		// If the two cards equal each other, return true
		if ((this._cardName == target.getCardName()) && (this._cardType == target.getCardType())) {
			return true;
		}
		return false;
	}
	
	// Card name getter method
	public String getCardName() {
		return this._cardName;
	}
	
	// Card type getter method
	public CardType getCardType() {
		return this._cardType;
	}
}

package clueGame;

public class Solution {
	// Member variables
	// Private values for solution cards
	private Card _room, _person, _weapon;
	
	// Constructor
	public Solution(Card roomSol, Card personSol, Card weaponSol) {
		_room = roomSol;
		_person = personSol;
		_weapon = weaponSol;
	}
	
	// Getter method for the room solution
	public Card getRoomSolution() {
		return _room;
	}
	
	// Getter method for the person solution
	public Card getPersonSolution() {
		return _person;
	}
	
	// Getter method for the weapon solution
	public Card getWeaponSolution() {
		return _weapon;
	}
}

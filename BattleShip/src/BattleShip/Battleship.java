package BattleShip;

//I'm a cruiser and I should inherit a ship...
public class Battleship extends Ship {
	
	public Battleship(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public char drawShipStatusAtCell( boolean isDamaged ) {
		if (isDamaged==true) {
			return 'b';
		} else {
			return 'B';
		}
	}
	
	public int getLength()  {return 4;}
}
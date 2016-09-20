package BattleShip;

//I'm a cruiser and I should inherit a ship...
public class Carrier extends Ship {
	
	public Carrier(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public char drawShipStatusAtCell( boolean isDamaged ) {
		if (isDamaged==true) {
			return 'k';
		} else {
			return 'K';
		}
	}
	
	public int getLength()  {return 5;}
}
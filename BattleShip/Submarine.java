package BattleShip;

//I'm a cruiser and I should inherit a ship...
public class Submarine extends Ship {
	
	public Submarine(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public char drawShipStatusAtCell( boolean isDamaged ) {
		if (isDamaged==true) {
			return 's';
		} else {
			return 'S';
		}
	}
	
	public int getLength()  {return 3;}
}
package BattleShip;

//I'm a cruiser and I should inherit a ship...
public class Cruiser extends Ship {
	
	public Cruiser(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public char drawShipStatusAtCell( boolean isDamaged ) {
		if (isDamaged==true) {
			return 'c';
		} else {
			return 'C';
		}
	}
	
	public int getLength()  {return 3;}
}
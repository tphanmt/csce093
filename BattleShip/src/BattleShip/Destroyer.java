package BattleShip;

//I'm a destroyer and I should inherit a ship...
public class Destroyer extends Ship {
	
	public Destroyer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public char drawShipStatusAtCell( boolean isDamaged ) {
		if (isDamaged==true) {
			return 'd';
		} else {
			return 'D';
		}
	}
	
	public int getLength()  {return 2;}
}
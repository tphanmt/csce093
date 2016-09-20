package BattleShip;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class GameBoard
{
	int rowCount = 10;
	int colCount = 10;
	
	final String LINE_END = System.getProperty("line.separator"); 
	
	ArrayList< ArrayList< Cell > > cells;
	ArrayList< Ship > myShips = new ArrayList<Ship>();
	
	public GameBoard( int rowCount, int colCount )
	{
		this.rowCount = rowCount;
		this.colCount = colCount;
		
		//create the 2D array of cells
		/* ArrayList<Cell> newRow = new ArrayList<Cell>();
		for (int j = 0; j < colCount; j++) { //create arraylist row with cells # equal to columns
			newRow.add(new Cell()); 
		}
		this.cells = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < rowCount; i++) { //populate 2d array of cells with rows
			this.cells.add(newRow);
		}
		*/
		
		
		this.cells = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < rowCount; i++) { //populate 2d array of cells with rows
			ArrayList<Cell> newRow = new ArrayList<Cell>();
			for (int j = 0; j < colCount; j++) {
				newRow.add(new Cell());
			}
			this.cells.add(newRow);
		}
	}
	
	public String draw()
	{
		for (int i=0; i<rowCount+2; i++) {
			StringBuilder sb = new StringBuilder(colCount+2);
			sb.append("|");
			for (int j = 0; j<colCount; j++) {
				if (i == 0 || i == rowCount+1) { //top and bottom edges of board
					sb.append("-"); 
				} else { //actual playing area
					sb.append(this.cells.get(i-1).get(j).draw());
				}
			}
			sb.append("|");
			System.out.println(sb);
		}
		String x = "GameBoard completed.";
		return x;

		//draw the entire board... I'd use a StringBuilder object to improve speed
		//remember - you must draw one entire row at a time, and don't forget the
		//pretty border...
	}
	
	//add in a ship if it fully 1) fits on the board and 2) doesn't collide w/
	//an existing ship.
	//Returns true on successful addition; false, otherwise
	public boolean addShip( Ship s , Position sternLocation, HEADING bowDirection )
	{
		
		if (bowDirection == HEADING.WEST) {
			int xBow = sternLocation.x - s.getLength();
			if (xBow >= 0) { //fits on board?
				ArrayList<Cell> newShipPos = new ArrayList<Cell>();
				for (int i = xBow; i < sternLocation.x; i++) {
					if (this.cells.get(sternLocation.y).get(i+1).getShip() == null) { //collides with other ship?
						newShipPos.add(this.cells.get(sternLocation.y).get(i));
						//this.cells.get(sternLocation.y).get(i).setShip(s);
					} else {
						return false;
					}
				}
				//add ship
				for (int i = xBow; i < sternLocation.x; i++) {
					this.cells.get(sternLocation.y).get(i+1).setShip(s);
				}
				s.setPosition(newShipPos);
				this.myShips.add(s);
				return true;
			}
		} else if (bowDirection == HEADING.EAST) {
			int xBow = sternLocation.x + s.getLength();
			if (xBow <= colCount) {
				ArrayList<Cell> newShipPos = new ArrayList<Cell>();
				for (int i = sternLocation.x; i < xBow; i++) {
					if (this.cells.get(sternLocation.y).get(i).getShip() == null) { //collides with other ship?
						newShipPos.add(this.cells.get(sternLocation.y).get(i));
						//this.cells.get(sternLocation.y).get(i).setShip(s);
					} else {
						return false;
					}
				}
				//add ship
				for (int i = sternLocation.x; i < xBow; i++) {
					this.cells.get(sternLocation.y).get(i).setShip(s);
				}
				s.setPosition(newShipPos);
				this.myShips.add(s);
				return true;
			}
		} else if (bowDirection == HEADING.NORTH) {
			int yBow = sternLocation.y - s.getLength();
			if (yBow >= 0) {
				ArrayList<Cell> newShipPos = new ArrayList<Cell>();
				for (int i = yBow; i < sternLocation.y; i++) {
					if (this.cells.get(i).get(sternLocation.x).getShip() == null) { //collides with other ship?
						newShipPos.add(this.cells.get(i+1).get(sternLocation.x));
						//this.cells.get(sternLocation.y).get(i).setShip(s);
					} else {
						return false;
					}
				}
				//add ship
				for (int i = yBow; i < sternLocation.y; i++) {
					this.cells.get(i+1).get(sternLocation.x).setShip(s);
				}
				s.setPosition(newShipPos);
				this.myShips.add(s);
				return true;
			}
		} else {
			int yBow = sternLocation.y + s.getLength();
			if (yBow <= rowCount) {
				ArrayList<Cell> newShipPos = new ArrayList<Cell>();
				for (int i = sternLocation.y; i < yBow; i++) {
					if (this.cells.get(i).get(sternLocation.x).getShip() == null) { //collides with other ship?
						newShipPos.add(this.cells.get(i).get(sternLocation.x));
						//this.cells.get(sternLocation.y).get(i).setShip(s);
					} else {
						return false;
					}
				}
				//add ship
				for (int i = sternLocation.y; i < yBow; i++) {
					this.cells.get(i).get(sternLocation.x).setShip(s);
				}
				s.setPosition(newShipPos);
				this.myShips.add(s);
				return true;
			}
		}
		return false;
	}
	
	//Returns A reference to a ship, if that ship was struck by a missle.
	//The returned ship can then be used to print the name of the ship which
	//was hit to the player who hit it.
	//Ensure you handle missiles that may fly off the grid
	public Ship fireMissle( Position coordinate )
	{
		if (coordinate.x > colCount || coordinate.y > rowCount) {
			System.out.println("Keep it in the grid!");
		}
		return this.cells.get(coordinate.x).get(coordinate.y).getShip();
		
	}
	
	//Here's a simple driver that should work without touching any of the code below this point
	public static void main( String [] args )
	{
		System.out.println( "Hello World" );
		GameBoard b = new GameBoard( 10, 10 );	
		System.out.println( b.draw() );
		
		Ship s = new Cruiser( "Cruiser" );
		if( b.addShip(s, new Position(3,6), HEADING.WEST ) )
			System.out.println( "Added " + s.getName() + "Location is " );
		else
			System.out.println( "Failed to add " + s.getName() );
		
		s = new Destroyer( "Vader" );
		if( b.addShip(s, new Position(3,5), HEADING.NORTH ) )
			System.out.println( "Added " + s.getName() + "Location is " );
		else
			System.out.println( "Failed to add " + s.getName() );
		
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(3,5) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,4) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,3) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(0,6) );
		b.fireMissle( new Position(1,6) );
		b.fireMissle( new Position(2,6) );
		b.fireMissle( new Position(3,6) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(6,6) );
		System.out.println( b.draw() );
	}

}

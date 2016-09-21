package BattleShip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Client
{
	final String NEWL = System.getProperty("line.separator");
	
	private String name = "Player";
	PrintWriter out = null;
	BufferedReader in = null;
	GameManager man = null;
	GameBoard board = new GameBoard(10,10);
	GameBoard targets = new GameBoard(10,10);
	
	Client( BufferedReader in, PrintWriter out, GameManager manager )
	{
		this.in = in;
		this.out = out;
		this.man = manager;
	}
	
	public void playGame() throws IOException
	{
		this.out.println( NEWL + NEWL + "   Missiles Away! Game has begun" );
		this.out.println( "   To Launch a missle at your enemy:" );
		this.out.println( "F 2 4" );
		this.out.println( "Fires a missile at coordinate x=2, y=4." );
		
		while( this.processCommand()) // put Code Here to process in game commands, after each command, print the target board and game board w/ updated state )
		{
			out.println( "------------------------" );
			out.println( "Target Board:" + this.targets.draw() );
			out.println( "Your Ships: " + this.board.draw() );
			out.println( "   Waiting for Next Command...\n\n" );
			out.flush();
			
			//Perform test here to see if we have won or lost
			if (this.allMyShipsAreDestroyed()) {
				out.println("Enemy has sunk all your ships. :( You lose.");
				break;
			} else if (this.allEnemyShipsAreDestroyed()) {
				out.println("You destroyed all enemy ships! :D You win.");
				break;
			}
		}
		out.println("Exiting game.");
	}
	
	//Returns a bool, true iff all of this client's ships are destroyed
	boolean allMyShipsAreDestroyed()
	{
		//out.println(this.board.myShips);
		for (int i = 0; i < this.board.myShips.size(); i++) {
			if (this.board.myShips.get(i).isAlive()) {
				return false;
			}
		}
		return true;
	}

	//Returns a bool, true iff all of the opponent's ships are destroyed
	boolean allEnemyShipsAreDestroyed()
	{
		//out.println(this.targets.myShips);
		for (int i = 0; i < this.targets.myShips.size(); i++) {
			if (this.targets.myShips.get(i).isAlive()) {
				return false;
			}
		}
		return true;
	}

	//"F 2 4" = Fire command
	//"C Hello world, i am a chat message"
	//"D" - Redraw the latest game and target boards
	boolean processCommand() throws IOException
	{
		Scanner s = new Scanner(in);
		String cmd = s.next();
		if (s.equals("F")) {
			String [] Fcmd = cmd.split(" ");
			processFireCmd(Fcmd);
		}
		else if (cmd.equals("C"))
			processChatCmd(cmd);
		else if (cmd.equals("D"))
			getGameBoard().draw();
		else
			out.println("Invalid command");
		s.close();
		return true;
	}
	
	//When a fire command is typed, this method parses the coordinates and launches a missle at the enemy
	boolean processFireCmd( String [] s )
	{
		//s should be in format F row col
		int fireRow = Integer.parseInt(s[1]); 
		int fireCol = Integer.parseInt(s[2]);
		this.targets.fireMissle( new Position(fireRow, fireCol) );
		return true;
	}
	
	//Send a message to the opponent
	boolean processChatCmd( String s )
	{
		out.println(s.substring(2));
		return true;
	}
	
	GameBoard getGameBoard() { return this.board; }
	
	public void initPlayer() throws IOException 
	{
		//1.Get player name
		out.println("Welcome to BattleShip.\nPlease enter your name:");
		Scanner s = new Scanner(in);
		//try {
		this.name = s.next();
		//}
		//catch (NoSuchElementException e) {
		//	System.out.println(e.getMessage());
		//}
		//newPlayer.close();
		
		//2.Print out instructions
		
//Here's some nice instructions to show a client		
		out.println("   You will now place 2 ships. You may choose between either a Cruiser (C) " );
		out.println("   and Destroyer (D)...");
		out.println("   Enter Ship info. An example input looks like:");
		out.println("\nD 2 4 S USS MyBoat\n");
		out.println("   The above line creates a Destroyer with the stern located at x=2 (col)," );
		out.println("   y=4 (row) and the front of the ship will point to the SOUTH (valid" );
		out.println("   headings are N, E, S, and W.\n\n" );
		out.println("   the name of the ship will be \"USS MyBoat\"");
		//out.println("Enter Ship 1 information:" );
		out.flush();
		
		//Get ship locations from the player for all 2 ships (or more than 2 if you're using more ships)
		for (int i = 0; i < 2; i++) {
			out.println("Enter Ship " + i+1 + " information:");
			//Scanner s = new Scanner(in);
			String[] newShip = s.next().split(" ");
			out.println(newShip);
			
			String shipName = "";
			for (int j = 4; j < newShip.length; j++) {
				shipName += " " + newShip[j];
			}
			
			int xPos = Integer.parseInt(newShip[1]);
			int yPos = Integer.parseInt(newShip[2]);
			
			//default heading is west
			HEADING heading = HEADING.WEST;
			if (newShip[3].equals("N")) {
				heading = HEADING.NORTH;
			} else if (newShip[3].equals("S")) {
				heading = HEADING.SOUTH;
			} else if (newShip[3].equals("E")) {
				heading = HEADING.EAST;
			} else if (newShip[3].equals("W")) {
			} else {
				out.println("Improper heading input. Assumed to go WEST.");
			}
			
			if (newShip[0].equals("D")) {
				Ship ship = new Destroyer(shipName);
				if( this.board.addShip(ship, new Position(xPos,yPos), heading) )
					out.println( "Added " + ship.getName() + "Location is " + "(" + xPos + ", " + yPos + ")");
				else
					out.println( "Failed to add " + ship.getName() );
				
			} else if (newShip[0].equals("C")) {
				Ship ship = new Cruiser(shipName);
				if( this.board.addShip(ship, new Position(xPos,yPos), heading) )
					out.println( "Added " + ship.getName() + "Location is " + "(" + xPos + ", " + yPos + ")");
				else
					out.println( "Failed to add " + ship.getName() );
				
			} else {
				out.println("Ship of type " + newShip[0] + " does not exist.");
			}
			
		}
	
		
		//After all game state is input, draw the game board to the client
		this.board.draw();
		
		System.out.println( "Waiting for other player to finish their setup, then war will ensue!" );
		s.close();
	}
	
	String getName() { return this.name; }
	
	public static void main( String [] args )
	{
			
		
	}
}

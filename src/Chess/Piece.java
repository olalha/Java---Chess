package Chess;

import java.io.Serializable;

/* Piece
 * 
 * Fields - Private name and colour, Public position
 * 
 * An abstract class that serves as the superclass for all Pieces in the game
 */

public abstract class Piece implements Serializable {
	
	// Private references to the name and colour of the Piece
	private String name;
	private Colour colour;
	
	// Public reference to the position of the Piece
	public Position position;
	
	/* Piece Constructor
	 * 
	 * Input Arguments - The name, colour and the starting position of the Piece
	 * 
	 * Sets all the fields of the Piece to their appropriate values
	 */
	
	public Piece(String name, Colour colour, Position position) {
		
		this.name = name;
		this.colour = colour;
		this.position = position;
	}
	
	// Returns the name of the Piece
	public String getName() {
		return name;
	}
	
	// Returns the colour of the Piece
	public Colour getColour() {
		return colour;
	}
	
	// Returns a boolean value based on if the inputed position is a valid destination/move for the Piece
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		return true;
	}
}

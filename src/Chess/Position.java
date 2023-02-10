package Chess;

import java.io.Serializable;
import java.util.ArrayList;

/* Position
 * 
 * Fields - Position values; Row, Column integers
 * 
 * Functions as an object that stores a row and column value to represent a Position
 */

public class Position implements Serializable {
	
	// Public references to the row and column
	public int row;
	public int column;
	
	// Private reference to a list that will store all possible Positions
	private static ArrayList<Position> all = new ArrayList<Position>(8*8);
	
	/* Position Constructor
	 * 
	 * Input Arguments - A row integer, A column integer
	 * 
	 * Sets the Position Object Fields to their appropriate values
	 */
	
	public Position(int row, int column) {
		
		this.row = row;
		this.column = column;
	}
	
	// Returns the Position as a string
	public String asString () {
		
		return row + " : " + column;
	}
	
	// A static method that returns a List with all the Possible Positions 
	public static ArrayList<Position> allPositions() {
		
		if (all.size() == 0) {
		// If the List is empty, it creates the List
			
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					
					all.add(new Position(x, y));
				}
			}
		}
		
		return all;
	}
}

package Chess;

/* Colour Enumeration
 * 
 * Enumerations  - WHITE, BLACK
 * 
 * Allows a colour enumeration to only be set to one of two colours
 */

public enum Colour {
	
	WHITE,
	BLACK;
	
	// Returns the opposite colour to the colour inputed
	public Colour oppositeColour() {
		
		switch (this) {
		case WHITE:
			return Colour.BLACK;
		case BLACK:
			return Colour.WHITE;
		}
		return null;
	}
	
	// Returns the colour as a String
	public String asString() {
		return this.toString().toLowerCase();
	}
}

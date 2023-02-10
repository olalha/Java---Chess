package Pieces;

import Chess.BoardLayout;
import Chess.Colour;
import Chess.Piece;
import Chess.Position;
import Chess.Tools;

public class King extends Piece {
	
	public King(Colour c, Position position) {
		
		super("King", c, position);
	}
	
	@Override	
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		
		if ( Tools.differnce(this.position.row, position.row) < 2 && Tools.differnce(this.position.column, position.column) < 2 ) {
				
			if ( !boardLayout.isOccupiedByColour(position, getColour())) { return true; }
		}
		
		return false;
	}
}

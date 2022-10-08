package Pieces;

import Chess.BoardLayout;
import Chess.Colour;
import Chess.Piece;
import Chess.Position;
import Chess.Tools;

public class Queen extends Piece {
	
	public Queen(Colour c, Position position) {
		
		super("Queen", c, position);
	}
	
	@Override	
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		
		boolean isValid = true;
		
		if (this.position.row == position.row || this.position.column == position.column ) {
			
			for (Position arrayPosition : Tools.postionsBetweenStraight(this.position, position)) {
				
				if (!boardLayout.isAvalible(arrayPosition)) { isValid = false; }
			}
			
		} else if (Tools.differnce(this.position.row, position.row) == Tools.differnce(this.position.column, position.column) ) {
			
			for (Position arrayPosition : Tools.postionsBetweenDiagonal(this.position, position)) {
				
				if (!boardLayout.isAvalible(arrayPosition)) { isValid = false; }
			}
			
		} else { isValid = false; }
		
		if (boardLayout.isOccupiedByColour(position, getColour())) { isValid = false; }
		
		return isValid;
	}
}

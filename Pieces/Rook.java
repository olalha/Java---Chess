package Pieces;

import java.util.ArrayList;

import Chess.BoardLayout;
import Chess.Colour;
import Chess.Piece;
import Chess.Position;
import Chess.Tools;

public class Rook extends Piece {
	
	public Rook (Colour c, Position position) {
		
		super("Rook", c, position);
	}
	
	@Override	
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		
		boolean isValid = true;
		
		if (this.position.row == position.row || this.position.column == position.column) {
			
			for (Position arrayPosition : Tools.postionsBetweenStraight(this.position, position)) {
				
				if (!boardLayout.isAvalible(arrayPosition)) { isValid = false; }
			}
			
		} else { isValid = false; }
		
		if (boardLayout.isOccupiedByColour(position, getColour())) { isValid = false; }
		
		return isValid;  
	}

}

package Pieces;

import Chess.BoardLayout;
import Chess.Colour;
import Chess.Piece;
import Chess.Position;

public class Pawn extends Piece {
	
	private int startRow;
	
	public Pawn (Colour colour, Position position) {
		
		super("Pawn", colour, position);
		
		this.startRow = position.row;
	}
	
	@Override	
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		
		int one;
		int two;
		
		if (getColour() == Colour.WHITE) { one = -1; two = -2; } else { one = 1; two = 2; }
		
		boolean isValid = false;
		
		if (this.position.row + one == position.row && this.position.column == position.column && boardLayout.isAvalible(position)) {
			
			isValid = true;
			
		} else if (this.position.row + two == position.row && this.position.column == position.column && boardLayout.isAvalible(position)) {
			
			if (boardLayout.isAvalible( new Position (this.position.row + one, this.position.column))) {
			
				if ( this.position.row == startRow ) { 
					
					isValid = true; 
				}
			}
			
		} else if (this.position.row + one == position.row && this.position.column == position.column + one && !boardLayout.isAvalible(position)) {
			
			isValid = true;
			
		} else if (this.position.row + one == position.row && this.position.column == position.column - one && !boardLayout.isAvalible(position)) {
			
			isValid = true;
		}
		
		if (boardLayout.isOccupiedByColour(position, getColour())) { isValid = false; }
		
		return isValid;
	}
}
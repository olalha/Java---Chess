package Pieces;

import Chess.BoardLayout;
import Chess.Colour;
import Chess.Piece;
import Chess.Position;
import Chess.Tools;

public class Knight extends Piece {
	
	public Knight (Colour c, Position position) {
		
		super("Knight", c, position);
	}
	
	@Override	
	public boolean isValidMove(Position position, BoardLayout boardLayout) {
		
		boolean isValid = false;
		
		int x = Tools.differnce(this.position.row, position.row);
		int xx = Tools.differnce(this.position.column, position.column);
		
		if (x == 1 && xx == 2 || x == 2 && xx == 1) {
				
			if (!boardLayout.isOccupiedByColour(position, getColour())) { isValid = true; }
		}
		
		return isValid;
	}
}

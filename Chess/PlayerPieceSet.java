package Chess;

import java.util.ArrayList;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Queen;
import Pieces.Rook;

/* PlayerPieceSet
 * 
 * Fields - no Fields
 * 
 * Only contains a constructor method
 * Creates and sets new Pieces of the the inputed colour sets them in the inputed Piece[][] 
 */

public class PlayerPieceSet {
	
	/* PlayerPieceSet Constructor
	 * 
	 * Input Arguments - Colour of the Pieces, The Piece[][] to set the Pieces on
	 * 
	 * Creates and sets new Pieces of the the inputed colour and sets them in the inputed Piece[][]
	 */
	
	public PlayerPieceSet(Colour c, Piece[][] firstBoard) {
		
		ArrayList<Piece> peices = new ArrayList<Piece>();
		
		int x = 0;
		int xx = 0;
		
		if (c == Colour.WHITE) { x = 6; xx = 7; } else { x = 1; xx = 0; }
		
		for (int y = 0; y < 8; y++) {
			peices.add(new Pawn(c, new Position(x, y) )); 
		}
		peices.add(new Rook(c, new Position(xx, 0)));
		peices.add(new Rook(c, new Position(xx, 7)));
		peices.add(new Knight(c, new Position(xx, 1)));
		peices.add(new Knight(c, new Position(xx, 6)));
		peices.add(new Bishop(c, new Position(xx, 2)));
		peices.add(new Bishop(c, new Position(xx, 5)));
		peices.add(new Queen(c, new Position(xx, 4)));
		peices.add(new King(c, new Position(xx, 3)));
		
		// For each Piece created
		for (Piece piece : peices) {
			
			// Set the location of that Piece on the Piece[][] to itself
			firstBoard[piece.position.row][piece.position.column] = piece;
		}
	}
}

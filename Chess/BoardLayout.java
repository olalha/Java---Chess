package Chess;

import java.io.Serializable;
import java.util.ArrayList;
import Pieces.King;

/* BoardLayout
 * 
 * Fields - Multiple private variables; 2D Piece array, Lists of Board Pieces, King Pieces on the Board
 * 
 * Functions as Object that stores information about the Layout of Pieces on a chess board
 * Includes methods for easier interaction with the BoardLayout Object
 */

public class BoardLayout implements Serializable {
	
	// Private reference to the Piece[][] which stores the locations of Pieces on this BoardLayout
	private Piece[][] board;
	
	// Private references to Lists and Pieces on this BoardLayout 
	private ArrayList<Piece> whitePieces;
	private ArrayList<Piece> blackPieces;
	private Piece whiteKing;
	private Piece blackKing;
	
	/* BoardLayout Constructor
	 * 
	 * Input Arguments - The Piece[][] that stores Pieces
	 * 
	 * Initialises the Piece[][] to represent the layout of Pieces on the board
	 * Sorts Pieces into lists based on colour
	 * Locates the two Kings on the board
	 * If a King is missing, the winner is announced and the program exits
	 */
	
	public BoardLayout(Piece[][] board) {
		
		this.board = board;
		
		whitePieces = new ArrayList<Piece>();
		blackPieces = new ArrayList<Piece>();
		
		// For each possible Position
		for (Position position : Position.allPositions()) {
			
			if (!isAvalible(position)) {
			// There is a Piece at the Position
				
				Piece piece = pieceAt(position);
				
				// Adds it to the appropriate list
				// If its a King then it is assigned to its variable 
				switch(piece.getColour()) {
				case WHITE:
					
					whitePieces.add(piece);
					if (piece instanceof King && piece.getColour() == Colour.WHITE) { whiteKing = piece; }
					
				case BLACK:
					
					blackPieces.add(piece);
					if (piece instanceof King && piece.getColour() == Colour.BLACK) { blackKing = piece; }
				}
			}
		}
		
		// If any of the Kings are missing from the Board
		// The Game will end since a King has been kicked
		if (whiteKing == null) { 
			
			GameMain.board.ui.showMessageDialog("Game Over", "The king of Player white has been kicked.\nPlayer black wins!");
			System.exit(0);
		}
		
		if (blackKing == null) { 
			
			GameMain.board.ui.showMessageDialog("Game Over", "The king of Player black has been kicked.\nPlayer white wins!");
			System.exit(0);
		}
	}
	
	// Returns the list of Pieces of the inputed colour
	public ArrayList<Piece> getPiecesOfColour(Colour c) {
		
		switch(c) {
		case WHITE:
			return whitePieces;
		case BLACK:
			return blackPieces;
		default:
			return null;
		}
	}
	
	// Returns the King of the inputed colour
	public Piece getKingOfColour(Colour c) {
		
		switch(c) {
		case WHITE:
			return whiteKing;
		case BLACK:
			return blackKing;
		default:
			return null;
		}
	}
	
	// Returns a boolean based on if the inputed Position is available in the Piece[][]
	public boolean isAvalible(Position p) {
		
		if (board[p.row][p.column] == null) { return true; }
		
		return false;
	}
	
	// Returns the Piece at the inputed Position based on the Piece[][]
	public Piece pieceAt(Position p) {
		
		return board[p.row][p.column];
	}
	
	// Returns a boolean based on if the inputed Position is occupied by a Piece of the inputed colour on the Piece[][]
	public boolean isOccupiedByColour(Position position, Colour colour) {
		
		// Done in order to avoid a null pointer exception
		if (!isAvalible(position)) {
		
			if (pieceAt(position).getColour() == colour) { return true; }
		}
			
		return false;
	}
}

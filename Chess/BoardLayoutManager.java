package Chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import Pieces.Queen;

/* BoardLayoutManager
 * 
 * Fields -  Private BoardLayout stack
 * 
 * Manages all BoardLayouts
 * Moves Pieces on the board
 * Keeps a record of all previous BoardLayouts in a stack
 * Determines Check and CheckMate 
 */

public class BoardLayoutManager implements Serializable {
	
	// Private reference to the BoardLayout stack
	private Stack<BoardLayout> boardStack;
	
	public Colour turn = Colour.WHITE;
	
	/* BoardLayoutManager Constructor
	 * 
	 * Input Arguments - The first boardLayout to be used 
	 * 
	 * Initialises the BoardLayout stack
	 * Pushes the first BoardLayout on to the stack
	 */
	
	public BoardLayoutManager() {
		
		Piece[][] pieceArray = new Piece[8][8];
		
		new PlayerPieceSet(Colour.WHITE, pieceArray);
		new PlayerPieceSet(Colour.BLACK, pieceArray);
		
		BoardLayout firstBoardLayout = new BoardLayout(pieceArray);
		
		boardStack = new Stack<BoardLayout>();
		boardStack.push(firstBoardLayout);
	}
	
	// Returns the current turn colour
	public Colour getTurnColour() {
		return turn;
	}
		
	// Switches the turn colour
	// Sets the Navigational Text Field to display the current turn
	public void switchTurn() {
		
		turn = turn.oppositeColour();
			
		GameMain.board.ui.setTextPlayerTurn();
	}
	
	// Returns the size of the BoardLayout stack
	public int getStackSize() {
		return boardStack.size();
	}
	
	// Returns the BoardLayout on top of the stack i.e The current BoardLayout being used
	public BoardLayout getCurrentBoardLayout() {
		return boardStack.peek();
	}
	
	// Removes the BoardLayout on top of the stack 
	// Updates Positions of the Pieces to the BoardLayout under it
	public void implementPrevious() {
		
		boardStack.pop();
		
		updatePiecesFor(getCurrentBoardLayout());
	}
	
	// Adds a BoardLayout inputed as an argument to the stack 
	// Updates Positions of the Pieces on the new BoardLayout 
	private void implementNewBoardLayout(BoardLayout newBoardLayout) {
		
		boardStack.push(newBoardLayout);
		
		updatePiecesFor(getCurrentBoardLayout());
	}
	
	// Updates the Positions of all Pieces based on where they are on the inputed BoardLayout
	private void updatePiecesFor(BoardLayout newBoardLayout) {
		
		// For all possible Positions
		for (Position p : Position.allPositions()) {
			
			if (!newBoardLayout.isAvalible(p)) {
			// There is a Piece at this Position
				
				// Sets the Position of the Piece to the position that its actually on now
				newBoardLayout.pieceAt(p).position = p;
			}
		}
	}
	
	// Moves an inputed Piece to an inputed position on the BoardLayout
	// A new BoardLayout is created with the change implemented and added to the BoardLayout stack
	public void movePiece(Piece piece, Position destinationPosition) {
		
		// Creates a new 2D Piece array that clones current BoardLayout in play
		Piece[][] newBoard = new Piece[8][8];
		for (Position p : Position.allPositions()) {
			newBoard[p.row][p.column] = getCurrentBoardLayout().pieceAt(p);
		}
 		
		// Changes it to include the new move
		newBoard[piece.position.row][piece.position.column] = null; // Old Position has no Piece now e.i null
		newBoard[destinationPosition.row][destinationPosition.column] = piece; // New Position has the Piece now
		
		// Implements the new BoardLayout and puts it in play
		implementNewBoardLayout(new BoardLayout(newBoard));
	}
	
	/* inquireCheck Method
	 * 
	 * Input Arguments - A BoardLayout to analyse, the Colour of player who may be in Check or CheckMate
	 * 
	 * Sets the defenseKing variable to the King of the inputed colour
	 * Determines whether the King of the inputed colour can be kicked i.e is in harm
	 * Informs the User if in Check
	 * Informs the User if in CheckMate and if so, exits the program 
	 */
	
	public void inquireCheck(BoardLayout boardLayout, Colour c) {
		
		// Initialises two booleans set to false 
		boolean check = false;
		boolean checkMate = false;
		
		// Sets boolean variables to their appropriate values
		if (isCheck(boardLayout, c)) {
			
			check = true;
			checkMate = isCheckmate(boardLayout, c);
		}
		
		if (checkMate) {
		// There is a CheckMate
			
			GameMain.board.ui.update();
			
			GameMain.board.ui.showMessageDialog("Game Over", "CheckMate!\nPlayer "
			+ c.oppositeColour().asString() + " has won!");
			
			System.exit(0);
		}
		
		if (check) { 
		// There is a Check
			
			GameMain.board.ui.showMessageDialog("Check", " Player "
			+ c.oppositeColour().asString() + " says 'Check'!");
		}
	}
	
	/* isCheck Method
	 * 
	 * Input Arguments - List of attacking Pieces, The defenseKing, The BoardLayout in use
	 * 
	 * Determines whether the inputed King can be kicked on the inputed BoardLayout
	 * It tests whether any Piece against the defenseKing can kick it 
	 * Returns a boolean based on the result
	 */
	
	private boolean isCheck(BoardLayout boardLayout, Colour c) {
		
		// Creates and sets the defenseKing variable to the King of the inputed colour
		Piece defenseKing = boardLayout.getKingOfColour(c);
		
		// List of Pieces against the King, refered to as attack
		ArrayList<Piece> attack = boardLayout.getPiecesOfColour(c.oppositeColour());
				
		// List of Pieces with the King, refered to as defense
		ArrayList<Piece> defense = boardLayout.getPiecesOfColour(c);
		
		// For each Piece that is playing against the defenseKing
		for (Piece piece : attack) {
			
			// Returns true (There is a Check) if any Piece can kick the defenseKing
			if (piece.isValidMove( defenseKing.position, boardLayout)) { return true; }
		}
		
		// Returns false (No Check) if no Piece can kick the defenseKing
		return false;
	}
	
	/* isCheckMate Method
	 * 
	 * Input Arguments - List of attacking Pieces, List of defending Pieces, The defenseKing, The BoardLayout in use
	 * 
	 * Determines whether the inputed King is in CheckMate or is safe/ can be saved
	 * It tests whether any move done by any of the defending Pieces will stop the defenseKing from being in Check
	 * Returns a boolean based on the result
	 */
	
	private boolean isCheckmate(BoardLayout boardLayout, Colour c) {
		
		Piece defenseKing = boardLayout.getKingOfColour(c);
		ArrayList<Piece> attack = boardLayout.getPiecesOfColour(c.oppositeColour());
		ArrayList<Piece> defense = boardLayout.getPiecesOfColour(c);
		
		boolean checkMate = true;
		
		// For each Piece that is playing with the defenseKing i.e defending it
		for (Piece piece : defense) {
			
			// For every possible Position
			for (Position position : Position.allPositions()) {
						
				if (piece.isValidMove(position, boardLayout)) {
				// The Piece can move to that Position
					
					// Moves the Piece to that Position
					movePiece(piece, position);
						
					// Tests if the defenseKing is still in Check
					if (!isCheck(getCurrentBoardLayout(), c)) { checkMate = false; }
					
					// Reverts the move made
					implementPrevious();
					
					// Returns false (No CheckMate) if the King is safe when the Piece was moved
				}
			}
		}
		
		return checkMate;
	}
}

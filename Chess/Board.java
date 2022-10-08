package Chess;

import java.awt.Color;

/* Board
 * 
 * Fields - Public board objects; UI, BoardLayoutManager
 * 
 * Controls the location and movement of pieces on the board
 * Handles Input and Output
 * Controls the User Interface
 */

public class Board {
	
	// Public static references to UI and BoardLayoutManager objects
	public UI ui;
	public BoardLayoutManager boardLayoutManager;
	
	/* Board Constructor
	 * 
	 * Creates a new BoardLayoutManager
	 * Initialises the chess UI 
	 */
	
	public Board() {
		
		this.boardLayoutManager = new BoardLayoutManager();
		
		initialiseChessUI();
	}
	
	/* Board Constructor
	 * 
	 * input arguments - A boardLayoutManger to be used
	 * 
	 * Uses the inputed BoardLayoutManager
	 * Initialises the chess UI
	 */
	
	public Board(BoardLayoutManager BLM) {
		
		this.boardLayoutManager = BLM;
		
		initialiseChessUI();
	}
	
	// Initialises the chess UI to start the game
	private void initialiseChessUI() {
		
		ui = new UI(boardLayoutManager);
		ui.setTextPlayerTurn();
		ui.update();
	}
	
	// Returns the current BoardLayout in use from the BoardLayoutManager
	public BoardLayout getCurrentBoardLayout() {
		return boardLayoutManager.getCurrentBoardLayout();
	}
	
	// Static variable references used in the buttonPress and isSelectionClick methods
	public static boolean selectionClick = true;
	
	private static Position selectedPiecePosition = null;
	
	/* UI_ClickListener method
	 * 
	 * Input Arguments - Position of Field clicked
	 * 
	 * Is called whenever a Field on the User Interface is click and processes it accordingly
	 * Changes the User Interface if any changes are applied
	 */
	
	public void UI_ClickListener(Position position) {
		
		ui.correctBackground();
	
		if (selectionClick) {
		// A Piece by the player who's turn it is, still needs to be selected
			
			if (isSelectionClick(position)) { selectionClick = false; }
			
		} else {
		// A Piece has already been selected
			
			if (!isSelectionClick(position)) {
			// It is not a selection of another possible Piece
				
				if (getCurrentBoardLayout().pieceAt(selectedPiecePosition).isValidMove(position, getCurrentBoardLayout())) {
				// The selected Piece can be moved to the clicked Field
					
					// Moves the Piece to the clicked Field
					boardLayoutManager.movePiece(getCurrentBoardLayout().pieceAt(selectedPiecePosition), position);
					
					// Updates the User Interface
					ui.update();
					ui.correctBackground();
					
					// Inquires if the opponent is now in Check or CheckMate
					boardLayoutManager.inquireCheck(getCurrentBoardLayout(), boardLayoutManager.getTurnColour().oppositeColour());
					
					selectionClick = true;
					boardLayoutManager.switchTurn();
					
				} else { 
				// The selected Piece cannot be moved to the clicked Field
					
					ui.fieldAt(selectedPiecePosition).setBackground(new Color(237, 159, 116)); // Colour - Peach
					ui.displayPossibleMoves(getCurrentBoardLayout().pieceAt(selectedPiecePosition));
					ui.setText("That is not a valid move!");
				}
			}
		}
	}
	
	/* isSelectionClick method
	 * 
	 * Input Arguments - Position of clicked Field
	 * 
	 * Determines if a Field click is selection of a correct Piece and operates accordingly
	 * Changes the User Interface accordingly 
	 * Returns a boolean depending on the result
	 */
	
	private boolean isSelectionClick(Position position) {
		
		if (getCurrentBoardLayout().isOccupiedByColour(position, boardLayoutManager.getTurnColour())) {
		// The clicked Field either contains a Piece that can be selected or an empty field
			
			ui.displayPossibleMoves(getCurrentBoardLayout().pieceAt(position)); 
			ui.fieldAt(position).setBackground(new Color(237, 159, 116)); // Colour - Peach
			ui.setText("Choose where to move with the selected piece!");
			
			// Sets the selectedPiecePosition to the clicked Fields Position
			selectedPiecePosition = position;
			return true;
			
		} else {
		// The clicked Field doesn't contain a Piece that can be selected or contains a Piece of the opposite colour
			
			ui.fieldAt(position).setBackground(new Color(217, 39, 66)); // Colour - Red
			ui.setText("That is not a valid selection! Please select a " + 
					boardLayoutManager.getTurnColour().asString() + " piece.");
			
			return false; 
		}
	}
}

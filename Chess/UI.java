package Chess;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

/* UI
 * 
 * Fields - Multiple private variables; JComponents, BoardLayoutManager, 2D arrays
 * 
 * Creates an interactive Application Window that operates as the User Interface for the program
 * 		- Visual representation of a chess board
 * 		- Undo Button
 * 		- Navigational Text Field 
 */

public class UI {
	
	// Private references to JComponets
	private JFrame frmChess;
	private JTextField textField;
	
	// Private reference to the BoardLayoutManager in use by the Board Object
	private BoardLayoutManager boardLayoutManager;
	
	// Private references to 2D arrays that hold all Fields of the UI and their respective background colours
	private Field[][] fields = new Field[8][8];
	private Color[][] backgroundColors = new Color[8][8];
	
	// Field object
	// Exact copy of the JButton class
	// Done for simplicity; Each chess board Field can be called a Field, instead of JButton
	public class Field extends JButton {}
	
	/* UI Constructor 
	 * 
	 * Input Arguments - BoardLayoutManager that is used by the Board Object
	 * 
	 * Sets the private BoardLayoutManager reference
	 * Creates the User Interface
	 */
	
	public UI(BoardLayoutManager boardLayoutManager) {
		
		this.boardLayoutManager = boardLayoutManager;
		
		// Initialises the JFrame
		frmChess = new JFrame();
		frmChess.setResizable(false);
		frmChess.setTitle("Chess");
		frmChess.setBounds(100, 100, 816, 889);
		frmChess.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChess.setLocationRelativeTo(null);
		frmChess.getContentPane().setLayout(null);
		
		// Creates the Undo Button 
		JButton undo = new JButton("Undo Move");
		undo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Whenever the undo button is clicked
				
				if (boardLayoutManager.getStackSize() > 1) {
				// There is a possible move that can be undone
					
					// Implements the previous BoardLayout
					boardLayoutManager.implementPrevious();
					
					boardLayoutManager.switchTurn();
					
					// Sets next button click to registered as a selection
					GameMain.board.selectionClick = true;
					
				} else {
				// There are no possible moves that can be undone
					
					showMessageDialog("Warning", "No moves can be undone.");
					setTextPlayerTurn();
				}
				
				update();
				correctBackground();
			} 
		});
		undo.setBounds(5, 7, 125, 35);
		undo.setBackground(new Color(212, 212, 212));
		undo.setFocusable(false);
		frmChess.getContentPane().add(undo);
		
		JButton save = new JButton("Save Game");
		save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			// Whenever the save button is clicked
			
				// Try to write the BoardLayoutManager object in the save file
				try {
					
					FileOutputStream fo = new FileOutputStream(new File("Save.txt"));
					ObjectOutputStream oo = new ObjectOutputStream(fo);
					
					oo.writeObject(boardLayoutManager);

					oo.close();
					fo.close();
					
					showMessageDialog("Success","Game has been saved.");
					
					setTextPlayerTurn();

				} catch (FileNotFoundException exeption) {
					showMessageDialog("Error","Save file is missing.");
				} catch (IOException exeption) {
					showMessageDialog("Error","File handling error.");
				}
			} 
		});
		save.setBounds(135, 7, 125, 35);
		save.setBackground(new Color(212, 212, 212));
		save.setFocusable(false);
		frmChess.getContentPane().add(save);
		
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
		// Initialises the Text Field
		textField = new JTextField();
		textField.setBorder(border);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		textField.setBounds(265, 7, 530, 35);
		textField.setBackground(new Color(232, 232, 232));
		frmChess.getContentPane().add(textField);
		
		// Variables set to be used to create all Fields
		boolean blackField = true;
		int row = 750;
		int column = 0;
		
		// Creates a new Field for all possible Positions on a chess board
		for (Position position : Position.allPositions()) {
			
			// Creates the Field
			Field b = new Field();
			b.setFont(new Font("Tahoma", Font.BOLD, 18));
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				// Whenever this Field is clicked
					
					// Will call the UI_ClickListener method using a static Board reference
					// Passes in its Position on the board as an argument
					GameMain.board.UI_ClickListener(position);
				} 
			});
			// The bounds of the Field are set by the private class variables
			b.setBounds(column, row, 100, 100);
			b.setFocusable(false);
			frmChess.getContentPane().add(b);
			
			// Determines the colour of the Field
			if (blackField) { 
				b.setBackground(new Color(160, 82, 45)); // Colour - DarkBrown
			} else { 
				b.setBackground(new Color(184, 134, 11)); // Colour - LightBrown
			}
				
			column += 100;
			
			// Alters variables for the next Field
			if (column == 800) { 
				column = 0; 
				row -= 100; 
			} else { 
				blackField = !blackField; 
			}
			
			// Adds this Field to the Field[][] based on its Position on the board
			fields[position.row][position.column] = b;
			
			// Adds the colour of this Field to the Colour[][] based on its Position on the board
			backgroundColors[position.row][position.column] = b.getBackground();
		}
		
		frmChess.setVisible(true);
	}
	
	// Returns the Field that is at the inputed Position
	public Field fieldAt(Position position) {
		return fields[position.row][position.column];
	}
	
	// Returns the Color of the Field that is at the inputed Position
	private Color colorAt(Position position) {
		return backgroundColors[position.row][position.column];
	}
	
	// Sets the text of  the Navigational Text Field to the inputed text
	public void setText(String text) {
		textField.setText(text);
	}
	
	// Sets the text of the Navigational Text Field to display who has their turn
	public void setTextPlayerTurn() {
		setText("Player " + boardLayoutManager.getTurnColour().asString().toLowerCase()
				+ " has their turn! Please select a piece.");
	}
	
	// Creates and displays a message dialog with the inputed title and text
	public void showMessageDialog(String title, String text) {
		
		setText(null);
		
		JOptionPane.showMessageDialog(
				frmChess,
			    text,
			    title,
			    JOptionPane.PLAIN_MESSAGE
		);
	}
	
	// Updates the User Interface to display the current BoardLayout in play
	public void update() {
		
		// Sets a BoardLayout variable to the current BoardLayout in play
		BoardLayout boardLayout = boardLayoutManager.getCurrentBoardLayout();
		
		// For each possible Position
		for (Position position : Position.allPositions()) {
			
			if (!boardLayout.isAvalible(position)) {
			// There is a Piece on the board at that position
				
				// Sets the text of the Field to the name of the Piece
				fieldAt(position).setText(boardLayout.pieceAt(position).getName()); 
				
				// Sets the colour of the text on the board to the colour of the Piece
				if (boardLayout.pieceAt(position).getColour().equals(Colour.WHITE)) { fieldAt(position).setForeground(Color.WHITE); }
				if (boardLayout.pieceAt(position).getColour().equals(Colour.BLACK)) { fieldAt(position).setForeground(Color.BLACK); }
					
			} else { 
			// There is not a Piece at that position
				
				// Set the text of the Field to null
				fieldAt(position).setText(null); 
			}
		}
	}
	
	// Visually displays the possible moves of a Piece on the board that is inputed based on the current BoardLayout
	public void displayPossibleMoves(Piece piece) {
		
		// For each possible Position
		for (Position position : Position.allPositions()) {
			
			if (piece.isValidMove(position, boardLayoutManager.getCurrentBoardLayout())) {
			// Moving to this position is a valid move by the Piece
				
				// Sets the colour of the Field at that position to green (One of two shades)
				if (colorAt(position).equals(new Color(184, 134, 11))) { fieldAt(position).setBackground(new Color(129, 255, 66)); } // Colours - LightBrown, LightGreen
				if (colorAt(position).equals(new Color(160, 82, 45))) { fieldAt(position).setBackground(new Color(59, 204, 51)); } // Colours - DarkBrown, DarkGreen
				
				if (boardLayoutManager.getCurrentBoardLayout().isOccupiedByColour(position, piece.getColour().oppositeColour())) {
				// The positions is occupied by an opponents Piece
					
					// Sets the Field at that position to blue
					fieldAt(position).setBackground(new Color(26, 201, 155)); // Colour - LightBlue
				}
			}
		}
	}
	
	// Sets all the Fields to their original background colours
	public void correctBackground() {
		
		// For all possible Positions
		for (Position position : Position.allPositions()) {
			
			// Sets the Field at that Position to its original colour
			fieldAt(position).setBackground(colorAt(position));
		}
	}
}

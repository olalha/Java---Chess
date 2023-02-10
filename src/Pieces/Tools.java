package Pieces;

import java.util.ArrayList;

/* Tools
 * 
 * Fields - No fields
 * 
 * Stores public static methods used by Piece child classes in their appropriate isValidMove methods
 */

import Chess.Position;

public class Tools {
	
	public static ArrayList<Integer> valuesBetween(int value1, int value2) {
		
		ArrayList<Integer> values = new ArrayList<Integer>();
			
		while (value1 != value2) {
			
			if (value1 > value2) { value1--; } else { value1++; }
			
			values.add(value1);
		}
		
		if (!values.isEmpty()) { values.remove(values.size()-1); }
		
		return values;
	}
	
	public static int differnce(int value1, int value2) {
		
		if (value1 > value2) { 
			
			return value1 - value2;
			
		} else { return value2 - value1; }
	}
	
	public static ArrayList<Position> postionsBetweenStraight(Position startPosition, Position finishPosition) {
		
		ArrayList<Position> positionArray = new ArrayList<Position>();
		
		if (startPosition.row == finishPosition.row) {
			
			ArrayList<Integer> column = valuesBetween(startPosition.column, finishPosition.column);
			
			for (int x = 0; x < column.size(); x++) {
				
				positionArray.add(new Position(startPosition.row, column.get(x)));
			}
		}
		
		if (startPosition.column == finishPosition.column) {
			
			ArrayList<Integer> row = valuesBetween(startPosition.row, finishPosition.row);
			
			for (int x = 0; x < row.size(); x++ ) {
				
				positionArray.add(new Position(row.get(x), startPosition.column));
			}
		}
			
		return positionArray;	
	}
	
	public static ArrayList<Position> postionsBetweenDiagonal(Position startPosition, Position finishPosition) {
		
		ArrayList<Integer> row = valuesBetween(startPosition.row, finishPosition.row);
		ArrayList<Integer> column = valuesBetween(startPosition.column, finishPosition.column);
		ArrayList<Position> positionArray = new ArrayList<Position>();
		
		for (int x = 0; x < row.size(); x++) {
	
			positionArray.add(new Position(row.get(x), column.get(x)));
		}
			
		return positionArray;	
	}
}

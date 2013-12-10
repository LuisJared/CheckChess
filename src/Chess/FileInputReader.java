package Chess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileInputReader 
{		 
//	private final Pattern CASTLING_VERIFIER = Pattern.compile("(?<kingCastle>[Ee][18]) ([CcGg][18]) (?<rookCastle>[AaHh][18]) ([DdFf][18])");
	private final Pattern PIECE_PLACE_FINDER = Pattern.compile("[\\w]{2}(?<position>[A-Ha-h][1-8])");
//	private final Pattern PIECE_MOVEMENT_CAPTURING = Pattern.compile("(?<startingPosition>[A-Ha-h][1-8]) (?<capturePosition>[A-Ha-h][1-8])(\\*)");
	private final Pattern PIECE_PLACING_VERIFIER = Pattern.compile("(?<pieceType>[KkQqBbNnRrPp])(?<pieceColor>[LlDd])(?<columnPosition>[A-Ha-h])(?<rowPosition>[1-8])");
	private final Pattern PIECE_MOVEMENT_VERIFIER = Pattern.compile("(?<startingColumn>[A-Ha-h])(?<startingRow>[1-8]) (?<endColumn>[A-Ha-h])(?<endRow>[1-8])");
	
	private Controller controller = new Controller();
	
	public void readFile(String file)
	{	
		findPieces(file);
	}
	
	public void findPieces(String fileName)
	{
		File file = new File(fileName);
		BufferedReader buffer = null;
		
		try
		{
			try
			{
				buffer = new BufferedReader(new FileReader(file));
			}
			catch(FileNotFoundException f)
			{
				System.out.println("The command line has an invalid file name!  Please input a proper file arguement!");
			}
			
			while(buffer.ready())
			{						
				String piece = "";
				String color = "";
				String placement = "";
				String line = buffer.readLine();

				Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
//				Matcher movementCapture = PIECE_MOVEMENT_CAPTURING.matcher(line);
				Matcher movementVerifier = PIECE_MOVEMENT_VERIFIER.matcher(line);
//				Matcher castleMatcher = CASTLING_VERIFIER.matcher(line);
				
				if(placeVerifier.matches())
				{
					Piece newPiece = new Piece(piece);
					
					newPiece = pieceType(line, newPiece);
					piece = newPiece.getPieceType();
					color = pieceColor(line, newPiece);
					placement = piecePosition(line);
					String pieceType = placeVerifier.group("pieceType");
					
//					placePiece(piece, color, placement, line);
					
					char columnLetter = placeVerifier.group("columnPosition").charAt(0);
					char rowNumber = placeVerifier.group("rowPosition").charAt(0);
					
					int column = columnLetter - 'a';
					int row = rowNumber - '1';
					
					pieceType = (color == "White") ? pieceType.toLowerCase() : pieceType.toUpperCase();
					
					newPiece.setPieceType(pieceType);
					
					controller.addPieceToBoard(newPiece, column, row);
				}							
				else if(movementVerifier.matches())
				{					
					char startingColumnLetter = movementVerifier.group("startingColumn").charAt(0);
					char startingRowNumber = movementVerifier.group("startingRow").charAt(0);
					char endColumnLetter = movementVerifier.group("endColumn").charAt(0);
					char endRowNumber = movementVerifier.group("endRow").charAt(0);
					
					int startingColumn = startingColumnLetter - 'a';
					int startingRow = startingRowNumber - '1';
					int endColumn = endColumnLetter - 'a';
					int endRow = endRowNumber - '1';
					
					Position startingPosition = makeStartMovementPosition(startingColumn, startingRow);
					Position endPosition = makeEndMovementPosition(endColumn, endRow);
					
					controller.movePieceOnBoard(line, startingPosition, endPosition);
				}
//				else if(movementCapture.matches())
//				{
//					capturePiece(line);
//				}
//				else if(castleMatcher.matches())
//				{
//					notifyCastle(line);
//				}
				else
				{
					System.out.println();
					System.out.println(line + " is an incorrect input!  Please revise it!");
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				buffer.close();
			}
			catch(IOException e)
			{
				System.out.println("Buffer hasn't closed");
			}
		}
	}
	private Position makeStartMovementPosition(int x, int y)
	{
		Position startPosition = new Position(x, y);		
		return startPosition;
	}
	private Position makeEndMovementPosition(int x, int y)
	{
		Position endPosition = new Position(x, y);		
		return endPosition;
	}
	
	private Piece pieceType(String line, Piece newPiece) 
	{
		String piece = "";
		
		Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
		
		if(placeVerifier.matches())
		{					
			String pieceType = placeVerifier.group("pieceType").toLowerCase();
			
			if(pieceType.equals("k"))
			{
				piece = "King";
				newPiece = new King(piece);
			}
			else if(pieceType.equals("q"))
			{
				piece = "Queen";
				newPiece = new Queen(piece);
			}
			else if(pieceType.equals("b"))
			{
				piece = "Bishop";
				newPiece = new Bishop(piece);
			}
			else if(pieceType.equals("n"))
			{
				piece = "Knight";
				newPiece = new Knight(piece);
			}
			else if(pieceType.equals("r"))
			{
				piece = "Rook";
				newPiece = new Rook(piece);
			}
			else if(pieceType.equals("p"))
			{
				piece = "Pawn";
				newPiece = new Pawn(piece);
			}
		}
		return newPiece;
	}
	
	private String pieceColor(String line, Piece piece)
	{
		Matcher placeVerifier = PIECE_PLACING_VERIFIER.matcher(line);
		
		String color = "";

		if(placeVerifier.matches())
		{			
			color = (placeVerifier.group("pieceColor").equals("l") || placeVerifier.group("pieceColor").equals("L")) ? "White" : "Black";
		}
		piece.setPieceColor(color);
		return color;
	}
	
	private String piecePosition(String line)
	{
		String position = "";
		Matcher pieceFinder = PIECE_PLACE_FINDER.matcher(line);
		
		if(pieceFinder.matches())
		{
			position = pieceFinder.group("position");			
		}
		
		return position;
	}
	
//	private void placePiece(String piece, String color, String placement, String line)
//	{
//		Matcher pieceFinder = PIECE_PLACE_FINDER.matcher(line);	
//		
//		if(pieceFinder.matches())
//		{
//			System.out.println("The " + color + " " + piece + " has been placed at " + pieceFinder.group("position"));
//		}
//	}

//	private void capturePiece(String line)
//	{		
//		Matcher movementCapture = PIECE_MOVEMENT_CAPTURING.matcher(line);
//		
//		if(movementCapture.matches())
//		{
//			System.out.println("The piece at " + movementCapture.group("startingPosition") + " has moved to " + movementCapture.group("capturePosition") + " and captured the piece at " + movementCapture.group("capturePosition"));
//		}
//	}
//	
//	private void movePiece(String line)
//	{
//		Matcher movementVerifier = PIECE_MOVEMENT_VERIFIER.matcher(line);
//		
//		if(movementVerifier.matches())
//		{
//			System.out.println("The piece at " + movementVerifier.group("startingColumn") + movementVerifier.group("startingRow") + " has moved to " + movementVerifier.group("endColumn") + movementVerifier.group("endRow"));
//		}
//	}
//	
//	private void notifyCastle(String line) 
//	{
//		Matcher castleMatcher = CASTLING_VERIFIER.matcher(line);
//		
//		if(castleMatcher.matches())
//		{
//			System.out.println("You have just performed Castling with " + castleMatcher.group("kingCastle") + " and " + castleMatcher.group("rookCastle"));
//		}
//	}
}
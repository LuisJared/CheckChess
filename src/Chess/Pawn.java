package Chess;

public class Pawn extends Piece
{	
	public Pawn(String pieceType) 
	{
		super(pieceType);
	}

	private double WHITE_PAWN_Y_STARTING_SPOT = 1;
	private double BLACK_PAWN_Y_STARTING_SPOT = 6;
	private double PAWN_MOVEMENT_RESTRICTION = 1;
	private double STARTING_PAWN_MOVEMENT_RESTRICTION = 2;
	private boolean hasMoved = false;

	public boolean validWhiteMovement(double x1, double y1, double x2, double y2, Square newSquare)
	{	
		boolean move = false;
		
		if( ((x2 == x1) && (y2 == y1 + PAWN_MOVEMENT_RESTRICTION)) && newSquare.getPiece().getPieceType() == "-")
		{
			move = true;
		}
		else if( ((x2 == x1 && (y1 == WHITE_PAWN_Y_STARTING_SPOT)) && (y2 == y1 + STARTING_PAWN_MOVEMENT_RESTRICTION) && !(hasMoved)
				&& newSquare.getPiece().getPieceType() == "-"))
		{
			move = true;
			hasMoved = true;
		}
		
		return move;
	}
	
	public boolean validBlackMovement(double x1, double y1, double x2, double y2, Square newSquare)
	{	
		boolean move = false;
		
		if( ((x2 == x1) && (y2 == y1 - PAWN_MOVEMENT_RESTRICTION)) && newSquare.getPiece().getPieceType() == "-")
		{
			move = true;
		}
		else if( ((x2 == x1 && (y1 == BLACK_PAWN_Y_STARTING_SPOT)) && (y2 == y1 - STARTING_PAWN_MOVEMENT_RESTRICTION) && !(hasMoved)
				&& newSquare.getPiece().getPieceType() == "-"))
		{
			move = true;
			hasMoved = true;
		}
		
		return move;
	}
	
	public boolean pawnCapturing(int x1, int y1, int x2, int y2, String color, Square newSquare)
	{
		boolean capture = false;
		
		if(color == "White")
		{
			if( (((x2 == x1 + PAWN_MOVEMENT_RESTRICTION) || (x2 == x1 - PAWN_MOVEMENT_RESTRICTION)) && y2 == y1 + PAWN_MOVEMENT_RESTRICTION)
				&& newSquare.getPiece().getPieceType() != "-")
			{
				capture = true;
			}
		}
		else
		{
			if( (((x2 == x1 + PAWN_MOVEMENT_RESTRICTION) || (x2 == x1 - PAWN_MOVEMENT_RESTRICTION)) && y2 == y1 - PAWN_MOVEMENT_RESTRICTION)
				&& newSquare.getPiece().getPieceType() != "-")
			{
				capture = true;
			}
		}		
		return capture;
	}
}
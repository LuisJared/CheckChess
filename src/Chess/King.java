package Chess;

public class King extends Piece
{
	public King(String pieceType) 
	{
		super(pieceType);
	}

	private static double KING_MOVEMENT_RESTRICTION = 1;
	
	@Override
	public boolean validMovement(double x1, double y1, double x2, double y2)
	{
		boolean move = false;
			
		if((x2 == x1-KING_MOVEMENT_RESTRICTION && y2 == y1)
			|| (x2 == x1-KING_MOVEMENT_RESTRICTION && y2 == y1+KING_MOVEMENT_RESTRICTION)
			|| (x2 == x1 && y2 == y1+KING_MOVEMENT_RESTRICTION)
			|| (x2 == x1+KING_MOVEMENT_RESTRICTION && y2 == y1+KING_MOVEMENT_RESTRICTION)
			|| (x2 == x1+KING_MOVEMENT_RESTRICTION && y2 == y1)
			|| (x2 == x1+KING_MOVEMENT_RESTRICTION && y2 == y1-KING_MOVEMENT_RESTRICTION)
			|| (x2 == x1 && y2 == y1-KING_MOVEMENT_RESTRICTION)
			|| (x2 == x1-KING_MOVEMENT_RESTRICTION && y2 == y1-KING_MOVEMENT_RESTRICTION)
			&& (y2 < super.maxBoardSize && y2 >= super.minBoardSize)
			&& (x2 < super.maxBoardSize && x2 >= super.minBoardSize))
		{
			move = true;
		}
		return move;
	}
}
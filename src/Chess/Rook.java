package Chess;

public class Rook extends Piece
{		
	public Rook(String pieceType) 
	{
		super(pieceType);
	}

	@Override
	public boolean validMovement(double x1, double y1, double x2, double y2)
	{
		boolean move = false;
		// 3,3 > 3,5
		// vertical movement
		if(x2 == x1 && (y2 < super.maxBoardSize && y2 >= super.minBoardSize))
		{
			move = true;
		}
		// 2,3 > 4,3
		// horizontal movement
		if(y2 == y1 && (x2 < super.maxBoardSize && x2 >= super.minBoardSize))
		{
			move = true;
		}
		return move;
	}
}
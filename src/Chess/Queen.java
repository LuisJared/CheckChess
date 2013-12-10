package Chess;

public class Queen extends Piece
{	
	public Queen(String pieceType) 
	{
		super(pieceType);
	}

	private static double DIVIDED_RESULT = 1;
	
	@Override
	public boolean validMovement(double x1, double y1, double x2, double y2)
	{
		boolean move = false;					
		// 3,3 > 3,5
		// horizontal
		if(x2 == x1 && (y2 < super.maxBoardSize && y2 >= super.minBoardSize))
		{
			move = true;
		}
		// 2,3 > 4,3
		// vertical
		if(y2 == y1 && (x2 < super.maxBoardSize && x2 >= super.minBoardSize))
		{
			move = true;
		}

		// 3,4 > 1,2	// 4,4 > 3,3	// 5,3 > 3,1	// 7,4 > 5,2
		// up left motion / down right motion
		if((x2 - y2) == (x1 - y1)
			&& (x2 < super.maxBoardSize && x2 >= super.minBoardSize)
			&& (y2 < super.maxBoardSize && y2 >= super.minBoardSize))
		{
			move = true;
		}
		// 2,3 > 4,1	// 2,3 > 3,2
		// up right motion
		try
		{
			if(((((x2 + y2)/(x1 + y1)) == DIVIDED_RESULT
			   && (x2 < super.maxBoardSize && x2 >= super.minBoardSize)
			   && (y2 < super.maxBoardSize && y2 >= super.minBoardSize))))
			{
				move = true;
			}
		}
		catch(ArithmeticException e)
		{
			move = true;
		}
		// 5,2 > 3,4	// 3,3 > 1,5	// 7,2 > 4,5	// 2,2 > 0,4
		// down left motion
		if((x2 + y2) == (x1 + y1))
		{
			move = true;
		}
		return move;
	}
}
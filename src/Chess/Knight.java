package Chess;

public class Knight extends Piece
{	
	public Knight(String pieceType) 
	{
		super(pieceType);
	}

	private static double ONE = 1;
	private static double TWO = 2;
	
	@Override
	public boolean validMovement(double x1, double y1, double x2, double y2)
	{
		boolean move = false;
	
		
		// 5,2 > 3,3
		// top left
		if((x2 == x1-TWO) && (y2 == y1+ONE)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}
		
		// 5,2 > 4,4
		// top top left
		if((x2 == x1-ONE) && (y2 == y1+TWO)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{	
			move = true;
		}
		// 5,2 > 6,4
		// top top right
		if((x2 == x1+ONE) && (y2 == y1+TWO)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}
		// 5,2 > 7,3
		// top right
		if((x2 == x1+TWO) && (y2 == y1+ONE)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}
		// 5,2 > 7,1
		// bottom right
		if((x2 == x1+TWO) && (y2 == y1-ONE)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{	
			move = true;
		}
		// 5,2 > 6,0
		// bottom bottom right
		if((x2 == x1+ONE) && (y2 == y1-TWO)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}
		
		// 5,2 > 4,0
		// bottom bottom left
		if((x2 == x1-ONE) && (y2 == y1-TWO)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}		
		
		// 5,2 > 3,1
		// bottom left
		if((x2 == x1-TWO) && (y2 == y1-ONE)
				&& ((x2 >= super.minBoardSize && x2 < super.maxBoardSize)
				&& (y2 >= super.minBoardSize && y2 < super.maxBoardSize)))
		{
			move = true;
		}			
		return move;
	}
}
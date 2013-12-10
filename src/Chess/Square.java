package Chess;

public class Square
{
	private Piece piece;
	private int piecePositionX;
	private int piecePositionY;
	
	public Square(Piece piece, Position position) 
	{
	 	this.piece = piece;
		this.piecePositionX = position.getPositionX();
		this.piecePositionY = position.getPositionY();
	}

	public Piece getPiece()
	{
		return piece;
	}
	public void setPiece(Piece piece) 
	{
		this.piece = piece;
	}
	
	public int getPiecePositionX()
	{
		return piecePositionX;
	}	
	public void setPiecePositionX(int piecePositionX) 
	{
		this.piecePositionX = piecePositionX;
	}
	
	public int getPiecePositionY()
	{
		return piecePositionY;
	}
	public void setPiecePositionY(int piecePositionY) 
	{
		this.piecePositionY = piecePositionY;
	}
}
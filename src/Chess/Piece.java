package Chess;

import java.util.ArrayList;

public class Piece 
{
	private String pieceType = "";
	private String pieceColor = "";
	private ArrayList<String> possibleMoves = new ArrayList<String>();
	protected double minBoardSize = 0;
	protected double maxBoardSize = 8;
	
	public Piece(String pieceType)
	{
		this.pieceType = pieceType;
	}

	public String getPieceType() 
	{
		return pieceType;
	}

	public void setPieceType(String pieceType)
	{
		this.pieceType = pieceType;
	}
	
	public String getPieceColor()
	{
		return pieceColor;
	}
	
	public void setPieceColor(String pieceColor)
	{
		this.pieceColor = pieceColor;
	}
	
	public boolean validMovement(double x1, double y1, double x2, double y2)
	{
		boolean move = false;
		
		return move;
	}

	public ArrayList<String> getPossibleMoves() 
	{
		return possibleMoves;
	}

	public void setPossibleMoves(String possibleMoves) 
	{
		this.possibleMoves.add(possibleMoves);
	}
}
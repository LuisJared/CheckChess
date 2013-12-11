package Chess;
import java.util.HashMap;

public class Team 
{
	private HashMap<Piece, Position> blackTeam = new HashMap<Piece, Position>();
	private HashMap<Piece, Position> whiteTeam = new HashMap<Piece, Position>();
	
	private Piece whiteKing;
	private Piece blackKing;
	
	public void setWhiteKing(Piece whiteKing) 
	{
		this.whiteKing = whiteKing;
	}
	public void setWhiteKingPosition(Position position)
	{
		whiteTeam.get(whiteKing).setPositionX(position.getPositionX());
		whiteTeam.get(whiteKing).setPositionY(position.getPositionY());
	}
	
	public void setBlackKing(Piece blackKing)
	{
		this.blackKing = blackKing;
	}
	public void setBlackKingPosition(Position position)
	{
		blackTeam.get(blackKing).setPositionX(position.getPositionX());
		blackTeam.get(blackKing).setPositionY(position.getPositionY());
	}
	
	public int getBlackTeamSize()
	{
		return blackTeam.size();
	}
	public int getWhiteTeamSize()
	{
		return whiteTeam.size();
	}
	
	public Position getKingFromWhiteTeam()
	{
		return whiteTeam.get(whiteKing);
	}	
	public Position getKingFromBlackTeam()
	{		
		return blackTeam.get(blackKing);
	}
	
	public HashMap<Piece, Position> getWhiteTeam()
	{
		return whiteTeam;
	}
	public HashMap<Piece, Position> getBlackTeam()
	{
		return blackTeam;
	}
	
	public void addWhitePieceToTeam(Piece whitePiece, Position position)
	{
		whiteTeam.put(whitePiece, position);
	}
	
	public void addBlackPieceToTeam(Piece blackPiece, Position position)
	{
		blackTeam.put(blackPiece, position);
	}
	
	public void removePieceFromBlackTeam(Piece piece)
	{
		blackTeam.remove(piece);
	}
	
	public void removePieceFromWhiteTeam(Piece piece)
	{
		whiteTeam.remove(piece);
	}
}
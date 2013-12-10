package Chess;

public class Board 
{
    private final int boardHeight = 8;
    private final int boardWidth = 8;
    private final Piece blankPiece = new Piece("-");

	private Square[][] chessBoard = new Square[boardHeight+1][boardWidth+1];
    
	public Piece getBlankPiece() 
    {
		return blankPiece;
	}
	
    public Square getChessBoardSquare(int x, int y) 
    {
		return chessBoard[x][y];
	}

	public void setChessBoardSquare(Square boardSquare, int x, int y) 
	{
		this.chessBoard[x][y] = boardSquare;
	}

	public Board()
    {        
        for(int i = 0; i < boardHeight; i++)
        {
            for(int k = 0; k < boardWidth; k++)
            {
                    Position position = new Position(i, k);
                    
                    chessBoard[i][k] = new Square(blankPiece, position);
            }
        }
    }

    public void printBoard()
    {
        for(int i = (boardHeight-1); i >= 0; i--)
        {                        
            System.out.println();
            
            for(int k = 0; k < boardWidth; k++)
            {
                    System.out.print("[ " + chessBoard[k][i].getPiece().getPieceType() + " ]");
            }
        }
    }
}
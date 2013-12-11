package Chess;

import java.util.ArrayList;

public class Controller 
{
	private Board board = new Board();
	private Team team = new Team();
	private boolean whiteTurn;
	private int totalTurns = 0;
	private int pieceAddCount = 0;
	private String white = "White";
	private String black = "Black";
	private int maxWidth = 8;
	private int maxHeight = 8;
	
	private boolean whitePlayerTurn()
	{		
		whiteTurn = (totalTurns % 2 == 0) ? true : false;
		return whiteTurn;
	}

	public void addPieceToBoard(Piece piece, int x, int y)
	{
		Position position = new Position(x, y);
		Square newSquare = new Square(piece, position);
		board.setChessBoardSquare(newSquare, x, y);
		
		if(piece.getPieceColor().equals(white))
		{
			team.addWhitePieceToTeam(piece, position);
		}
		else
		{
			team.addBlackPieceToTeam(piece, position);
		}
		
		if(piece.getPieceType().equals("k"))
		{
			team.setWhiteKing(piece);
		}
		if(piece.getPieceType().equals("K"))
		{
			team.setBlackKing(piece);
		}
		
		pieceAddCount++;
		if(pieceAddCount == 16)
		{
			board.printBoard();
			System.out.println();
		}
	}

	public void movePieceOnBoard(String command, Position pieceStart, Position pieceEnd)
	{
		int x1 = pieceStart.getPositionX();
		int y1 = pieceStart.getPositionY();
		int x2 = pieceEnd.getPositionX();
		int y2 = pieceEnd.getPositionY();
		String startSpot = command.substring(0,2);
		String endSpot = command.substring(3,5);
		String player = "";
		player = whitePlayerTurn() ? "White" : "Black";        
		Piece piece = board.getChessBoardSquare(x1, y1).getPiece();
		Square newSquare = new Square(board.getChessBoardSquare(x2, y2).getPiece(), pieceEnd);
		Pawn dummyPawn = new Pawn("Pawn");

		System.out.println("\nIt is Player " + player + "'s turn");

		if(piece.getClass() == dummyPawn.getClass())
		{
			if(piece.getPieceColor() == white)
			{
				if(dummyPawn.validWhiteMovement(x1, y1, x2, y2, newSquare))
				{
					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x1, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else if(dummyPawn.pawnCapturing(x1, y1, x2, y2, piece.getPieceColor(), newSquare))
				{
					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else
				{
					System.out.print(command + " is an invalid move command! Please revise it!");
					System.out.println();
				}
			}
			else if(piece.getPieceColor() == black)
			{
				if(dummyPawn.validBlackMovement(x1, y1, x2, y2, newSquare))
				{
					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x1, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else if(dummyPawn.pawnCapturing(x1, y1, x2, y2, piece.getPieceColor(), newSquare))
				{
					getPiecePath(x1, y1, x2, y2);
					movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
				}
				else
				{
					System.out.print(command + " is an invalid move command! Please revise it!");
					System.out.println();
				}
			}
		}
		else
		{
			if(piece.validMovement(x1, y1, x2, y2))
			{
				getPiecePath(x1, y1, x2, y2);
				movePieceCheck(piece, x1, y1, x2, y2, pieceStart, pieceEnd, command, startSpot, endSpot);
			}
			else
			{
				System.out.println();
				System.out.print(command + " is an invalid move command! Please revise it!");
				System.out.println();
			}
		}
	}

	private void movePieceCheck(Piece piece, int x1, int y1, int x2, int y2, Position start, Position end, String command, String startSpot, String endSpot)
	{		 
		String color = whitePlayerTurn() ? white : black;
		
		if(kingIsInCheck())
		{
			System.out.println("\nPlayer " + color + "'s King is in Check!");
		}
		else
		{
			System.out.println("\nPlayer " + color + "'s King is not in Check");
		}
		if(board.getChessBoardSquare(x2, y2).getPiece().getPieceColor() != (piece.getPieceColor()))
		{
			if(otherPieceExistsInPath(x1, y1, x2, y2))
			{
				if(piece.getPieceColor() == color)
				{
					board.setChessBoardSquare(new Square(piece, end), x2, y2);

					board.setChessBoardSquare(new Square(new Piece("-"), start), x1, y1);
					board.getChessBoardSquare(x1, y1).getPiece().setPieceColor("-");

					totalTurns++;
					
					if(piece.getPieceType().equals("k"))
					{
						Position newPosition = new Position(x2, y2);
						
						team.setWhiteKingPosition(newPosition);
					}
					if(piece.getPieceType().equals("K"))
					{
						Position newPosition = new Position(x2, y2);
						team.setBlackKingPosition(newPosition);
					}

					System.out.println();
					System.out.print("Successfully moved piece " + startSpot + " to " + endSpot);
					board.printBoard();
					System.out.println();
				}
				else
				{
					System.out.println();
					System.out.println(command);
					System.out.println("Piece at " + startSpot + " is not your piece!  Choose from your own color!");
				}
			}
			else
			{
				System.out.println();
				System.out.println(command + " \nYou can't do that!  Your path is being blocked!");
			}
		}
		else
		{
			System.out.println();
			System.out.print("There is a an ally piece on the spot you are trying to move to! " + endSpot + " \nMove is not valid!");
		}
	}
	
	private boolean kingIsInCheck()
	{
		boolean inCheck = false;
		Position whiteKingPosition = team.getKingFromWhiteTeam();
		Position blackKingPosition = team.getKingFromBlackTeam();
		String colorCheck = whitePlayerTurn() ? "Black" : "White";
		
		System.out.println("\nPieces that can move for " + colorCheck + "'s Team");
		
		for(int y = 0; y < maxWidth; y++)
		{
			for(int x = 0; x < maxHeight; x++)
			{
				Piece currentPiece = board.getChessBoardSquare(x, y).getPiece();
				
				if(currentPiece != board.getBlankPiece() && currentPiece.getPieceColor().equals(colorCheck))
				{
					Position positionCheck = new Position(x, y);
					ArrayList<Position> pieceMoves = board.getChessBoardSquare(x, y).getPiece().getPossibleMoves();
					
					possibleMovesForPiece(currentPiece, positionCheck);
					
					for(int i = 0; !inCheck && i < pieceMoves.size(); i++)
					{
						Position indexPosition = pieceMoves.get(i);
						
						if(indexPosition.equals(whiteKingPosition) || indexPosition.equals(blackKingPosition))
						{
							inCheck = true;
						}
					}
				}
			}
		}
		
		return inCheck;
	}

	private void possibleMovesForPiece(Piece piece, Position positionCheck)
	{		
		int x1 = positionCheck.getPositionX();
		int y1 = positionCheck.getPositionY();
		String pieceType = piece.getPieceType().toLowerCase();
		char column = (char) (x1 + 65);
		String row = ""+(y1+1);
		String position = column + row;
		
		// ROOK
		if(pieceType.equals("r"))
		{
			System.out.print("\nPossible moves for Rook at " + position + ": ");
			// origin to top
			topPossibleMoves(piece, positionCheck);
			
			// origin to bottom
			bottomPossibleMoves(piece, positionCheck);
			
			// origin to right
			rightPossibleMoves(piece, positionCheck);
			
			// origin to left
			leftPossibleMoves(piece, positionCheck);
		}		
		// BISHOP
		else if(pieceType.equals("b"))
		{
			System.out.print("\nPossible moves for Bishop at " + position + ": ");
			// origin to top left
			topLeftPossibleMoves(piece, positionCheck);
			
			// origin to top right
			topRightPossibleMoves(piece, positionCheck);
			
			// origin to bottom left
			bottomLeftPossibleMoves(piece, positionCheck);
			
			// origin to bottom right
			bottomRightPossibleMoves(piece, positionCheck);			
		}
		
		// QUEEN
		else if(pieceType.equals("q"))
		{
			System.out.print("\nPossible moves for Queen at " + position + ": ");
			// origin to top left
			topLeftPossibleMoves(piece, positionCheck);
			
			// origin to top
			topPossibleMoves(piece, positionCheck);
			
			// origin to top right
			topRightPossibleMoves(piece, positionCheck);
			
			// origin to the right
			rightPossibleMoves(piece, positionCheck);
			
			// origin to bottom right
			bottomRightPossibleMoves(piece, positionCheck);
			
			// origin to bottom
			bottomPossibleMoves(piece, positionCheck);
			
			// origin to bottom left
			bottomLeftPossibleMoves(piece, positionCheck);
			
			// origin to the left
			leftPossibleMoves(piece, positionCheck);
		}
		
		// KNIGHT
		else if(pieceType.equals("n"))
		{
			System.out.print("\nPossible moves for Knight at " + position + ": ");
			
			// top left
			knightTopLeftPossibleMove(piece, positionCheck);
			
			// top top left
			knightTopTopLeftPossibleMove(piece, positionCheck);
			
			// top top right
			knightTopTopRightPossibleMove(piece, positionCheck);
			
			// top right
			knightTopRightPossibleMove(piece, positionCheck);
			
			// bottom right
			knightBottomRightPossibleMove(piece, positionCheck);
			
			// bottom bottom right
			knightBottomBottomRightPossibleMove(piece, positionCheck);
			
			// bottom bottom left
			knightBottomBottomLeftPossibleMove(piece, positionCheck);
			
			// bottom left
			knightBottomLeftPossibleMove(piece, positionCheck);
		}
		
		// KING
		else if(pieceType.equals("k"))
		{
			System.out.print("\nPossible moves for King at " + position + ": ");
			
			// top left
			kingTopLeftPossibleMove(piece, positionCheck);
			
			// top
			kingTopPossibleMove(piece, positionCheck);
			
			// top right
			kingTopRightPossibleMove(piece, positionCheck);
			
			// right
			kingRightPossibleMove(piece, positionCheck);
			
			// bottom right
			kingBottomRightPossibleMove(piece, positionCheck);
			
			// bottom
			kingBottomPossibleMove(piece, positionCheck);
			
			// bottom left
			kingBottomLeftPossibleMove(piece, positionCheck);
			
			// left
			kingLeftPossibleMove(piece, positionCheck);
		}
	}
	
	private String coordinateToPosition(int x1, int y1)
	{	
		char column = (char) (x1 + 65);
		String row = ""+(y1+1);
		String position = column + row;
		return position;
	}
	
	private void getPiecePath(int x1, int y1, int x2, int y2)
	{
		int verticleMovement = y2 - y1;
		int horizontalMovement = x2 - x1;
		int upRightMovement = verticleMovement - horizontalMovement;                
		int diagonalMovement = verticleMovement + horizontalMovement;
		int mathCheck = 0;

		if(verticleMovement != mathCheck && (horizontalMovement == mathCheck))
		{                        
			if(y2 > y1)
			{
				for(int i = y1+1; i <= y2; i++)
				{			
					System.out.print(board.getChessBoardSquare(x1, i).getPiece().getPieceType() + " ");					
				}
			}
			else
			{
				for(int i = y1-1; i >= y2; i--)
				{			
					System.out.print(board.getChessBoardSquare(x1, i).getPiece().getPieceType()	+ " ");
				}
			}
		}

		if (horizontalMovement != mathCheck && (verticleMovement == mathCheck)) 
		{
			if (x2 > x1) 
			{
				for (int i = x1 + 1; i <= x2; i++) 
				{
					System.out.print(board.getChessBoardSquare(i, y1).getPiece().getPieceType()	+ " ");
				}
			} 
			else 
			{
				for (int i = x1 - 1; i >= x2; i--) 
				{
					System.out.print(board.getChessBoardSquare(i, y1).getPiece().getPieceType() + " ");
				}
			}
		}

		if (diagonalMovement == mathCheck) 
		{
			if ((x2 < x1) && (y2 > y1)) 
			{
				int y = y1 + 1;
				for (int x = x1 - 1; x >= x2; x--, y++) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			} 
			else 
			{
				int y = y1 - 1;
				for (int x = x1 + 1; x <= x2; x++, y--) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			}
		}

		if (upRightMovement == mathCheck || (verticleMovement == horizontalMovement)) 
		{
			if ((x2 > x1) && (y2 > y1)) 
			{
				int y = y1 + 1;
				for (int x = x1 + 1; x <= x2; x++, y++) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			} 
			else
			{
				int y = y1 - 1;
				for (int x = x1 - 1; x >= x2; x--, y--) 
				{
					System.out.print(board.getChessBoardSquare(x, y).getPiece().getPieceType());
				}
			}
		}
	}

	private boolean otherPieceExistsInPath(int x1, int y1, int x2, int y2) 
	{
		boolean moveCompletable = true;

		int verticleMovement = y2 - y1;
		int horizontalMovement = x2 - x1;
		int upRightMovement = verticleMovement - horizontalMovement;
		int diagonalMovement = verticleMovement + horizontalMovement;
		int mathCheck = 0;

		if (verticleMovement != mathCheck && (horizontalMovement == mathCheck)) 
		{
			moveCompletable = (y2 > y1) ? verticleMovementPathCheck(x1, y1, y2) : verticleMovementPathCheck(x1, y2, y1);
		}

		if (horizontalMovement != mathCheck && (verticleMovement == mathCheck)) 
		{
			moveCompletable = (x2 > x1) ? horizontalMovementPathCheck(y1, x1, x2) : horizontalMovementPathCheck(y1, x2, x1);
		}

		if (diagonalMovement == mathCheck) 
		{
			moveCompletable = (x2 < x1) && (y2 > y1) ? diagonalMovementPathCheckBottomRightToTopLeft(y1, x1, x2) : diagonalMovementPathCheckBottomRightToTopLeft(y2, x2, x1);
		}

		if (upRightMovement == mathCheck || (verticleMovement == horizontalMovement)) 
		{
			moveCompletable = (x2 > x1) && (y2 > y1) ? diagonalMovementPathCheckBottomLeftToTopRight(y1, x1, x2) : diagonalMovementPathCheckBottomLeftToTopRight(y2, x2, x1);
		}

		return moveCompletable;
	}

	private boolean verticleMovementPathCheck(int x1, int y1, int y2) 
	{
		boolean moveCompletable = true;

		for (int i = y1 + 1; moveCompletable && (i < y2); i++) 
		{
			if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}

	private boolean horizontalMovementPathCheck(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;

		for (int i = x1 + 1; moveCompletable && (i < x2); i++) 
		{
			if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}

	private boolean diagonalMovementPathCheckBottomRightToTopLeft(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;
		int y = y1 + 1;
		for (int x = x1 - 1; moveCompletable && (x > x2); x--, y++) 
		{
			if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}

		return moveCompletable;
	}

	private boolean diagonalMovementPathCheckBottomLeftToTopRight(int y1, int x1, int x2) 
	{
		boolean moveCompletable = true;

		int y = y1 + 1;
		for (int x = x1 + 1; moveCompletable && (x < x2); x++, y++) 
		{
			if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
			{
				moveCompletable = false;
			}
		}
		return moveCompletable;
	}
	
	private void topPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(y1 >= 0 && y1 < 7)
		{				
			boolean pieceNotFound = true;

			for (int i = y1 + 1; pieceNotFound && (i < maxHeight) && (board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor()); i++)
			{
				System.out.print(" " + coordinateToPosition(x1, i));
				Position newMove = new Position(x1, i);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}			
	}	
	private void rightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(x1 >= 0 && x1 < 7)
		{
			boolean pieceNotFound = true;

			for (int i = x1 + 1; pieceNotFound && (i < maxWidth) && (board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor()); i++)
			{
				System.out.print(" " + coordinateToPosition(i, y1));
				Position newMove = new Position(i, y1);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}
	private void bottomPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(y1 >= 1 && y1 <= 7)
		{				
			boolean pieceNotFound = true;
			for (int i = y1 - 1; pieceNotFound && (i >= 0) && (board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor()); i--)
			{
				System.out.print(" " + coordinateToPosition(x1, i));
				Position newMove = new Position(x1, i);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x1, i).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x1, i).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					}
				}
			}
		}
	}
	private void leftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if(x1 >= 1 && x1 <= 7)
		{
			boolean pieceNotFound = true;

			for (int i = x1 - 1; pieceNotFound && (i >= 0) && (board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor()); i--)
			{
				System.out.print(" " + coordinateToPosition(i, y1));
				Position newMove = new Position(i, y1);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(i, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(i, y1).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}
	private void topLeftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			boolean pieceNotFound = true;

			int y = y1 + 1;
			for (int x = x1 - 1; pieceNotFound && (x >= 0) && (y < maxHeight) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x--, y++) 
			{
				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}	
	private void topRightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 <= 6))
		{
			boolean pieceNotFound = true;
			
			int y = y1 + 1;
			for (int x = x1 + 1; pieceNotFound && (x < maxWidth) && (y < maxHeight) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x++, y++) 
			{
				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}	
	private void bottomRightPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 6 && y1 <= maxHeight))
		{
			boolean pieceNotFound = true;
			
			int y = y1 - 1;
			for (int x = x1 + 1; pieceNotFound && (x < maxWidth) && (y >= 0) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x++, y--) 
			{
				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}
	private void bottomLeftPossibleMoves(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			boolean pieceNotFound = true;
			
			int y = y1 - 1;
			for (int x = x1 - 1; pieceNotFound && (x >= 0) && (y >= 0) && (board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor()); x--, y--) 
			{
				System.out.print(" " + coordinateToPosition(x, y));
				Position newMove = new Position(x, y);
				piece.setPossibleMoves(newMove);
				
				if (board.getChessBoardSquare(x, y).getPiece().getPieceType() != board.getBlankPiece().getPieceType()) 
				{
					pieceNotFound = false;
					if(board.getChessBoardSquare(x, y).getPiece().getPieceColor() != piece.getPieceColor())
					{
						System.out.print("*");
					} 
				}
			}
		}
	}
	private void knightTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 2 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1-2, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-2, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-2, y1+1)+"*");
					Position newMove = new Position(x1-2, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-2, y1+1));
				Position newMove = new Position(x1-2, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightTopTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 5))
		{
			if(board.getChessBoardSquare(x1-1, y1+2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1+2).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-1, y1+2)+"*");
					Position newMove = new Position(x1-1, y1+2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-1, y1+2));
				Position newMove = new Position(x1-1, y1+2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightTopTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 < 6 && y1 <= 5))
		{
			if(board.getChessBoardSquare(x1+1, y1+2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1+2).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+1, y1+2)+"*");
					Position newMove = new Position(x1+1, y1+2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+1, y1+2));
				Position newMove = new Position(x1+1, y1+2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 5 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1+2, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+2, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+2, y1+1)+"*");
					Position newMove = new Position(x1+2, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+2, y1+1));
				Position newMove = new Position(x1+2, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 5 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+2, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+2, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+2, y1-1)+"*");
					Position newMove = new Position(x1+2, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+2, y1-1));
				Position newMove = new Position(x1+2, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightBottomBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 2) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1-2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1-2).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+1, y1-2)+"*");
					Position newMove = new Position(x1+1, y1-2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+1, y1-2));
				Position newMove = new Position(x1+1, y1-2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightBottomBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 2) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1-2).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1-2).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-1, y1-2)+"*");
					Position newMove = new Position(x1-1, y1-2);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-1, y1-2));
				Position newMove = new Position(x1-1, y1-2);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void knightBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 2 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-2, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-2, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-2, y1-1)+"*");
					Position newMove = new Position(x1-2, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-2, y1-1));
				Position newMove = new Position(x1-2, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingTopLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-1, y1+1)+"*");
					Position newMove = new Position(x1-1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-1, y1+1));
				Position newMove = new Position(x1-1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingTopPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 < maxWidth && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1, y1+1)+"*");
					Position newMove = new Position(x1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1, y1+1));
				Position newMove = new Position(x1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingTopRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 <= 6))
		{
			if(board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1+1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+1, y1+1)+"*");
					Position newMove = new Position(x1+1, y1+1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+1, y1+1));
				Position newMove = new Position(x1+1, y1+1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 0) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+1, y1)+"*");
					Position newMove = new Position(x1+1, y1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+1, y1));
				Position newMove = new Position(x1+1, y1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingBottomRightPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 <= 6 && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1+1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1+1, y1-1)+"*");
					Position newMove = new Position(x1+1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1+1, y1-1));
				Position newMove = new Position(x1+1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingBottomPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 0 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1, y1-1)+"*");
					Position newMove = new Position(x1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1, y1-1));
				Position newMove = new Position(x1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingBottomLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 1) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1-1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-1, y1-1)+"*");
					Position newMove = new Position(x1-1, y1-1);
					piece.setPossibleMoves(newMove);
				} 
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-1, y1-1));
				Position newMove = new Position(x1-1, y1-1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
	private void kingLeftPossibleMove(Piece piece, Position position)
	{
		int x1 = position.getPositionX();
		int y1 = position.getPositionY();
		
		if((x1 >= 1 && y1 >= 0) && (x1 < maxWidth && y1 < maxHeight))
		{
			if(board.getChessBoardSquare(x1-1, y1).getPiece().getPieceType() != board.getBlankPiece().getPieceType())
			{
				if(board.getChessBoardSquare(x1-1, y1).getPiece().getPieceColor() != piece.getPieceColor())
				{
					System.out.print(" " + coordinateToPosition(x1-1, y1)+"*");
					Position newMove = new Position(x1-1, y1);
					piece.setPossibleMoves(newMove);
				}
			}
			else
			{
				System.out.print(" " + coordinateToPosition(x1-1, y1));
				Position newMove = new Position(x1-1, y1);
				piece.setPossibleMoves(newMove);
			}
		}
	}
}
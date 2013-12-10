package Chess;

public class Position 
{
	private int positionX;
	private int positionY;
	
	public Position(int x, int y)
	{
		this.positionX = x;
		this.positionY = y;
	}
	
	public int getPositionX() 
	{
		return positionX;
	}

	public void setPositionX(int positionX) 
	{
		this.positionX = positionX;
	}

	public int getPositionY() 
	{
		return positionY;
	}

	public void setPositionY(int positionY) 
	{
		this.positionY = positionY;
	}
}
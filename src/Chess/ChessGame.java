package Chess;

public class ChessGame 
{
	/**
	 * This is where the code begins!
	 */
	public static void main(String[] args)
	{
		FileInputReader fileReader = new FileInputReader();
		
		try
		{
			fileReader.readFile(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("No command line!  Please input one!");
		}
		catch(NullPointerException a)
		{
			System.out.println("Incorrect arguement! Please input a proper arguement!");
		}
	}
}
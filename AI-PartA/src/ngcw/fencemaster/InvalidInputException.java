/**
 * @author Chin Wai Ng (ngcw)
 * @author Renlord Yang (rnyang)
 * 
 * Input Exception
 * 
 */
package ngcw.fencemaster;
import java.lang.Exception;

public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Prevents invalid input of pieces
	 */
	public InvalidInputException(char input, int row, int col){
		super("The input [" + input + "] at (" + row + "," + col + ") is not valid");
		}
}

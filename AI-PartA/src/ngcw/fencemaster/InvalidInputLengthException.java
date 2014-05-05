/**
 * @author Chin Wai Ng (ngcw)
 * @author Renlord Yang (rnyang)
 * 
 * Input Length Exception
 * 
 */
package ngcw.fencemaster;
import java.lang.Exception;

public class InvalidInputLengthException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * prevents invalid length of input at each rows in board
	 */
	public InvalidInputLengthException(int row){
		super("The input length at row [" + row + "] is not valid");
		}
}
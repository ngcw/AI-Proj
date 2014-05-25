/**
 * @author Chin Wai Ng (ngcw)
 * @author Renlord Yang (rnyang)
 * 
 * Game state exception
 * 
 */
package aiproj.fencemaster;
import java.lang.Exception;

public class InvalidWinnerException extends Exception{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Prevents invalid number of winners
	 */
	public InvalidWinnerException(){
		super("Cannot have two winners in a game!");
		}
}

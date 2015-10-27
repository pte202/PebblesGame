package CA1;

/**
 * IllegalNumberOfPlayersException class. 
 * Custom created exception class that will be
 * thrown if the number of players is less than 0.
 * @author user
 *
 */
public class IllegalNumberOfPlayersException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for IllegalNumberOfPlayersException.
	 * 
	 * @param message -- the error message that will be 
	 * 				     displayed when exception is thrown
	 */
	public IllegalNumberOfPlayersException(String message) {
		super(message);
	}
}
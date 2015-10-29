package CA1;

/**
 * InvalidNumberOfPebblesException class.
 * Custom created exception class that will be
 * thrown if the number of pebbles is not valid.
 * 
 * @authors Exeter Students
 *
 */
public class InvalidNumberOfPebblesException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for InvalidNumberOfPebblesException.
	 * 
	 * @param message -- the error message that will be 
	 * 					 displayed when exception is thrown
	 */
	public InvalidNumberOfPebblesException(String message) {
		super(message);
	}
}
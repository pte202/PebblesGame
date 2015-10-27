package CA1;

/**
 * InvalidNumberOfPebblesException class.
 * Custom created exception class that will be
 * thrown if the weight of a pebble is less than 0.
 * 
 * @author Preslav Enchev and Nikolay Dochev
 *
 */
public class InvalidNumberOfPebblesException extends Exception {
	
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
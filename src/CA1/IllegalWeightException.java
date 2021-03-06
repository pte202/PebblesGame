package CA1;

/**
 * IllegalWeightException class. 
 * Custom created exception class that will be
 * thrown if the weight of a pebble is less than 0.
 * 
 * @authors Exeter Students
 *
 */
public class IllegalWeightException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for IllegalWeightException.
	 * 
	 * @param message -- the error message that will be 
	 * 					 displayed when exception is thrown
	 */
	public IllegalWeightException(String message) {
    	
        super(message);
    }
}
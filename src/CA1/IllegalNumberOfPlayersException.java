package CA1;

public class IllegalNumberOfPlayersException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalNumberOfPlayersException(String message) {
		super(message);
	}
}
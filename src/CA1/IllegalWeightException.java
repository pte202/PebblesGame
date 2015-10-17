package CA1;

/*
 * Class containing all customs exceptions
 */
public class IllegalWeightException extends Exception {
    public IllegalWeightException() {
    	
        super("You are trying to use files where there is a negative weight value!");
    }
}
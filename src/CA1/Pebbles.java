package CA1;

/**
 * 
 * Pebbles class.
 * 
 * Includes set and get methods
 * for the weight. * 
 * 
 * @author Exeter Students
 *
 */
public class Pebbles {
	
	private int weight;
	
	/**
	 * Default Constructor
	 */
	Pebbles () {
		
	}
	
	/**
	 * Constructor, which takes the weight 
	 * of the pebble object.
	 * @param weight
	 */
	Pebbles (int weight) {
		
		this.weight = weight;
	}
	
	
	/**
	 * Set the weight of the Pebbles object
	 * 
	 * @param weight    the weight of the pebble
	 * @throws IllegalWeightException if weight < 0
	 * @return no return
	 * 
	 */
	public void setWeight (int weight) throws IllegalWeightException{
		
		this.weight = weight;	
	}
	
	/**
	 * Get the weight of the Pebbles object
	 * 
	 * @return the weight of the pebbles object
	 */
	
	public int getWeight () {
	
		return weight;
	}
}
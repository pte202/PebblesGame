package CA1;

public class Pebbles {
	
	private int weight;
	private int id;
	private int correspondingBag;
	
	Pebbles () {
		
	}
	
	Pebbles (int weight, int correspondingBag) {
		
		this.weight = weight;
		this.correspondingBag = correspondingBag;
				
	}
	
	Pebbles (int weight) {
		
		this.weight = weight;
	}
	
	
	/**
	 * Set the weight of the Pebbles object
	 * 
	 * @param weight the weight of the pebble
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
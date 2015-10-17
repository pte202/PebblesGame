package CA1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class FileIO
{
	/**
	 * Receives user input 
	 * and returns the input directory
	 * @pre directory is valid
	 * 
	 * @return String fileDir
	 */
    public String getFile() {
		
		Scanner keyboardInput = new Scanner(System.in);
		//System.out.println("Enter the file path");
		//String fileDir = keyboardInput.nextLine();
		String test = "C:/Users/Niko216/Desktop/example_file_2.csv";
		return test;

	}
    /**Receives a file and returns an ArrayList
     * filled with the contents of the file.
     * @pre The file has to exist. 
     * @pre All values should be positive.
     * @post
     *	
     * 
     * @throws FileNotFoundException 
     * @param String fileDir    
     * @return ArrayList<Integer> bagContents
     * @throws IllegalWeightException 
     */
    public ArrayList<Integer> readFileAndFill(String fileDir) throws FileNotFoundException, IllegalWeightException {
    	ArrayList<Integer> bagContents = new ArrayList<Integer>();
    	//Get scanner instance
        Scanner scanner = new Scanner(new File(fileDir));
         
        //Set the delimiter used in file
        scanner.useDelimiter(",");
         
        //Get all tokens and store them in some data structure
        //I am just printing them
        while (scanner.hasNext())
        {
        	int value = Integer.parseInt(scanner.next());
        	if(value>=0) {
				bagContents.add(value);
			} else {
				scanner.close();
				throw new IllegalWeightException();
			}
        }
         
        //Do not forget to close the scanner 
        scanner.close();
        
        return bagContents;
    }
    

}



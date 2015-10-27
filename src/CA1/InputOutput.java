package CA1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * InputOutput class. The class includes different static methods
 * that help with the set up of the game (getFiles, getNumberOfPlayers,
 * readFileAndFillArray, isValidNumberOfPlayers, isValidFile)
 * @author user
 *
 */
public class InputOutput
{
	/**
	 * Receives user input and returns the input directory
	 * 
	 * @pre directory is valid and it exists
	 * @return String fileDir
	 * @throws FileNotFoundException 
	 */
    public static String getFileDir(int statement) throws FileNotFoundException {

    	Scanner keyboardInput = null;
    	System.out.println("Enter the file path " + statement + ": ");
    	keyboardInput = new Scanner(System.in);
    	String fileDir = keyboardInput.nextLine();
			
    	File f = new File(fileDir);
    	if (f.exists() && !f.isDirectory()) 
    		return fileDir;
    	else {
			throw new FileNotFoundException();
		}
			
    }
		

    public static int getNumberOfPlayers() {
    	
    	System.out.println("Enter the number of players");
		Scanner keyboardInput = new Scanner(System.in);
		int numberOfPlayers = Integer.parseInt(keyboardInput.nextLine());
		
		// close scanner
		//keyboardInput.close();
		
		return numberOfPlayers;    	
    }
    
    
    /**
     * Receives a file and returns an ArrayList
     * filled with the contents of the file.
     * @pre The file has to exist. 
     * @pre All values should be positive.
     * @post
     *	
     * 
     * @param String fileDir    
     * @return ArrayList<Integer> bagContents
     * @throws IllegalWeightException 
     * @throws FileNotFoundException 
     * 
     */
    public static ArrayList<Pebbles> readFileAndFillArray(String fileDir, ArrayList<Pebbles> bagContentsPebble) throws IllegalWeightException, FileNotFoundException {

    	//Get scanner instance
        Scanner scanner = new Scanner(new FileReader(fileDir));
         
        //Set the delimiter used in file
        scanner.useDelimiter(",");
        
        //Get all tokens and store them a list
        while (scanner.hasNext())
        {
        	String value = scanner.next();

        	// convert to integer and get rid of extra space with trim
        	int valueInt = Integer.parseInt(value.trim());
       
        	// check if the weight is valid
        	if(valueInt >= 0) {
        		bagContentsPebble.add(new Pebbles(valueInt));
			} 
        	// if the weight is not valid -> warn the user
			else {
				scanner.close();
				throw new IllegalWeightException(null);
			}
        }
        
        //System.out.println(bagContents.toString());
        
        //Do not forget to close the scanner 
        scanner.close();
        
        return bagContentsPebble; 
        //return null;
    }
    
    /**
     * 
     * Checks if the number of players is valid.
     * Does not return anything
     * 
     * @param numberOfPlayers
     * @throws IllegalNumberOfPlayersException
     */
    
    public static void isValidNumberOfPlayers(int numberOfPlayers) throws IllegalNumberOfPlayersException {
    	
    	if (numberOfPlayers <= 0) 
    		throw new IllegalNumberOfPlayersException(null);
    	
    }
    
    /**
     * 
     * Checks if the number of pebbles is valid.
     * Does not return anything.
     * 
     * @param numberOfPlayers
     * @param bagContentsPebble
     * @throws InvalidNumberOfPebblesException
     */
    public static void isValidPlayerToWeightRation(int numberOfPlayers, ArrayList<Pebbles> bagContentsPebble) throws InvalidNumberOfPebblesException {
    	
    	if (bagContentsPebble.size() < 11*numberOfPlayers )
    		throw new InvalidNumberOfPebblesException(null);    		
    	
    }

    

}

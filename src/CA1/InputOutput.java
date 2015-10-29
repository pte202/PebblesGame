package CA1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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
	 * getFileDir method.
	 * Receives user input and returns the input directory
	 * 
	 * @precondition directory is valid and it exists
	 * @return String fileDir
	 * @throws FileNotFoundException 
	 */
    public static String getFileDir(int statement) throws FileNotFoundException {

    	Scanner keyboardInput = null;
    	System.out.println("Enter the file path " + statement + ": ");
    	keyboardInput = new Scanner(System.in);
    	String fileDir = keyboardInput.nextLine();
    	
		if(fileDir.equalsIgnoreCase("X"))
			System.exit(0);
		
    	File f = new File(fileDir);
    	if (f.exists() && !f.isDirectory()) 
    		return fileDir;
    	else {
			throw new FileNotFoundException();
		}			
    }
		
    /**
     * getNumberOfPlayers method.
     * Get the number of players, who are going 
     * to play the Pebble game from the user
     * 
     * @return numberOfPlayers
     */
    public static int getNumberOfPlayers() {
    	
    	System.out.println("Enter the number of players");
		Scanner keyboardInput = new Scanner(System.in);
		String nextLine = keyboardInput.nextLine();
		if(nextLine.equalsIgnoreCase("X"))
			System.exit(0);
		int numberOfPlayers = Integer.parseInt(nextLine);
		
		// close scanner
		//keyboardInput.close();
		
		return numberOfPlayers;    	
    }
    
    
    /**
     * 
     * readFileAndFillArray method.
     * Receives a file and returns an ArrayList
     * filled with the contents of the file.
     * 
     * @precondition The file has to exist. 
     * @precondition All values should be positive.
     * @postcondition Array filled with pebble weights
     * 
     * @param String fileDir                    file containing the pebbles' weights 
     * @param ArrayList<Pebbles> bagContentsPebble empty array representing one of the bags 
     * @return ArrayList<Integer> bagContents      the filled array representing one of the bags
     * @throws IllegalWeightException 
     * @throws FileNotFoundException 
     * 
     */
    public static ArrayList<Pebbles> readFileAndFillArray(String fileDir, ArrayList<Pebbles> bagContentsPebble) 
    		throws IllegalWeightException, FileNotFoundException {

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
        
        //Do not forget to close the scanner 
        scanner.close();
        
        return bagContentsPebble; 
    }
    
    /**
     *
     * isValidNumberOfPlayers method.
     * Checks if the number of players is valid.
     * Does not return anything
     * 
     * @param numberOfPlayers       the number of players
     * @throws IllegalNumberOfPlayersException
     */    
    public static void isValidNumberOfPlayers(int numberOfPlayers) throws IllegalNumberOfPlayersException {
    	
    	if (numberOfPlayers <= 0) 
    		throw new IllegalNumberOfPlayersException(null);
    	
    }
    
    /**
     * 
     * isValidPlayerToWeightRation method.
     * Checks if the number of pebbles is valid 
     * compared to the players in order to start  
     * the game.
     * Does not return anything.
     * 
     * @param numberOfPlayers            the number of players
     * @param bagContentsPebble          arrayList containing all the pebbles
     * @throws InvalidNumberOfPebblesException
     */
    public static void isValidPlayerToWeightRation(int numberOfPlayers, ArrayList<Pebbles> bagContentsPebble) throws InvalidNumberOfPebblesException {
    	
    	if (bagContentsPebble.size() < 11*numberOfPlayers )
    		throw new InvalidNumberOfPebblesException(null);    		
    	
    }
    
    /**
     * createFile method.
     * Creates a file in the program directory.
     * 
     * @preconditions a valid file name
     * @postconditions a valid file
     * 
     * @param filename       the name of the file
     * @throws UnsupportedEncodingException 
     * @throws FileNotFoundException 
     * 
     */
    public static void createFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException{
    	PrintWriter writer = new PrintWriter(fileName, "UTF-8");
    	writer.close();
    }
    
    /**
     * writeToFile method.
     * Writes given information to a 
     * given file and gives true or 
     * false if the operation is successful or
     * not successful, respectively.
     * 
     * @param line      line to be written
     * @param fileName  name of the file
     * @return boolean  true if operations is successful,
     * 					false otherwise
     *
     */
    public static boolean writeToFile(String line, String fileName) {
    	
    	Writer output;
    	try {
			output= new BufferedWriter(new FileWriter(fileName, true));
		
    	output.append(line);
    	output.append("\n");
    	output.close();
    	} catch (IOException e) {
			return false;
		}
    	return true;

    }
    
    /**
     * Method checks if a file exists 
     * and returns true or false depending
     * on the check.
     * 
     * @param file     name of a file
     * @return boolean variable
     */
    public static boolean checkFileExistance(String file) {
    	
    	File f = new File(file);
    	
    	if (f.exists() && !f.isDirectory()) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
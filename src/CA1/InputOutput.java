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
	 * @pre directory is valid and it exists
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
     * isValidNumberOfPlayers method.
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
     * isValidPlayerToWeightRation method.
     * Checks if the number of pebbles is valid 
     * compared to the players in order to start  
     * the game.
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
    
    /**
     * createFile method.
     * Creates a file in the program directory.
     * 
     * @param filename;
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
     * @param line
     * @param fileName
     * @return boolean
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
     * 
     * @param file
     * @return
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
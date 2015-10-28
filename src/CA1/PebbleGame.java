package CA1;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
//import java.util.concurrent.locks.ReentrantLock;


/**
 * PebbleGame Class. This class includes several nested classes.
 * The Main class that simulates the pebble game. The Player class
 * which represent a thread (player). The StaticMethods class, 
 * which includes several helpful methods that are used in order to
 * run the game correctly. The PlayerActions class that defines the
 * actions of each thread, such as draw a pebble and discard a pebble.
 * 
 * @authors Nikolai Dochev, Preslav Enchev 
 * @version 1.0 28/10/2015
 *
 */
public class PebbleGame {
		
	// black bags
	private static ArrayList<Pebbles> pebblesBlack1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack3 = new ArrayList<Pebbles>();
	
	///private static boolean isFirst = true;
	private static ArrayList <Thread> activeThreads=new ArrayList <Thread>();
	private static boolean isFirst = true;

	
	// white bags
	private static ArrayList<Pebbles> pebblesWhite1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesWhite2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesWhite3 = new ArrayList<Pebbles>();
	
	/**
	 * Nested static Main class.
	 * Simulates the Pebble Game by using gameSetUpAndStart method, which 
	 * ensures that every case need for the game to start is 
	 * satisfied. it also uses threadSetUp initialize the 
	 * number of threads(players) based on the user input.
	 * The class has a main method, which invokes the whole game
	 *
	 */
	static class Main {
		
		/**
	     * Method sets up the game by getting the number of 
	     * players and the file paths with the 
	     * pebbles'weights  from the user
	     *
	     */
		private static void gameSetUpAndStart() {
			
			int numberOfPlayers = 0;
			
			// array with the files for each bag
			String [] files = new String [3];
			
			// get a valid number of players
			while(true){
				
				try {
					numberOfPlayers = 3;
//					numberOfPlayers = InputOutput.getNumberOfPlayers();
					
					InputOutput.isValidNumberOfPlayers(numberOfPlayers);
				}
				catch (IllegalNumberOfPlayersException e) {
					System.out.println("This is an invalid number of players! "
							+ "Please try again!");
					continue;
				}
				catch (NumberFormatException e) {
					System.out.println("This is invalid number format!"
							+ "Please try again!");
					continue;
				}
				catch (Exception e) {
					System.out.println("Invalid number of players!"
							+ "Please try again!");
				}
				break;					
			}	
			
			// get valid files
			while(true) {
				int count=0;
				while(true) {
					
					try {
						String fileDir = "C:/Users/user/Desktop/example_file_3.csv";
//						String fileDir = InputOutput.getFileDir(count);
						
						files[count] = fileDir;
					}
//					catch (FileNotFoundException e) {
//						System.out.println("Not a valid file!");
//						continue;
//					}
					catch (Exception e) {
						System.out.println("Not a valid file!");
						continue;
					}
					count++;
					if(count == 3)
						break;
				}
				
				try {
				
				pebblesBlack1 = InputOutput.readFileAndFillArray(files[0], pebblesBlack2);
					
				InputOutput.isValidPlayerToWeightRation(numberOfPlayers, pebblesBlack1);
					
				pebblesBlack2 = InputOutput.readFileAndFillArray(files[1], pebblesBlack2);
					
				InputOutput.isValidPlayerToWeightRation(numberOfPlayers, pebblesBlack2);
					
				pebblesBlack3 = InputOutput.readFileAndFillArray(files[2], pebblesBlack3);
					
				InputOutput.isValidPlayerToWeightRation(numberOfPlayers, pebblesBlack3);
				  
				} catch (InvalidNumberOfPebblesException e) {
					System.out.println("This is an invalid number of pebbles! "
							+ "Pebbles in each bag must be at least 11 times the "
							+ "number of players. " 
							+ "Please try with another file!");
					continue;
				} catch (IllegalWeightException e){
					//System.out.println("You are trying to use files where there is a negative weight value!");   
				} catch (Exception e) {
					System.out.println("There is a problem!");
					e.printStackTrace();
					continue;
				} 					
				break;
				
//				System.out.println(Thread.currentThread().getName()+"'s hand is: "+ playerActions.getArrayList().toString().replace("[", "").replace("]", ""));

				//C:/Users/user/Desktop/example_file_3.csv
			}
			
			threadSetUp(numberOfPlayers);			
		}
		
		/**
		 * Method sets up the number of threads
		 * depending on the user input		 
		 * 
		 * @param numberOfPlayers    number of players
		 */
		private static void threadSetUp(int numberOfPlayers) {
			
			PebbleGame pebbleGame = new PebbleGame();
			Player player = pebbleGame.new Player();
			for(int i=0; i<numberOfPlayers; i++){
				Thread thread = new Thread(player);
				thread.setName("Player "+i);
				activeThreads.add(thread);
				thread.start();
				
				
			}	
		}
		
		/**
		 * This is the main method of the program. 
		 * It executes the whole code - sets up the 
		 * game and starts it
		 * 	
		 * @param args
		 * @throws FileNotFoundException
		 * @throws IllegalWeightException
		 */
		public static void main (String[] args) throws FileNotFoundException, IllegalWeightException {

			gameSetUpAndStart();	
			
		}
	}
	
	/**
	 * Nested Player class. Implements the Runnable interface.
	 * The class acts as a thread. It overrides the 
	 * run method, which defines the actions of the thread
	 * during the game
	 */
	class Player implements Runnable{
		
		@Override
		public void run() {
			int bag;
			PebbleGame pebbleGame = new PebbleGame();
			PlayerActions playerActions = pebbleGame.new PlayerActions();
			
			// get the name of the players actions's file
			String actionsFile = StaticMethods.getFile();
			
		
			// choose a bag
			bag = playerActions.chooseBag();
			
			// check if the players'actions file exists 
			if (InputOutput.checkFileExistance(actionsFile)) {
				try {
					// create player actions'file
					InputOutput.createFile(actionsFile);
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				}
				catch (UnsupportedEncodingException e) {
					System.out.println("Unsupported encoding");
				}
				catch (Exception e) {
					System.out.println("Error while trying to create playerActionsFile");
				}
			}
			synchronized(this){
				// draw 10 pebbles
				playerActions.initialDrawPebbles(bag);
				System.out.println(Thread.currentThread().getName()+"'s hand is: "+ playerActions.getArrayList().toString().replace("[", "").replace("]", ""));
			}
			
			
			while (!playerActions.isWinning()&&isFirst) {
				synchronized(this){
					bag = playerActions.chooseBag();
					
					while(true)
					{
						if(StaticMethods.getBag(bag).size()==0){
							playerActions.transferPebbles(bag);
							bag = playerActions.chooseBag();

						}
						else
							break;
					}
					
					playerActions.discardPebbles(bag);
					playerActions.drawPebbles(bag);		
				}
			}
			
			if (isFirst){
				isFirst = false;
				System.out.println(Thread.currentThread().getName()+"  has won!!!\nThe winning hand is: "+ playerActions.getArrayList().toString());
			}
			
//			for (Thread thread : activeThreads){
//				thread.interrupt();
//			}
			
			
		}
		
	}
	
	/**
	 * Nested static StaticMethods class. Includes static methods - 
	 * getFile, randInt, getBag, which help 
	 */
	static class StaticMethods {
		
		// name of the player actions file
		private static String file = "PebbleGame.txt";	
		/**
		* Returns the file containing the actions of 
		* the players playing the pebbles game
		* 
		* @return file    a file
		*/
		public static String getFile(){
			return file;		
		}
		
		/**
		 * The method returns a random number between the 
		 * minimum and maximum values that are 
		 * passed to it. 
		 *
		 * @param min      Minimum value
		 * @param max      Maximum value.  Must be greater than min.
		 * @return Integer between min and max, inclusive.
		 * @see java.util.Random#nextInt(int)
		 */
		public synchronized static int randInt(int min, int max) {
			
		    Random rand = new Random();
		    int randomNum = 0;
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    // also makes sure that if the inputted number is 0
		    // it doesn't enter the function and thus 
		    // does not cause an exception
	    	randomNum = rand.nextInt((max - min) + 1) + min;
		    return randomNum;
		   
		}
		
		/**
		 * The method returns the chosen bag corresponding
		 * to the randomized number that it accepts
		 * 
		 * @param bag       randomized number of the bag
		 * @return          the chosen the bag
		 */
		public synchronized static ArrayList<Pebbles> getBag (int bag){
			switch (bag){
				case 1:
					return pebblesBlack1;
				case 2:
					return pebblesBlack2;
				case 3:
					return pebblesBlack3;
			}
			return null;
		}
	}
	
	
	/**
	 * Nested PlayerActions class. Contains methods
	 * which determine the actions of the players(threads).
	 * 
	 */
	class PlayerActions {
		
		// pebbles that the players holds
//		private ArrayList<Pebbles> pebblesInHand = new ArrayList<Pebbles>();
		
		// array with the pebble weights
		private ArrayList<Integer> weightArray = new ArrayList<Integer>();
		
		
		
		
		// status of the player; when it turns 
		// to true this means he has won the game
		private boolean playerStatus = false;
		
		/**
		 * Choose a bag to draw pebbles from on random
		 * 
		 * @return bag     chosen bag
		 */
		public synchronized int chooseBag() {
			
			// generate random number to represent bag
			int bag = StaticMethods.randInt(1,3);
			
			return bag;				
		}
		
		/**
		 * Initial draw of the  pebbles. The method uses the 
		 * drawPebbles method and executes it 10 times 
		 * (each player draws 10 random pebbles from a randomly 
		 * chosen bag) to satisfy the Pebble game rules.
		 * 
		 * @param bag      randomized bag number
		 */
		public synchronized void initialDrawPebbles(int bag) {
			for (int i=1; i <= 10; i++) {
				
				drawPebbles(bag);				
			}	
		
		}
		
		/**
		 * The process of drawing pebbles from a
		 * black bag
		 * @param bag      randomized bag number
		 * @throws UnsupportedEncodingException 
		 * @throws FileNotFoundException 
		 */
		public synchronized void drawPebbles(int bag) {			
			
			// the index of the random pebble the player has chosen
			int pebbleRand;
			  
			switch(bag) {
			case 1: 				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack1.size()-1);
				
				// add the chosen pebble to our hand
//				pebblesInHand.add(pebblesBlack1.get(pebbleRand));
				
				// add the pebble weight to array
				weightArray.add(pebblesBlack1.get(pebbleRand).getWeight());
				
//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack1.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack1.get(pebbleRand).getWeight()+
						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				// remove the chosen pebble
				pebblesBlack1.remove(pebbleRand);		
				
				break;
				
			case 2: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack2.size()-1);

				// add the chosen pebble to our hand
//				pebblesInHand.add(pebblesBlack2.get(pebbleRand));
				

				
				// add the pebble weight to array
				weightArray.add(pebblesBlack2.get(pebbleRand).getWeight());
				
//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack2.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack2.get(pebbleRand).getWeight()+
						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				// remove the chosen pebble
				pebblesBlack2.remove(pebbleRand);
						
				
				break;
				
			case 3: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack3.size()-1);

				// add the chosen pebble to our hand
//				pebblesInHand.add(pebblesBlack3.get(pebbleRand));
				
				// add the pebble weight to array
				weightArray.add(pebblesBlack3.get(pebbleRand).getWeight());

//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack3.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack3.get(pebbleRand).getWeight()+
						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
//				 remove the chosen pebble
				pebblesBlack3.remove(pebbleRand);
						
				break;
		
			}
		}
		
		/**
		 * The process of discarding a pebble from 
		 * the player's current hand into the white bag
		 * @param bag
		 */
		public synchronized void discardPebbles(int bag){
			
			
			// the index of the random pebble the player has chosen
			int pebbleRand;
			
			switch(bag) {
			
			case 1: 	
				
				// get a random pebble
//				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);
				pebbleRand = StaticMethods.randInt(0, weightArray.size()-1);
				
				// add the chosen pebble to the corresponding white bag
//				pebblesWhite1.add(pebblesInHand.get(pebbleRand));
				
				Pebbles pebble1 = new Pebbles();
				try {
					pebble1.setWeight(weightArray.get(pebbleRand));
				} catch (IllegalWeightException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebble1);
				
				// remove the chosen pebble
//				pebblesInHand.remove(pebbleRand);
				
//				System.out.println(Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
//						" to white bag "+bag+". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				String output1 = Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
						" to white bag "+bag+". ";
				
				
				// remove the pebble weight from array
				try {
				weightArray.remove(weightArray.get(pebbleRand));
				output1 += Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", "");
				
				InputOutput.writeToFile(output1,StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
				
			case 2: 
				
				// get a random pebble
//				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);
				pebbleRand = StaticMethods.randInt(0, weightArray.size()-1);

				
				// add the chosen pebble to the corresponding white bag
//				pebblesWhite2.add(pebblesInHand.get(pebbleRand));

				Pebbles pebble2 = new Pebbles();
				try {
					pebble2.setWeight(weightArray.get(pebbleRand));
				} catch (IllegalWeightException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebble2);
				
				// remove the chosen pebble
//				pebblesInHand.remove(pebbleRand);
				
//				System.out.println(Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
//						" to white bag "+bag+". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				String output2 = Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
						" to white bag "+bag+". ";
				
//				// remove the pebble weight from array
				weightArray.remove(weightArray.get(pebbleRand));	
				output2 += Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", "");
				
				InputOutput.writeToFile(output2,StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
				
			case 3: 

				// get a random pebble
//				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);
				pebbleRand = StaticMethods.randInt(0, weightArray.size()-1);

				
				// add the chosen pebble to the corresponding white bag
//				pebblesWhite3.add(pebblesInHand.get(pebbleRand));
				
				Pebbles pebble3 = new Pebbles();
				try {
					pebble3.setWeight(weightArray.get(pebbleRand));
				} catch (IllegalWeightException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebble3);
 
				// remove the chosen pebble
//				pebblesInHand.remove(pebbleRand);
				
//				System.out.println(Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
//						" to white bag "+bag+". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				String output3 = Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
						" to white bag "+bag+". ";
				
//				// remove the pebble weight from array
				weightArray.remove(weightArray.get(pebbleRand));	
				
				output3 += Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", "");
				
				InputOutput.writeToFile(output3,StaticMethods.getFile());
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
		
			}	
		}
		
		/**
		 * The process of transferring all the pebbles from 
		 * a white bag to its paired black bag 
		 * 
		 * @param bag
		 */
		public synchronized void transferPebbles(int bag){
//			in each case transfers all the pebbles from 
//			the white bag into the black bag
			
			switch(bag) {
			
			case 1:
				
				for(int i = 0; i < pebblesWhite1.size(); i++){
					pebblesBlack1.add(pebblesWhite1.get(i));
					pebblesWhite1.remove(i);
				}
				
				break;
				
			case 2: 

				for(int i = 0; i < pebblesWhite2.size(); i++){
					pebblesBlack2.add(pebblesWhite2.get(i));
					pebblesWhite2.remove(i);

				}
				
				break;
				
			case 3: 

				for(int i = 0; i < pebblesWhite3.size(); i++){
					pebblesBlack3.add(pebblesWhite3.get(i));
					pebblesWhite3.remove(i);

				}
				
				break;
		
			}	
			
		}
		
		/**
		 * A function that returns the current status of 
		 * a chosen thread -- whether it is winning or not.
		 * 
		 * @return playerStatus
		 */
		
		// TODO: implement this functionality with an event listener 
		public boolean isWinning() {
			
			int sumOfPebbles = 0;
			
			for (int i=0; i<getArrayList().size(); i++) {
				
				sumOfPebbles +=getArrayList().get(i);
				
			}
//			System.out.println(Thread.currentThread().getName()+"'s current hand is:\n"+sumOfPebbles);
			if (sumOfPebbles == 100) {
				
				playerStatus = true;
					
				return playerStatus;
			}
			
			return playerStatus;
			
		}	
		
		/**
		 * Gives an array with all of the pebbleweights
		 * @return weightArray
		 */
		public ArrayList<Integer> getArrayList(){
			return weightArray;
		}
	}
	

}
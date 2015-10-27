package CA1;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


/**
 * PebbleGame Class. This class simulates a games a pebbles.
 * It includes nested classes for the game configuration,  
 * 
 *
 */
public class PebbleGame {
		
	// black bags
	private static ArrayList<Pebbles> pebblesBlack1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack3 = new ArrayList<Pebbles>();
	
	///private static boolean isFirst = true;
	private static ArrayList <Thread> activeThreads=new ArrayList <Thread>();

	
	// white bags
	private static ArrayList<Pebbles> pebblesWhite1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesWhite2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesWhite3 = new ArrayList<Pebbles>();
	
	/**
	 * 
	 *
	 */
	static class Main {
		
		/**
	     * Method set ups the game by getting the number of 
	     * players and the file paths with the 
	     * pebbles'weights  from the user
	     *
	     * @throws NegativeNumberOfRecordsAddedException
	     * @throws RecordMismatchException
	     * @throws IllegalIDException
	     */
		private static void gameSetUp() {
			
			int numberOfPlayers=0;
			
			String [] files = new String [3];
			
			while(true){
				
				try {
				
					numberOfPlayers = InputOutput.getNumberOfPlayers();
					
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

			while(true) {
				int count=0;
				while(true) {
					
					try {
						String fileDir = InputOutput.getFileDir(count);
						files[count] = fileDir;
					}
					catch (FileNotFoundException e) {
						System.out.println("Not a valid file!");
						continue;
					}
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
		 * 
		 * 
		 * 
		 * @param numberOfPlayers
		 */
		private static void threadSetUp(int numberOfPlayers) {
			
			PebbleGame pebbleGame = new PebbleGame();
			Player player = pebbleGame.new Player();
			for(int i=0; i<=numberOfPlayers; i++){
				Thread thread = new Thread(player);
				thread.setName("Player "+i);
				activeThreads.add(thread);
				thread.start();
				
				
			}	
		}
		
		
		/**
		 * 
		 * @param args
		 * @throws FileNotFoundException
		 * @throws IllegalWeightException
		 */
		public static void main (String [] args) throws FileNotFoundException, IllegalWeightException {

			gameSetUp();	
			
		}
	}
	
	/**
	 *
	 */
	class Player implements Runnable{
		
		// Creates a lock for your method
		private ReentrantLock lock = new ReentrantLock();

	
		
		@Override
		public void run() {
			int bag;
			PebbleGame pebbleGame = new PebbleGame();
			PlayerActions playerActions = pebbleGame.new PlayerActions();
			
			// get the name of the players actions's file
			String actionsFile = playerActions.getFile();
			
			lock.lock();
		
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
			
			// draw 10 pebbles
			playerActions.initialDrawPebbles(bag);
			System.out.println(Thread.currentThread().getName()+"'s hand is: "+ playerActions.getArrayList().toString().replace("[", "").replace("]", ""));

			
			lock.unlock();
			
			while (!playerActions.isWinning()) {
				lock.lock();
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
		
				lock.unlock();
			}
							
				
			
	
			System.out.println(Thread.currentThread().getName()+"  has won!!!\nThe winning hand is: "+ playerActions.getArrayList().toString());
			for (Thread thread : activeThreads){
				thread.interrupt();
			}
			
			
			
			
		}
		
	}
	
	/**
	 * 
	 * 
	 *
	 */
	static class StaticMethods {
		static ReentrantLock lock = new ReentrantLock();
		/**
		 * Returns a pseudo-random number between min and max, inclusive.
		 * The difference between min and max can be at most
		 * <code>Integer.MAX_VALUE - 1</code>.
		 *
		 * @param min Minimum value
		 * @param max Maximum value.  Must be greater than min.
		 * @return Integer between min and max, inclusive.
		 * @see java.util.Random#nextInt(int)
		 */
		public static int randInt(int min, int max) {
			lock.lock();
			
		    Random rand = new Random();
		    int randomNum = 0;
		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    // also makes sure that if the inputted number is 0
		    // it doesn't enter the function and thus 
		    // does not cause an exception
	    	randomNum = rand.nextInt((max - min) + 1) + min;
	    	lock.unlock();
		    return randomNum;
		   
		}
		
		public static ArrayList<Pebbles> getBag (int bag){
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
	 * Nested player class
	 */
	class PlayerActions {
		
		// pebbles that the players holds
		private ArrayList<Pebbles> pebblesInHand = new ArrayList<Pebbles>();
		
		// array with the pebble weights
		private ArrayList<Integer> weightArray = new ArrayList<Integer>();
		
		private ReentrantLock lock = new ReentrantLock();
		
		// name of the player actions file
		private String file = "PebblesOutput.txt";
		
		/**
		 * Returns the file containing the actions of 
		 * the players playing the pebbles game
		 * 
		 * @return file
		 */
		public String getFile(){
			return file;
		}
		
		// status of the player; when it turns 
		// to true this means he has won the game
		private boolean playerStatus = false;
		
		public PlayerActions () {
			
		}
		
		/**
		 * Choose a bag to draw pebbles from on random
		 * 
		 * @return chosen bag
		 */
		
		public int chooseBag() {
			
			// generate random number to represent bag
			int bag = StaticMethods.randInt(1,3);
			
			return bag;				
		}
		
		/**
		 * Initial draw of the  pebbles. The method uses the drawPebbles 
		 * method and executes it 10 times.
		 * (each player draws 10 random pebbles from a randomly chosen bag)
		 */
		public void initialDrawPebbles(int bag) {
			lock.lock();
			for (int i=1; i <= 10; i++) {
				
				drawPebbles(bag);				
			}	
			lock.unlock();
		
		}
		
		/**
		 * The process of drawing pebbles from a
		 * black bag
		 * @param bag
		 * @throws UnsupportedEncodingException 
		 * @throws FileNotFoundException 
		 */
		public void drawPebbles(int bag) {			
			
			// the index of the random pebble the player has chosen
			int pebbleRand;
			
			lock.lock();
			  
			switch(bag) {
			case 1: 				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack1.size()-1);
				
				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack1.get(pebbleRand));
				
				// add the pebble weight to array
				weightArray.add(pebblesBlack1.get(pebbleRand).getWeight());
				
//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack1.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack1.get(pebbleRand).getWeight()+
						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),file);
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
				pebblesInHand.add(pebblesBlack2.get(pebbleRand));
				

				
				// add the pebble weight to array
				weightArray.add(pebblesBlack2.get(pebbleRand).getWeight());
				
//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack2.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack2.get(pebbleRand).getWeight()+
						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),file);
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
				pebblesInHand.add(pebblesBlack3.get(pebbleRand));
				
				// add the pebble weight to array
				weightArray.add(pebblesBlack3.get(pebbleRand).getWeight());

//				System.out.println(Thread.currentThread().getName()+" has drawn a "+pebblesBlack3.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
//				try {
//				InputOutput.writeToFile(Thread.currentThread().getName()+" has drawn a "+pebblesBlack3.get(pebbleRand).getWeight()+
//						" from black bag "+bag +". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""),file);
//				}
//				catch (Exception e) {
//					System.out.println("Unpredicted error while trying to write information to a file!");
//				}
//				 remove the chosen pebble
				pebblesBlack3.remove(pebbleRand);
						
				break;
		
			}
			lock.unlock();	
		}
		
		/**
		 * The process of discarding a pebble from 
		 * the player's current hand into the white bag
		 * @param bag
		 */
		public void discardPebbles(int bag){
			
			lock.lock();
			// the index of the random pebble the player has chosen
			int pebbleRand;
			
			switch(bag) {
			
			case 1: 	
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebblesInHand.get(pebbleRand));
						
				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
				
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
				
				InputOutput.writeToFile(output1,file);
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
				
			case 2: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite2.add(pebblesInHand.get(pebbleRand));

				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
				
//				System.out.println(Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
//						" to white bag "+bag+". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				String output2 = Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
						" to white bag "+bag+". ";
				
				// remove the pebble weight from array
				weightArray.remove(weightArray.get(pebbleRand));	
				output2 += Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", "");
				
				InputOutput.writeToFile(output2,file);
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
				
			case 3: 

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite3.add(pebblesInHand.get(pebbleRand));
 
				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
				
//				System.out.println(Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
//						" to white bag "+bag+". "+Thread.currentThread().getName()+ 
//						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", ""));
				try {
				String output3 = Thread.currentThread().getName()+" has discarded a "+weightArray.get(pebbleRand)+
						" to white bag "+bag+". ";
				
				// remove the pebble weight from array
				weightArray.remove(weightArray.get(pebbleRand));	
				
				output3 += Thread.currentThread().getName()+ 
						"'s hand is: "+ getArrayList().toString().replace("[", "").replace("]", "");
				
				InputOutput.writeToFile(output3,file);
				}
				catch (Exception e) {
					System.out.println("Unpredicted error while trying to write information to a file!");
				}
				break;
		
			}	
			lock.unlock();
		}
		
		/**
		 * The process of transferring all the pebbles from 
		 * a white bag to its paired black bag 
		 * 
		 * @param bag
		 */
		public void transferPebbles(int bag){
//			in each case transfers all the pebbles from 
//			the white bag into the black bag
			
			lock.lock();
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
			
			lock.unlock();
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
			
			for (int i=0; i<pebblesInHand.size(); i++) {
				
				sumOfPebbles +=pebblesInHand.get(i).getWeight();
				
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
package CA1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


/**
 * 
 *
 */
public class PebbleGame {
	
	private int numOfWhiteBags = 3;
	private int numOfBlackBags = 3;
	
	// black bags
	private static ArrayList<Pebbles> pebblesBlack1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack3 = new ArrayList<Pebbles>();
	
	private static boolean isFirst = true;
	
	// white bags
	private static ArrayList<Pebbles> pebblesWhite1 = new ArrayList<Pebbles>();;
	private static ArrayList<Pebbles> pebblesWhite2 = new ArrayList<Pebbles>();;
	private static ArrayList<Pebbles> pebblesWhite3 = new ArrayList<Pebbles>();;
	
	PebbleGame() {
		
	}
	public static void run() throws FileNotFoundException, IllegalWeightException{
		//Main.testmain();
	}
	/**
	 * 
	 *
	 */
	static class Main {
		
		private static void gameSetUp() {
			
			// create file instance
			FileIO file = new FileIO();
			
			while(true){
				
				try {
				
					int numberOfPlayers = file.getNumberOfPlayers();
					
					file.isValidNumberOfPlayers(numberOfPlayers);
					
					String [] files = file.getFiles();
					
					pebblesBlack1=file.readFileAndFill(files[0], pebblesBlack1);
					
					file.isValidFile(numberOfPlayers, pebblesBlack1);
					
					pebblesBlack2=file.readFileAndFill(files[0], pebblesBlack2);
					
					file.isValidFile(numberOfPlayers, pebblesBlack2);
					
					pebblesBlack3=file.readFileAndFill(files[0], pebblesBlack3);
					
					file.isValidFile(numberOfPlayers, pebblesBlack3);
				  
				} catch (InvalidNumberOfPebblesException e) {
					System.out.println("This is an invalid number of pebbles! "
							+ "Pebbles must be at least 11 times the "
							+ "number of players" 
							+ "Please try with another file!");
					continue;
				} catch (IllegalNumberOfPlayersException e) {
					System.out.println("This is an invalid number of players! "
							+ "Please try again!");
					continue;
				} catch (IllegalWeightException e){
					System.out.println("You are trying to use files where there is a negative weight value!");
				    
				} catch (Exception e) {
					System.out.println("There is a problem!");
					continue;
				} 
					
				break;
				
			
			
			
			
			
			// get the file location
			//String fileDir1 = file.getFile("C:/Users/user/Desktop/example_file_1.csv");
			//String fileDir2 = file.getFile("C:/Users/user/Desktop/example_file_2.csv");
			//String fileDir3 = file.getFile("C:/Users/user/Desktop/example_file_3.csv");
			
			
			
			
			
	
	
			//System.out.println("Black 1 " + pebblesBlack1.toString());
			//System.out.println("Black 1 " + pebblesBlack2.toString());
			//System.out.println("Black 1 " + pebblesBlack3.toString());
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
			
			PebbleGame pebbleGame = new PebbleGame();
			Player player = pebbleGame.new Player();
			PlayerActions playerAction = pebbleGame.new PlayerActions();
			for(int i=0; i<=7; i++){
				Thread thread = new Thread(player);
				thread.setName("Player "+i);
				thread.start();
			}
			
			
		}
	}
	/**
	 *
	 */
	class Player implements Runnable{
		@Override
		public void run() {
			int bag;
			PebbleGame pebbleGame = new PebbleGame();
			PlayerActions playerActions = pebbleGame.new PlayerActions();
			
			
			
			synchronized(this){
				// choose a bag
				bag = playerActions.chooseBag();
				// draw 10 pebbles
				playerActions.initialDrawPebbles(bag);
			}
			//status = p1.isWinning();
						
			while (!playerActions.isWinning()&&isFirst) {
				synchronized(this){
					bag = playerActions.chooseBag();
					
					while(true)
					{
						if(StaticMethods.getBag(bag).size()==0){
							playerActions.transferPebbles(bag);
							bag = playerActions.chooseBag();
//							System.out.println("new bag");
						}
						else
							break;
					}
					playerActions.discardPebbles(bag);
					playerActions.drawPebbles(bag);
				}
							
			}
			if(isFirst)
			{
				isFirst=false;
				ArrayList<Integer> weightArray = new ArrayList<Integer>();
				for (Pebbles n: playerActions.pebblesInHand){
					weightArray.add(n.getWeight());
				}
				System.out.println( Thread.currentThread().getName()+"  has won!!!\nThe winning hand is: "+ weightArray.toString());
			
			}
			
			
			
		}
		
	}
	/**
	 * 
	 * 
	 *
	 */
	static class StaticMethods {
		
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
		
		// status of the player; when it turns 
		// to true this means he has won the game
		private boolean playerStatus = false;
		
		public PlayerActions () {
			
		}
		
		/**
		 * Choose a bag to draw pebbles from on random
		 * @return chosen bag
		 */
		
		public int chooseBag() {
			
			// generate random number to represent bag
			int bag = StaticMethods.randInt(1,3);
			
			return bag;				
		}
		
		/**
		 * Initial draw of the  pebbles
		 * (each player draws 10 random pebbles from a randomly chosen bag)
		 */
		public synchronized void initialDrawPebbles(int bag) {
			
			for (int i=1; i <= 10; i++) {
				
				drawPebbles(bag);				
			}			
		}
		
		/**
		 * The process of drawing pebbles from a
		 * black bag
		 * @param bag
		 */
		public synchronized void drawPebbles(int bag) {
		
			int pebbleRand;
			  
			switch(bag) {
			case 1: 				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack1.size()-1);
				
				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack1.get(pebbleRand));

				// remove the chosen pebble
				pebblesBlack1.remove(pebbleRand);		
				
				break;
				
			case 2: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack2.size()-1);

				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack2.get(pebbleRand));

				// remove the chosen pebble
				pebblesBlack2.remove(pebbleRand);
						
				
				break;
				
			case 3: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack3.size()-1);

				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack3.get(pebbleRand));

				// remove the chosen pebble
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
			
			int pebbleRand;
			switch(bag) {
			
			case 1: 				
				

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebblesInHand.get(pebbleRand));
						
				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
				
				break;
				
			case 2: 

				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite2.add(pebblesInHand.get(pebbleRand));

				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
						
				
				break;
				
			case 3: 

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite3.add(pebblesInHand.get(pebbleRand));
 
				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
						
				
				break;
		
			}		
		
			
			
		}
		/**
		 * The process of transferring all the pebbles from 
		 * a white bag to its paired black bag 
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
	}
	

}


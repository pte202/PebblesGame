package CA1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 *
 */
public class PebbleGame {
	
	private int numOfWhiteBags = 3;
	private int numOfBlackBags = 3;
	
	private static ArrayList<Pebbles> pebblesBlack1 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack2 = new ArrayList<Pebbles>();
	private static ArrayList<Pebbles> pebblesBlack3 = new ArrayList<Pebbles>();
	
	
	
	private ArrayList<Pebbles> pebblesWhite1 = new ArrayList<Pebbles>();;
	private ArrayList<Pebbles> pebblesWhite2 = new ArrayList<Pebbles>();;
	private ArrayList<Pebbles> pebblesWhite3 = new ArrayList<Pebbles>();;
	
	PebbleGame() {
		
	}
	/**
	 * 
	 *
	 */
	static class Main {
		private static void Test(){
			for(int i =0; i<100;i++)
			{
				
				pebblesBlack1.add(new Pebbles(i));
				pebblesBlack2.add(new Pebbles(i));
				pebblesBlack3.add(new Pebbles(i));
				System.out.println("Pebbles1: "+pebblesBlack1.get(i).getWeight()
						+"Pebbles2: "+pebblesBlack2.get(i).getWeight()
						+"Pebbles3: "+pebblesBlack3.get(i).getWeight());
			
				
			}
		}
		private static void TestIO() throws FileNotFoundException, IllegalWeightException{
			ArrayList<Integer> bag1 = new ArrayList<Integer>();
			ArrayList<Integer> bag2 = new ArrayList<Integer>();
			ArrayList<Integer> bag3 = new ArrayList<Integer>();
			FileIO file = new FileIO();
			String fileDir1 = file.getFile("C:/Users/Niko216/Desktop/example_file_1.csv");
			String fileDir2 = file.getFile("C:/Users/Niko216/Desktop/example_file_2.csv");
			String fileDir3 = file.getFile("C:/Users/Niko216/Desktop/example_file_3.csv");
			bag1=file.readFileAndFill(fileDir1);
			bag2=file.readFileAndFill(fileDir2);
			bag3=file.readFileAndFill(fileDir3);
			for (Integer element: bag1){
				pebblesBlack1.add(new Pebbles(element));
			}
			for (Integer element: bag2){
				pebblesBlack2.add(new Pebbles(element));
			}
			for (Integer element: bag3){
				pebblesBlack3.add(new Pebbles(element));
			}
			
			
		}
		/**
		 * 
		 * @param args
		 * @throws FileNotFoundException
		 * @throws IllegalWeightException
		 */
		public static void main (String [] args) throws FileNotFoundException, IllegalWeightException {
//			Test();
			TestIO();
			PebbleGame pebbleGame = new PebbleGame();
			Player player = pebbleGame.new Player();
			PlayerActions playerAction = pebbleGame.new PlayerActions();
			new Thread(player).start();
			new Thread(player).start();
//			int bag;
//			boolean playerCountValidation ;
//			Pebbles test = new Pebbles();
//			PebbleGame pebbleGame = new PebbleGame();
//			Players p1 = pebbleGame.new Players();
//			
//			
//			bag = p1.chooseBag();
//			p1.initialDrawPebbles(bag);
//			//status = p1.isWinning();
//			System.out.println(p1.pebblesInHand.toString());
//			
//			while (!p1.isWinning()) {
//				bag = p1.chooseBag();
//				if (pebblesBlack1.size()== 1){
//					p1.transferPebbles(1);
////					System.out.println("transfer1");
//				}
//				if (pebblesBlack2.size()== 1){
//					p1.transferPebbles(2);
////					System.out.println("transfer2");
//				}
//				if (pebblesBlack3.size()== 1){
//					p1.transferPebbles(3);
////					System.out.println("transfer3");
//				}
//				p1.discardPebbles(bag);
//				p1.drawPebbles(bag);
//				
//			}
//			ArrayList<Integer> weightArray = new ArrayList<Integer>();
//			for (Pebbles n: p1.pebblesInHand){
//				weightArray.add(n.getWeight());
//			}
//			System.out.println("You won mate!!!\n Your hand is: "+ weightArray.toString());		
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
			
			bag = playerActions.chooseBag();
			playerActions.initialDrawPebbles(bag);
			//status = p1.isWinning();
						
			while (!playerActions.isWinning()) {
				synchronized(this){
				bag = playerActions.chooseBag();
				if (pebblesBlack1.size()== 1){
					playerActions.transferPebbles(1);
//					System.out.println("transfer1");
				}
				if (pebblesBlack2.size()== 1){
					playerActions.transferPebbles(2);
//					System.out.println("transfer2");
				}
				if (pebblesBlack3.size()== 1){
					playerActions.transferPebbles(3);
//					System.out.println("transfer3");
				}
				playerActions.discardPebbles(bag);
				playerActions.drawPebbles(bag);
				}
				
			}
			ArrayList<Integer> weightArray = new ArrayList<Integer>();
			for (Pebbles n: playerActions.pebblesInHand){
				weightArray.add(n.getWeight());
			}
			System.out.println( Thread.currentThread().getName()+"  has won!!!\n The winning hand is: "+ weightArray.toString());	
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

		    // NOTE: This will (intentionally) not run as written so that folks
		    // copy-pasting have to think about how to initialize their
		    // Random instance.  Initialization of the Random instance is outside
		    // the main scope of the question, but some decent options are to have
		    // a field that is initialized once and then re-used as needed or to
		    // use ThreadLocalRandom (if using at least Java 1.7).
		    Random rand = new Random();

		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    int randomNum = rand.nextInt((max - min) + 1) + min;

		    return randomNum;
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
//				System.out.println(pebblesBlack1.size());
//				System.out.println(pebblesInHand.size());

				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack1.get(pebbleRand));
//				System.out.println(pebblesInHand.toString());

				// remove the chosen pebble
				pebblesBlack1.remove(pebbleRand);
						
				
				break;
				
			case 2: 
				
				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack2.size()-1);
//				System.out.println(pebblesBlack2.size());
//				System.out.println(pebblesInHand.size());

				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack2.get(pebbleRand));
//				System.out.println(pebblesInHand.toString());

				// remove the chosen pebble
				pebblesBlack2.remove(pebbleRand);
						
				
				break;
				
			case 3: 

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesBlack3.size()-1);
//				System.out.println(pebblesBlack3.size());
//				System.out.println(pebblesInHand.size());

				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack3.get(pebbleRand));
//				System.out.println(pebblesInHand.toString());
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
//				System.out.println(pebblesInHand.size());

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite1.add(pebblesInHand.get(pebbleRand));
						
				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
				
				break;
				
			case 2: 

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);
//				System.out.println(pebblesInHand.size());

				
				// add the chosen pebble to the corresponding white bag
				pebblesWhite2.add(pebblesInHand.get(pebbleRand));

				// remove the chosen pebble
				pebblesInHand.remove(pebbleRand);
						
				
				break;
				
			case 3: 

				// get a random pebble
				pebbleRand = StaticMethods.randInt(0, pebblesInHand.size()-1);
//				System.out.println(pebblesInHand.size());

				
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
		// TODO: implement this functionality with a listener 
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



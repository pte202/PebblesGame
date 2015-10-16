package CA1;

import java.util.ArrayList;
import java.util.Random;

import CA1.FileIO.ReadCVS;

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
	
	static class Main {
		private static void Test(){
			for(int i =0; i<100;i++)
			{
				
//				pebblesBlack1.add(new Pebbles(i));
				pebblesBlack2.add(new Pebbles(i));
				pebblesBlack3.add(new Pebbles(i));
//				System.out.println("Pebbles1: "+pebblesBlack1.get(i).getWeight()
//						+"Pebbles2: "+pebblesBlack2.get(i).getWeight()
//						+"Pebbles3: "+pebblesBlack3.get(i).getWeight());
			
				
			}
		}
		private static void TestIO(){
			ReadCVS file = new ReadCVS();
			String myFile = file.getFile();
			file.readFile(myFile);
			System.out.println(file.readFile(myFile).toString());
			
		}
	
		public static void main (String [] args) {
			Test();
			TestIO();
			

			int bag;
			boolean playerCountValidation ;
			Pebbles test = new Pebbles();
			PebbleGame pebbleGame = new PebbleGame();
			Players p1 = pebbleGame.new Players();
			
			
			bag = p1.choseBag();
			p1.initialDrawPebbles(bag);
			//status = p1.isWinning();
			System.out.println(p1.pebblesInHand.toString());
			
			while (!p1.isWinning()) {
				if (pebblesBlack1.size()== 0){
					p1.transferPebbles(1);
				}
				if (pebblesBlack2.size()== 0){
					p1.transferPebbles(2);
				}
				if (pebblesBlack3.size()== 0){
					p1.transferPebbles(3);
				}

				p1.discardPebbles(bag);
				p1.drawPebbles(bag);
//				p1.isWinning();
				
			}
			ArrayList<Integer> weightArray = new ArrayList<Integer>();
			for (Pebbles n: p1.pebblesInHand){
				weightArray.add(n.getWeight());
			}
			System.out.println("You won mate!!!\n Your hand is: "+ weightArray.toString());		
		}
		
	}
	
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
	class Players {
		
		// pebbles that the players holds
		private ArrayList<Pebbles> pebblesInHand = new ArrayList<Pebbles>();
		
		// status of the player; when it turns 
		// to true this means he won the game
		private boolean playerStatus = false;
		
		public Players () {
			
		}
		
		/**
		 * Chose a bag to draw pebbles from on random
		 * @return chosen bag
		 */
		
		public int choseBag() {
			
			// generate random number to represent bag
			int bag = StaticMethods.randInt(1,3);
			
			return bag;				
		}
		
		/**
		 * 
		 */
		public void initialDrawPebbles(int bag) {
			
			for (int i=1; i <= 10; i++) {
				
				drawPebbles(bag);				
			}			
		}
		
		/**
		 * The process of drawing pebbles from a
		 * black bag
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
		public synchronized void transferPebbles(int bag){
//			in each case transfers all the pebbles from 
//			the white bag into the black bag
			switch(bag) {
			
			case 1:
				
				for(int i = 0; i < pebblesWhite1.size(); i++){
					pebblesBlack1.add(pebblesWhite1.get(i));
				}
				
				break;
				
			case 2: 

				for(int i = 0; i < pebblesWhite1.size(); i++){
					pebblesBlack1.add(pebblesWhite1.get(i));
				}
				
				break;
				
			case 3: 

				for(int i = 0; i < pebblesWhite1.size(); i++){
					pebblesBlack1.add(pebblesWhite1.get(i));
				}
				
				break;
		
			}		
		}
		
		public boolean isWinning() {
			
			int sumOfPebbles = 0;
			
			for (int i=0; i<pebblesInHand.size(); i++) {
				
				sumOfPebbles +=pebblesInHand.get(i).getWeight();
				
			}
			
			if (sumOfPebbles == 100) {
				
				playerStatus = true;
				
				return playerStatus;
			}
			
			return playerStatus;
			
		}	
	}		
}



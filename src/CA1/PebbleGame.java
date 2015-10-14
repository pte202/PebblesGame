package CA1;

import java.util.ArrayList;
import java.util.Random;

public class PebbleGame {
	
	private int numOfWhiteBags = 3;
	private int numOfBlackBags = 3;
	
	private ArrayList<Pebbles> pebblesBlack1;
	private ArrayList<Pebbles> pebblesBlack2;
	private ArrayList<Pebbles> pebblesBlack3;
	
	private ArrayList<Pebbles> pebblesWhite1;
	private ArrayList<Pebbles> pebblesWhite2;
	private ArrayList<Pebbles> pebblesWhite3;
	
	PebbleGame() {
		
	}
	
	static class Main {
	
		public static void main (String [] args) {
					
					int bag;
					
					// get a random bag
					bag = staticMethods.randInt(1, 3);
					
					Pebbles test = new Pebbles();
					PebbleGame pebbleGame = new PebbleGame();
					Players p1 = pebbleGame.new Players();
					
					
		}
		
	}
	
	static class staticMethods {
		
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
		private ArrayList<Pebbles> pebblesInHand;
		
		// status of the player; when it turns 
		// to true this means he won the game
		private boolean playerStatus = false;
		
		public Players () {
			
		}
		
		
		/**
		 * The process of drawing pebbles from a
		 * black bag
		 */
		public void drawPebbles(int bag) {
		
			int pebbleRand;
			
			
			
			switch(bag) {
			
			case 1: 				
				
				// get a random pebble
				pebbleRand = staticMethods.randInt(0, pebblesBlack1.size());
				
				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack1.get(pebbleRand));
				
				// remove the chosen pebble
				pebblesBlack1.remove(pebbleRand);
						
				
				break;
				
			case 2: 

				// get a random pebble
				pebbleRand = staticMethods.randInt(0, pebblesBlack2.size());
				
				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack2.get(pebbleRand));
				
				// remove the chosen pebble
				pebblesBlack2.remove(pebbleRand);
						
				
				break;
				
			case 3: 

				// get a random pebble
				pebbleRand = staticMethods.randInt(0, pebblesBlack3.size());
				
				// add the chosen pebble to our hand
				pebblesInHand.add(pebblesBlack3.get(pebbleRand));
				
				// remove the chosen pebble
				pebblesBlack3.remove(pebbleRand);
						
				
				break;
		
			}
			
			
	
		}
		
		
		
		
			
			
			
			
			
		}
	
		
		
	}



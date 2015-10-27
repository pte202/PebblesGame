package CA1;

public class SleepyThreads {
	
	public static void main (String [] args) {
		
		SleepyRunnable sr = new SleepyRunnable ();
		
		Thread uno = new Thread (sr, " Jose");
		Thread dos = new Thread (sr, " Pablo");
		Thread tres = new Thread (sr, "Juan");
		
		uno.start();
		dos.start();
		tres.start();
	}

}

class SleepyRunnable implements Runnable {
	
	public void run() {
		
		for (int i = 0; i < 5; i++) {
			System.out.println(Thread.currentThread().getName() + " is running");
		}
		
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {}
	}
}

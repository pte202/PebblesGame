package CA1;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
	
	public static void main (String [] args) {

		Map<String, Integer> n = new HashMap<String, Integer>();
		for (int i = 1; i < 4; i++) {
			n.put("n" + i, 5);
		}
		
		String first = n.firstEntry().getValue();
		System.out.println();
	}
}

import java.util.Random;

public class Tools {
	public static void randomOrder(Object[] os) {
		Random rand = new Random();
		
		// Vi använder Fisher-Yates-shuffle
		for (int i = os.length - 1; i > 0; i--) {
			// Slumpmässigt index mellan (inklusive) 0 och i
			int j = rand.nextInt(i + 1);
			
			//swap values
			Object temp = os[i];
			os[i] = os[j];
			os[j] = temp;
		}
	}
}

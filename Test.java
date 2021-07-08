import java.util.ArrayList;
import java.util.StringTokenizer;

public class Test {
	
	public static void main(String[] args) {
		
		String line = "/.   the dog's jumped the hedge’s \n       fdfda    \n s";
		
		//StringTokenizer tokenizer = new StringTokenizer(line, " !?.',’");
		//StringTokenizer tokenizer = new StringTokenizer(line, " !?.',’");
		StringTokenizer tokenizer = new StringTokenizer(line, "[^a-zA-Z]");
		
		//String[] words = line.split("\\P{Alpha}+");
		//String[] words = line.split("\\s");
		
//		for (String word : words) {
//			if (word.length() > 0)
//			System.out.println(word.toLowerCase() + " ");
//		}
		
		//System.out.println();
		
		while (tokenizer.hasMoreTokens()) {
			String word = tokenizer.nextToken().toLowerCase().trim();
			//word = word.replaceAll("[^a-zA-Z0-9\\s]", "");
			System.out.println(word);

		}
		
		
	}

}

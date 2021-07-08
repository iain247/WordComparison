
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Word2Vec {

	private int k = 5;
	private HashSet<String> vocabulary;
	private ArrayList<String> allWords;
	private HashMap<String, SparseVector> vectors;
	private TextTokenizer tt;
	
	Word2Vec(String fileName) {
		tt = new TextTokenizer(fileName);
		vocabulary = tt.getVocabulary();
		allWords = tt.getAllWords();	
		initialiseVectors();
	}
	
	public HashSet<String> getVocabulary() {
		return vocabulary;
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}
	
	public HashMap<String, SparseVector> getVectors() {
		return vectors;
	}
	
	public void initialiseVectors() {
		/*
		 * Initialises a sparse vector for each unique word
		 */
		vectors = new HashMap<String, SparseVector>();
		for (String word : vocabulary) {
			vectors.put(word, new SparseVector());
		}
	}
	
	public void printVocabulary() {
		for (String word : vocabulary) System.out.println(word);
	}
	
	public void printAllWords() {
		for (String word : allWords) System.out.println(word);
	}
	
	public SparseVector findContextWords(String word) {
		/*
		 * Returns an individual SparseVector object for a given word
		 */
		
		SparseVector contextFrequency = new SparseVector();
		
		for (int i=0; i<allWords.size(); i++) {
			if (allWords.get(i).equals(word)) {
				for (int j=i-k; j<=i+k; j++) {	
					if (j==i) continue;
					try { 
						String contextWord = allWords.get(j);
						contextFrequency.incrementFrequency(contextWord);			
					} catch(IndexOutOfBoundsException e) {continue;}
				}				
			}
		}
		
		return contextFrequency;
	}
	
	public void findSparseVectors() {
		/*
		 * Creates a SparseVector object for each word unique word
		 */
			
		for (int i=0; i<allWords.size(); i++) {
			String currentWord = allWords.get(i);
			SparseVector currentVector = vectors.get(currentWord);
			
			for (int j=i-k; j<=i+k; j++) {
				if (i==j) continue; // if currentWord = contextWord, skip this iteration
				try {
					String contextWord = allWords.get(j);
					currentVector.incrementFrequency(contextWord);
				} catch (IndexOutOfBoundsException e) {}
			}
		}
	}
		
	public void saveVectors() {
		/*
		 * Saves all vectors to a random access file by invoking filehandler's
		 * saving method.
		 */
		FileHandler.saveVectorsToFile(vectors);
	}
	
	public SparseVector getSparseVector(String name) {
		/*
		 * Returns a SparseVector object for a given word by invoking filehandler's
		 * file reading method.
		 */
		return FileHandler.readVectorFromFile(name);
	}

	public static void main(String[] args) {
		
		Word2Vec x = new Word2Vec("shakespeare.txt");
		
		x.findSparseVectors();
		x.saveVectors();

		SparseVector y = x.getSparseVector("venice");
		y.printValues();
		
//		int count = 0;
//		for (String name : x.getAllWords()) {
//			if (name.equals("portia")) count++;
//		}
//		System.out.println(count);

	}
	
}

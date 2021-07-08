import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class TextTokenizer {
	
	private String corpus;
	private HashSet<String> vocabulary;
	private ArrayList<String> allWords;
	private ArrayList<String> stopWords;
	
	public TextTokenizer(String corpus) {
		this.corpus = corpus;
		vocabulary = new HashSet<String>();
		allWords = new ArrayList<String>();
		readStopWords();
		readData();
		
	}
	
	public HashSet<String> getVocabulary() {
		return vocabulary;
	}

	public ArrayList<String> getAllWords() {
		return allWords;
	}
	
	public ArrayList<String> getStopWords() {
		return stopWords;
	}

	private void readData() {
		/*
		 * This method reads in data from the corpus file and tokenizes it
		 * It produces a set of unique words as well as a list of all words in the correct order
		 */
	
		try {
			FileReader fr = new FileReader(corpus);
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while ((line = br.readLine()) != null) {
	
				String[] words = line.toLowerCase().trim().split("\\P{Alpha}+");
				for (String x : words) {
					if (!x.equals("")) // delete empty strings caused by leading delimiters in line
					allWords.add(x);
				}
			}
			
			allWords.removeAll(stopWords);
			vocabulary = new HashSet<String>(allWords);
			
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	
	private void readStopWords() {
		
		stopWords = new ArrayList<String>();
		
		try {
			FileReader fr = new FileReader("stop.txt");
			@SuppressWarnings("resource")
			Scanner s = new Scanner(fr);
			while (s.hasNext()) {
				stopWords.add(s.next());
			}
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		TextTokenizer tt = new TextTokenizer("shakespeare.txt");
		
		for (String word : tt.getVocabulary()) {
			if (word.contains("portia"))
			System.out.println(word);
		}
		
		System.out.println(tt.getAllWords().size());
		System.out.println(tt.getVocabulary().size());
	}

}

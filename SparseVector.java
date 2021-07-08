import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;



@SuppressWarnings("serial")
public class SparseVector implements Serializable {
	
	private HashMap<String, Integer> contextWords = new HashMap<String, Integer>();
	
	public List<Integer> getFrequencies() {
		/*
		 * returns the frequencies of each context word
		 */
		return new ArrayList<Integer>(contextWords.values());
	}
	
	public Set<String> getContextWords() {
		return contextWords.keySet();
	}
	
	public Integer get(String word) {
		return contextWords.get(word);
	}
	
	public void incrementFrequency(String word) {
		/*
		 * Increments the frequency of an item in the contextWords hashmap.
		 * If the word does not exist in the hashmap, it is added as a new entry with frequency 1.
		 */
		
		// check if word exists in list of vector elements
		if (contextWords.containsKey(word)) { // if yes, increment frequency
			contextWords.put(word, contextWords.get(word)+1);
		} else { // if no, add new word to hashmap
			contextWords.put(word, 1);
		}
	}
	
	public void printValues() {
		/*
		 * Orders the contextWords hashmap by frequency and prints to the console
		 */
		
		// convert hashmap into a list of entries
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(contextWords.entrySet());
		
		// create comparator object
		Comparator<Entry<String,Integer>> cmp = new SVComparator();
		
		// sort list using this comparator
		Collections.sort(list, cmp);
		
		// print sorted list
		for (Entry<String, Integer> s : list) {
			System.out.println(s.getKey() + ": " + s.getValue());
		}	
	}
	
	public void printValues(int n) {
		/*
		 * Orders the contextWords hashmap by frequency and prints the top n values to the console
		 */
		
		// convert hashmap into a list of entries
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(contextWords.entrySet());
		
		// create comparator object
		Comparator<Entry<String,Integer>> cmp = new SVComparator();
		
		// sort list using this comparator
		Collections.sort(list, cmp);
		
		// print first n elements of sorted list
		for (int i=0; i<n; i++) {
			System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
		}
		
	}
	
	public static byte[] createByteArray(SparseVector sv) {
		/*
		 * serialize a SparseVector object
		 */	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(sv);
			oos.flush();
			return bos.toByteArray();
		} catch(IOException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	
	public static SparseVector createSparseVector(byte[] bytes) {
		/*
		 * Deserialize a SparseVector object
		 */
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		try {
			ObjectInputStream ois = new ObjectInputStream(bin);
			return (SparseVector) ois.readObject();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	private class SVComparator implements Comparator<Entry<String,Integer>> {
		/*
		 * Comparator class for sorting list of hashmap entries by frequency
		 */
		@Override
		public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
			return o2.getValue().compareTo(o1.getValue());
		};
		
	}
	
}

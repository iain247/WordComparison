
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class FileHandler {
		
	public static void saveVectorsToFile(HashMap<String, SparseVector> vectors) {
		/*
		 * Saves the vectors hashmap to a random access file named "output.ser"
		 */
		HashMap<String, ByteData> bytePos = new HashMap<String, ByteData>();
		int pos = 0;
		
		RandomAccessFile outputFile = null;
		try {
			outputFile = new RandomAccessFile("output.ser", "rw");
			for (String word : vectors.keySet()) {
				SparseVector sv = vectors.get(word);
				byte[] data = SparseVector.createByteArray(sv);
				outputFile.seek(pos);
				outputFile.write(data);
				
				bytePos.put(word, new ByteData(pos,data.length));
				pos += data.length;
			}

		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputFile.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		// serialize the hashmap of byte positions
		try {
			FileOutputStream fos = new FileOutputStream("bytePos.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(bytePos);
			oos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, ByteData> readBytePos() {
		/*
		 * Reads in serialised hashmap holding the appropriate ByteData object for
		 * each SparseVector object
		 */
		ObjectInputStream ois = null;
		try {
			FileInputStream fis = new FileInputStream("bytePos.ser");
			ois = new ObjectInputStream(fis);
			return (HashMap<String, ByteData>) ois.readObject();
		} catch (IOException e) {e.printStackTrace();
		} catch (ClassNotFoundException e) {e.printStackTrace();
		} finally {
			try { 	
				ois.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		return null;
	}
	
	public static SparseVector readVectorFromFile(String word) {
		/*
		 * returns the appropriate sparse vector for the given argument by
		 * retrieving it from the random access file
		 */
		
		// get data position and size from serialized hashmap
		HashMap<String, ByteData> bytePos = readBytePos();
		ByteData bd = bytePos.get(word);
		int pos = bd.getPos();
		int size = bd.getSize();
		
		// read approprate bytes from "output.ser"
		byte[] bytes = new byte[size];
		RandomAccessFile outputFile = null;
		try {
			outputFile = new RandomAccessFile("output.ser", "r");
			outputFile.seek(pos);
			outputFile.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			
		// convert array of bytes to sparse vector object
		return SparseVector.createSparseVector(bytes);
	}

}

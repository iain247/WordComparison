import java.io.Serializable;

@SuppressWarnings("serial")
public class ByteData implements Serializable {
	/*
	 * This class holds information about the position and size of the byte array
	 * for information to be read from a random access file
	 */
	
	private int pos;
	private int size;
	
	public ByteData(int pos, int size) {
		this.pos = pos;
		this.size = size;
	}
	
	public int getPos() {
		return pos;
	}
	public int getSize() {
		return size;
	}

}

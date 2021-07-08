
public class FindWordPairSim {
	 
	public static float norm(SparseVector sv) {
		/*
		 * Normalises a sparse vector.
		 */
		float norm = 0;
		for (int frequency : sv.getFrequencies()) {
			norm += frequency*frequency;
		}
		return (float) Math.sqrt(norm);
	}
    
    public static float sim(String word1, String word2) {
    	/*
    	 * Compares two words using their corresponding sparse vectors and returns 
    	 * this value as a float.
    	 */
    	
    	float sim = 0;
    	SparseVector sv1 = FileHandler.readVectorFromFile(word1);
    	SparseVector sv2 = FileHandler.readVectorFromFile(word2);
    	
    	for (String contextWord : sv1.getContextWords()) {
    		
    		// skip contextWords that are not shared between both vectors
    		@SuppressWarnings("unused")
			Integer frequency;
    		if ((frequency = sv2.get(contextWord)) != null) {
    			sim += sv1.get(contextWord) * sv2.get(contextWord);
    		}
    	}
    	return sim / (norm(sv1)*norm(sv2));
    	
    }

	
	public static void main(String[] args) {
		
		float y = sim("shylock","bassanio");
		
		System.out.println(y);
		
	}

}

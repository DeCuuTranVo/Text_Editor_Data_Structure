package spelling;

import java.util.TreeSet;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class DictionaryBST implements Dictionary 
{
   private TreeSet<String> dict;
	
    // TODO: Implement the dictionary interface using a TreeSet.  
 	// You'll need a constructor here
   	public DictionaryBST() {
   		dict = new TreeSet<String>();
   	}
	
    
    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	if (word == null) {
    		throw new NullPointerException();
    	}
    	
    	// convert input word to lowercase
    	word = word.toLowerCase();
    	
    	// add the word to dictionary
    	if (this.dict.contains(word)) {
    		return false;
    	} else {
    		this.dict.add(word);
    		return true;
    	}
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
    	// TODO: Implement this method
        return this.dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	if (s == null) {
    		throw new NullPointerException();
    	}
    	
    	// convert input word to lowercase
    	s = s.toLowerCase();
    	
        return this.dict.contains(s);
    }
}

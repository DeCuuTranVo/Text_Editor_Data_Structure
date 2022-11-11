package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		// safety  check
		if (word == null) {
			throw new NullPointerException();
		}
		
		// convert word to lower case
		word = word.toLowerCase();
		
		// 
		TrieNode currentNode = root;
		// loop through every character in the query word
		for (int i = 0; i < word.length(); i++) {
			char currentChar = word.charAt(i);
			
			// check if current node have the link to next node with this character
			if (currentNode.getValidNextCharacters().contains(currentChar)) {
				// go to the sub node with currentChar
				currentNode = currentNode.getChild(currentChar);
			} else {
				// insert a sub node with currentChar and go to it
				currentNode = currentNode.insert(currentChar);
			}
		}
		
		if (currentNode.endsWord()) { // check if the last node is a word
			// the word is already in dictionary -> false
			return false;
		} else {
			// the word is not in dictionary, denote currentNode as endsWord
			currentNode.setEndsWord(true);
			// increase num words
			this.size++;
			// return true
		    return true;
		}
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		// safety  check
		if (s == null) {
			throw new NullPointerException();
		}
		
		// convert word to lower case
		s = s.toLowerCase();
		
		TrieNode currentNode = root;
		// loop through every character in the query word
		for (int i = 0; i < s.length(); i++) {
			char currentChar = s.charAt(i);
			
			// check if current node have the link to next node with this character
			if (currentNode.getValidNextCharacters().contains(currentChar)) {
				// go to the sub node with currentChar
				currentNode = currentNode.getChild(currentChar);
			} else {
				// this string is not a word in the dictionary
				return false;
			}
		}
		
		if (currentNode.endsWord()) { // check if the last node is a word
			// the word is already in dictionary -> false
			return true;
		} else {
			return false;
		}
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 
    	 // convert prefix to lowercase
    	 prefix = prefix.toLowerCase();
    	 
    	 // find the node relevant to prefix
    	 TrieNode currentNode = root;
 		// loop through every character in the query word
 		for (int i = 0; i < prefix.length(); i++) {
 			char currentChar = prefix.charAt(i);
 			
 			// check if current node have the link to next node with this character
 			if (currentNode.getValidNextCharacters().contains(currentChar)) {
 				// go to the sub node with currentChar
 				currentNode = currentNode.getChild(currentChar);
 			} else {
 				// this string is not present in the dictionary -> return an empty list
 				return new LinkedList<String>();
 			}
 		}
    	 
// 		System.out.println(currentNode.getText());
// 		System.out.println(currentNode.getValidNextCharacters());
    	 
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
 		Queue<TrieNode> queue = new LinkedList<TrieNode>();
    	 //    Create a list of completions to return (initially empty)
 		List<String> completions = new LinkedList<String>();
    	 //    While the queue is not empty and you don't have enough completions:
 		queue.add(currentNode);
 		while (!queue.isEmpty()) {
 		 //    remove the first Node from the queue
 			TrieNode iterNode = queue.remove();
 		 //    If it is a word, add it to the completions list
 			if (iterNode.endsWord()) {
 				String iterString = iterNode.getText();
 				completions.add(iterString);
 			}
 		 //    Add all of its child nodes to the back of the queue
 			for (Character currentChar : iterNode.getValidNextCharacters()) {
 				TrieNode childNode = iterNode.getChild(currentChar);
 				queue.add(childNode);
 			}
 			
 		}
    	 // Return the list of completions
 		// sort the list according to length
 		Collections.sort(completions, new StringLengthComparator());
 		
 		// return first numCompletions of the list
 		List<String> returnList = new LinkedList<String>();
 		for (int i = 0; (i < numCompletions) && (i < completions.size()); i++) {
 			returnList.add(completions.get(i));
 		}
        return returnList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText() + " " + curr.endsWord()); //*
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	public static void main(String[] args) {
 		AutoCompleteDictionaryTrie testTrie = new AutoCompleteDictionaryTrie();
 		testTrie.addWord("a");
 		testTrie.addWord("app");
 		testTrie.addWord("and");
 		testTrie.addWord("at");
 		testTrie.addWord("be");
 		testTrie.addWord("bee");
// 		testTrie.printTree();
 		
// 		System.out.println(testTrie.isWord("app"));
// 		System.out.println(testTrie.isWord("a"));
// 		System.out.println(testTrie.isWord("application"));
// 		System.out.println(testTrie.isWord("apt"));
// 		System.out.println(testTrie.isWord("ap"));
 		
 		System.out.println(testTrie.predictCompletions("", 0));
 		System.out.println(testTrie.predictCompletions("", 0).size());
 		
 	}	
}

class StringLengthComparator implements Comparator<String> {

	@Override
	public int compare(String s1, String s2) {
		// TODO Auto-generated method stub
		int compareLength = s1.length() - s2.length();
		if (compareLength < 0) {
			return -1;
		} else if (compareLength > 0) {
			return 1;
		} else {
			return s1.compareTo(s2);
		}
	}
	
}
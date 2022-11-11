package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	protected List<String> getTokens(String sourceText, String pattern)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(sourceText);
		
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		// Create token list from the string sourceText
		String pattern = "[.?!a-zA-Z]+";
		List<String> tokens = this.getTokens(sourceText, pattern);
		// System.out.println(tokens);
		if (tokens.size()==0) {
			return;
		}
		
		// set "starter" to be the first word in the text  
		this.starter = tokens.get(0);
		// set "prevWord" to be starter
		String prevWord = this.starter;
		// for each word "w" in the source text starting at the second word
		for (int i = 1; i < tokens.size(); i++) {
			String w = tokens.get(i);
			// check to see if "prevWord" is already a node in the list
			ListNode queryPrevWordNode = new ListNode(prevWord); 
			int indexOfPrevWordNode = this.wordList.indexOf(queryPrevWordNode);
			if (indexOfPrevWordNode != -1) { // if "prevWord" is a node in the list
				// add "w" as a nextWord to the "prevWord" node			
				ListNode prevWordNode = this.wordList.get(indexOfPrevWordNode);
				prevWordNode.addNextWord(w);
			} else { // else
				//add a node to the list with "prevWord" as the node's word
				this.wordList.add(queryPrevWordNode);
				// add "w" as a nextWord to the "prevWord" node	
				queryPrevWordNode.addNextWord(w);
			}
			// set "prevWord" = "w"
			prevWord = w; 
		}

		// add starter to be a next word for the last word in the source text.
		String lastWord = tokens.get(tokens.size()-1);
		ListNode queryLastWordNode = new ListNode(lastWord);
		
		int indexOfLastWordNode = this.wordList.indexOf(queryLastWordNode);
		if (indexOfLastWordNode != -1) {
			ListNode lastWordNode = this.wordList.get(indexOfLastWordNode);
			lastWordNode.addNextWord(lastWord);
		} else {
			this.wordList.add(queryLastWordNode);
			queryLastWordNode.addNextWord(this.starter);
		}

	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		// If the generator has not yet been trained, the generateText method should simply return an empty String.
		if (this.wordList.size() == 0) {
			return "";
		}
		
		// The text generator shouldn't produce anything when zero words are requested.
		if (numWords == 0) {
			return "";
		}
		
		// set "currWord" to be the starter word
		String currWord = this.starter;
		// set "output" to be ""
		ArrayList<String> output = new ArrayList<String>(); 
		// add "currWord" to output
		output.add(currWord);
		
		// while you need more words
		int countWord = output.size();
		while (countWord < numWords) {
			// find the "node" corresponding to "currWord" in the list
			ListNode querryCurrWordNode = new ListNode(currWord);
			int indexOfCurrWordNode = this.wordList.indexOf(querryCurrWordNode);
			ListNode currWordNode = this.wordList.get(indexOfCurrWordNode);
			// select a random word "w" from the "wordList" for "node"
			String w = currWordNode.getRandomNextWord(rnGenerator);
			// add "w" to the "output"
			output.add(w);
			// set "currWord" to be "w" 
			currWord = w;
			// increment number of words added to the list
			countWord++;
		}

		return String.join(" ", output);
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		// Re-initialize instance variables
		this.wordList = new LinkedList<ListNode>();
		this.starter = "";
		// Retrain model
		this.train(sourceText);
		
	}
	
	// TODO: Add any private helper methods you need here.
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
//		String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int randomIndex = generator.nextInt(this.nextWords.size());
		String randomWord = this.nextWords.get(randomIndex);
	    return randomWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		if (!(other instanceof ListNode)) {
			throw new ClassCastException();
		}
		
		if (this.word.equals(((ListNode) other).word)) {
			return true;
		} else {
			return false;
		}
	}
}



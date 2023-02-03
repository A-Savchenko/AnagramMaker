
import java.util.ArrayList;
import java.util.Arrays;

/**
 *	AnagramMaker - user inputs a phrase, amount of words inside the anagram, and the number of anagrams that they
 *	would like to be outputted, and computer will output all the anagrams that it was able to find in the word.
 *
 *	Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *	@author	Artem Savchenko
 *	@since	2/1/23
 */
public class AnagramMaker {

	//Field Variables
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker() {
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}

	/**
	 * creates an object of the AnagramMaker class, and uses it to call the run method, which branches out into
	 * other helper methods
	 * @param args arguments collected from the terminal line
	 */
	public static void main(String[] args) {
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**
	 * The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker() {
		boolean fr = true;
		while(fr)
		{
			System.out.println();
			String phrase = Prompt.getString("Word(s), name or phrase (q to quit) ");
			if(phrase.equals("q"))
			{
				fr = false;
				continue;
			}
			int numWords = Prompt.getInt("Number of words in anagram");
			int maxPhrases = Prompt.getInt("Maximum number of anagrams to print");
			numPhrases = 0;
			System.out.println();
			ArrayList<String> listOfAnagrams = new ArrayList<String>();
			phrase = phrase.replaceAll("[^a-zA-Z0-9\'s]", "");
			findAnagrams(phrase,numWords,maxPhrases,listOfAnagrams);

			System.out.println("Stopped at "+numPhrases+" anagrams.\n");
		}
	}

	/**
	 * Recursive method to find and output all the anagrams of a given phrase
	 * @param phrase the phrase that the user inputted
	 * @param numWords number of words there should be in each anagram
	 * @param maxPhrases max number of phrases/anagrams there should be in the output
	 * @param listOfAnagrams list of all the words that can be found in the user's phrase
	 */
	private void findAnagrams(String phrase, int numWords, int maxPhrases, ArrayList<String> listOfAnagrams)
	{
		if (!phrase.isEmpty())
		{
			ArrayList<String> allWords = wu.allWords(phrase);
			for (String foundedWord : allWords)
			{
				String newPhrase = phrase;
				listOfAnagrams.add(foundedWord);
				for (char letter : foundedWord.toCharArray())
				{
					newPhrase = newPhrase.replaceFirst("" + letter, "");
				}
				if (numPhrases < maxPhrases && listOfAnagrams.size() <= numWords)
					findAnagrams(newPhrase, numWords, maxPhrases, listOfAnagrams);
				if (listOfAnagrams.size()>0) listOfAnagrams.remove(listOfAnagrams.size()-1);
			}
		}
		else
		{
			if (listOfAnagrams.size()==numWords)
			{
				String output = ""+ Arrays.toString(listOfAnagrams.toArray());
				output = output.replaceAll("[^a-zA-Z - ]", "");
				System.out.println(output);
				numPhrases++;
			}
		}
	}
}
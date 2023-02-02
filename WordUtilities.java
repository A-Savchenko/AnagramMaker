import java.util.ArrayList;
import java.util.Scanner;
/**
 *	Provides utilities for word games:
 *	1. finds all words in the dictionary that match a list of letters
 *	2. prints an array of words to the screen in tabular format
 *	3. finds the word from an array of words with the highest score
 *	4. calculates the score of a word according to a table
 *
 *	Uses the FileUtils and Prompt classes.
 *
 *	@author Artem Savchenko
 *	@since	10/20/22
 */

public class WordUtilities {
    private String[] words;        // the dictionary of words
    // File containing dictionary of almost 100,000 words.
    private  String WORD_FILE = "wordList.txt";

    /**
     *	Determines if a word's characters match a group of letters
     *	@param word		the word to check
     *	@param letters	the letters
     *	@return			true if the word's chars match; false otherwise
     */
    private boolean wordMatch(String word, String letters) {
        // if the word is longer than letters return false
        if (word.length() > letters.length()) return false;

        // while there are still characters in word, check each word character
        // with letters
        while (word.length() > 0) {
            // using the first character in word, find the character's index inside letters
            // and ignore the case
            int index = letters.toLowerCase().indexOf(Character.toLowerCase(word.charAt(0)));
            // if the word character is not in letters, then return false
            if (index < 0) return false;

            // remove character from word and letters
            word = word.substring(1);
            letters = letters.substring(0, index) + letters.substring(index + 1);
        }
        // all word letters were found in letters
        return true;
    }

    /**
     *	finds all words that match some or all of a group of alphabetic characters
     *	Precondition: letters can only contain alphabetic characters a-z and A-Z
     *	@param letters		group of alphabetic characters
     *	@return				an ArrayList of all the words that match some or all
     *						of the characters in letters
     */
    public ArrayList<String> allWords(String letters) {
        ArrayList<String> wordsFound = new ArrayList<String>();
        // check each word in the database with the letters
        for (String word: words)
            if (wordMatch(word, letters))
                wordsFound.add(word);
        return wordsFound;
    }

    /**
     *	Sort the words in the database
     */
    public void sortWords() {
        SortMethods sm = new SortMethods();
        sm.mergeSort(words);
    }

    /* Constructor */
    public WordUtilities() {
        words = new String[100000];
    }

    /**
     * Load all of the dictionary from a file into words array.
     */
    private void loadWords() {
        Scanner sc = FileUtils.openToRead(WORD_FILE);
        for (int i = 0; i < words.length && sc.hasNext(); i++) {
            words[i] = sc.next();
        }
    }

    /**
     * Find all words that can be formed by a list of letters.
     *
     * @param letter string containing list of letters
     * @return array of strings with all words found.
     */
    public String[] findAllWords(String letter) {
        String letters = letter;
        String[] returnStrings = new String[2000];
        boolean goodWord = false;
        String word = "";
        int index = 0;
        for (int i = 0; i < words.length - 1 && words[i] != null; i++) {
            String str = words[i];
            for (int x = 0; x < str.length(); x++) {
                goodWord = true;
                if (letters.contains(str.charAt(x) + "")) {
                    goodWord = true;
                    int indexInWord = letters.indexOf(str.charAt(x));
                    letters = letters.substring(0, indexInWord) + '#' + letters.substring(indexInWord + 1);
                } else {
                    x = str.length();
                    goodWord = false;
                }
            }
            letters = letter;
            if (goodWord) {
                returnStrings[index] = str;
                index++;
            }
        }
        return returnStrings;
    }

    /**
     * Print the words found to the screen.
     *
     * @param wordList array containing the words to be printed
     */
    public void printWords(String[] wordList) {
        for (int i = 0; i < wordList.length && wordList[i] != null; i++) {
            System.out.printf("%-20s", wordList[i]);
            if ((i + 1) % 5 == 0) System.out.println();
        }
    }

    /**
     * @param wordList   An array of words to check
     * @param scoreTable An array of 26 integer scores in letter order
     * @return The word with the highest score
     */
    public String bestWord(String[] wordList, int[] scoreTable, int minLength) {
        String bestWord = "";
        int maxVal = 0;
        int holder = 0;
        int index;

        for (int i = 0; i < wordList.length && wordList[i] != null; i++) {
            if(wordList[i].length() > minLength) {
                for (int x = 0; x < wordList[i].length(); x++) {
                    index = wordList[i].toLowerCase().charAt(x) - 97;
                    holder += scoreTable[index];
                }
                if (Math.max(holder, maxVal) == holder) {
                    maxVal = holder;
                    bestWord = wordList[i];
                }
                holder = 0;
            }
        }
        return bestWord;
    }

    /**
     * Calculates the score of one word according to a score table.
     *
     * @param word       The word to score
     * @param scoreTable An array of 26 integer scores in letter order
     * @return The integer score of the word's score
     */
    public int getScore(String word, int[] scoreTable) {
        int holder = 0;
        int index;
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            index = word.toLowerCase().charAt(i) - 97;
            holder += scoreTable[index];
        }
        return holder;
    }

    /**
     * accessor method to load the words into an array
     */
    public void accessWords() {
        loadWords();
    }

    /***************************************************************/
    /************************** Testing ****************************/
    /***************************************************************/
    public static void main(String[] args) {
        WordUtilities wu = new WordUtilities();
        wu.run();
    }


    /**
     * method for testing, calling the methods
     * responsible for the program's flow
     */
    public void run() {
        loadWords();
        String letters = "$";
        while (!letters.equals("##")) {
            letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
            letters = letters.replace(" ", "");
            letters = letters.toLowerCase();
            String[] word = findAllWords(letters);
            System.out.println();
            printWords(word);


            // Score table in alphabetic order according to Scrabble
            int[] scoreTable = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            String best = bestWord(word, scoreTable, 0) ;
            System.out.println("\n\nHighest scoring word: " + best + "\nScore = "
                    + getScore(best, scoreTable) + "\n");
        }
    }

    public void readWordsFromFile(String file_name) {
        WORD_FILE = file_name;
        loadWords();
    }
}



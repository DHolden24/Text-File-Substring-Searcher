import java.util.*;
import java.io.*;

/**
 * CountSubstrings counts the number of times a string appears in a text file
 * 
 * @author 	Darren Holden
 * @date	10/02/2017
 *
 */
public class CountSubstrings {
	private static String fileName, initialPattern;				// Strings to hold the file name and the pattern
	private static Scanner reader = new Scanner(System.in);		// Initialize a scanner
	private static long start, end;								// Longs to hold the start and end times
	private static int count;									// The number of times the pattern appears
	
	public static void main(String[] args) {
		System.out.print("Enter the name of the text file, including the extension: ");		// Prompt the user for the filename
		fileName = getInput();																// Retrieve the file name
		System.out.print("Enter the pattern to search for: ");								// Prompt the user for the string to find
		initialPattern = getInput();														// Retrieve the string
		
		start = System.currentTimeMillis();													// Record the start time
		count = arrayListCounter(fileName, stringToArrayList(initialPattern));		// Count the instances of the pattern using ArrayLists
		end = System.currentTimeMillis();													// Record the end time
		System.out.println("Using ArrayLists: " + count + " matches in " + (end - start) + " milliseconds.");	// Print the results
		
		start = System.currentTimeMillis();													// Record the start time
		count = linkedListCounter(fileName, stringToLinkedList(initialPattern));	// Count the instances of the pattern using LinkedLists
		end = System.currentTimeMillis();													// Record the end time
		System.out.println("Using LinkedLists: " + count + " matches in " + (end - start) + " milliseconds.");	// Print the results
		
	}
	
	/**
	 * findBrute			Finds the index of the first instance of the given pattern in the text, given in lab assignment
	 * 
	 * @param text			The list of characters to be searched
	 * @param pattern		The list containing the pattern of characters to find
	 * @return				The index of the first instance of the pattern in the text, -1 if no result
	 */
    private static int findBrute(List<Character> text, List<Character> pattern) {
        int n = text.size();		// The size of the text to be searched
        int m = pattern.size();		// The size of the pattern to be found
        
        // For all of the possible starting positions
        for (int i = 0; i <= n - m; i++) {
        	int k = 0;				// Counter initialization
        	
        	// While within the size of the pattern, and while the characters match
        	while (k < m && text.get(i + k) == pattern.get(k))
        		k++;				// Increment the counter
        	
        	// If the entire pattern is found
        	if (k == m)
        		return i;			// Return the index of the pattern
        }
        
        return -1;					// If the pattern was not found, return -1
    }
    
    /**
     * subStringCounter		Counts the instances of a pattern in a text using findBrute
     * 
     * @param text			The list of characters to be searched
     * @param pattern		The list containing the pattern of characters to be found
     * @return				The number of times the pattern apperas in the text
     */
    private static int subStringCounter(List<Character> text, List<Character> pattern) {
    	int count = 0;				// Initialize the count
    	int findBruteResult = findBrute(text, pattern);		// The index of the first instance of the pattern
    	
    	// While the pattern is in the text
    	while (findBruteResult != -1) {
    		count += 1;				// Increment the counter
    		text = text.subList(findBruteResult + pattern.size() - 1, text.size());		// Remove up to the found pattern from the text
    		findBruteResult = findBrute(text, pattern);		// Search the rest of the text for the pattern
    	}	
    	
    	return count;				// Return the number of times the pattern was found
    }
    
    /**
     * arrayListCounter		Converts each line in the text file to an ArrayList of characters, and then finds the number of instances of the
     * 						pattern in the text
     * 
     * @param fileName		The name of the file
     * @param pattern		The ArrayList containing the pattern to be found
     * @return				Returns the count of the number times the pattern was found
     */
    private static int arrayListCounter(String fileName, ArrayList<Character> pattern) {
    	ArrayList<Character> text;		// An ArrayList to hold the the text to be searched
    	int count = 0;					// Initialize the count to 0
    	
    	try {
    		FileReader reader = new FileReader(fileName);		// Create a reader to read the text file
    		BufferedReader bufferedReader = new BufferedReader(reader);
    		
    		String line = bufferedReader.readLine();			// Read the first line
    		
    		// While there are lines to be read
    		while (line != null) {
    			text = stringToArrayList(line);					// Convert the line to an ArrayList of characters
    			
    			count += subStringCounter(text, pattern);		// Add the number of times the pattern appears in the line to count
    			line = bufferedReader.readLine();				// Read the next line
    		}
    		
    		bufferedReader.close();		// Close the reader
    		return count;			// Return the final count
    		
    	// If the file is not found, catch the FileNotFoundException
    	} catch (FileNotFoundException fnfe) {
    		System.out.println("The file, '" + fileName + "', could not be found.");	// Print the error message
    		
    	// If there is an error reading the file, catch the IOException
    	} catch (IOException ioe) {
    		System.out.println("Error reading '" + fileName + "'.");					// Print error message
    	}
    	return 0;					// Return 0
    }
    
    /**
     * linkedListCounter	Converts each line in the text file to an ArrayList of characters, and then finds the number of instances of the
     * 						pattern in the text
     * 
     * @param fileName		The name of the file
     * @param pattern		The LinkedList containing the pattern to be found
     * @return				Returns the count of the number times the pattern was found
     */
    private static int linkedListCounter(String fileName, LinkedList<Character> pattern) {
    	LinkedList<Character> text;		// An ArrayList to hold the the text to be searched
    	int count = 0;					// Initialize the count to 0
    	
    	try {
    		FileReader reader = new FileReader(fileName);		// Create a reader to read the text file
    		BufferedReader bufferedReader = new BufferedReader(reader);
    		
    		String line = bufferedReader.readLine();			// Read the first line
    		
    		// While there are lines to be read
    		while (line != null) {
    			text = stringToLinkedList(line);				// Convert the line to a LinkedList of characters
    			
    			count += subStringCounter(text, pattern);		// Add the number of times the pattern appears in the line to count
    			line = bufferedReader.readLine();				// Read the next line
    		}
    		
    		bufferedReader.close();		// Close the reader
    		return count;			// Return the final count
    		
    	// If the file is not found, catch the FileNotFoundException
    	} catch (FileNotFoundException fnfe) {
    		System.out.println("The file, '" + fileName + "', could not be found.");	// Print the error message
    		
    	// If there is an error reading the file, catch the IOException
    	} catch (IOException ioe) {
    		System.out.println("Error reading '" + fileName + "'.");					// Print error message
    	}
    	return 0;					// Return 0
    }
    
    /**
     * getInput				Retrieves input from the user
     * 
     * @return				Returns the string containing the user's input
     */
    private static String getInput() {
    	return reader.nextLine();	// Return the input retrieved by the scanner
    }
    
    /**
     * stringToArrayList	Converts a string to an ArrayList of characters
     * 
     * @param s				The string to be converted
     * @return				The generated ArrayList of characters
     */
    private static ArrayList<Character> stringToArrayList(String s) {
    	ArrayList<Character> text = new ArrayList<Character>();			// Initialize the ArrayList
    	
    	// For each character in the string
    	for (int i = 0; i < s.length(); i ++) {
			text.add(s.charAt(i));				// Add the character to the ArrayList
		}
    	
    	return text;		//Return the generated ArrayList
    }
    
    /**
     * stringToLinkedList	Converts a string to a LinkedList of characters
     * 
     * @param s				The string to be converted
     * @return				The generated ArrayList of character
     */
    private static LinkedList<Character> stringToLinkedList(String s) {
    	LinkedList<Character> text = new LinkedList<Character>();		// Initialize the LinkedList

    	// For each character in the string
    	for (int i = 0; i < s.length(); i ++) {
			text.add(s.charAt(i));				// Add the character to the ArrayList
		}
    	
    	return text;		//Return the generated ArrayList
    }
}

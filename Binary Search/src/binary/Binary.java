package binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import binary.KeyPair;

/**
 * This class uses the binary algorithm for searching.
 * 
 * @author Siddhartha
 *
 */
public class Binary {

	public static final int CHAR_LENGTH = 1;
	public static int size = 0;
	public static int dictionarySize = 0;
	public static int mid;
	public static int idx;
	public static int cost;
	private int[] keys = new int[100000];
	private KeyPair[] dictionary = new KeyPair[2000];
	private KeyPair key;
	
	/**
	 * Runs the program by creating an instance of Binary
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Binary binSearch = new Binary();
		binSearch.startUI();
	}
	
	/**
	 * The constructor for the search function
	 */
	public Binary() {

	}
	
	/**
	 * Executes the methods for the search function
	 */
	public void startUI() {
		Scanner console = new Scanner(System.in);
		Scanner input = getInputScanner(console);
		processFile(input);
		PrintStream output = getOutputPrintStream(console);
		printOutput(output);
		System.out.println("There were " + comparisons() + " comparisons");
	}
	
	/**
	 * Gets the input file and returns it.
	 * 
	 * @param console a scanner to obtain an input file.
	 * @return input the input file to be manipulated.
	 */
	public Scanner getInputScanner(Scanner console) {
		Scanner input = null;

		while (input == null) {
			System.out.print("Enter input file: ");
			String name = console.nextLine();

			try {
				input = new Scanner(new File(name));
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		return input;
	}

	/**
	 * Initializes an output file and returns it.
	 * 
	 * @param console a scanner to obtain an output file.
	 * @return output the output file to write to.
	 */
	public PrintStream getOutputPrintStream(Scanner console)
	{
		PrintStream output = null;
		
		while (output == null)
		{
			System.out.print("Enter output file: ");
			String name = console.nextLine();
			File f = new File(name);
			boolean overwrite = true;
			
			while (f.exists()) 
			{
				System.out.print("File exists. Overwrite? (Y/N): ");
				String answer = console.nextLine();
				char answerLetter = ' ';
				
				if (answer.length() == CHAR_LENGTH) 
				{
					answerLetter = answer.toUpperCase().charAt(0);
					
					if (answerLetter == 'Y') 
					{
						System.out.println("Overwriting file.");
						break;
					} 
					else if (answerLetter == 'N') 
					{
						overwrite = false;
						System.out.println("Not overwriting file.");
						break;
					} 
					else 
					{
						System.out.println("Invalid answer. Please try again.");
					}
					
				} 
				else 
				{
					System.out.println("Invalid answer. Please try again.");
				}
			}
			
			if (overwrite) 
			{
				try 
				{
					output = new PrintStream(new File(name));
				}
				catch (FileNotFoundException e) 
				{
					System.out.println(e.getMessage());
				}
			}
		}
		return output;  
	}

	/**
	 * Processes the input file.
	 * 
	 * @param input a scanner that contains the input file.
	 */
	public void processFile(Scanner input) {
		while (input.hasNextLine()) {
			int num = input.nextInt();
			String line = input.nextLine().trim();
			key = new KeyPair(num, line);
			keys[size] = key.getKey();
			size++;
			if(size == 1) {
				dictionary[0] = key;
				dictionarySize++;
			} else {
				idx = binarySearch(key.getKey(), dictionary, 0, dictionarySize - 1);
				if(idx > -1) {
					for(int i = dictionarySize; i > idx; i--) {
						dictionary[i] = dictionary[i - 1];
					}
					dictionary[idx] = key;
					dictionarySize++;
				}
			}
		}
	}
	
	public int binarySearch(int key, KeyPair[] a, int low, int high) {
		cost++;
		if(low > high) {
			return low;
		} else {
			mid = (low + high) / 2;
			if(a[mid].getKey() == key) {
				return -1;
			} else if(key < a[mid].getKey()) {
				return binarySearch(key, a, low, mid - 1);
			} else {
				return binarySearch(key, a, mid + 1, high);
			}
		}
	}
	
	/**
	 * Returns the cost
	 * @return the cost
	 */
	public int comparisons() {
		cost = 0;
		int count = 0;
		for(int i = 0; i < size; i++) {
			binarySearch(keys[i], dictionary, 0, dictionarySize - 1);
			count++;
			if(count % 1000 == 0) {
				System.out.println(count + " " + cost);
			}
		}
		return cost;
	}
	
	/**
	 * Writes the dictionary to a test file
	 * @param output
	 */
	public void printOutput(PrintStream output) {
		for(int i = 0; i < dictionarySize; i++) {
			output.println(dictionary[i].getKey() + "    " + dictionary[i].getValue());
		}
	}
}

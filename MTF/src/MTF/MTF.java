package MTF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import MTF.KeyPair;
import MTF.MultiPurposeList;

/**
 * This class uses the MTF algorithm for searching.
 * 
 * @author Siddhartha
 *
 */
public class MTF {

	public static final int CHAR_LENGTH = 1;
	public static int size = 0;
	private int[] keys = new int[100000];
	private MultiPurposeList<KeyPair> list = new MultiPurposeList<KeyPair>();
	private KeyPair key;

	/**
	 * Runs the program by creating an instance of MTF
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MTF mtf = new MTF();
		mtf.startUI();
	}

	/**
	 * The constructor for the search function
	 */
	public MTF() {

	}

	/**
	 * Executes the methods for the search function
	 */
	public void startUI() {
		Scanner console = new Scanner(System.in);
		Scanner input = getInputScanner(console);
		processFile(input);
		int comparison = comparisons();
		PrintStream output = getOutputPrintStream(console);
		printOutput(output);
		System.out.println("There were " + comparison + " comparisons");
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
	 * @param console
	 *            a scanner to obtain an output file.
	 * @return output the output file to write to.
	 */
	public PrintStream getOutputPrintStream(Scanner console) {
		PrintStream output = null;

		while (output == null) {
			System.out.print("Enter output file: ");
			String name = console.nextLine();
			File f = new File(name);
			boolean overwrite = true;

			while (f.exists()) {
				System.out.print("File exists. Overwrite? (Y/N): ");
				String answer = console.nextLine();
				char answerLetter = ' ';

				if (answer.length() == CHAR_LENGTH) {
					answerLetter = answer.toUpperCase().charAt(0);

					if (answerLetter == 'Y') {
						System.out.println("Overwriting file.");
						break;
					} else if (answerLetter == 'N') {
						overwrite = false;
						System.out.println("Not overwriting file.");
						break;
					} else {
						System.out.println("Invalid answer. Please try again.");
					}

				} else {
					System.out.println("Invalid answer. Please try again.");
				}
			}

			if (overwrite) {
				try {
					output = new PrintStream(new File(name));
				} catch (FileNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return output;
	}

	/**
	 * Processes the input file
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
			if (list.size() == 0) {
				list.addItem(0, key);
			} else {
				boolean flag = true;
				list.resetIterator();
				KeyPair item;
				while(list.hasNext()) {
					item = list.next();
					if (item.getKey() == key.getKey()) {
						flag = false;
						break;
					}
				}
				if (flag) {
					list.addToRear(key);
				}
			}
		}
	}

	/**
	 * Returns the cost
	 * @return the cost
	 */
	public int comparisons() {
		int comp = 0;
		int count = 0;
		for (int i = 0; i < size; i++) {
			list.resetIterator();
			KeyPair item;
			int idx = 0;
			while(list.hasNext()) {
				item = list.next();
				if (item.getKey() == keys[i]) {
					list.addItem(0, list.remove(idx));
					comp++;
					break;
				}
				idx++;
				comp++;
			}
			count++;
			if(count % 1000 == 0) {
				System.out.println(count + " " + comp);
			}
		}
		return comp;
	}

	/**
	 * Writes the dictionary to a test file
	 * @param output
	 */
	public void printOutput(PrintStream output) {
		for (int i = 0; i < list.size(); i++) {
			output.println(list.lookAtItemN(i).getKey() + "    "
					+ list.lookAtItemN(i).getValue());
		}
	}
}
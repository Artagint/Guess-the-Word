// Artem Tagintsev, CSE223, PA4, 05/17/2024
// The TwentyQ class is basically the main file of this program and handles the initial setup, interactions with the user, and the flow of the game. The class checks if the proper of the command line argument was given, creates new instance of the tree, checks if the textfile properly ingested, and a new scanner, call the method to play the game, and close the scanner.
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

class TwentyQ{
	public static void main(String[] args){
		// Checks if proper command line format is used of java TwentyQ <filename>, prints error message and exits if not
		if(args.length != 1){
			System.out.println("ERROR! Please follow specified format: java TwentyQ <filename>");
			return;
		}

		// For readability set var filename to args[0] since it holds the name of file
		String filename = args[0];
		System.out.println("Hello - welcome to my guessing game!");

		// Make a new instance of the tree
		Tree decisionTree = new Tree();

		// Ingests tree from the file and prints an error message if the file can't be opened
		if(!decisionTree.ingest(filename)){
			System.out.println("ERROR: Can't open file!");
			return;
		}

		// Make a Scanner object for the user input
		Scanner sc = new Scanner(System.in);

		// Calls the method that plays the game
		decisionTree.playGame(sc, filename);	
		
		// After user presses CTRL-D, close the Scanner 'sc'
		sc.close();
	}
}

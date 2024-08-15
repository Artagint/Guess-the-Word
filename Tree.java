// The Tree class essentially manages the structure of the decision tree. The tree declares a Node root as null, ingest method, read the tree in NLR older using the readTree method, play the game in the playGame method, and update the tree using the writeBack method. The logic of the class has the user response to questions with a 'y' or 'n' until an answer node is hit which then the user respondes with 'y' if the answer is right, or 'n' if its wrong, then the game asks the user to update the tree by asking for the right answer and a new question for that answer. 
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

class Tree{
	Node root = null; // Declares 'root' var of type Node and initializes it as null, so the tree starts off empty
	
	// Method to ingest the structure of the tree from a file
	public boolean ingest(String filename){
		try {
			// Make Scanner 's' to read from the file
		       	Scanner s = new Scanner(new File(filename));
			root = readTree(s); // Read the tree from Scanner 's' and set to the root of tree
			return true;
		} catch (Exception e) { // If there is an exception, print error message and exit program
			return false;
		}
	}

	// Method to recursively read the tree from the Scanner 's' in NLR order
	Node readTree(Scanner s){
		if(!s.hasNextLine()) return null; // Return null if no lines to read
		String readLine = s.nextLine(); // Read the current line
		String qaLine = readLine.substring(0, 3); // Get first 3 char to check if they are 'Q: ' or 'A: '
		String cutLine = readLine.substring(3); // Get the rest of the line after the first 3 char
		Node node = new Node(cutLine); // Make a new node for the String cutLine
		if(qaLine.equals("Q: ")){
			node.no = readTree(s); // Read 'no' subtree recursively (left)
			node.yes = readTree(s); // Read 'yes' subtree recursively (right)
		}
		// The reason we don't need to check for 'A: ' is because if qaLine ISN'T 'Q: ' then the only other thing it can be is 'A: ' but we don't need to add any code for if it's 'A: ' because, node.yes and node.no will remain null by default and that is the same behavior we wan't for handling 'A: '. 
		return node; // Return the newly created node
	}

	// This method is what plays the game and has many parts to it, including getting user input of 'y' or 'n' for question nodes and calling the isQuestion method, and also getting user input on if the answer it gave is correct, and if it isn't update the tree by getting a right answer, new question, and calling the writeBack method to update the tree
	public void playGame(Scanner sc, String filename){
		System.out.println("Think of an object, and I'll try to guess what you're thinking of.");
		Node temp = root; // Begin to play with the root of the tree, which is the first question
		// Loop until user is at an answer node
		while(temp.isQuestion()){
			System.out.print(temp.data + " ");
			// Close the program when CTRL-D is pressed
			if(!sc.hasNextLine()){
				sc.close(); // Close scanner
				System.out.println("");
				System.out.println("Thanks for playing - Goodbye!");
				System.out.println("");
				System.exit(0); // Close program
			}
			String response = sc.nextLine(); // Read the user input, which should just be 'n' or 'y'
			if(response.equals("n")){
				temp = temp.no; // Move down to the 'no' child node
			}
			else if(response.equals("y")){
				temp = temp.yes; // Move down to the 'yes' child node
			}
			else{ // Prints error message if improper input and reprompts user
				System.out.println("Please answer with y or n");
			}
		}
		// Loop for handling the answer node, I have it set for 'true' so that if user types in an improper input, it can reprompt the user for the proper response of 'y' or 'n'
		while(true){
			System.out.print("Okay, I think Iv'e got it...is it a " + temp.data + "? ");
			if(!sc.hasNextLine()){
				sc.close();
				System.out.println("");
				System.out.println("Thanks for playing - Goodbye!");
				System.out.println("");
		       		System.exit(0);
			}
			String isCorrect = sc.nextLine(); // Read the user input, which should just be 'n' or 'y'
			// If the answer is correct, go back to the start of the game to play again
			if(isCorrect.equals("y")){
				System.out.println("Woohoo! I got it right!");
				playGame(sc, filename); // Go back to start of game to play again
			}
			// If the answer is incorrect 'n', ask user for the right answer, a new question for that answer, then update the tree by moving nodes around and adding some and writing them into the tree when calling the writeBack method
			else if(isCorrect.equals("n")){
				System.out.println("Okay, you stumped me!");
				System.out.println("What were you thinking of?");
				System.out.print("> ");
				String rightAnswer = sc.nextLine(); // Ask user for the right answer
				System.out.println("Cool. Can you tell me a yes/no question to help me next time?");
				System.out.println("Please type a question for which a 'yes' answer means a " + rightAnswer + " and a 'no' answer means a " + temp.data);
				String newQuestion = sc.nextLine(); // Ask user for a question for that right answer
				System.out.println("Thanks! I shall do better next time...");
				Node noAnswerNode = new Node(temp.data); // Make a new node to hold the current answer, the 'no' node
				Node yesAnswerNode = new Node(rightAnswer); // Make a new node to hold the new answer, the 'yes' node
				temp.data = newQuestion; // Set the current node as the new question
				temp.no = noAnswerNode; // Set the no field to the current answer
				temp.yes = yesAnswerNode; // Set the yes field to the new answer
				writeBack(filename); // Update the file with the newly written tree
				playGame(sc, filename); // Go back to start of game to play again
			}
			else{ // Prints error message if improper input and reprompts user
				System.out.println("Please answer with y or n");
			}
		}
	}

	// Update the file with the newly written tree by writing it into the file
	public void writeBack(String filename){
		PrintWriter pw; 
		try{
			pw = new PrintWriter(new File(filename)); // Make a PrintWriter object for the file
		}catch(Exception e){
			System.out.println("ERROR: Can't open file <" + filename + ">!"); // Print error messsage and return if there is an issue opening the file
			return;
		}	
		root.traverse(pw); // Calls the traverse method from the Node class
		pw.close();
	}	
}

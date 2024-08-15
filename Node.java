// The Node class basically represents a single node in the tree for which each node is either a question or answer node. This class builds the structture of the node with data, no, yes. The constructor initializes our node with the data we specify, the isQuesion method checks if the node is a question node or an answer now, and the traverse method will recursively traverse the tree in NLR order and write into the file.
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
class Node{
	String data; // Holds the contents of a node, which is either a leaf node (answer, A), or parent node (question, Q)
	Node no; // Represents the right child node answer "no" to 'data'
	Node yes; // Represents the left child node answer "yes" to 'data'

	// Constructor for Node class. Initializes a new Node with 'data' and initializes child references to null
	public Node(String data){
		this.data = data; // Assigns argument 'data' to 'data' field of the Node instance, hence the this.data,which represents Q or A
		no = yes = null; // Initializes 'no' and 'yes' child node instances to null, since when a node is first made, it has no children
	}

	// Ceecks if the current node is a Q or A node by checking if it has children or not, if yes is null then so is no and vice versa so we can just check for one child node
	public boolean isQuestion(){
		if(yes != null) return true; // Return true if there is a child node
		return false; // Return false if there are no children
	}

	// Traverse the tree recursively in NLR order and write the user response into the structure of the tree
	public void traverse(PrintWriter pw){
		if(yes != null){ // Same as in boolean isQuestion, check if node has children
			pw.println("Q: " + data); // Write the question user wrote into the file
			no.traverse(pw); // Recursively traverse 'no', right
			yes.traverse(pw); // Recursively traverse 'yes', left
		}
		else{
			pw.println("A: " + data); // If node has no children then it's an answer node, write the answer the user wrote into the file
		}

	}
}

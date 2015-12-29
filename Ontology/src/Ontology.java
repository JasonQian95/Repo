import java.util.ArrayList;
import java.util.Scanner;

public class Ontology {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node root;
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();// number of nodes, dont use this
		String nodeNames = scanner.nextLine();// TODO:parse
		String[] nodeTokens = nodeNames.split(" ");
		root = new Node(nodeTokens[0]);
		ArrayList<Node> parents = new ArrayList<Node>(999); //Code too slow; trying to avoid reallocating
		Node lastNodeCreated = root;
		for (int i = 1; i < nodeTokens.length; i++) {
			String currNodeToken = nodeTokens[i];
			if (currNodeToken.compareTo("(") == 0) {
				parents.add(lastNodeCreated);
			} else if (currNodeToken.compareTo(")") == 0) {
				parents.remove(parents.size() - 1);
			} else {
				lastNodeCreated = new Node(currNodeToken);
				parents.get(parents.size() - 1).addChild(lastNodeCreated);
			}
		}
		
		int noOfQuestions = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < noOfQuestions; i++) {
			String line = scanner.nextLine();
			int index = line.indexOf(':'); //Trying to speed things up
			Node node = root.findNode(line.substring(0, index));
			node.addQuestion(line.substring(index + 2, line.length()));
		}

		int noOfQueries = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < noOfQueries; i++) {
			String line = scanner.nextLine();
			// could tokenize but feel like this should be faster
			int index = line.indexOf(' '); //trying to speed things up
			Node node = root.findNode(line.substring(0, index));
			System.out.println(node.matchStr(line.substring(
					index + 1, line.length())));
		}

		scanner.close();
	}

	// static because Ontology is not an object
	private static class Node {
		private String value;
		private ArrayList<Node> children;
		private ArrayList<String> questions;

		Node(String value) {
			this.value = value;
			children = new ArrayList<Node>(999); //Code too slow; trying to avoid reallocating
			questions = new ArrayList<String>(999); //Code too slow; trying to avoid reallocating
		}

		Node findNode(String str) {
			if (this.value.compareTo(str) == 0) {
				return this;
			}
			for (Node child : children) {
				if (child.findNode(str) != null) {
					return child.findNode(str);
				}
			}
			return null;
		}

		int matchStr(String str) {
			int noOfMatch = 0;
			for (String question : questions) {
				if (question.startsWith(str)){
					noOfMatch++;
				}
			}
			for (Node child: children){
				noOfMatch += child.matchStr(str);
			}
			return noOfMatch;
		}

		void addChild(Node node) {
			this.children.add(node);
		}

		ArrayList<Node> getChildren() {
			return this.children;
		}

		void addQuestion(String question) {
			this.questions.add(question);
		}

		ArrayList<String> getQuestions() {
			return this.questions;
		}

		public String toString() {
			String str = this.value + " (";
			for (Node child : children) {
				str += " " + child.toString();
			}
			str += " )";
			return str;
		}
	}

}

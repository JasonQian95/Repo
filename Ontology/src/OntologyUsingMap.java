import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class OntologyUsingMap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, ArrayList<String>> children = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> questions = new HashMap<String, ArrayList<String>>();
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine(); // number of nodes, dont use this
		String[] nodeTokens = scanner.nextLine().split(" ");
		ArrayList<String> parents = new ArrayList<String>(999);
		String lastNodeCreated = nodeTokens[0];
		children.put(lastNodeCreated, new ArrayList<String>(999));
		questions.put(lastNodeCreated, new ArrayList<String>(999));
		for (int i = 1; i < nodeTokens.length; i++) {
			String currNodeToken = nodeTokens[i];
			if (currNodeToken.compareTo("(") == 0) {
				parents.add(lastNodeCreated);
			} else if (currNodeToken.compareTo(")") == 0) {
				parents.remove(parents.size() - 1);
			} else {
				lastNodeCreated = currNodeToken;
				if (children.get(lastNodeCreated) == null) {
					children.put(lastNodeCreated, new ArrayList<String>(999));
				}
				children.get(parents.get(parents.size() - 1)).add(
						lastNodeCreated);
				questions.put(lastNodeCreated, new ArrayList<String>(999));
			}
		}

		int noOfQuestions = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < noOfQuestions; i++) {
			String line = scanner.nextLine();
			int temp = line.indexOf(':');
			questions.get(line.substring(0, temp)).add(
					line.substring(temp + 2, line.length()));
		}

		for (String key : questions.keySet()) {
			Collections.sort(questions.get(key));
		}

		int noOfQueries = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < noOfQueries; i++) {
			String line = scanner.nextLine();
			// could tokenize but feel like this should be faster
			int temp = line.indexOf(' ');
			String currToMatch = line.substring(0, temp);
			String strToMatch = line.substring(temp + 1, line.length());
			ArrayList<String> queue = new ArrayList<String>(999);
			queue.add(currToMatch);
			int noOfMatches = 0;
			while (children.get(currToMatch) != null) {
				queue.remove(queue.size() - 1);
				ArrayList<String> currMatchQuestions = questions
						.get(currToMatch);
				int index = bSearch(currMatchQuestions, strToMatch);
				while (index < currMatchQuestions.size()
						&& currMatchQuestions.get(index).startsWith(strToMatch)) {
					noOfMatches++;
					index++;
				}
				ArrayList<String> childs = children.get(currToMatch);
				for (String str : childs) {
					queue.add(str);
				}
				currToMatch = queue.size() == 0 ? null : queue
						.get(queue.size() - 1);
			}
			System.out.println(noOfMatches);
		}

		scanner.close();
	}

	public static int bSearch(ArrayList<String> arr, String str) {
		int upper = arr.size() - 1;
		int lower = 0;
		int mid = (upper + lower) / 2;
		while (lower <= upper) {
			mid = (upper + lower) / 2;
			if (arr.get(mid).compareTo(str) == 0) {
				return mid;
			}
			if (arr.get(mid).compareTo(str) < 0) {
				lower = mid + 1;
			}
			if (arr.get(mid).compareTo(str) > 0) {
				upper = mid - 1;
			}
		}
		return mid;
	}
}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Encrypt {
	static int minKey = 100;
	static int maxKey = 1000;
	static String defaultString = "The quick fox jumped over the lazy dog.";
	static int defaultKey = 1234;

	public static int generateRandomPrime() {
		int randInt = (int) (Math.random() * (maxKey - minKey)) + minKey;
		while (!isPrime(randInt)) {
			randInt = (int) (Math.random() * (maxKey - minKey)) + minKey;
		}
		return randInt;
	}

	public static boolean isPrime(int num) {
		int root = (int) Math.sqrt(num);
		if (num % 2 == 0) {
			return false;
		}
		for (int i = 3; i < root; i += 2) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static String encrypt(String str, int key) {
		StringBuffer encryptStr = new StringBuffer(str);
		while (key > 0) {
			int digit = key % 10;
			key /=  10;
			for (int i = digit; i < str.length(); i += digit) {
				if (digit != 9 && digit != 0) {
					if (digit % 2 == 0) {
						int bit = 1 << (digit - 1);
						encryptStr.setCharAt(i, (char) (encryptStr.charAt(i) ^ bit));
					} else if (digit % 2 == 1) {
						int leftBits = 0 | encryptStr.charAt(i);
						leftBits = leftBits >> (16 - digit);
						encryptStr.setCharAt(i, (char) (encryptStr.charAt(i) << digit));
						//int firstEightBits = (int) (Math.pow(2, 8) - 1);
						//encryptStr.setCharAt(i, (char) (encryptStr.charAt(i) & firstEightBits));
						encryptStr.setCharAt(i,
								(char) (encryptStr.charAt(i) | leftBits));
					}
				}
			}
		}
		return encryptStr.toString();
	}
	
	public static String decrypt(String str, int key) {
		StringBuffer decryptStr = new StringBuffer(str);
		ArrayList<Integer> digits = new ArrayList<Integer>();
		while (key > 0) {
			int digit = key % 10;
			key /=  10;
			digits.add(0, digit);
		}
		for (int i = 0; i < digits.size(); i++){
			int digit = digits.get(i);
			for (int j = digit; j < str.length(); j += digit) {
				if (digit != 9 && digit != 0) {
					if (digit % 2 == 0) {
						int bit = 1 << (digit - 1);
						decryptStr.setCharAt(j, (char) (decryptStr.charAt(j) ^ bit));
					} else if (digit % 2 == 1) {
						int rightBits = 0 | decryptStr.charAt(j);
						rightBits = rightBits << (16 - digit);
						decryptStr.setCharAt(j, (char) (decryptStr.charAt(j) >> digit));
						//int firstEightBits = (int) (Math.pow(2, 8) - 1);
						//decryptStr.setCharAt(j, (char) (decryptStr.charAt(j) & firstEightBits));
						decryptStr.setCharAt(j,
								(char) (decryptStr.charAt(j) & rightBits));
					}
				}
			}
		}
		return decryptStr.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter a string to encrypt: ");
		String input = scanner.nextLine();
		if (input.compareTo("") == 0) {
			input = defaultString;
		}
		System.out.println("If you cant think of a key here is one: "
				+ generateRandomPrime());
		System.out.print("Enter a key to encrypt with: ");
		String keyLine = scanner.nextLine();
		int key;
		if (keyLine.compareTo("") == 0) {
			key = defaultKey;
		} else {
			key = Integer.parseInt(keyLine);
		}

		String encryptedString = encrypt(input, key);
		System.out.println("Encrypted string is: " + encryptedString);
		System.out.println("Decrypted string is: " + decrypt(encryptedString, key));

		scanner.close();
	}
}

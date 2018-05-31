/*
 *Braille Translation
 */

public class Answer {
	public static String answer(String plaintext) {
		String str = "";
		String dot1 = "abcdefghklmnopqruvxyz12345678";
		String dot2 = "bfghijlpqrstvw267890?!,.";
		String dot3 = "klmnopqrstuvxyz?!'-";
		String dot4 = "cdfgijmnpqstwxy346790";
		String dot5 = "deghjnoqrtwyz45780!.";
		String dot6 = "uvwxyz?-.";
		String[] dots = {dot1, dot2, dot3, dot4, dot5, dot6};
		String capital = "000001";
		String number = "001111";
		for (char c : plaintext.toCharArray()) {
			if (Character.isDigit(c)) {
				str += number;
			}
			if (Character.isUpperCase(c)) {
				str += capital;
			}
			for (String s : dots) {
				if (s.contains(Character.toString(Character.toLowerCase(c)))) {
					str += "1";
				} else {
					str += "0";
				}
			}
		}
		return str;
	}

	public static void main(String[] args) {
		System.out.println(answer("code").equals("100100101010100110100010"));
		System.out.println(answer("Braille")
				.equals("000001110000111010100000010100111000111000100010"));
		System.out.println(
				answer("The quick brown fox jumped over the lazy dog").equals(
						"000001011110110010100010000000111110101001010100100100101000000000110000111010101010010111101110000000110100101010101101000000010110101001101100111100100010100110000000101010111001100010111010000000011110110010100010000000111000100000101011101111000000100110101010110110"));
	}
}
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Dodge the Lasers! =================
 * 
 * Oh no! You've managed to escape Commander Lambdas collapsing space station in
 * an escape pod with the rescued bunny prisoners - but Commander Lambda isnt
 * about to let you get away that easily. She's sent her elite fighter pilot
 * squadron after you - and they've opened fire!
 * 
 * Fortunately, you know something important about the ships trying to shoot you
 * down. Back when you were still Commander Lambdas assistant, she asked you to
 * help program the aiming mechanisms for the starfighters. They undergo
 * rigorous testing procedures, but you were still able to slip in a subtle bug.
 * The software works as a time step simulation: if it is tracking a target that
 * is accelerating away at 45 degrees, the software will consider the targets
 * acceleration to be equal to the square root of 2, adding the calculated
 * result to the targets end velocity at each timestep. However, thanks to your
 * bug, instead of storing the result with proper precision, it will be
 * truncated to an integer before adding the new velocity to your current
 * position. This means that instead of having your correct position, the
 * targeting software will erringly report your position as sum(i=1..n,
 * floor(i*sqrt(2))) - not far enough off to fail Commander Lambdas testing, but
 * enough that it might just save your life.
 * 
 * If you can quickly calculate the target of the starfighters' laser beams to
 * know how far off they'll be, you can trick them into shooting an asteroid,
 * releasing dust, and concealing the rest of your escape. Write a function
 * answer(str_n) which, given the string representation of an integer n, returns
 * the sum of (floor(1*sqrt(2)) + floor(2*sqrt(2)) + ... + floor(n*sqrt(2))) as
 * a string. That is, for every number i in the range 1 to n, it adds up all of
 * the integer portions of i*sqrt(2).
 * 
 * For example, if str_n was "5", the answer would be calculated as
 * floor(1*sqrt(2)) + floor(2*sqrt(2)) + floor(3*sqrt(2)) + floor(4*sqrt(2)) +
 * floor(5*sqrt(2)) = 1+2+4+5+7 = 19 so the function would return "19".
 * 
 * str_n will be a positive integer between 1 and 10^100, inclusive. Since n can
 * be very large (up to 101 digits!), using just sqrt(2) and a loop won't work.
 * Sometimes, it's easier to take a step back and concentrate not on what you
 * have in front of you, but on what you don't.
 */

public class Answer9 {
	// decimal places of the square root of two up to 100 places.
	static BigDecimal SQRT2MINUS1 = new BigDecimal(
			"0.4142135623730950488016887242096980785696718753769480731766797379907324784621070388503875343276415727");
	// static final int SQRT2MINUS1LENGTH = SQRT2MINUS1.bitLength();

	public static String answer(String str_n) {
		BigDecimal n = new BigDecimal(str_n);
		return answerHelper(n).toString();
	}

	/**
	public static BigInteger answerHelper(BigInteger n) {
		if (n.equals(BigInteger.ZERO)) {
			return BigInteger.ZERO;
		}
		BigInteger m = n.multiply(SQRT2MINUS1);
		int mLength = m.bitLength();
		if (mLength > SQRT2MINUS1LENGTH) {
			m = m.shiftRight(SQRT2MINUS1LENGTH);
		} else {
			m = BigInteger.ZERO;
		}

		BigInteger temp1 = m.add(n);
		BigInteger temp2 = temp1.add(BigInteger.ONE);
		temp1 = temp1.multiply(temp2);
		temp1 = temp1.divide(new BigInteger("2"));
		temp2 = m.multiply(m.add(BigInteger.ONE));
		temp1 = temp1.subtract(temp2);
		return temp1.subtract(answerHelper(m));
	}
	**/

	/**
	public static BigInteger answerHelper(BigInteger n) {
		if (n.equals(BigInteger.ONE)) {
			return BigInteger.ONE;
		}
		if (n.compareTo(BigInteger.ZERO) < 0) {
			return BigInteger.ZERO;
		}

		return (n.add(m)).multiply(n.add(m).add(BigInteger.ONE))
				.divide(new BigInteger("2"))
				.subtract(m.multiply(m.add(BigInteger.ONE)))
				.subtract(answerHelper(m));
	}

	public static BigInteger mult(BigInteger n) {
		return n.multiply(SQRT2MINUS1).divide(new BigInteger(
				"10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));
	}
	**/

	public static BigDecimal answerHelper(BigDecimal n) {
		if (n.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO;
		}
		BigDecimal m = n.multiply(SQRT2MINUS1).setScale(0, RoundingMode.DOWN);

		return (n.add(m)).multiply(n.add(m).add(BigDecimal.ONE))
				.divide(new BigDecimal("2"))
				.subtract(m.multiply(m.add(BigDecimal.ONE)))
				.subtract(answerHelper(m));
	}

	public static void main(String[] args) {
		System.out.println(answer("5").equals("19"));
		System.out.println(answer("77").equals("4208"));
	}
}

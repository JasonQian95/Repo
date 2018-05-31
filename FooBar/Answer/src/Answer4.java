import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/***
 * Bomb Baby ===========
 * 
 * You're so close to destroying the LAMBCHOP doomsday device you can taste it!
 * But in order to do so, you need to deploy special self-replicating bombs
 * designed for you by the brightest scientists on Bunny Planet. There are two
 * types: Mach bombs (M) and Facula bombs (F). The bombs, once released into the
 * LAMBCHOP's inner workings, will automatically deploy to all the strategic
 * points you've identified and destroy them at the same time.
 * 
 * But there's a few catches. First, the bombs self-replicate via one of two
 * distinct processes: Every Mach bomb retrieves a sync unit from a Facula bomb;
 * for every Mach bomb, a Facula bomb is created; Every Facula bomb
 * spontaneously creates a Mach bomb.
 * 
 * For example, if you had 3 Mach bombs and 2 Facula bombs, they could either
 * produce 3 Mach bombs and 5 Facula bombs, or 5 Mach bombs and 2 Facula bombs.
 * The replication process can be changed each cycle.
 * 
 * Second, you need to ensure that you have exactly the right number of Mach and
 * Facula bombs to destroy the LAMBCHOP device. Too few, and the device might
 * survive. Too many, and you might overload the mass capacitors and create a
 * singularity at the heart of the space station - not good!
 * 
 * And finally, you were only able to smuggle one of each type of bomb - one
 * Mach, one Facula - aboard the ship when you arrived, so that's all you have
 * to start with. (Thus it may be impossible to deploy the bombs to destroy the
 * LAMBCHOP, but that's not going to stop you from trying!)
 * 
 * You need to know how many replication cycles (generations) it will take to
 * generate the correct amount of bombs to destroy the LAMBCHOP. Write a
 * function answer(M, F) where M and F are the number of Mach and Facula bombs
 * needed. Return the fewest number of generations (as a string) that need to
 * pass before you'll have the exact number of bombs necessary to destroy the
 * LAMBCHOP, or the string "impossible" if this can't be done! M and F will be
 * string representations of positive integers no larger than 10^50. For
 * example, if M = "2" and F = "1", one generation would need to pass, so the
 * answer would be "1". However, if M = "2" and F = "4", it would not be
 * possible.
 ***/

public class Answer4 {

	/***
	public static String answer(String M, String F) {
		long m = Long.parseLong(M);
		long f = Long.parseLong(F);
		int count = 0;

		if (m == 1 && f == 1) {
			return "0";
		}
		if (m == f || (m % 2 == 0 && f % 2 == 0)) {
			return "impossible";
		}

		HashSet<long[]> curGen;
		HashSet<long[]> nextGen = new HashSet<long[]>();
		nextGen.add(new long[] { 1, 1 });
		do {
			curGen = nextGen;
			nextGen = new HashSet<long[]>();
			for (long[] MF : curGen) {
				if (MF[0] == m && MF[1] == f) {
					return String.valueOf(count);
				}
				if (MF[0] + MF[1] <= m) {
					nextGen.add(new long[] { MF[0] + MF[1], MF[1] });
				}
				if (MF[0] + MF[1] <= f) {
					nextGen.add(new long[] { MF[0], MF[0] + MF[1] });
				}
			}
			count++;
		} while (curGen.size() > 0);
		return "impossible";
	}

	public static String answer(String M, String F) {
		long m = Long.parseLong(M);
		long f = Long.parseLong(F);
		int count = 0;
		int ans = answerHelper(m, f, count);
		if (ans >= 0) {
			return String.valueOf(ans);
		}
		return "impossible";
	}

	public static int answerHelper(long M, long F, int count) {
		if (M < 1 || F < 1) {
			return -1;
		}
		if (M == 1 && F == 1) {
			return count;
		}
		int ans;
		ans = answerHelper(M - F, F, count + 1);
		if (ans >= 0) {
			return ans;
		}
		ans = answerHelper(M, F - M, count + 1);
		if (ans >= 0) {
			return ans;
		}
		return -1;
	}
	***/
	
	public static String answer(String M, String F) {
		BigInteger m = new BigInteger(M);
		BigInteger f = new BigInteger(F);
		BigInteger count = BigInteger.valueOf(0);
		BigInteger ans = answerHelper(m, f, count);
		if (ans.compareTo(BigInteger.valueOf(0)) > 0){
		    return ans.toString();
		}
		return "impossible";
	}
	
	public static BigInteger answerHelper(BigInteger M, BigInteger F, BigInteger count){
	    if (M.compareTo(BigInteger.valueOf(1)) == 0 && F.compareTo(BigInteger.valueOf(1)) == 0){
	        return count;
	    }
	    if (M.compareTo(BigInteger.valueOf(1)) < 0 || F.compareTo(BigInteger.valueOf(1)) < 0 || M.compareTo(F) == 0){
	        return BigInteger.valueOf(-1);
	    }
	    BigInteger newCount;
	    BigInteger countDif;
	    BigInteger newM;
	    BigInteger newF;
	    BigDecimal decM = new BigDecimal(M);
	    BigDecimal decF = new BigDecimal(F);
	    if (M.compareTo(F) > 0){
	    	countDif = decM.subtract(decF).divide(decF, RoundingMode.UP).toBigInteger();
	        newCount = count.add(countDif);
    	    newM = M.subtract(F.multiply(countDif));
    	    newF = F;
        }
	    else{
	    	countDif = decF.subtract(decM).divide(decM, RoundingMode.UP).toBigInteger();
	        newCount = count.add(countDif);
    	    newM = M;
    	    newF = F.subtract(M.multiply(countDif));
	    }
	    return answerHelper(newM, newF, newCount);
	}

	public static void main(String[] args) {
		System.out.println(answer("2", "1").equals("1"));
		System.out.println(answer("4", "7").equals("4"));
	}
}

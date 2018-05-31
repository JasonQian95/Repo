import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Distract the Guards ===================
 * 
 * The time for the mass escape has come, and you need to distract the guards so
 * that the bunny prisoners can make it out! Unfortunately for you, they're
 * watching the bunnies closely. Fortunately, this means they haven't realized
 * yet that the space station is about to explode due to the destruction of the
 * LAMBCHOP doomsday device. Also fortunately, all that time you spent working
 * as first a minion and then a henchman means that you know the guards are fond
 * of bananas. And gambling. And thumb wrestling.
 * 
 * The guards, being bored, readily accept your suggestion to play the Banana
 * Games.
 * 
 * You will set up simultaneous thumb wrestling matches. In each match, two
 * guards will pair off to thumb wrestle. The guard with fewer bananas will bet
 * all their bananas, and the other guard will match the bet. The winner will
 * receive all of the bet bananas. You don't pair off guards with the same
 * number of bananas (you will see why, shortly). You know enough guard
 * psychology to know that the one who has more bananas always gets
 * over-confident and loses. Once a match begins, the pair of guards will
 * continue to thumb wrestle and exchange bananas, until both of them have the
 * same number of bananas. Once that happens, both of them will lose interest
 * and go back to guarding the prisoners, and you don't want THAT to happen!
 * 
 * For example, if the two guards that were paired started with 3 and 5 bananas,
 * after the first round of thumb wrestling they will have 6 and 2 (the one with
 * 3 bananas wins and gets 3 bananas from the loser). After the second round,
 * they will have 4 and 4 (the one with 6 bananas loses 2 bananas). At that
 * point they stop and get back to guarding.
 * 
 * How is all this useful to distract the guards? Notice that if the guards had
 * started with 1 and 4 bananas, then they keep thumb wrestling! 1, 4 -> 2, 3 ->
 * 4, 1 -> 3, 2 -> 1, 4 and so on.
 * 
 * Now your plan is clear. You must pair up the guards in such a way that the
 * maximum number of guards go into an infinite thumb wrestling loop!
 * 
 * Write a function answer(banana_list) which, given a list of positive integers
 * depicting the amount of bananas the each guard starts with, returns the
 * fewest possible number of guards that will be left to watch the prisoners.
 * Element i of the list will be the number of bananas that guard i (counting
 * from 0) starts with.
 * 
 * The number of guards will be at least 1 and not more than 100, and the number
 * of bananas each guard starts with will be a positive integer no more than
 * 1073741823 (i.e. 2^30 -1). Some of them stockpile a LOT of bananas.
 **/

public class Answer7 {

	static int lca(int[] match, int[] base, int[] p, int a, int b) {
		boolean[] used = new boolean[match.length];
		while (true) {
			a = base[a];
			used[a] = true;
			if (match[a] == -1)
				break;
			a = p[match[a]];
		}
		while (true) {
			b = base[b];
			if (used[b])
				return b;
			b = p[match[b]];
		}
	}

	static void markPath(int[] match, int[] base, boolean[] blossom, int[] p, int v, int b, int children) {
		for (; base[v] != b; v = p[match[v]]) {
			blossom[base[v]] = blossom[base[match[v]]] = true;
			p[v] = children;
			children = match[v];
		}
	}

	static int findPath(List<Integer>[] graph, int[] match, int[] p, int root) {
		int n = graph.length;
		boolean[] used = new boolean[n];
		Arrays.fill(p, -1);
		int[] base = new int[n];
		for (int i = 0; i < n; ++i)
			base[i] = i;

		used[root] = true;
		int qh = 0;
		int qt = 0;
		int[] q = new int[n];
		q[qt++] = root;
		while (qh < qt) {
			int v = q[qh++];

			for (int to : graph[v]) {
				if (base[v] == base[to] || match[v] == to)
					continue;
				if (to == root || match[to] != -1 && p[match[to]] != -1) {
					int curbase = lca(match, base, p, v, to);
					boolean[] blossom = new boolean[n];
					markPath(match, base, blossom, p, v, curbase, to);
					markPath(match, base, blossom, p, to, curbase, v);
					for (int i = 0; i < n; ++i)
						if (blossom[base[i]]) {
							base[i] = curbase;
							if (!used[i]) {
								used[i] = true;
								q[qt++] = i;
							}
						}
				} else if (p[to] == -1) {
					p[to] = v;
					if (match[to] == -1)
						return to;
					to = match[to];
					used[to] = true;
					q[qt++] = to;
				}
			}
		}
		return -1;
	}

	// This, and the 3 funcs above, are Edmond's Blossom Algorithm, as given by indy256
	public static int maxMatching(List<Integer>[] graph) {
		int n = graph.length;
		int[] match = new int[n];
		Arrays.fill(match, -1);
		int[] p = new int[n];
		for (int i = 0; i < n; ++i) {
			if (match[i] == -1) {
				int v = findPath(graph, match, p, i);
				while (v != -1) {
					int pv = p[v];
					int ppv = match[pv];
					match[v] = pv;
					match[pv] = v;
					v = ppv;
				}
			}
		}

		int matches = 0;
		for (int i = 0; i < n; ++i)
			if (match[i] != -1)
				++matches;
		return matches / 2;
	}

	public static int answer(int[] list) {
		int[][] possibleMatches = new int[list.length][list.length];
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list.length; j++) {
				int z = (list[i] + list[j]) / BigInteger.valueOf(list[i]).gcd(BigInteger.valueOf(list[j])).intValue();
				possibleMatches[i][j] = ((z - 1) & z) == 0 ? 0 : 1;
			}
		}

		List<Integer>[] graph = new List[list.length];
		for (int i = 0; i < list.length; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list.length; j++) {
				if (possibleMatches[i][j] == 1) {
					graph[i].add(j);
				}
			}
		}

		return list.length - maxMatching(graph) * 2;
	}

	public static void main(String[] args) {
		int[] in1 = { 1, 1 };
		System.out.println(answer(in1) == 2);
		int[] in2 = { 1, 7, 3, 21, 13, 19 };
		System.out.println(answer(in2) == 0);
	}
}

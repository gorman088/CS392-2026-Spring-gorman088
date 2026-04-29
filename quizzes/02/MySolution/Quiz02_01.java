//
// HX-2026-04-28: 50 points
//
// This question tests your understanding
// of recursion and time analysis involving
// recursion.
// Given a sequence xs, a subsequence of xs
// can be represented as a list of integers
// (representing indices). For instance, given
// xs = "Hello", (0, 2, 4) refers to the subeqence
// "Hlo" (since xs[0] = 'H', xs[2] = 'l', and
// xs[4] = 'o'); (0, 3, 4) also refers to "Hlo".
// The subsequece (0, 2, 4) is to the left of
// the subsequece (0, 3, 4) as (0, 2, 4) is less
// than (0, 3, 4) according to the lexicographic
// ordering.
//
// Here you are asked to implement a function that
// finds the longest leftmost ascending subsequence
// of a given sequence.
// For instance, suppose xs = [1,2,1,2,3,1,2,3,4],
// the longest leftmost ascending subsequence of xs
// is represented by (0, 1, 3, 4, 7, 8) (which refers
// to [1,2,2,3,3,4] in xs).
//
// In order to receive 50 points, your implementation
// should be quadratic time, that is, O(n^2) time and
// you MUST give a brief explanation as to why it is so.
// Otherwise, a working solution receives at most 60%, that
// is, 30 points out of 50 points.
//
import MyLibrary.FnList.*;
// Please see Library00/FnList for FnList.java
import MyLibrary.FnA1sz.*;
// Please see Library00/FnA1sz for FnA1sz.java

/* Runtime Explanation:
The algorithm uses two nested loops where for each index i it checks every later index j so the total number of checks is:
(n - 1) + (n - 2) + ... + 1 = n(n - 1)/2
This is O(n^2) time. The final step that builds the answer list takes O(n) time so the overall runtime is still O(n^2)
*/
public class Quiz02_01 {
    public static
	<T extends Comparable<T>>
	FnList<Integer> FnA1szLongestMonoSubsequence(FnA1sz<T> xs) {
	// HX-2025-11-19:
	// This method finds the leftmost longest ascending subsequence
	// of xs. Note that the returned list consists of the indices of
	// the elements of the subsequence.
		int n = xs.length();

        if (n == 0) {
            return new FnList<Integer>();
        }

        int[] len = new int[n];   // len[i] = best length starting at i
        int[] next = new int[n];  // next[i] = next index after i

        // fill from right to left
        for (int i = n - 1; i >= 0; i -= 1) {
            len[i] = 1;
            next[i] = -1;

            for (int j = i + 1; j < n; j += 1) {
                // ascending here means nondecreasing xs[i] <= xs[j]
                if (xs.getAt(i).compareTo(xs.getAt(j)) <= 0) {
                    if (1 + len[j] > len[i]) {
                        len[i] = 1 + len[j];
                        next[i] = j;
                    }
                }
            }
        }

        // pick the leftmost starting index among all longest subsequences
        int best = 0;
        for (int i = 1; i < n; i += 1) {
            if (len[i] > len[best]) {
                best = i;
            }
        }

        // build the answer in reverse then reverse it back.
        FnList<Integer> ans = new FnList<Integer>();
        int cur = best;

        while (cur != -1) {
            ans = new FnList<Integer>(cur, ans);
            cur = next[cur];
        }

        return FnListSUtil.reverse(ans);
    }
    public static void main (String[] args) {
	// HX-2025-11-19:
	// Please write minimal testing code for FnA1szLongestMonoSubsequence
	    Integer[] arr = {1, 2, 1, 2, 3, 1, 2, 3, 4};
		FnA1sz<Integer> xs = new FnA1sz<Integer>(arr);
        FnList<Integer> ans = FnA1szLongestMonoSubsequence(xs);

        FnListSUtil.System$out$print(ans);
        System.out.println();
        // expected to gets FnList(0,1,3,4,7,8)

    }
} // end of [public class Quiz02_01{...}]

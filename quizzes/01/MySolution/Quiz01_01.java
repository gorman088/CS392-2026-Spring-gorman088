//
// HX: 20 points
//
/*
import Library00.FnA1sz.*;
*/
import Library00.FnA1sz.FnA1sz;
public class Quiz01_01 {
    public static
	<T extends Comparable<T>>
	int FnA1szBinarySearch(FnA1sz<T> A, T key) {
	// HX-2026-03-03:
	// Please implement binary search on a sorted functional array (FnA1sz)
	// that returns the largest index i such that key >= A[i] if such i exists,
	// or the method returns -1. The comparison function should be the compareTo
	// method implemented by the class T.
		int n = A.length();
        int lo = 0;
        int hi = n - 1;
        int ans = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            // want the largest index with A[mid] <= key
            if (A.getAt(mid).compareTo(key) <= 0) {
                ans = mid;       // mid works so try to find a larger one
                lo = mid + 1;
            } else {
                hi = mid - 1;    // mid too large so go left
            }
        }
        return ans;
    }

    public static void main (String[] args) {
	// HX-2026-03-04:
	// Please write minimal testing code for FnA1szBinarySearch
	// Should test for cases T = Integer and T = String
		FnA1sz<Integer> Ai = new FnA1sz<Integer>(new Integer[]{1, 3, 3, 7, 10});

        System.out.println("Integer tests:");
        System.out.println(FnA1szBinarySearch(Ai, 0)  + " (expected -1)");
        System.out.println(FnA1szBinarySearch(Ai, 1)  + " (expected 0)");
        System.out.println(FnA1szBinarySearch(Ai, 3)  + " (expected 2)");
        System.out.println(FnA1szBinarySearch(Ai, 6)  + " (expected 2)");
        System.out.println(FnA1szBinarySearch(Ai, 10) + " (expected 4)");
        System.out.println(FnA1szBinarySearch(Ai, 99) + " (expected 4)");

        // String tests (lexicographic order)
        FnA1sz<String> As = new FnA1sz<String>(new String[]{"ant", "bat", "cat", "cat", "dog"});

        System.out.println("String tests:");
        System.out.println(FnA1szBinarySearch(As, "a")   + " (expected -1)");
        System.out.println(FnA1szBinarySearch(As, "ant") + " (expected 0)");
        System.out.println(FnA1szBinarySearch(As, "cat") + " (expected 3)");
        System.out.println(FnA1szBinarySearch(As, "cow") + " (expected 3)");
        System.out.println(FnA1szBinarySearch(As, "dog") + " (expected 4)");
        System.out.println(FnA1szBinarySearch(As, "z")   + " (expected 4)");

	return /*void*/;
    }
}

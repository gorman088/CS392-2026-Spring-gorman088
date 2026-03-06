import java.util.HashMap;
import java.util.HashSet;

public class Quiz01_02 {
    public static boolean solve_3prod(Integer[] A) {
	// Please give a soft quadratic time implementation
	// that solves the 3-prod problem. The function call
	// solve_3prod(A) returns true if and only if there exist
	// distinct indices i, j, and k satisfying A[i]*A[j] = A[k].
	// Why is your implementation soft O(n^2)? Please give a
	// BRIEF explanation
		int n = A.length;
        if (n < 3) return false;

        // count how many times each value appears in A
        HashMap<Integer, Integer> cnt = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            int x = A[i];
            Integer old = cnt.get(x);
            if (old == null) {
                cnt.put(x, 1);
            } else {
                cnt.put(x, old + 1);
            }
        }

        // put all values into set to check if p in A
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < n; i++) {
            set.add(A[i]);
        }

        // try all distinct pairs 
        // Soft O(n^2)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int a = A[i];
                int b = A[j];
                int p = a * b; // produxt we want to find

                if (!set.contains(p)) continue;

                // If p is different from a and b then we can pick k at some index with A[k]=p
                if (p != a && p != b) return true;

                // Otherwise p equals a or b so we need enough copies to make i, j, k distinct
                Integer c = cnt.get(p); // could be null
                if (c == null) continue;

                if (p == a && p == b) {
                    // need 3 occurrences of that value
                    if (c >= 3) return true;
                } else {
                    // p equals exactly one of a then b need 2 copies
                    if (c >= 2) return true;
                }
            }
        }

        return false;
    }
	/* Brief Explanation: Counting + building is O(n). The double loop is O(n^2) pairs.
	Each pair uses expected O(1) hash operations so it is still soft O(n^2)
	*/
    public static void main(String[] argv) {
	// Please write some code here for testing solve_3prod
		Integer[] A1 = {2, 3, 6};     // 2*3=6
        Integer[] A2 = {2, 3, 7};     // no
        Integer[] A3 = {0, 5, 0};     // 0*5=0 with distinct indices
        Integer[] A4 = {1, 2, 4, 8};  // 2*4=8
        Integer[] A5 = {5, 5, 25};    // 5*5=25
        Integer[] A6 = {5, 5};        // too short

        System.out.println(solve_3prod(A1) + " (expected true)");
        System.out.println(solve_3prod(A2) + " (expected false)");
        System.out.println(solve_3prod(A3) + " (expected true)");
        System.out.println(solve_3prod(A4) + " (expected true)");
        System.out.println(solve_3prod(A5) + " (expected true)");
        System.out.println(solve_3prod(A6) + " (expected false)");
    }
}

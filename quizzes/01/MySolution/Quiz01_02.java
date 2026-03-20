public class Quiz01_02 {
    // simple insertion sort
    private static void sort(Integer[] A) {
        for (int i = 1; i < A.length; i++) {
            int x = A[i];
            int j = i - 1;

            while (j >= 0 && A[j] > x) {
                A[j + 1] = A[j];
                j--;
            }
            A[j + 1] = x;
        }
    }

    // simple binary search
    private static boolean contains(Integer[] A, int x) {
        int lo = 0;
        int hi = A.length - 1;

        while (lo <= hi) {
            int mid = (lo + hi) / 2;

            if (A[mid] == x) return true;
            if (A[mid] < x) lo = mid + 1;
            else hi = mid - 1;
        }
        return false;
    }
    
    
    public static boolean solve_3prod(Integer[] A) {
	// Please give a soft quadratic time implementation
	// that solves the 3-prod problem. The function call
	// solve_3prod(A) returns true if and only if there exist
	// distinct indices i, j, and k satisfying A[i]*A[j] = A[k].
	// Why is your implementation soft O(n^2)? Please give a
	// BRIEF explanation
		int n = A.length;
        if (n < 3) return false;

        // copy A
        Integer[] B = new Integer[n];
        for (int i = 0; i < n; i++) {
            B[i] = A[i];
        }

        // sort the copy
        sort(B);

        // try all distinct pairs i, j
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int p = A[i] * A[j];
                if (contains(B, p)) return true;
            }
        }

        return false;
    }
	/* Brief Explanation: Sorting takes O(n^2). The double loop checks O(n^2) pairs.
      Each binary search takes O(log n). So total time is O(n^2 log n) which is soft quadratic.
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

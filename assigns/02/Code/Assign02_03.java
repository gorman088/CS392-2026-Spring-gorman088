public class Assign02_03 {
    public static boolean solve_3sum(Integer[] A) {
	// Please give a soft qudratic time implementation
	// that solves the 3-sum problem. The function call
	// solve_3sum(A) returns true if and only if there exist
	// distinct indices i, j, and k satisfying A[i]+A[j] = A[k].
	// Why is your implementation soft O(n^2)?
		int n = A.length;
        if (n < 3) return false;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                int target = A[i] + A[j];

                // Look for k > j so i, j, k are automatically distinct
                if (binarySearch(A, target, j + 1, n - 1) != -1) {
                    return true;
                }
            }
        }
        return false;
    }

    // manual binary search on A[lo..hi] returns index if found else -1
    private static int binarySearch(Integer[] A, int key, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int midVal = A[mid];

            if (key < midVal) hi = mid - 1;
            else if (key > midVal) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] argv) {
	// Please write some code here for testing solve_3sum
		Integer[] A1 = {1, 2, 3};         
        Integer[] A2 = {1, 5, 10};        
        Integer[] A3 = {0, -1, 1, 2, 3};  
        Integer[] A4 = {2, 2, 4};         
        Integer[] A5 = {7};               

        System.out.println(solve_3sum(A1)); // true
        System.out.println(solve_3sum(A2)); // false
        System.out.println(solve_3sum(A3)); // true
        System.out.println(solve_3sum(A4)); // true
        System.out.println(solve_3sum(A5)); // false
    }
}

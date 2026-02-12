public class Assign02_03 {
    public static boolean solve_3sum(Integer[] A) {
	// Please give a soft qudratic time implementation
	// that solves the 3-sum problem. The function call
	// solve_3sum(A) returns true if and only if there exist
	// distinct indices i, j, and k satisfying A[i]+A[j] = A[k].
	// Why is your implementation soft O(n^2)?
		int n = A.length;
        if (n < 3) return false;

        for (int k = 0; k < n; k++) {
            int i = 0; // start pointer
            int j = n - 1; // end pointer

            // continue while the ponters don't cross
            while (i < j) {
                // make sure they distinct
                if (i == k) { 
                    i++; 
                    continue; 
                }
                if (j == k) { 
                    j--;
                    continue;
                }

                int sum = A[i] + A[j];
                int target = A[k];

                if (sum == target) {
                    return true; // found distinct i, j, k
                } else if (sum < target) {
                    i++;
                } else {
                    j--;
                }
            }
        }
        // nothing found
        return false;
    }


    public static void main(String[] argv) {
	// Please write some code here for testing solve_3sum
		Integer[] A1 = {1, 2, 3};         
        Integer[] A2 = {1, 5, 10};        
        Integer[] A3 = {0, -1, 1, 2, 3};  
        Integer[] A4 = {2, 2, 4};         
        Integer[] A5 = {7};
        Integer[] A6 = {-3, -2, -1};               

        System.out.println(solve_3sum(A1)); // true
        System.out.println(solve_3sum(A2)); // false
        System.out.println(solve_3sum(A3)); // true
        System.out.println(solve_3sum(A4)); // true
        System.out.println(solve_3sum(A5)); // false
        System.out.println(solve_3sum(A6)); // true
    }
}

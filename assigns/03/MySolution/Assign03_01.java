/*
HX-2026-02-05: 10 points
*/
public class Assign03_01 {
    //
    // HX-2025-09-15:
    // This implementation of f91
    // is not tail-recursive. Please
    // translate it into a version that
    // is tail-recursive
    //
    /*
    static int f91(int n) {
	if (n > 100)
	    return n-10;
	else
	    return f91(f91(n+11);
    }
    */
    static int f91(int n) {
        return f91helper(n, 1);
   }

   // tail recursive approach
   static int f91helper(int n, int k) {
    System.out.println(n + " " + k); 
    if (k == 0) {
        return n;
    }
    if (n > 100) {
        return f91helper(n - 10, k - 1);   // tail call
    } else {
        return f91helper(n + 11, k + 1);   // tail call
    }
   }

   // iterative approach
    static int f91helper_2(int n, int k) {
        while (k > 0) {
            System.out.println(n + " " + k);
            if (n > 100) {
                n = n - 10;
                k--;
                System.out.println(n + " " + k);
            } else {
                n = n + 11;
                k++;
            }
        }
        return n;
    }

    public static void main(String[] argv) {
	// Please write some testing code here
        int num_failed = 0;
        int num_passed = 0;

        /* TEST 1 */
        System.out.println("---TEST 1---");
        int a = 100;
        int b = f91(110);
        if (a == b) {
            System.out.println("Test Passed");
            num_passed++;
        } else {
            System.out.println("Test Failed");
            num_failed++;
        }

        System.out.println();

        /* TEST 2 */
        System.out.println("---TEST 2---");
        int c = 91;
        int d = f91(40);
        if (c == d) {
            System.out.println("Test Passed");
            num_passed++;
        } else {
            System.out.println("Test Failed");
            num_failed++;
        }

        System.out.println();

        /* TEST 3 */
        System.out.println("---TEST 3---");
        int e = 91;
        int f = f91(99);
        if (e == f) {
            System.out.println("Test Passed");
            num_passed++;
        } else {
            System.out.println("Test Failed");
            num_failed++;
        }

        System.out.println();

        /* TEST 4 */
        System.out.println("---TEST 4---");
        int g = 91;
        int h = f91(0);
        if (g == h) {
            System.out.println("Test Passed");
            num_passed++;
        } else {
            System.out.println("Test Failed");
            num_failed++;
        }

        System.out.println();

        /* TEST 5 */
        System.out.println("---TEST 5---");
        int i = 191;
        int j = f91(201);
        if (i == j) {
            System.out.println("Test Passed");
            num_passed++;
        } else {
            System.out.println("Test Failed");
            num_failed++;
        }

        System.out.println();

        System.out.println("Passed " + num_passed + "/5 Tests");
        System.out.println("Failed " + num_failed+ "/5 Tests");
    }
}

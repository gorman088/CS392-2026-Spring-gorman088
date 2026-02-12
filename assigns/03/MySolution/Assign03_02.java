/*
HX-2026-02-05: 10 points
*/
import Library00.FnList.FnList;

public class Assign03_02 {
    static boolean balencedq(String text) {
	//
	// There are only '(', ')', '[', ']', '{', and '}'
	// appearing in [text]. This method should return
	// true if and only if the parentheses/brackets/braces
	// in [text] are balenced.
	// Your solution must make proper use of FnList (as a stack)!
	//
		FnList<Character> st = new FnList<>();

		// iterate through each character
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			// if it is opening bracket push onto stack
			if (c == '(' || c == '[' || c == '{') {
				st = new FnList<>(c, st);
				continue;
			} 

			if (st.nilq()) {
					return false;
			}

			// look at top element
			char top = st.hd();

			// check matching pairs
            if (c == ')') {
                if (top != '(') return false;
            } else if (c == ']') {
                if (top != '[') return false;
            } else if (c == '}') {
                if (top != '{') return false;
            } else {
                return false;
            }

			// if matched pop
			st = st.tl();
			
		}
		// stack is empthy means its balanced
		return st.nilq();
    }

    public static void main(String[] argv) {
	// Please write some testing code for your 'balencedq"
		int num_failed = 0;
		int num_passed = 0;

		/* TEST 1 */
		System.out.println("---TEST 1---");
		boolean a = true;
		boolean b = balencedq("");
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
		boolean c = true;
		boolean d = balencedq("()[]{}");
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
		boolean e = true;
		boolean f = balencedq("([{}])");
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
		boolean g = false;
		boolean h = balencedq("([)]");
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
		boolean i = false;
		boolean j = balencedq("(((");
		if (i == j) {
			System.out.println("Test Passed");
			num_passed++;
		} else {
			System.out.println("Test Failed");
			num_failed++;
		}
		System.out.println();

		/* TEST 6 */
		System.out.println("---TEST 6---");
		boolean k = false;
		boolean l = balencedq("())");
		if (k == l) {
			System.out.println("Test Passed");
			num_passed++;
		} else {
			System.out.println("Test Failed");
			num_failed++;
		}
		System.out.println();

		System.out.println("Passed " + num_passed + "/6 Tests");
		System.out.println("Failed " + num_failed + "/6 Tests");
	}
}

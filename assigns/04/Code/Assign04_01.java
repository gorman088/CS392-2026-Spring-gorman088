/*
HX-2026-02-13: 10 points
*/
import Library00.FnList.FnList;
import Library00.FnStrn.FnStrn;

public class Assign04_01 {
    static boolean balencedq(String text) {
	//
	// There are only '(', ')', '[', ']', '{', and '}'
	// appearing in [text]. This method should return
	// true if and only if the parentheses/brackets/braces
	// in [text] are balenced.
	// Your solution must make proper use of FnList (as a stack)!
	//
		FnStrn s = new FnStrn(text);

        // Stack holder so we can mutate 
        final FnList<Character>[] st = new FnList[] { new FnList<>() };

        boolean ok = s.forall(c -> {
            // push openers
            if (c == '(' || c == '[' || c == '{') {
                st[0] = new FnList<>(c, st[0]);
                return true;
            }

            // fail if nothing to match
            if (st[0].nilq()) return false;

            char top = st[0].hd();

            // check match
            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;

            // if matched then pop
            st[0] = st[0].tl();
            return true;
        });

        // balanced if all checks passed and stack is empty
        return ok && st[0].nilq();
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
} // end of [public class Assign04_01{...}]

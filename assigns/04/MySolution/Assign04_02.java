/*
HX-2026-02-13: 20 points
*/
import Library00.FnList.FnList;
import Library00.FnStrn.FnStrn;

public class Assign04_02 {
    static FnStrn
	FnList$FnStrn_concate(FnList<FnStrn> xs) {
		// compute total length
		final int[] total = new int[] {0};

		xs.foritm(s -> {
			total[0] += s.length();
		});

		// allocate result array
		char[] result = new char[total[0]];

		// fill result array
		final int[] index = new int[] {0};

		xs.foritm(s -> {
			s.foritm(c -> {
				result[index[0]] = c;
				index[0]++;
			});
		});

		// return new FnStrn
		return new FnStrn(result);
}
    

    public static void main(String[] argv) {
	// Please write some testing code for your 'FnList$FnStrn_concate"
		int num_failed = 0;
		int num_passed = 0;

		/* TEST 1 */
		System.out.println("---TEST 1---");
		FnList<FnStrn> t1 =
			new FnList<>(new FnStrn("a"),
			new FnList<>(new FnStrn("bc"),
			new FnList<>()));

		FnStrn r1 = FnList$FnStrn_concate(t1);
		if (r1.toString().equals("abc")) {
			System.out.println("Test Passed");
			num_passed++;
		} else {
			System.out.println("Test Failed");
			num_failed++;
		}
		System.out.println();

		/* TEST 2 */
		System.out.println("---TEST 2---");
		FnList<FnStrn> t2 = new FnList<>();
		FnStrn r2 = FnList$FnStrn_concate(t2);
		if (r2.toString().equals("")) {
			System.out.println("Test Passed");
			num_passed++;
		} else {
			System.out.println("Test Failed");
			num_failed++;
		}
		System.out.println();

		/* TEST 3 */
		System.out.println("---TEST 3---");
		FnList<FnStrn> t3 =
			new FnList<>(new FnStrn("hi"),
			new FnList<>(new FnStrn("!"),
			new FnList<>()));

		FnStrn r3 = FnList$FnStrn_concate(t3);
		if (r3.toString().equals("hi!")) {
			System.out.println("Test Passed");
			num_passed++;
		} else {
			System.out.println("Test Failed");
			num_failed++;
		}
		System.out.println();

		System.out.println("Passed " + num_passed + "/3 Tests");
		System.out.println("Failed " + num_failed + "/3 Tests");
	}
} // end of [public class Assign04_02{...}]

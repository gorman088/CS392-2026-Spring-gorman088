import Library00.FnList.*;
    
import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign05_01 {

    public static
	<T extends Comparable<T>>
	FnList<T> insertSort(FnList<T> xs) {
	return insertSort(xs, (x1, x2) -> x1.compareTo(x2));
    }
//
    public static<T> FnList<T>
	insertSort(FnList<T> xs, ToIntBiFunction<T,T> cmp) {
	// HX-2026-02-26: Please implement this method
	// You can use while-loops but cannot make recursive
	// calls.
		// reverse the original list first
		FnList<T> rev = new FnList<>();
        FnList<T> cur = xs;

        while (!cur.nilq()) {
            rev = new FnList<>(cur.hd(), rev);
            cur = cur.tl();
        }

		// stores the growing sorted list
        FnList<T> sorted = new FnList<>();
        cur = rev;

		// insert each itme from rev into the sorted list
        while (!cur.nilq()) {
            T x0 = cur.hd();
            cur = cur.tl();

			// leftRev stores the part before the insertion point in reverse order
            FnList<T> leftRev = new FnList<>();
            FnList<T> scan = sorted;

			// find where x0 should be inserted
            while (!scan.nilq()) {
                T hd = scan.hd();
                int sgn = cmp.applyAsInt(x0, hd);

				// insert before hd if x0 <= hd
                if (sgn <= 0) {
                    break;
                }

                leftRev = new FnList<>(hd, leftRev);
                scan = scan.tl();
            }

			// insert x0 into correct position
            FnList<T> merged = new FnList<>(x0, scan);

			// put the left side back in front
            while (!leftRev.nilq()) {
                merged = new FnList<>(leftRev.hd(), merged);
                leftRev = leftRev.tl();
            }

            sorted = merged;
        }

        return sorted;
    }

    

    public static void main(String[] args) {
	// Please write some testing code that applies
	// insertSort to the following list of 1M numbers:
	// 1, 0, 3, 2, 5, 4, 7, 6, 9, 8, 11, 10, ..., 999999, 999998.
		final int N = 1_000_000;
        FnList<Integer> xs = new FnList<>();

		// build the list
        for (int i = N - 2; i >= 0; i -= 2) {
            xs = new FnList<>(i, xs);
            xs = new FnList<>(i + 1, xs);
        }

		// sort list
        FnList<Integer> sorted = insertSort(xs);

		// check if the result is in right oreder
        boolean ok = true;
        FnList<Integer> cur = sorted;

        if (!cur.nilq()) {
            int prev = cur.hd();
            cur = cur.tl();

            while (!cur.nilq()) {
                int now = cur.hd();
                if (prev > now) {
                    ok = false;
                    break;
                }
                prev = now;
                cur = cur.tl();
            }
        }

        if (ok) {
            System.out.println("Sorted correctly");
        } else {
            System.out.println("Not sorted correctly");
        }
    }
    

} // end of [public class Assign05_01{...}]

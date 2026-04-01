import Library00.LnStrm.*;
import Library00.FnTuple.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign07_02 {
    public static
	LnStrm<Integer>
	ramanujanNumbers() {
	// Return a stream of all the ramanujanNumbers
		return findRamanujan(cubeSumOrderedIntegerPairs());
    }

    public static
	LnStrm<
	  FnTupl2<Integer,Integer>>
	cubeSumOrderedIntegerPairs() {
	// Return a stream of all the positive integer pairs
	// that are ordered according to the sum of the cubes
	// of the two integer components
		return Assign07_01.mergeLnStrm(
            allRows(1),
            (p1, p2) -> {
                int s1 = cubeSum(p1);
                int s2 = cubeSum(p2);

                // compare pairs by cube sum first
                if (s1 < s2) return -1;
                if (s1 > s2) return 1;

                // if cube sums are equal then break ties by normal tuple order
                return FnTupl2SUtil.compare(p1, p2);
            }
        );
    }

	// build the outer stream of rows:
    // row(1), row(2), row(3), ...
    private static
    LnStrm<LnStrm<FnTupl2<Integer,Integer>>>
    allRows(int x) {
        return new LnStrm<>(() ->
            new LnStcn<>(
                oneRow(x, x),
                allRows(x + 1)
            )
        );
    }


    // build one row of pairs:
    // (x,x), (x,x+1), (x,x+2), ...
    private static
    LnStrm<FnTupl2<Integer,Integer>>
    oneRow(int x, int y) {
        return new LnStrm<>(() ->
            new LnStcn<>(
                new FnTupl2<>(x, y),
                oneRow(x, y + 1)
            )
        );
    }

	// scan the ordered pair stream and find repeated cube sums
    private static
    LnStrm<Integer>
    findRamanujan(LnStrm<FnTupl2<Integer,Integer>> ps) {
        return new LnStrm<>(() -> {
            // look at the first pair
            LnStcn<FnTupl2<Integer,Integer>> c1 = ps.eval0();
            if (c1.nilq()) return new LnStcn<>();

            // look at the next pair
            LnStcn<FnTupl2<Integer,Integer>> c2 = c1.tl().eval0();
            if (c2.nilq()) return new LnStcn<>();

            FnTupl2<Integer,Integer> p1 = c1.hd();
            FnTupl2<Integer,Integer> p2 = c2.hd();

            int s1 = cubeSum(p1);
            int s2 = cubeSum(p2);

            // if two consecutive pairs have the same cube sum them
            // that sum is a ramanujan number
            if (s1 == s2) {
                return new LnStcn<>(
                    s1,
                    skipSameSum(c2.tl(), s1)
                );
            } else {
                // otherwise continue searching from the second pair
                return findRamanujan(new LnStrm<>(() -> c2)).eval0();
            }
        });
    }

	// skip over all consecutive pairs that have the same cube sum
    private static
    LnStrm<Integer>
    skipSameSum(LnStrm<FnTupl2<Integer,Integer>> ps, int sum) {
        return new LnStrm<>(() -> {
            LnStcn<FnTupl2<Integer,Integer>> c = ps.eval0();

            if (c.nilq()) return new LnStcn<>();

            if (cubeSum(c.hd()) == sum) {
                return skipSameSum(c.tl(), sum).eval0();
            } else {
                return findRamanujan(new LnStrm<>(() -> c)).eval0();
            }
        });
    }

    // compute x^3 + y^3 for a pair x,y
    private static
    int cubeSum(FnTupl2<Integer,Integer> p) {
        int x = p.sub0;
        int y = p.sub1;
        return x*x*x + y*y*y;
    }


    public static void main(String[] args) {
		System.out.println("First 10 cubesum ordered pairs:");
        printFirstPairs(cubeSumOrderedIntegerPairs(), 10);

        System.out.println();
        System.out.println("First 5 Ramanujan numbers:");
        printFirstInts(ramanujanNumbers(), 5);
    }

	// print the first n pairs from a pair stream
    private static
    void printFirstPairs(LnStrm<FnTupl2<Integer,Integer>> ps, int n) {
        LnStrm<FnTupl2<Integer,Integer>> cur = ps;
        for (int i = 0; i < n; i++) {
            LnStcn<FnTupl2<Integer,Integer>> c = cur.eval0();
            if (c.nilq()) return;
            System.out.println(c.hd() + " sum=" + cubeSum(c.hd()));
            cur = c.tl();
        }
    }

    // print the first n integers from an integer stream
    private static
    void printFirstInts(LnStrm<Integer> xs, int n) {
        LnStrm<Integer> cur = xs;
        for (int i = 0; i < n; i++) {
            LnStcn<Integer> c = cur.eval0();
            if (c.nilq()) return;
            System.out.println(c.hd());
            cur = c.tl();
        }
    }

} // end of [public class Assign07_02{...}]


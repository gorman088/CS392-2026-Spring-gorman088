import Library00.LnStrm.*;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.ToIntBiFunction;

public class Assign07_01 {
//
    public static<T>
	LnStrm<T> mergeLnStrm(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
        return mergeOuter(fxss, cmpr);
    }

    // merge the stream of streams one element at a time
    private static <T>
    LnStrm<T> mergeOuter(LnStrm<LnStrm<T>> fxss, ToIntBiFunction<T,T> cmpr) {
        return new LnStrm<T>(() -> {
            // look at the first inner stream
            LnStcn<LnStrm<T>> cxss = fxss.eval0();

            // if there are no inner streams then return empty
            if (cxss.nilq()) {
                return new LnStcn<T>();
            }

            LnStrm<T> first = cxss.hd();
            LnStcn<T> cfirst = first.eval0();

            // if the first inner stream is empty then skip it
            if (cfirst.nilq()) {
                return mergeOuter(cxss.tl(), cmpr).eval0();
            }

            T x = cfirst.hd();
            LnStrm<T> firstTail = cfirst.tl();

            // put the tail back into the outer stream in sorted order
            LnStrm<LnStrm<T>> newOuter = insertStream(firstTail, cxss.tl(), cmpr);

            // output x then continue merging
            return new LnStcn<T>(
                x,
                mergeOuter(newOuter, cmpr)
            );
        });
    }

   private static <T>
    LnStrm<LnStrm<T>> insertStream(
        LnStrm<T> xs,
        LnStrm<LnStrm<T>> xss,
        ToIntBiFunction<T,T> cmpr
    ) {
        return new LnStrm<LnStrm<T>>(() -> {
            LnStcn<T> cxs = xs.eval0();

            // if xs is empty, do not insert it
            if (cxs.nilq()) {
                return xss.eval0();
            }

            // rebuild xs bc eval0() consumes it
            LnStrm<T> xsAgain = new LnStrm<T>(() -> cxs);

            LnStcn<LnStrm<T>> cxss = xss.eval0();

            // if outer stream is empty then xs is the only stream
            if (cxss.nilq()) {
                return new LnStcn<LnStrm<T>>(xsAgain);
            }

            LnStrm<T> ys = cxss.hd();
            LnStcn<T> cys = ys.eval0();

            // if ys is empty then skip it
            if (cys.nilq()) {
                return insertStream(xsAgain, cxss.tl(), cmpr).eval0();
            }

            // rebuild ys bc eval0() consumes it
            LnStrm<T> ysAgain = new LnStrm<T>(() -> cys);

            // keep the outer stream ordered by first elements
            if (cmpr.applyAsInt(cxs.hd(), cys.hd()) <= 0) {
                return new LnStcn<LnStrm<T>>(
                    xsAgain,
                    new LnStrm<LnStrm<T>>(() ->
                        new LnStcn<LnStrm<T>>(ysAgain, cxss.tl())
                    )
                );
            } else {
                return new LnStcn<LnStrm<T>>(
                    ysAgain,
                    insertStream(xsAgain, cxss.tl(), cmpr)
                );
            }
        });
    }
    
} // end of [public class Assign07_01{...}]


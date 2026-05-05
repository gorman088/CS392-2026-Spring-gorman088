/*
// HX: 50 points for Final_02
// HX: This one tests your quicksort and mergesort
// In Final_01, pg2701_word$strmize() is implemented
// that lists all the words in pg2701.txt. Here, you
// are asked to generate FnList of pairs; each pair consists
// of a word (FnList<Character>) and a count (Integer) such that
// the count is the number of occurrences of the word in pg2701.txt.
// Note that a lower case letter is considered the same as its
// corresponding upper case. For instance, "Whale" and "whale"
// are considered the same word.
*/


import MyLibrary.FnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.Sorts.*;
import MyLibrary.LnStrm.*;


public class Final_02 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize2() {
	// HX-2026-05-04:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Turn this stream into an array A1 of words (FnList<Character>[])
	// 3. Call the quicksort in MyLibrary to sort A1
	// 4. Use sorted A1 to generate a list L2 of word-count pairs
	// 5. Use the mergesort (mergeSort) in MyLibrary to sort L2 using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 6. The sorted L2 is the return value of pg2701_word$count$listize2()
		
	    // get the word stream from Final_01.
		LnStrm<FnList<Character>> wxs = Final_01.pg2701_word$strmize();

		// convert the stream of words into a FnList of words
		FnList<FnList<Character>> words = streamToList(wxs);

		// convert the FnList of words into a FnA1sz array wrapper
		FnA1sz<FnList<Character>> A1 =
			new FnA1sz<FnList<Character>>(words);

		// sort the words alphabetically using quicksort (MyLibrary)
		A1 = Sorts.quickSort(
			A1,
			(w1, w2) -> compareWords(w1, w2)
		);

		// count equal adjacent words in the sorted word array
		FnList<FnTupl2<FnList<Character>, Integer>> counts =
			countSortedWords(A1);

		// convert the wordcount list into an array wrapper bc
		// to avoid the FnList mergeSort stack overflow
		FnA1sz<FnTupl2<FnList<Character>, Integer>> A2 =
			new FnA1sz<FnTupl2<FnList<Character>, Integer>>(counts);

		// sort the wordcount pairs using array based mergeSort from MyLibrary
		A2 = Sorts.mergeSort(
			A2,
			(p1, p2) -> comparePairs(p1, p2)
		);

		// convert the sorted array back into required FnList
		return pairArrayToList(A2);
    }

	// converts a sorted FnA1sz of pairs back into a FnList of pairs
	private static FnList<FnTupl2<FnList<Character>, Integer>>
	pairArrayToList(FnA1sz<FnTupl2<FnList<Character>, Integer>> A) {
		FnList<FnTupl2<FnList<Character>, Integer>> result =
			new FnList<FnTupl2<FnList<Character>, Integer>>();

		for (int i = A.length() - 1; i >= 0; i -= 1) {
			result =
				new FnList<FnTupl2<FnList<Character>, Integer>>(
					A.getAt(i),
					result
				);
		}

		return result;
	}

	// converts a stream of words into a FnList of words
    private static FnList<FnList<Character>>
    streamToList(LnStrm<FnList<Character>> wxs) {
        FnList<FnList<Character>> result =
            new FnList<FnList<Character>>();

        // read words from the stream one by one
        while (true) {
            LnStcn<FnList<Character>> node = wxs.eval0();

            if (node.nilq()) {
                break;
            }

            // addcurrent word to the front of the list
            result =
                new FnList<FnList<Character>>(node.hd(), result);

            // move to the rest of the stream
            wxs = node.tl();
        }

        // reverse so the words are in the original order
        return result.reverse();
    }

    // count equal words after the word array has been sorted
    private static FnList<FnTupl2<FnList<Character>, Integer>>
    countSortedWords(FnA1sz<FnList<Character>> A1) {
        FnList<FnTupl2<FnList<Character>, Integer>> result =
            new FnList<FnTupl2<FnList<Character>, Integer>>();

        int i = 0;
        int n = A1.length();

        // walk through sorted array
        while (i < n) {
            // current word to count
            FnList<Character> word = A1.getAt(i);

            int count = 1;
            int j = i + 1;

            // count repeated copies of this same word
            while (j < n && compareWords(word, A1.getAt(j)) == 0) {
                count += 1;
                j += 1;
            }

            // add  wordcount pair to the result list
            result =
                new FnList<FnTupl2<FnList<Character>, Integer>>(
                    new FnTupl2<FnList<Character>, Integer>(word, count),
                    result
                );

            // jump to next new word
            i = j;
        }

        return result;
    }

    // compare two words alphabetically
    private static int compareWords(
        FnList<Character> w1,
        FnList<Character> w2
    ) {
        // compare character by character
        while (!w1.nilq() && !w2.nilq()) {
            char c1 = w1.hd();
            char c2 = w2.hd();

            if (c1 < c2) {
                return -1;
            }

            if (c1 > c2) {
                return 1;
            }

            // move to the next character in each word
            w1 = w1.tl();
            w2 = w2.tl();
        }

        // if both ended they equal
        if (w1.nilq() && w2.nilq()) {
            return 0;
        }

        // if w1 ended first it is shorter and comes first
        if (w1.nilq()) {
            return -1;
        }

        return 1;
    }

    // compares wordcount pairs
    private static int comparePairs(
        FnTupl2<FnList<Character>, Integer> p1,
        FnTupl2<FnList<Character>, Integer> p2
    ) {
        int n1 = p1.sub1;
        int n2 = p2.sub1;

        // larger count come first
        if (n1 > n2) {
            return -1;
        }

        // smaller count  later
        if (n1 < n2) {
            return 1;
        }

        // if counts are equal compare words alphabetically
        return compareWords(p1.sub0, p2.sub0);
    }

    // prints one word 
    private static void printWord(FnList<Character> word) {
        while (!word.nilq()) {
            System.out.print(word.hd());
            word = word.tl();
        }
    }

    // print one wordcount pair on one line
    private static void printPair(
        FnTupl2<FnList<Character>, Integer> pair
    ) {
        printWord(pair.sub0);
        System.out.print(" ");
        System.out.println(pair.sub1);
    }
    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$count$listize2()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	
		// generate the sorted wordcount list
        FnList<FnTupl2<FnList<Character>, Integer>> pairs =
            pg2701_word$count$listize2();

        // orint the first 100 pairs
        int i = 0;

        while (i < 100 && !pairs.nilq()) {
            printPair(pairs.hd());
            pairs = pairs.tl();
            i += 1;
        }
    }
}

/*
// HX: 50 points for Final_03
// HX: This one tests your hash map implementation
// In Final_02, pg2701_word$count$listize2() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_03, you are asked to implement the same functionality
// with a different approach.
*/


import MyLibrary.FnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.Sorts.*;
import MyLibrary.HashMap.*;


public class Final_03 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize3() {
	// HX-2026-05-04:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the hash map implemented in Assign08_02 (open addressing)
	//    to count the number of occurrences of each word in the stream of words
	// 3. Then figure out a way to turn the hash map into a list WNS (FnList) of
	//    word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize3()
	    
		// get word stream from Final_01
        LnStrm<FnList<Character>> wxs = Final_01.pg2701_word$strmize();

        // use open addressing hashmap to count words
        QuadraticProbingHashMap<String, Integer> map =
            new QuadraticProbingHashMap<String, Integer>();

        // keep track of unique words because there no iterator
        FnList<String> uniqueWords = new FnList<String>();

        // read every word from the stream
        while (true) {
            LnStcn<FnList<Character>> node = wxs.eval0();

            if (node.nilq()) {
                break;
            }

            String word = wordToString(node.hd());
            Integer oldCount = map.get(word);

            // first time seeing this word
            if (oldCount == null) {
                map.put(word, 1);
                uniqueWords = new FnList<String>(word, uniqueWords);
            }
            // word already exists so update count.
            else {
                map.put(word, oldCount + 1);
            }

            wxs = node.tl();
        }

        // turn hashmap results into list of wordcount pairs
        FnList<FnTupl2<FnList<Character>, Integer>> pairs =
            makePairList(uniqueWords, map);

        // convert to array so we can use arraybased mergesort
        FnA1sz<FnTupl2<FnList<Character>, Integer>> A =
            new FnA1sz<FnTupl2<FnList<Character>, Integer>>(pairs);

        // dort by count descending then alphabetically
        A = Sorts.mergeSort(
            A,
            (p1, p2) -> comparePairs(p1, p2)
        );

        // convert sorted array back to FnList
        return pairArrayToList(A);
    }

	// converts a word from FnList<Character> to string
    private static String wordToString(FnList<Character> word) {
        String result = "";

        while (!word.nilq()) {
            result = result + word.hd();
            word = word.tl();
        }

        return result;
    }

    // converts a String back into a FnList<Character>
    private static FnList<Character> stringToWord(String s) {
        FnList<Character> result = new FnList<Character>();

        // add chars from right to left so the final list iscorrect order
        for (int i = s.length() - 1; i >= 0; i -= 1) {
            result = new FnList<Character>(s.charAt(i), result);
        }

        return result;
    }

    // build a list of wordcount pairs using the unique word list and hashmap
    private static FnList<FnTupl2<FnList<Character>, Integer>>
    makePairList(
        FnList<String> words,
        QuadraticProbingHashMap<String, Integer> map
    ) {
        FnList<FnTupl2<FnList<Character>, Integer>> result =
            new FnList<FnTupl2<FnList<Character>, Integer>>();

        // go through every unique word
        while (!words.nilq()) {
            String word = words.hd();

            // get this words count from the hashmap
            Integer count = map.get(word);

            // add the pair to result list
            result =
                new FnList<FnTupl2<FnList<Character>, Integer>>(
                    new FnTupl2<FnList<Character>, Integer>(
                        stringToWord(word),
                        count
                    ),
                    result
                );

            words = words.tl();
        }

        return result;
    }

    // converts a sorted FnA1sz of pairs back into a FnList
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

    // compare two wordcount pairs
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

        // Smaller count come later
        if (n1 < n2) {
            return 1;
        }

        // if counts are equal compare alphabetically by word
        return compareWords(p1.sub0, p2.sub0);
    }

    // compare two words represented as FnList<Character>
    private static int compareWords(
        FnList<Character> w1,
        FnList<Character> w2
    ) {
        // compare one char at a tim
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

        // if both words ended they are equal
        if (w1.nilq() && w2.nilq()) {
            return 0;
        }

        // if w1 ended firstit is shorter and comes first
        if (w1.nilq()) {
            return -1;
        }

		return 1;
    }

    //print one word
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
	// Please write minimal testing code for pg2701_word$count$listize3()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	       
		// generate the sorted wordcount list
        FnList<FnTupl2<FnList<Character>, Integer>> pairs =
            pg2701_word$count$listize3();

        // print the first 100 pairs
        int i = 0;

        while (i < 100 && !pairs.nilq()) {
            printPair(pairs.hd());
            pairs = pairs.tl();
            i += 1;
        }
	
    }
}

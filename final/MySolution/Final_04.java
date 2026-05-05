/*
// HX: 50 points for Final_04
// HX: This one tests your RBST implementation done in Quiz02_06.
// In Final_02, pg2701_word$count$listize1() is implemented
// to list words in pg2701.txt according their frequencies.
// In Final_04, you are asked to implement the same functionality
// with a different approach.
*/

import MyLibrary.LnStrm.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.FnList.*;
import MyLibrary.FnTuple.*;
import MyLibrary.Sorts.*;


public class Final_04 {
    static FnList<FnTupl2<FnList<Character>, Integer>> pg2701_word$count$listize4() {
	// HX-2026-05-04:
	// Your implementation must contain the following steps:
	// 1. Call pg2701_word$strmize() to get a stream of words
	// 2. Then use the RBST implemented in Quiz02_06 to count the number of
	//    occurrences of each word in the stream of words.
	//    Note that you need to modify your Quiz02_06 implementation to turn
	//    it into an generic associative map for this part.
	// 3. Then figure out a way to turn the RBST-based map into a list WNS
	//    (FnList) of word-count pairs
	// 4. Use the mergesort (mergeSort) in Assign05_01 to sort WNS using
	//    the order (w1, n1) <= (w2, n2) if n1 > n2 or n1 = n2 and w1 <= w2
	// 5. The sorted WNS is the return value of pg2701_word$count$listize4()
		
	    // get the stream of words from Final_01
        LnStrm<FnList<Character>> wxs = Final_01.pg2701_word$strmize();

        // use an RBST based map to count words
        RBSTMap map = new RBSTMap();

        // read every word from the stream
        while (true) {
            LnStcn<FnList<Character>> node = wxs.eval0();

            if (node.nilq()) {
                break;
            }

            // convert the word to a String key
            String word = wordToString(node.hd());

            // look up old count
            Integer oldCount = map.get(word);

            // first time seeing this word
            if (oldCount == null) {
                map.put(word, 1);
            }
            // word already exists so increase count
            else {
                map.put(word, oldCount + 1);
            }

            // move to the rest of the word stream
            wxs = node.tl();
        }

        // convert the RBST map into a list of wordcount pairs
        FnList<FnTupl2<FnList<Character>, Integer>> pairs =
            map.toPairList();

        // convert to array wrapper so can use arraybased mergesort
        FnA1sz<FnTupl2<FnList<Character>, Integer>> A =
            new FnA1sz<FnTupl2<FnList<Character>, Integer>>(pairs);

        // sort pairs by count descending then alphabetically
        A = Sorts.mergeSort(
            A,
            (p1, p2) -> comparePairs(p1, p2)
        );

        // return the sorted pairs as a FnList
        return pairArrayToList(A);
    }

	// convert FnList<Character> into a string
    private static String wordToString(FnList<Character> word) {
        String result = "";

        while (!word.nilq()) {
            result = result + word.hd();
            word = word.tl();
        }

        return result;
    }

    // convert a String back into FnList<Character>.
    private static FnList<Character> stringToWord(String s) {
        FnList<Character> result = new FnList<Character>();

        // add from right to left so the final FnList is correct order
        for (int i = s.length() - 1; i >= 0; i -= 1) {
            result = new FnList<Character>(s.charAt(i), result);
        }

        return result;
    }

    // convert sorted array of pairs back into FnList
    private static FnList<FnTupl2<FnList<Character>, Integer>>
    pairArrayToList(FnA1sz<FnTupl2<FnList<Character>, Integer>> A) {
        FnList<FnTupl2<FnList<Character>, Integer>> result =
            new FnList<FnTupl2<FnList<Character>, Integer>>();

        // add from back to front to keep sorted order
        for (int i = A.length() - 1; i >= 0; i -= 1) {
            result =
                new FnList<FnTupl2<FnList<Character>, Integer>>(
                    A.getAt(i),
                    result
                );
        }

        return result;
    }

    // Compare two word-count pairs.
    private static int comparePairs(
        FnTupl2<FnList<Character>, Integer> p1,
        FnTupl2<FnList<Character>, Integer> p2
    ) {
        int n1 = p1.sub1;
        int n2 = p2.sub1;

        // larger count comes first
        if (n1 > n2) {
            return -1;
        }

        // smaller count comes later
        if (n1 < n2) {
            return 1;
        }

        // if counts tie compare alphabetically.
        return compareWords(p1.sub0, p2.sub0);
    }

    // compare two words represented as FnList<Character>
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

            w1 = w1.tl();
            w2 = w2.tl();
        }

        // both ended means equal
        if (w1.nilq() && w2.nilq()) {
            return 0;
        }

        // dhorter word come first
        if (w1.nilq()) {
            return -1;
        }

        return 1;
    }

    // Print one word
    private static void printWord(FnList<Character> word) {
        while (!word.nilq()) {
            System.out.print(word.hd());
            word = word.tl();
        }
    }

    // print one wordcount pair
    private static void printPair(
        FnTupl2<FnList<Character>, Integer> pair
    ) {
        printWord(pair.sub0);
        System.out.print(" ");
        System.out.println(pair.sub1);
    }

	/*
     * RBST based associative map
     *
     * This is adapted from Quiz02_06
     * each node has parent, left child, right child, and size
     * rotations are used by reroot()
     * keys are String words
     * values are Integer counts
     */

    private static class RBSTMap {
        private Node root = null;
        private java.util.Random rand = new java.util.Random();

        private class Node {
            String key;
            Integer value;
            int size;
            Node parent;
            Node lchild;
            Node rchild;
        }

        // get the size of a subtree
        private int size(Node n) {
            if (n == null) {
                return 0;
            }

            return n.size;
        }

        // make a new node
        private Node makeNode(String key, Integer value, Node parent) {
            Node n = new Node();

            n.key = key;
            n.value = value;
            n.size = 1;
            n.parent = parent;
            n.lchild = null;
            n.rchild = null;

            return n;
        }

        // update the size of one node
        private void updateSize(Node n) {
            if (n != null) {
                n.size = 1 + size(n.lchild) + size(n.rchild);
            }
        }

        // update sizes from this node back up to the root
        private void updateSizesUpward(Node n) {
            while (n != null) {
                updateSize(n);
                n = n.parent;
            }
        }

        // search for a key and return its value
        public Integer get(String key) {
            Node cur = root;

            while (cur != null) {
                int cmp = key.compareTo(cur.key);

                if (cmp < 0) {
                    cur = cur.lchild;
                } else if (cmp > 0) {
                    cur = cur.rchild;
                } else {
                    return cur.value;
                }
            }

            return null;
        }

        // insert a key value pair or replace existing value
        public void put(String key, Integer value) {
            if (root == null) {
                root = makeNode(key, value, null);
                return;
            }

            Node cur = root;
            Node parent = null;
            int cmp = 0;

            // search for the correct place
            while (cur != null) {
                parent = cur;
                cmp = key.compareTo(cur.key);

                if (cmp < 0) {
                    cur = cur.lchild;
                } else if (cmp > 0) {
                    cur = cur.rchild;
                } else {
                    // key already exists so only update value
                    cur.value = value;
                    return;
                }
            }

            // insert a new leaf node
            Node n = makeNode(key, value, parent);

            if (cmp < 0) {
                parent.lchild = n;
            } else {
                parent.rchild = n;
            }

            // fix subtree sizes
            updateSizesUpward(parent);

            // randomly reroot after a new insertion
            reroot();
        }

        // picks one random node from the tree
        private Node randomNode() {
            int r = rand.nextInt(root.size);
            Node cur = root;

            while (cur != null) {
                int leftSize = size(cur.lchild);

                if (r < leftSize) {
                    cur = cur.lchild;
                } else if (r == leftSize) {
                    return cur;
                } else {
                    r = r - leftSize - 1;
                    cur = cur.rchild;
                }
            }

            return root;
        }

        // rotate right around y
        private void rotateRight(Node y) {
            Node x = y.lchild;
            Node beta = x.rchild;
            Node p = y.parent;

            x.parent = p;

            if (p == null) {
                root = x;
            } else if (p.lchild == y) {
                p.lchild = x;
            } else {
                p.rchild = x;
            }

            x.rchild = y;
            y.parent = x;

            y.lchild = beta;

            if (beta != null) {
                beta.parent = y;
            }

            updateSize(y);
            updateSize(x);
        }

        // rotate left around x
        private void rotateLeft(Node x) {
            Node y = x.rchild;
            Node beta = y.lchild;
            Node p = x.parent;

            y.parent = p;

            if (p == null) {
                root = y;
            } else if (p.lchild == x) {
                p.lchild = y;
            } else {
                p.rchild = y;
            }

            y.lchild = x;
            x.parent = y;

            x.rchild = beta;

            if (beta != null) {
                beta.parent = x;
            }

            updateSize(x);
            updateSize(y);
        }

        // randomly select a node and rotate it to root
        private void reroot() {
            if (root == null) {
                return;
            }

            Node chosen = randomNode();

            while (chosen.parent != null) {
                Node p = chosen.parent;

                if (p.lchild == chosen) {
                    rotateRight(p);
                } else {
                    rotateLeft(p);
                }
            }
        }

        // convert the RBST map into a list of wordcount pairs
        public FnList<FnTupl2<FnList<Character>, Integer>> toPairList() {
            return inorder(root, new FnList<FnTupl2<FnList<Character>, Integer>>());
        }

        // inorder traversal that builds a FnList of pairs
        private FnList<FnTupl2<FnList<Character>, Integer>>
        inorder(
            Node n,
            FnList<FnTupl2<FnList<Character>, Integer>> acc
        ) {
            if (n == null) {
                return acc;
            }

            acc = inorder(n.rchild, acc);

            FnTupl2<FnList<Character>, Integer> pair =
                new FnTupl2<FnList<Character>, Integer>(
                    stringToWord(n.key),
                    n.value
                );

            acc =
                new FnList<FnTupl2<FnList<Character>, Integer>>(
                    pair,
                    acc
                );

            acc = inorder(n.lchild, acc);

            return acc;
        }
    }

    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$count$listize4()
	// In particular, please print out the first 100 word-count pairs, where
	// each line should contain only one word-count pair.
	    
		// generate the sorted wordcount list
        FnList<FnTupl2<FnList<Character>, Integer>> pairs =
            pg2701_word$count$listize4();

        // print the first 100 pairs
        int i = 0;

        while (i < 100 && !pairs.nilq()) {
            printPair(pairs.hd());
            pairs = pairs.tl();
            i += 1;
        }
    }
}

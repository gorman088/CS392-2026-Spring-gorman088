//
// HX-2026-04-28: 30 points
// (plus up to 20 bonus points)
// This is more of a theory problem
// than a programming one.
//
import Library00.LnStrm.*;
//
public class Quiz02_04 {
    public class AVLnode {
	int key;
	AVLnode lchild;
	AVLnode rchild;
    }
    //
    // HX: 10 points for this one
    // HX: If your implementation only
    // visit each node in [avl] at most once,
    // then it will be rewarded with some bonus
    // points (up to 20 bonus points).
    // For instance, if you compute the size of
    // height of a tree, then you have already
    // visited each node once.
    //
    public static boolean isAVL (AVLnode avl) {
	// HX: Please implement a function that
	// tests whether a given AVLnode is a valid
	// AVL tree. If it is unclear what an
	// AVL tree, you can readily find it on-line
	// Note that you are not asked to check if avl is
	// a binary search tree in this case.
        return heightOrBad(avl) != -1;
    }

    // helper
    private static int heightOrBad(AVLnode node) {
        // empty tree has height 0 and is AVL.
        if (node == null) {
            return 0;
        }

        // Check the left subtree and get its height
        int lh = heightOrBad(node.lchild);
        // if left subtree is not AVL then stop early
        if (lh == -1) {
            return -1;
        }

        // Check the right subtree and get its height
        int rh = heightOrBad(node.rchild);
        // If the right subtree is not AVL stop early
        if (rh == -1) {
            return -1;
        }

        // AVL rule left and right heights can differ by at most 1
        if (Math.abs(lh - rh) > 1) {
            return -1;
        }

        // return this subtrees height if it is valid
        return 1 + Math.max(lh, rh);
    }
    //
    // HX: 20 points
    // This is largely about understanding AVL trees.
    // Please explain BRIEFLY as to why the generated AVL is
    // of maximal height (not minimal height). Note that this
    // is different from what is asked in Quiz02_05.
    //
    public static boolean genAVLBST() {
	// Please genenerate a binary search RBT that
	// contains exactly 1 million keys: 0, 1, 2, ..., 999999
	// such that the height of this tree is maximal (that is,
	// as large as possible). What is this height? Please give
	// a brief explanation on your implementation strategy.
        int n = 1000000;

        int[] minNodes = new int[50];

        minNodes[0] = 0; // empty tree has height 0
        minNodes[1] = 1; // one node has height 1

        for (int h = 2; h < minNodes.length; h += 1) {
            minNodes[h] = 1 + minNodes[h - 1] + minNodes[h - 2];
        }

        int h = 0;

        while (h + 1 < minNodes.length && minNodes[h + 1] <= n) {
            h += 1;
        }

        System.out.println("Maximal AVL height for 1m nodes is " + h);

        return true;
    }
    public static void main (String[] args) {
	// Please add minimal testing code for isRBT()
	// Please add minimal testing code for genAVLBST()
	    System.out.println("Testing genAVLBST:");
        genAVLBST();

        System.out.println("Testing isAVL on empty tree:");
        System.out.println(isAVL(null)); // true
    }
}

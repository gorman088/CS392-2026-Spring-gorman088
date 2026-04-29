//
// HX-2026-04-28: 50 points
//
/*
A description on Game-of-24 and an accompanying
demo can be found by visiting the following link:
https://github.com/githwxi/XATSHOME/tree/main/contrib/githwxi/pground/proj002%40250507/misc004
Please give a high-level description in English as to
how Game-of-24 can be solved using either DFS or BFS.
Your description should be given in a README file for
this assignment.
1. Please give a DFS-based implementation according to your
   description that should directly use the DFirstEnumerate method.
2. Please give a BFS-based implementation according to your
   description that should directly use the BFirstEnumerate method.
*/
//
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;
import MyLibrary.FnList.*;

class UnsupportedOpr
    extends RuntimeException {
    String opr;
    public UnsupportedOpr(String opr) {
	this.opr = opr;
    }
}

abstract class Term {
    public String tag = "Term";
    public abstract double eval();
    // eval() returns the value of the term
}

class TermInt extends Term {
    public int val;
    public TermInt(int val) {
	this.tag = "TermInt"; this.val = val;
    }
    public double eval() { return val; }
}

class TermOpr extends Term {
    public String opr;
    public Term arg1, arg2;
    public TermOpr(String opr0, Term arg1, Term arg2) {
	this.tag = "TermOpr";
	this.opr = opr0; this.arg1 = arg1; this.arg2 = arg2;
    }
    public double eval() {
	switch (opr) {
	  case "+":
	      return arg1.eval() + arg2.eval();
	  case "-":
	      return arg1.eval() - arg2.eval();
	  case "*":
	      return arg1.eval() * arg2.eval();
	  case "/":
	      return arg1.eval() / arg2.eval();
	}
	throw new UnsupportedOpr(     opr     );
    }
}

public class Quiz02_03 {
    // used for comparing doubles because division can create errrors
    static final double EPS = 0.000001;

    // state stores the terms that are still available in the game
    static class State {
        Term[] terms;

        State(Term[] terms) {
            this.terms = terms;
        }
    }

    public LnStrm<Term> GameOf24_bfs_solve
        (int n1, int n2, int n3, int n4) {

        // make the starting state with the four input numbers
        State start = startState(n1, n2, n3, n4);

        // build the full game tree starting from this state
        FnGtree<State> root = makeTree(start);

        // enumerate the tree using BFS then keep only the solutions
        return getSolutions(FnGtreeSUtil.BFirstEnumerate(root));
    }

    public LnStrm<Term> GameOf24_dfs_solve
        (int n1, int n2, int n3, int n4) {

        // make the starting state with the four input numbers
        State start = startState(n1, n2, n3, n4);

        // build the full game tree starting from this state
        FnGtree<State> root = makeTree(start);

        // enumerate the tree using DFS then keep only the solutions
        return getSolutions(FnGtreeSUtil.DFirstEnumerate(root));
    }

    static State startState(int n1, int n2, int n3, int n4) {
        // convert the four integers into TermInt objects
        Term[] terms = {
            new TermInt(n1),
            new TermInt(n2),
            new TermInt(n3),
            new TermInt(n4)
        };

        return new State(terms);
    }

    static FnGtree<State> makeTree(State st) {
        // create a tree node whose value is st
        // its children are all possible next states
        return new FnGtree<State>() {
            public State value() {
                return st;
            }

            public FnList<FnGtree<State>> children() {
                return makeChildren(st);
            }
        };
    }

    static FnList<FnGtree<State>> makeChildren(State st) {
        // this list stores all possible children of the current state
        FnList<FnGtree<State>> children = new FnList<FnGtree<State>>();

        // if only one term remainsthere are no more moves
        if (st.terms.length == 1) {
            return children;
        }

        // pick every pair of terms
        for (int i = 0; i < st.terms.length; i += 1) {
            for (int j = i + 1; j < st.terms.length; j += 1) {
                Term a = st.terms[i];
                Term b = st.terms[j];

                // try all possible operations on the pair
                children = add(children, st, i, j, new TermOpr("+", a, b));
                children = add(children, st, i, j, new TermOpr("-", a, b));
                children = add(children, st, i, j, new TermOpr("-", b, a));
                children = add(children, st, i, j, new TermOpr("*", a, b));

                // only divide if the denominator is not zero
                if (Math.abs(b.eval()) > EPS) {
                    children = add(children, st, i, j, new TermOpr("/", a, b));
                }

                // also try the division in the other order
                if (Math.abs(a.eval()) > EPS) {
                    children = add(children, st, i, j, new TermOpr("/", b, a));
                }
            }
        }

        return children;
    }

    static FnList<FnGtree<State>> add
        (FnList<FnGtree<State>> children, State st, int i, int j, Term t) {

        // the next state has one fewer term because two terms become one
        Term[] next = new Term[st.terms.length - 1];

        // put the newly created term first
        next[0] = t;

        int k = 1;

        // copy over all terms except the two that were combined
        for (int p = 0; p < st.terms.length; p += 1) {
            if (p != i && p != j) {
                next[k] = st.terms[p];
                k += 1;
            }
        }

        // add the new state as a child tree
        return new FnList<FnGtree<State>>(
            makeTree(new State(next)),
            children
        );
    }

    static LnStrm<Term> getSolutions(LnStrm<State> states) {
        // lazily scan through all states and return only the winning terms
        return new LnStrm<Term>(() -> {
            LnStrm<State> rest = states;

            while (true) {
                LnStcn<State> cur = rest.eval0();

                // no more states means no more solutions
                if (cur.nilq()) {
                    return new LnStcn<Term>();
                }

                State st = cur.hd();
                rest = cur.tl();

                //solution has one term left and that term evaluates to 24
                if (st.terms.length == 1 &&
                    Math.abs(st.terms[0].eval() - 24.0) < EPS) {

                    return new LnStcn<Term>(
                        st.terms[0],
                        getSolutions(rest)
                    );
                }
            }
        });
    }

    public static void main(String[] args) {
        Quiz02_03 q = new Quiz02_03();

        // test BFS on a known good input (10 * 10 - 4) / 4 = 24.
        LnStcn<Term> bfs =
            q.GameOf24_bfs_solve(10, 10, 4, 4).eval0();

        if (!bfs.nilq()) {
            System.out.println("BFS found: " + bfs.hd().eval());
        }

        // test DFS on another known good input (5 - 11 / 7) * 7 = 24.
        LnStcn<Term> dfs =
            q.GameOf24_dfs_solve(5, 7, 7, 11).eval0();

        if (!dfs.nilq()) {
            System.out.println("DFS found: " + dfs.hd().eval());
        }
    }
}
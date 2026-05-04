//
// HX-2026-04-21: 50 points
//
// Please see lectures/lecture-04-21 for an
// example using DFirstEnumerate/BFirstEnumerate
//
// Some "hard" Sudoku puzzles can be
// found here: https://sudoku.com/hard/.
// You are asked to use DFirstEnumerate and BFirstEnumerate
// in FnGtree to solve Sudoku puzzles. Your solution should
// be able to solve "hard" Sudoku puzzles effectively.
//
import MyLibrary.FnList.*;
import MyLibrary.LnStrm.*;
import MyLibrary.FnGtree.*;

class Sudoku {
    // Please find a way to represent a Sudoku puzzle
    int[] board; // 1D array for 81 cells 0 means blank

    // creates a Sudoku board from an 81 character string
    Sudoku(String s) {
        board = new int[81];

        for (int i = 0; i < 81; i += 1) {
            char ch = s.charAt(i);

            // dots or zeroes represent empty cells
            if (ch == '.' || ch == '0') {
                board[i] = 0;
            } else {
                board[i] = ch - '0';
            }
        }
    }

    // creates a Sudoku board from an existing array
    Sudoku(int[] b) {
        board = b;
    }

    // checks if the board has no empty cells
    boolean solved() {
        for (int i = 0; i < 81; i += 1) {
            if (board[i] == 0) {
                return false;
            }
        }

        return true;
    }

    // finds the first blank cell on the board
    int firstBlank() {
        for (int i = 0; i < 81; i += 1) {
            if (board[i] == 0) {
                return i;
            }
        }

        return -1; // no blanks found
    }

    // checks if a value can legally be placed at a position
    boolean canPlace(int pos, int value) {
        int row = pos / 9;
        int col = pos % 9;

        // check the row
        for (int c = 0; c < 9; c += 1) {
            if (board[row * 9 + c] == value) {
                return false;
            }
        }

        // check the column
        for (int r = 0; r < 9; r += 1) {
            if (board[r * 9 + col] == value) {
                return false;
            }
        }

        // find the topleft corner of the 3x3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        // check the 3x3 box
        for (int r = boxRow; r < boxRow + 3; r += 1) {
            for (int c = boxCol; c < boxCol + 3; c += 1) {
                if (board[r * 9 + c] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    // returns a new Sudoku board with one value placed
    Sudoku place(int pos, int value) {
        int[] copy = board.clone(); // copy board so original is unchanged
        copy[pos] = value;
        return new Sudoku(copy);
    }

    // prints the Sudoku board
    public String toString() {
        String result = "";

        for (int i = 0; i < 81; i += 1) {
            result += board[i] + " ";

            // start a new line after every row
            if (i % 9 == 8) {
                result += "\n";
            }
        }

        return result;
    }
}

// represents the search tree for Sudoku states
class SudokuTree implements FnGtree<Sudoku> {
    Sudoku puzzle;

    // stores the Sudoku board for this tree node
    SudokuTree(Sudoku puzzle) {
        this.puzzle = puzzle;
    }

    // returns the Sudoku board at this tree node
    public Sudoku value() {
        return puzzle;
    }

    // generates all possible next boards from the current board
    public FnList<FnGtree<Sudoku>> children() {
        FnList<FnGtree<Sudoku>> list = new FnList<FnGtree<Sudoku>>();

        // find the next empty position
        int pos = puzzle.firstBlank();

        // if there are no blanks there are no children
        if (pos == -1) {
            return list;
        }

        // try placing each value from 1 to 9
        for (int value = 9; value >= 1; value -= 1) {
            if (puzzle.canPlace(pos, value)) {
                Sudoku next = puzzle.place(pos, value);

                // add this new board as a child node
                list = new FnList<FnGtree<Sudoku>>(new SudokuTree(next), list);
            }
        }

        return list;
    }
}


public class Quiz11_01 {
    public LnStrm<Sudoku> Soduku_dfs_solve(Sudoku puzzle) {
	    
        FnGtree<Sudoku> tree = new SudokuTree(puzzle);

        // enumerate the search tree with DFS then keep solved boards only
        return solvedOnly(FnGtreeSUtil.DFirstEnumerate(tree));
    }

    public LnStrm<Sudoku> Soduku_bfs_solve(Sudoku puzzle) {
	    
        FnGtree<Sudoku> tree = new SudokuTree(puzzle);

        // enumerate the search tree with BFS then keep solved boards only
        return solvedOnly(FnGtreeSUtil.BFirstEnumerate(tree));
    }

    // filters a stream so that only solved Sudoku boards remain
    static LnStrm<Sudoku> solvedOnly(LnStrm<Sudoku> stream) {
        return new LnStrm<Sudoku>(
            () -> {
                LnStrm<Sudoku> cur = stream;

                while (true) {
                    LnStcn<Sudoku> cell = cur.eval0();

                    // if the stream is empty then return an empty stream
                    if (cell.nilq()) {
                        return new LnStcn<Sudoku>();
                    }

                    // if this board is solved then keep it in the stream
                    if (cell.hd().solved()) {
                        return new LnStcn<Sudoku>(cell.hd(), solvedOnly(cell.tl()));
                    }

                    // otherwise move to the next board
                    cur = cell.tl();
                }
            }
        );
    }

    // gets the first Sudoku board from a stream
    static Sudoku first(LnStrm<Sudoku> stream) {
        LnStcn<Sudoku> cell = stream.eval0();

        if (cell.nilq()) {
            return null;
        }

        return cell.hd();
    }
//
    public static void main (String[] args) {
	// Please add minimal testing code for Sudoku_dfs_solve
	// Please add minimal testing code for Sudoku_bfs_solve
	    Quiz11_01 solver = new Quiz11_01();

        // Example Sudoku puzzle
        // Dots represent blank cells
        Sudoku puzzle = new Sudoku(
            "53..7...." +
            "6..195..." +
            ".98....6." +
            "8...6...3" +
            "4..8.3..1" +
            "7...2...6" +
            ".6....28." +
            "...419..5" +
            "....8..79"
        );

        System.out.println("Original:");
        System.out.println(puzzle);

        System.out.println("DFS solution:");
        System.out.println(first(solver.Soduku_dfs_solve(puzzle)));

        System.out.println("BFS solution:");
        System.out.println(first(solver.Soduku_bfs_solve(puzzle)));
    }
//
}

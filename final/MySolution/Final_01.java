/*
// HX: 20 points for Final_01
// A word consists of a sequence of
// letters ([a-z]+[A-Z]) plus aprostrophe (')
// And words are separated by non-letters-aprostrophe
// (such as blanks, punctuations, etc.) in pg2701.txt.
*/


import MyLibrary.FnList.*;
import MyLibrary.LnStrm.*;


public class Final_01 {
    static LnStrm<FnList<Character>> pg2701_word$strmize() {
	// HX-2026-05-04:
	// Please construct a stream of words contained in the
	// file Data/pg2701.txt
	// Note that a word is represented as a list of characters
	// in the English alphabet plus aprostrophe (')
	// Also every upper case letter in the original text should
	// be turned into its corresponding lower case.
	// This stream should be built on top of pg2701_char$strmize
	// which is already implemented in Final_00.
	// In particular, you should NOT use Java library functions
	// for processing files!
		return wordsFrom(Final_00.pg2701_char$strmize());
    }

	// converts a stream of characters into a stream of words
    private static LnStrm<FnList<Character>> wordsFrom(LnStrm<Character> cs) {
        return new LnStrm<FnList<Character>>(
            () -> {
                // skip characters that are not part of a word
                LnStrm<Character> cs1 = skipNonWordChars(cs);

                // evaluate the stream after skipping nonword chars
                LnStcn<Character> cxs = cs1.eval0();

                // if the stream is empty return an empty word stream
                if (cxs.nilq()) {
                    return new LnStcn<FnList<Character>>();
                }

                // collect one complete word starting from this word character
                WordResult res =
                    collectWord(cxs.hd(), cxs.tl(), new FnList<Character>());

                // return the collected word and lazily continue with the rest
                return new LnStcn<FnList<Character>>(
                    res.word,
                    wordsFrom(res.rest)
                );
            }
        );
    }

	// skips all nonword chars until a word character is found
    private static LnStrm<Character> skipNonWordChars(LnStrm<Character> cs) {
        return new LnStrm<Character>(
            () -> {
                // evaluate the current character stream
                LnStcn<Character> cxs = cs.eval0();

                // if empty return  empty stream
                if (cxs.nilq()) {
                    return new LnStcn<Character>();
                }

                // current char
                Character ch = cxs.hd();

                // if it is a valid word character stop skipping
                if (isWordChar(ch)) {
                    return new LnStcn<Character>(ch, cxs.tl());
                }

                // keep skipping
                return skipNonWordChars(cxs.tl()).eval0();
            }
        );
    }

	// collects one full word from the character stream
    private static WordResult collectWord(
        Character ch,
        LnStrm<Character> rest,
        FnList<Character> acc
    ) {
        // add lowercase version of the current char word
        acc = new FnList<Character>(toLower(ch), acc);

        // evaluate the rest of the stream
        LnStcn<Character> cxs = rest.eval0();

        // if the stream ends return the completed word
        if (cxs.nilq()) {
            return new WordResult(acc.reverse(), rest);
        }

        // look at next character
        Character next = cxs.hd();

        // if the next character is not part of a word the word is complete
        if (!isWordChar(next)) {
            return new WordResult(acc.reverse(), cxs.tl());
        }

        // otherwise keep collecting characters into the current word
        return collectWord(next, cxs.tl(), acc);
    }

    // a word character is aletter or an apostrophe
    private static boolean isWordChar(Character ch) {
        return isLower(ch) || isUpper(ch) || isApostrophe(ch);
    }

    // check for lowercase letters
    private static boolean isLower(Character ch) {
        return ch >= 'a' && ch <= 'z';
    }

    // check for uppercase letters
    private static boolean isUpper(Character ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    // check for apostrophes
    private static boolean isApostrophe(Character ch) {
        return ch == '\'' || ch == '\u2019' || ch == '\u2018';
    }

    // conver uppercase letters to lowercase
    private static Character toLower(Character ch) {
        if (isUpper(ch)) {
            return (char)(ch - 'A' + 'a');
        }

        return ch;
    }

    // helper for returning both a word and remaining character stream
    private static class WordResult {
        FnList<Character> word;
        LnStrm<Character> rest;

        WordResult(FnList<Character> word, LnStrm<Character> rest) {
            this.word = word;
            this.rest = rest;
        }
    }



    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$strmize()
	    
        LnStrm<FnList<Character>> wxs = pg2701_word$strmize();

        // prints first 40 words
        int i = 0;

        while (i < 40) {
            // evaluate current word stream
            LnStcn<FnList<Character>> node = wxs.eval0();

            if (node.nilq()) {
                break;
            }

            FnList<Character> word = node.hd();

            word.System$out$print();
            System.out.println();

            wxs = node.tl();

            i += 1;
        }
    }

}

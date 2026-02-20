package Library00.FnStrn;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FnStrn {

    // String root;
    char root[];

    public FnStrn() {
	root = null;
    }
    public FnStrn(char[] cs) {
	// root = cs;
	int ln = cs.length;
	root = new char[ln];
	for (int i = 0; i < ln; i += 1) {
	    root[i] = cs[i];
	}
    }
    public FnStrn(String cs) {
	int ln = cs.length();
	root = new char[ln];
	for (int i = 0; i < ln; i += 1) {
	    root[i] = cs.charAt(i);
	}
    }

    public int length() {
	return root.length;
    }

    public char getAt(int index) {
	return root[index];
    }

	public boolean forall(Predicate<Character> pred) {
        for (int i = 0; i < root.length; i++) {
            if (!pred.test(root[i])) return false;
        }
        return true;
    }

	@Override
	public String toString() {
    	String s = "";
    	for (int i = 0; i < root.length; i++)
        	s += root[i];
    	return s;
	}

	public void foritm(Consumer<Character> work) {
    	for (int i = 0; i < root.length; i++) {
        	work.accept(root[i]);
    	}
	}
} // end of [public class FnStrn{...}]

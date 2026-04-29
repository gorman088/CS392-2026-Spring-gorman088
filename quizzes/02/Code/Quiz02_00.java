//
// HX: 20 points
//
/*
import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;
// Add more imports as you see fit
*/
import MyLibrary.FnList.*;
import MyLibrary.FnA1sz.*;
import MyLibrary.FnGtree.*;
import MyLibrary.FnStrn.*;
import MyLibrary.FnTuple.*;
import MyLibrary.LnList.*;
import MyLibrary.LnStrm.*;
import MyLibrary.MyQueue.*;
import MyLibrary.MyRefer.*;
import MyLibrary.MyStack.*;
public class Quiz02_00 {

/*
    MyLibrary Description

    FnList<T>:
        A functional list class.

    FnA1sz<T>:
        A fixed-size array wrapper. It stores an array and provides methods
        such as length(), getAt(), and iteration methods.

    FnGtree<T>:
        An interface for a generic tree. A tree has a value and a list of children.

    FnStrn:
        A simple string class that stores characters in a char array.

    FnTupl2<T0,T1>:
        A tuple class that stores two values.

    FnTupl3<T0,T1,T2>:
        A tuple class that stores three values.

    LnList<T>:
        A linked-list class.

    LnStrm<T>:
        A lazy stream class.

    MyRefer<T>:
        A mutable reference class that stores one value.

    MyQueueArray<T>:
        An array-based queue.

    MyQueueList<T>:
        A linked-list-based queue.

    MyStackArray<T>:
        An array-based stack.

    MyStackList<T>:
        A linked-list-based stack.

    MyLibrary also contains some interfaces and abstract base classes such as
    MyQueue, MyQueueBase, MyStack, MyStackBase, and MyMap00. 
*/
     


    public static void main (String[] args) {
	    // FnList object
        FnList<Integer> FnList_Integer_obj =
            new FnList<Integer>();

        // FnA1sz object
        Integer[] nums = new Integer[] {1, 2, 3};

        FnA1sz<Integer> FnA1sz_Integer_obj =
            new FnA1sz<Integer>(nums);

        // FnStrn objects
        FnStrn FnStrn_String_obj =
            new FnStrn("hello");

        char[] chars = new char[] {'a', 'b', 'c'};

        FnStrn FnStrn_CharArray_obj =
            new FnStrn(chars);

        // Tuple objects
        FnTupl2<String, Integer> FnTupl2_String_Integer_obj =
            new FnTupl2<String, Integer>("age", 20);

        FnTupl3<String, Integer, Double> FnTupl3_String_Integer_Double_obj =
            new FnTupl3<String, Integer, Double>("score", 95, 95.5);

        // LnList objects
        LnList<Integer> LnList_Integer_obj =
            new LnList<Integer>();

        LnList<Integer> LnList_Integer_from_array_obj =
            new LnList<Integer>(FnA1sz_Integer_obj);

        LnList<Integer> LnList_Integer_cons_obj =
            new LnList<Integer>(0, LnList_Integer_from_array_obj);

        // LnStrm objects
        LnStrm<Integer> LnStrm_Integer_empty_obj =
            new LnStrm<Integer>();

        LnStrm<Integer> LnStrm_Integer_one_obj =
            new LnStrm<Integer>(10);

        // MyRefer object
        MyRefer<String> MyRefer_String_obj =
            new MyRefer<String>("stored value");

        // Queue objects
        MyQueueArray<Integer> MyQueueArray_Integer_obj =
            new MyQueueArray<Integer>(10);

        MyQueueList<Integer> MyQueueList_Integer_obj =
            new MyQueueList<Integer>();

        // Stack objects
        MyStackArray<Integer> MyStackArray_Integer_obj =
            new MyStackArray<Integer>(10);

        MyStackList<Integer> MyStackList_Integer_obj =
            new MyStackList<Integer>();

        MyQueueArray_Integer_obj.enque$raw(1);
        MyQueueArray_Integer_obj.enque$raw(2);

        MyQueueList_Integer_obj.enque$raw(3);
        MyQueueList_Integer_obj.enque$raw(4);

        MyStackArray_Integer_obj.push$raw(5);
        MyStackArray_Integer_obj.push$raw(6);

        MyStackList_Integer_obj.push$raw(7);
        MyStackList_Integer_obj.push$raw(8);

        System.out.println("object creation completed.");

        System.out.print("FnA1sz object: ");
        FnA1sz_Integer_obj.System$out$print();
        System.out.println();

        System.out.print("FnTupl2 object: ");
        FnTupl2_String_Integer_obj.System$out$print();
        System.out.println();

        System.out.print("FnTupl3 object: ");
        FnTupl3_String_Integer_Double_obj.System$out$print();
        System.out.println();

        System.out.print("LnList object: ");
        LnList_Integer_cons_obj.System$out$print1();
        System.out.println();

        System.out.print("MyQueueArray object: ");
        MyQueueArray_Integer_obj.System$out$print();
        System.out.println();

        System.out.print("MyQueueList object: ");
        MyQueueList_Integer_obj.System$out$print();
        System.out.println();

        System.out.print("MyStackArray object: ");
        MyStackArray_Integer_obj.System$out$print();
        System.out.println();

        System.out.print("MyStackList object: ");
        MyStackList_Integer_obj.System$out$print();
        System.out.println();

        return /*void*/;
    }
} // end of [class Quiz01_00{...}]

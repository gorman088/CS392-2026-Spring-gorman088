//
// HX: 10 points
//
/*
import MyLibrary.FnList.*;
// Add for imports as you see fit
*/
import Library00.FnA1sz.FnA1sz;
import Library00.FnList.*;
import Library00.FnStrn.*;
import Library00.MyStack.*;
import Library00.MyQueue.*;

public class Quiz01_00 {
    /*
     Please give a description of your MyLibrary
     What classes have you implemented? For each class
     you have implemented in MyLibrary, please create an
     object of that class as follows:
     */
    public static void main (String[] args) {
	// For instance, 
	// FnList<Integer> FnList_Integer_obj = new FnList<Integer>();
        FnList<Integer> FnList_Integer_obj = new FnList<Integer>();

        Integer[] arr = new Integer[] { 1, 2, 3 };
        FnA1sz<Integer> FnA1sz_Integer_obj = new FnA1sz<Integer>(arr);

        FnStrn FnStrn_obj = new FnStrn("hello");

        MyStackArray<Integer> MyStackArray_Integer_obj = new MyStackArray<Integer>(5);

        MyQueueArray<Integer> MyQueueArray_Integer_obj = new MyQueueArray<Integer>(5);

        // (Optional) Print something so it's clear at runtime too
        System.out.println("Created:");
        System.out.println("  " + FnList_Integer_obj.getClass().getName());
        System.out.println("  " + FnA1sz_Integer_obj.getClass().getName());
        System.out.println("  " + FnStrn_obj.getClass().getName());
        System.out.println("  " + MyStackArray_Integer_obj.getClass().getName());
        System.out.println("  " + MyQueueArray_Integer_obj.getClass().getName());
    }
}

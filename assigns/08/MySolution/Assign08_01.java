import java.util.function.BiConsumer;

import Library00.FnList.*;
import Library00.LnList.*;
import Library00.LnStrm.LnStrm;
import Library00.FnTuple.*;
import Library00.MyMap00.*;

public class Assign08_01<V>
    implements MyMap00<String, V> {
    // HX-2026-04-01:
    // Please give an implementation of hash table
    // that uses separate chaining for handling collisions.
    private LnList<FnTupl2<String, V>> table[];

    // number of items in the map
    private int size;

        // constructor
    public Assign08_01() {
        // make 101 buckets
        table = (LnList<FnTupl2<String, V>>[]) new LnList[101];

        // start each bucket as an empty list
        for (int i = 0; i < table.length; i = i + 1) {
            table[i] = new LnList<FnTupl2<String, V>>();
        }

        size = 0;
    }

    // compute which bucket a key belongs in
    private int hash(String key) {
        int h = key.hashCode() % table.length;
        if (h < 0) {
            h = h + table.length;
        }
        return h;
    }

    // return number of stored pairs
    public int size() {
        return size;
    }

    // separate chaining does not really get full here
    public boolean isFull() {
        return false;
    }

    // check if there are no stored pairs
    public boolean isEmpty() {
        return size == 0;
    }

    // reurn all key/value pairs into a stream
    public LnStrm<FnTupl2<String, V>> keyval_strmize() {
        return streamFrom(0, new LnList<FnTupl2<String, V>>());
    }


    // assume key is present
    public V search$old(String key) {
        return search$opt(key);
    }

    // returns null if missing
    public V search$exn(String key) {
        return search$opt(key);
    }

    // search for a key in its bucket
    public V search$opt(String key) {
        LnList<FnTupl2<String, V>> xs = table[hash(key)];

        while (xs.consq1()) {
            FnTupl2<String, V> p = xs.hd1();

            // If keys match return the value
            if (p.sub0.equals(key)) {
                return p.sub1;
            }

            xs = xs.tl1();
        }

        // key not found
        return null;
    }

    // insert a new key/value pair
    // or replace old value if key already exists
    public V insert$opt(String key, V val) {
        int i = hash(key);
        LnList<FnTupl2<String, V>> xs = table[i];

        // first check if key is already in the bucket
        while (xs.consq1()) {
            FnTupl2<String, V> p = xs.hd1();

            if (p.sub0.equals(key)) {
                V old = p.sub1;
                p.sub1 = val;
                return old;
            }

            xs = xs.tl1();
        }

        // if key was not found add new pair at front of bucket
        table[i] = new LnList<FnTupl2<String, V>>(
            new FnTupl2<String, V>(key, val),
            table[i]
        );
        size = size + 1;
        return null;
    }

    // insert assuming key is not already there
    public void insert$new(String key, V val) {
        int i = hash(key);

        table[i] = new LnList<FnTupl2<String, V>>(
            new FnTupl2<String, V>(key, val),
            table[i]
        );

        size = size + 1;
    }

    // assume key is present
    public V remove$old(String key) {
        return remove$opt(key);
    }

    // returns null if missing
    public V remove$exn(String key) {
        return remove$opt(key);
    }

    // eemove a key/value pair from its bucket
    public V remove$opt(String key) {
        int i = hash(key);

        // xs walks through old bucket
        LnList<FnTupl2<String, V>> xs = table[i];

        // ys stores kept items in reverse order
        LnList<FnTupl2<String, V>> ys = new LnList<FnTupl2<String, V>>();

        V old = null;

        while (xs.consq1()) {
            FnTupl2<String, V> p = xs.hd1();

            // remove the first matching key
            if (old == null && p.sub0.equals(key)) {
                old = p.sub1;
            } else {
                ys = new LnList<FnTupl2<String, V>>(p, ys);
            }

            xs = xs.tl1();
        }

        // if we removed something restore bucket order
        if (old != null) {
            table[i] = ys.reverse0();
            size = size - 1;
        }

        return old;
    }

    // apply work to every key value pair in the table
    public void foritm(BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < table.length; i = i + 1) {
            LnList<FnTupl2<String, V>> xs = table[i];

            while (xs.consq1()) {
                FnTupl2<String, V> p = xs.hd1();
                work.accept(p.sub0, p.sub1);
                xs = xs.tl1();
            }
        }
    }
}
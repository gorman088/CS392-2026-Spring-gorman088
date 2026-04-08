import java.util.function.BiConsumer;

import Library00.FnList.*;
import Library00.LnList.*;
import Library00.LnStrm.LnStcn;
import Library00.LnStrm.LnStrm;
import Library00.FnTuple.*;
import Library00.MyMap00.*;

public class Assign08_02<V>
    implements MyMap00<String, V> {
    // HX-2026-04-01:
    // Please give an implementation of hash table
    // based on open addressing. The probing strategy
    // chosen for handling collisions is quadratic probing.
    private FnTupl2<String, V> table[];

    // number of key/value pairs currently stored
    private int size;

    // constructor
    public Assign08_02() {
        // make a table with 101 slots
        table = (FnTupl2<String, V>[]) new FnTupl2[101];
        size = 0;
    }

    // compute the starting hash index for a key
    private int hash(String key) {
        int h = key.hashCode() % table.length;
        if (h < 0) {
            h = h + table.length;
        }
        return h;
    }

    // compute the jth quadratic probe position
    private int probe(String key, int j) {
        return (hash(key) + j * j) % table.length;
    }

    // return number of stored pairs
    public int size() {
        return size;
    }

    // tble is full if every slot is used
    public boolean isFull() {
        return size == table.length;
    }

    // table is empty if size is 0
    public boolean isEmpty() {
        return size == 0;
    }

    // build a stream of all non null entries in the table
    public LnStrm<FnTupl2<String, V>> keyval_strmize() {
        return streamFrom(0);
    }

    // helper for keyval_strmize
    private LnStrm<FnTupl2<String, V>> streamFrom(int i) {
        return new LnStrm<FnTupl2<String, V>>(() -> {
            int j = i;

            // skip over empty slots
            while (j < table.length && table[j] == null) {
                j = j + 1;
            }

            // if no more entries return empty stream cell
            if (j >= table.length) {
                return new LnStcn<FnTupl2<String, V>>();
            }

            // return current entry and continue from next slot
            return new LnStcn<FnTupl2<String, V>>(
                table[j],
                streamFrom(j + 1)
            );
        });
    }

    // assume key is present
    public V search$old(String key) {
        return search$opt(key);
    }

    // returns null if missing
    public V search$exn(String key) {
        return search$opt(key);
    }

    // search for a key using quadratic probing
    public V search$opt(String key) {
        for (int j = 0; j < table.length; j = j + 1) {
            int i = probe(key, j);

            // if we hit an empty slot key is not in the table
            if (table[i] == null) {
                return null;
            }

            // ff keys match return the value
            if (table[i].sub0.equals(key)) {
                return table[i].sub1;
            }
        }

        // key was not found
        return null;
    }

    // insert a new key/value pair, or replace old value if key already exists
    public V insert$opt(String key, V val) {
        // first look for the key
        for (int j = 0; j < table.length; j = j + 1) {
            int i = probe(key, j);

            // empty slot means insert here
            if (table[i] == null) {
                table[i] = new FnTupl2<String, V>(key, val);
                size = size + 1;
                return null;
            }

            // if key already exists replace old value
            if (table[i].sub0.equals(key)) {
                V old = table[i].sub1;
                table[i].sub1 = val;
                return old;
            }
        }

        // if table is full do nothing
        return null;
    }

    // insert assuming key is not already there
    public void insert$new(String key, V val) {
        for (int j = 0; j < table.length; j = j + 1) {
            int i = probe(key, j);

            // put new pair in the first empty slot found
            if (table[i] == null) {
                table[i] = new FnTupl2<String, V>(key, val);
                size = size + 1;
                return;
            }
        }

        // if table is full do nothing
        return;
    }

    // assume key is present
    public V remove$old(String key) {
        return remove$opt(key);
    }

    // returns null if missing
    public V remove$exn(String key) {
        return remove$opt(key);
    }

    // remove a key/value pair
    public V remove$opt(String key) {
        for (int j = 0; j < table.length; j = j + 1) {
            int i = probe(key, j);

            // if we hit an empty slot, key is not present
            if (table[i] == null) {
                return null;
            }

            // if key matches, remove it
            if (table[i].sub0.equals(key)) {
                V old = table[i].sub1;
                table[i] = null;
                size = size - 1;

                // rebuild the table so probing still works correctly
                rehashTable();

                return old;
            }
        }

        return null;
    }

    // reinsert all remaining entries into a fresh empty table
    private void rehashTable() {
        FnTupl2<String, V>[] oldTable = table;

        // make a new empty table of same size
        table = (FnTupl2<String, V>[]) new FnTupl2[101];

        // save current size, then rebuild it
        int oldSize = size;
        size = 0;

        // reinsert every non-null entry
        for (int i = 0; i < oldTable.length; i = i + 1) {
            if (oldTable[i] != null) {
                insert$new(oldTable[i].sub0, oldTable[i].sub1);
            }
        }

        // restore correct size value
        size = oldSize;
    }

    // apply work to every key/value pair in the table
    public void foritm(BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < table.length; i = i + 1) {
            if (table[i] != null) {
                work.accept(table[i].sub0, table[i].sub1);
            }
        }
    }

}

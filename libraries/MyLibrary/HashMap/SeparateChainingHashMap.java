package MyLibrary.HashMap;

/*
 * HashMap implemented using separate chaining.
 */
public class SeparateChainingHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry<K, V>[] table;
    private int size;

    public SeparateChainingHashMap() {
        table = (Entry<K, V>[]) new Entry[8];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean keyEquals(K a, K b) {
        return a == b || (a != null && a.equals(b));
    }

    private int index(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    public boolean containsKey(K key) {
        return findEntry(key) != null;
    }

    public V get(K key) {
        Entry<K, V> entry = findEntry(key);
        return entry == null ? null : entry.value;
    }

    private Entry<K, V> findEntry(K key) {
        int i = index(key);
        Entry<K, V> current = table[i];

        while (current != null) {
            if (keyEquals(current.key, key)) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("null keys not alowed");
        }

        if (size >= table.length * 2) {
            resize(table.length * 2);
        }

        int i = index(key);
        Entry<K, V> current = table[i];

        while (current != null) {
            if (keyEquals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        table[i] = new Entry<K, V>(key, value, table[i]);
        size++;
    }

    public V remove(K key) {
        int i = index(key);
        Entry<K, V> prev = null;
        Entry<K, V> current = table[i];

        while (current != null) {
            if (keyEquals(current.key, key)) {
                if (prev == null) {
                    table[i] = current.next;
                } else {
                    prev.next = current.next;
                }

                size--;
                return current.value;
            }

            prev = current;
            current = current.next;
        }

        return null;
    }

    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[newCapacity];

        int oldSize = size;
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> current = oldTable[i];

            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }

        size = oldSize;
    }
}

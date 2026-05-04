package MyLibrary.HashMap;

/*
 * HashMap implemented using open addressing with quadratic probing
 *
 * Probe formula: index = (hash(key) + i * i) % capacity
 *
 */
public class QuadraticProbingHashMap<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;
        boolean deleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.deleted = false;
        }
    }

    private Entry<K, V>[] table;
    private int size;

    public QuadraticProbingHashMap() {
        table = (Entry<K, V>[]) new Entry[11];
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

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % table.length;
    }

    public boolean containsKey(K key) {
        return findIndex(key) != -1;
    }

    public V get(K key) {
        int index = findIndex(key);
        return index == -1 ? null : table[index].value;
    }

    private int findIndex(K key) {
        int start = hash(key);

        for (int i = 0; i < table.length; i++) {
            int index = (start + i * i) % table.length;
            Entry<K, V> entry = table[index];

            if (entry == null) {
                return -1;
            }

            if (!entry.deleted && keyEquals(entry.key, key)) {
                return index;
            }
        }

        return -1;
    }

    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("null keys not allowed");
        }

        if ((size + 1) * 2 >= table.length) {
            resize(nextPrime(table.length * 2));
        }

        putInternal(key, value);
    }

    private void putInternal(K key, V value) {
        int start = hash(key);
        int firstDeleted = -1;

        for (int i = 0; i < table.length; i++) {
            int index = (start + i * i) % table.length;
            Entry<K, V> entry = table[index];

            if (entry == null) {
                int target = firstDeleted == -1 ? index : firstDeleted;
                table[target] = new Entry<K, V>(key, value);
                size++;
                return;
            }

            if (entry.deleted) {
                if (firstDeleted == -1) {
                    firstDeleted = index;
                }
            } else if (keyEquals(entry.key, key)) {
                entry.value = value;
                return;
            }
        }

        resize(nextPrime(table.length * 2));
        putInternal(key, value);
    }

    public V remove(K key) {
        int index = findIndex(key);

        if (index == -1) {
            return null;
        }

        table[index].deleted = true;
        size--;

        return table[index].value;
    }

    private void resize(int newCapacity) {
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[newCapacity];
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            Entry<K, V> entry = oldTable[i];

            if (entry != null && !entry.deleted) {
                putInternal(entry.key, entry.value);
            }
        }
    }

    private int nextPrime(int n) {
        while (!isPrime(n)) {
            n++;
        }

        return n;
    }

    private boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}

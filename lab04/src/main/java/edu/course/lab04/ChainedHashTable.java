package edu.course.lab04;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ChainedHashTable<K, V>{

    public class Entry<K, V>{
        int hash;
        K key;
        V value;
        Entry<K, V> next;

        Entry (int hash, K key, V value, Entry<K, V> next){
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int size;
    private Entry<K, V>[] bkts;
    private int default_c = 16;

    public ChainedHashTable() {
        bkts = new Entry[default_c];
        size = 0;
    }

    private int getIndex(K key, int length){
        if (key == null) return 0;
        return Math.abs(key.hashCode() % length);
    }


    public void put(K key, V value) {
        int index = getIndex(key, bkts.length);
        Entry<K, V> head = bkts[index];

        Entry<K, V> current = head;
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        int hash = (key == null) ? 0 : key.hashCode();
        Entry<K, V> newEntry = new Entry<>(hash, key, value, head);
        bkts[index] = newEntry;
        size++;

    }


    public V get(K key) {
        int index = getIndex(key, bkts.length);
        Entry<K, V> current = bkts[index];

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key, bkts.length);
        Entry<K, V> current = bkts[index];
        Entry<K, V> prev = null;

        while (current != null) {
            if (Objects.equals(current.key, key)) {
                if (prev == null) {
                    bkts[index] = current.next;
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


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }



}

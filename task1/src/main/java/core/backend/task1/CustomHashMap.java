package core.backend.task1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.backend.task1.CustomHashMapIterator.DEFAULT_INDEX;

public class CustomHashMap<K, V> implements Iterable<KeyValuePojo<K, V>> {

    private static final int BUCKET_CAPACITY = 64;
    private DoublyLinkedList<K, V>[] buckets;
    private final Object editLock = new Object();

    // For Shipping.
    public CustomHashMap() {
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        this.buckets = new DoublyLinkedList[BUCKET_CAPACITY];
        for (int i = 0; i < BUCKET_CAPACITY; i++) {
            this.buckets[i] = new DoublyLinkedList<>();
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % BUCKET_CAPACITY;
    }

    private Stream<DoublyLinkedList<K, V>> nonEmptyBucketsStream() {
        return Arrays.stream(this.buckets).filter(bucket -> !bucket.isEmpty());
    }

    int getNextNonEmptyBucketFromIndex(int index) {
        if (index >= BUCKET_CAPACITY)
            return DEFAULT_INDEX;
        int i = index;
        while (i < BUCKET_CAPACITY && this.buckets[i].isEmpty())
            i++;
        return i == BUCKET_CAPACITY ? DEFAULT_INDEX : i;
    }

    DoublyLinkedList<K, V> getListInBucket(int index) {
        return this.buckets[index];
    }

    public void put(K key, V value) {
        synchronized (editLock) {
            int index = hash(key);
            DoublyLinkedList<K, V> list = this.buckets[index];
            list.add(key, value);
        }
    }

    public List<V> values() {
        return nonEmptyBucketsStream().flatMap(DoublyLinkedList::getAllValues).collect(Collectors.toList());
    }

    @Override
    public Iterator<KeyValuePojo<K, V>> iterator() {
        return new CustomHashMapIterator<>(this, editLock);
    }
}

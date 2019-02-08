package impl;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.PriorityQueue;

/**
 * PriorityQueue.java
 *
 * Class to implement a priority queue using a (max) heap.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2016
 */

public class HeapPriorityQueue<E> extends Heap<E> implements PriorityQueue<E> {

    /**
     * Constructor. Initialize this pq to empty.
     * @param maxSize The capacity of this priority queue.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public HeapPriorityQueue(int maxSize, Comparator<E> compy) {
        internal = (E[]) new Object[maxSize];
        heapSize = 0;
        this.compy = compy;
        //assert isHeap();
    }

    /**
     * Constructor. Initialize this pq to the keys in the
     * given iterable. The number of keys in the iterable
     * collection is taken as the capacity of the pq.
     * @param items An iterable collection of keys taken as the
     * initial contents of the pq.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public HeapPriorityQueue(Iterable<E> items, Comparator<E> compy) {
        int size = 0;
        for (Iterator<E> it = items.iterator(); it.hasNext(); it.next()) size++;
        internal = (E[]) new Object[size];
        this.compy = compy;
        heapSize = 0;
        for (E item : items) {
           internal[heapSize] = item;
           heapSize++;
        }
        for (int i = heapSize - 1; i >= 0; i--)
            sinkKeyAt(i);
        //assert isHeap();
    }
   
    /**
     * Constructor. Initialize this pq to the keys in the
     * given iterable. The number of keys in the iterable
     * collection is taken as the capacity of the pq.
     * @param items An iterable collection of keys taken as the
     * initial contents of the pq.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public HeapPriorityQueue(E[] items, Comparator<E> compy) {
        internal = (E[]) new Object[items.length];
        this.compy = compy;
        heapSize = 0;
        for (E item : items) {
           internal[heapSize] = item;
           heapSize++;
        }
        for (int i = heapSize - 1; i >= 0; i--)
            sinkKeyAt(i);
        //assert isHeap();
    }

    /**
     * Constructor. Initialize this pq to the keys in the
     * given iterable. The number of keys in the iterable
     * collection is taken as the capacity of the pq.
     * @param items An iterable collection of keys taken as the
     * initial contents of the pq.
     * @param size The number of items, passed in to avoid an
     * extra iteration over the items to count them.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public HeapPriorityQueue(Iterable<E> items, int size, Comparator<E> compy) {
        internal = (E[]) new Object[size];
        this.compy = compy;
        heapSize = 0;
        for (E item : items) {
           internal[heapSize] = item;
           heapSize++;
        }
        for (int i = heapSize - 1; i >= 0; i--)
            sinkKeyAt(i);
        //assert isHeap();
    }
    
    /**
     * Is this pq empty?
     * It is if its heap size is zero.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * Is this pq full?
     * It is if its heap size is equal to the array's size.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() {
        return heapSize == internal.length;
    }

    /**
     * Insert a new item into this pq.
     * @param x The item to insert.
     */
    public void insert(E x) {
        if (isFull()) throw new FullContainerException();
         throw new UnsupportedOperationException();
        //assert isHeap();
    }

    /**
     * Return (but do not remove) the maximum element.
     * According to the (max-) heap property, the maximum element
     * should be at position 0.
     * @return The maximum element.
     */
    public E max() { return internal[0]; }


    /**
     * Return and remove the maximum element.
     * @return The maximum element.
     */
    public E extractMax() {


         throw new UnsupportedOperationException();
    }

    /**
     * Determine whether this key is in the pq.
     * @param key The key to look for.
     * @return True if this key is in the pq, false otherwise.
     */
    public boolean contains(E key) {
        int pos = findKey(key);
        return pos != -1 && pos < heapSize;
    }

    /**
     * Indicate that the priority of a key at a given key
     * has changed, which may affect the internal storage
     * of the pq.
     * @param key The key whose priority has changed.
     */
    public void increaseKey(E key) {
        int i = findKey(key);

        if (i == -1)
            throw new NoSuchElementException();
        else 
            raiseKeyAt(i);

    }

    /**
     * Find the location of a given key. 
     * This is the bottleneck in PQ performance, since it reverts
     * to linear search. OptimizedHeapPriorityQueue should override this.
     * @param key The key whose location to find.
     * @return The location of that key or -1 if nowhere
     */
    protected int findKey(E key) {
        int i = -1;
        for (int j = 0; i == -1 && j < heapSize; j++) 
            if (internal[j].equals(key)) i = j;
        return i;
    }

}

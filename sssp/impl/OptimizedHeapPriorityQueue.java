package impl;

import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.PriorityQueue;

/**
 * OptimizedHeapPriorityQueue.java
 *
 * Class to implement a priority queue using a (max) heap
 * optimized for elements that know where they are in
 * the underlying array.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 */
public class OptimizedHeapPriorityQueue<E extends HeapPositionAware> extends HeapPriorityQueue<E> {

    
    /**
     * Constructor. Initialize this pq to empty.
     * @param maxSize The capacity of this priority queue.
     * @param compy The Comparator defining the priority of
     * these items.
     */
    @SuppressWarnings("unchecked")
    public OptimizedHeapPriorityQueue(int maxSize, Comparator<E> compy) {
        super(0, compy);
        internal = (E[]) new HeapPositionAware[maxSize];
        heapSize = 0;
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
    public OptimizedHeapPriorityQueue(E[] items, Comparator<E> compy) {
        super(items.length, compy);
        internal = (E[]) new HeapPositionAware[items.length];
        for (E item : items) {
            set(heapSize, item);
            heapSize++;
        }
        for (int i = heapSize - 1; i >= 0; i--)
            sinkKeyAt(i);
    }

    /**
     * Interchange the values at two locations. This method should be
     * used instead of direct changes to the internal array.
     * This method is to be overridden by OptimizedPriorityQueue.
     */
    @Override
    protected void swap(int i, int j) {
        assert 0 <= i && i < heapSize && 0 <= j && j < heapSize;
        E temp = ((E[]) internal)[i];
        set(i, internal[j]);
        set(j, temp);
    }
    
    
    /**
     * Set the value at a position in the underlying array.
     * This also informs the value itself where it is in the
     * array.
     * @param i
     * @param item
     */
    private void set(int i, E item) {
        internal[i] = item;
        item.setPosition(i);
    }
  
    /**
     * Find the location of a given key. 
     * Since the keys know their position, we can ask them.
     * @param key The key whose location to find.
     * @return The location of that key or -1 if nowhere
     */
    @Override
    protected int findKey(E key) {
        return key.getPosition();
    }


}

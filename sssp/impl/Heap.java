package impl;

import java.util.Comparator;

/**
 * Heap.java
 * 
 * Abstract class to provide the basic functionality of a heap, to
 * be used, for example, in heapsort or in a priority queue.
 *
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College   
 * Originally for CSCI 245, Spring 2007
 * Revised June 2, 2015 and Jan 2, 2018
 */

public abstract class Heap<E> {

    /**
     * The array containing the internal data of the heap.
     */
    protected E[] internal;

    /**
     * The portion of the array currently used to store the heap.
     */
    protected int heapSize;

    /**
     * Comparator to determine the priority of keys.
     */
    protected Comparator<E> compy;

    
    // A valid state for this heap is specified by this invariant:
    // heapSize <= internal.length 
    // && for all i in [0, heapSize), 
    //      if left(i) < heapSize, then internal[i] <= internal[left(i)]
    //      and if right(i) < heapSize, then internal[i] <= internal[right(i)],
    //      according to the comparator compy.
    
    
    /**
     * Find the index of the parent of the node at a given index.
     * @param i The index whose parent we want.
     * @return The index of the parent.
     */
    protected int parent(int i) { return (i - 1) / 2; }

    /**
     * Find the index of the left child of the node at a given index.
     * @param i The index whose left child we want.
     * @return The index of the left child.
     */
    protected int left(int i ) { return 2 * i + 1; }

    /**
     * Find the index of the right child of the node at a given index.
     * @param i The index whose right child we want.
     * @return The index of the right child.
     */
    protected int right(int i) { return 2 * i + 2; }

    /**
     * Interchange the values at two locations. This method should be
     * used instead of direct changes to the internal array.
     * This method is to be overridden by OptimizedPriorityQueue.
     */
    protected void swap(int i, int j) {
        assert 0 <= i && i < heapSize && 0 <= j && j < heapSize;
        E temp = internal[i];
        internal[i] = internal[j];
        internal[j] = temp;
    }
    
    /** 
     * Enforce the heap property on the subtree rooted at the given index.
     * To be used when initially building a heap and when fixing up
     * a heap after an item has been removed.
     * @param i The index where we want to make a heap.
     * PRECONDITION: The subtrees rooted at the left and right
     * children of i are already heaps.
     * POSTCONDITION: The subtree rooted at i is a heap.
     */
    protected void sinkKeyAt(int i) {
        assert isHeapBut(i);
         throw new UnsupportedOperationException();
     }
    
    /** 
     * Correct a heap in which one value is larger than its ancestors.
     * To be used with insert() and increaseKey().
     * @param i The index of the value that may be larger than its ancestors.
     * PRECONDITION: The heap property holds for all values in internal before heapSize,
     * except that i may be larger than its ancestors.
     * POSTCONDITION: internal bounded by heapSize is heap. (That is to say: the heap 
     * property holds for all values in internal before heapSize).
     * 
     */
    protected void raiseKeyAt(int i) {
        //assert i == 0 || isHeapBut(parent(i));
        
         throw new UnsupportedOperationException();
        //assert i != 0 || isHeap();
    }
    /**
     * Display the state of the heap as an array. The entire 
     * array is displayed; a vertical bar (pipe) indicates the 
     * end of the heap.
     * @return A string displaying the state of the heap.     * 
     */
    @Override
    public String toString() {
        String toReturn = "[";
        for (int i = 0; i < internal.length; i++) {
            if (i == heapSize)
                toReturn += "| ";
            toReturn += internal[i] + " ";
        }
        if (heapSize == internal.length) 
            toReturn += "|";
        toReturn += "]";
        return toReturn;
            
    }

    /**
     * Validate that the subtrees rooted at the children of i,
     * if any, are valid heaps.
     * @param i The parameter the subtrees rooted at whose children should 
     * be valid heaps
     * @return True if the indicated node's children are valid heaps
     */
    protected boolean isHeapBut(int i) {
        int a = left(i),
            b = right(i),
            j = a;
        boolean validHeap = true;
        while (validHeap && left(j) < heapSize) {
            validHeap &= compy.compare(internal[j], internal[left(j)]) >= 0;
            validHeap &= (right(j) == heapSize || 
                    compy.compare(internal[j], internal[right(j)]) >= 0);
            j++;
            if (j > b) {
                a = left(a);
                b = right(b);
                j = a;
            }
        }
        return validHeap;
    }

    /**
     * Validate that the heap property holds for the entire structure.
     * @return True if the class invariant holds.
     */
    protected boolean isHeap() {
        boolean validHeap = true;
        for (int i = 0; validHeap && left(i) < heapSize; i++) {
            validHeap &= compy.compare(internal[i], internal[left(i)]) >= 0;
            validHeap &= (right(i) == heapSize || 
                    compy.compare(internal[i], internal[right(i)]) >= 0);
        }
        return validHeap;
    }
    
}

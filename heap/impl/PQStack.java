package impl;
/**
 * Stack.java
 *
 * Class to implement a stack using a priority queue.
 * 
 * CS 345, Wheaton College
 * Originally for CSCI 245, Spring 2007
 * Revised Jan 4, 2016
 */

import java.util.Comparator;
import java.util.NoSuchElementException;

import adt.FullContainerException;
import adt.Map;
import adt.Stack;

public class PQStack<E> implements Stack<E> {

    /**
     * The priority queue to use as an internal representation.
     */
    private HeapPriorityQueue<E> pq;

    /**
     * Place to store data associated with representative
     * values in the priority queue.
     */
    private Map<E, Integer> arrivalTimes;
    

 
    /**
     * Constructor.
     * @param maxSize The capacity of this stack.
     */
    public PQStack(int maxSize) {
        arrivalTimes = new ListMap<E, Integer>();
        Comparator<E> compy = new Comparator<E>() {

			public int compare(E o1, E o2) {
				if (arrivalTimes.get(o1) < arrivalTimes.get(o2))
					return 1;
				else if (arrivalTimes.get(o1) > arrivalTimes.get(o2))
					return -1;
				return 0;
			}
        	
        };
        
        pq = new HeapPriorityQueue<E>(maxSize, compy);
    }

    /**
     * Is this stack empty? It is if the pq is empty.
     * @return True if this is empty, false otherwise.
     */
    public boolean isEmpty() { return pq.isEmpty(); }

    /**
     * Is this stack full? It is if the pq is full.
     * @return True if this is full, false otherwise.
     */
    public boolean isFull() { return pq.isFull(); }

    /**
     * Retrieve (but do not remove) the top element of this stack.
     * @return The top element.
     */
    public E top() { 
    	if (isEmpty()) throw new NoSuchElementException();
        return pq.max();
    }

    /**
     * Retrieve and remove the top element of this stack.
     * @return The top element.
     */
    public E pop() {
    	if (isEmpty()) throw new NoSuchElementException();
    	arrivalTimes.remove(pq.max());
        return pq.extractMax();
    }

    /**
     * Add an element to this stack.
     * @param x The element to add.
     */
    public void push(E x) {
    	if (isFull()) throw new FullContainerException();
    	Map<E, Integer> newTimes = new ListMap<E, Integer>();
    	
    	for (E key : arrivalTimes)
    		newTimes.put(key, arrivalTimes.get(key) + 1);
    	
    	newTimes.put(x, 0);
    	arrivalTimes = newTimes;
        pq.insert(x);
    }

    public String toString() { return pq.toString(); }
    
}

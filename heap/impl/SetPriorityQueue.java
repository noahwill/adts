package impl;

import java.util.Comparator;

import adt.PriorityQueue;
import adt.Set;

public class SetPriorityQueue<E> implements PriorityQueue<E> {

    private Set<E> internal;
    private Comparator<E> compy;
    
    public SetPriorityQueue(Iterable<E> itably, Comparator<E> compy) {
        internal = new ListSet<E>();
        for (E x : itably) internal.add(x);
        this.compy = compy;
    }

    public SetPriorityQueue(int size, Comparator<E> compy) {
        internal = new ListSet<E>();
        this.compy = compy;
    }

    public boolean isEmpty() {
        return internal.isEmpty();
    }

    public void insert(E key) {
        internal.add(key);
    }

    public E max() {
        E maxKey = null;
        for (E x : internal)
            if (maxKey == null || compy.compare(maxKey, x) < 0)
                maxKey = x;
        return maxKey;
    }

    public E extractMax() {
        E maxKey = max();
        internal.remove(maxKey);
        return maxKey;
    }

    public boolean contains(E key) {
        return internal.contains(key);
    }

    public void increaseKey(E key) {

    }

    public void decreaseKey(E key) {

    }

}

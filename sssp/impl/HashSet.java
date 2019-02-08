package impl;

import java.util.Iterator;

import adt.Map;
import adt.Set;

public class HashSet<E> implements Set<E> {

    private Map<E, Object> internal;
    
    public HashSet() {
        internal = new SeparateChainingHashMap<E, Object>();
    }
    
    public Iterator<E> iterator() {
        return internal.iterator();
    }

    public void add(E item) {
        internal.put(item, null);
    }

    public boolean contains(E item) {
        return internal.containsKey(item);
    }

    public void remove(E item) {
        internal.remove(item);
    }

    public int size() {
        int count = 0;
        for (Iterator<E> it = internal.iterator(); it.hasNext(); it.next()) count++;
        return count;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

}

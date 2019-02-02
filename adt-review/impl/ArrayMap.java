package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import adt.Map;

/**
 * ArrayMap
 * 
 * Class to implement the Map ADT using an array. * 
 * 
 * (Unlike Stack and Queue, Map is not specified to
 * throw NoSuchElementException when get() or remove()
 * are called with non-existent keys. Instead get()
 * returns null and remove() does nothing. The only
 * reason for this decision is that that's what the tests
 * for Maps that I already had assumed. Similarly put() doesn't
 * throw a FullContainerException.)
 * 
 * CSCI 345, Wheaton College
 * Spring 2016
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */
public class ArrayMap<K, V> implements Map<K, V> {

    /**
     * Class for key-value pairs. This map implementation
     * is essentially an array of these.
     */
    private static class Association<K, V> {
        K key;
        V val;
        Association(K key, V val) {
            this.key = key;
            this.val = val;
        }
        @Override
        public String toString() {
        	return "key"+"="+val;
        }
    }

    /**
     * An array of key-value associations, the internal
     * representation of this map.
     */
    private Association<K,V>[] internal;
    private int size = 0;
    

    /**
     * Plain constructor. 
     */
    @SuppressWarnings("unchecked")
    public ArrayMap() {
        // 100 as length of the initial array is an arbitrary choice.
        internal = (Association<K,V>[]) new Association[100];
         
    }

    /**
     * Cause the internal array to double in size.
     */
    private void grow() {
        @SuppressWarnings("unchecked")
        Association<K,V>[] temp = (Association<K,V>[]) new Association[internal.length * 2];
        for (int i = 0; i < internal.length; i++)
            temp[i] = internal[i];
        internal = temp;
    }

    /**
     * Return an iterator over this collection (remove() is
     * unsupported, nor is concurrent modification checked).
     */
    public Iterator<K> iterator() {
    	return new Iterator<K>() {
    		private int pos = 0;
    		private K toReturn;
    		
    		public boolean hasNext() { return size>pos; }
    		
    		public K next() {
    			if (this.hasNext()) {
    				toReturn = internal[pos].key;
    				pos++;
    				return toReturn; 
    			}
    			else 
    				throw new NoSuchElementException();}
    	};
    }
   
    
    
    /**
     * Add an association to the map.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
     Association<K, V> toPut = new Association(key, val);
     boolean b = this.containsKey(key);
    
     if (size+1 >= internal.length) grow();
      
     if (b) {
    	 int i = indexOf(key);
    	 internal[i].val = val;
      }
   
     if (!b) {
    	 for(int i = 0; i < internal.length; i++) {
    		 if (internal[i] == null) {
    			internal[i] = toPut;
	   		 	size++;
	   		 	break;
       	  	}  
    	 }
     }
      
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
        
    	for (int i = 0; i < size; i++) {
        	if (internal[i].key.equals(key)) return internal[i].val;
        }
        
        return null;
    }

    /**
     * Test if this map contains an association for this key.
     * @param key The key to test.
     * @return true if there is an association for this key, false otherwise
     */
    public boolean containsKey(K key) {
        
    	for (int i = 0; i < size; i++) {
         	if (internal[i].key.equals(key)) return true;
        }
        
        return false;
    }
    
    public Integer indexOf(K key) {
    	
    	for (int i = 0; i < size; i++) {
    		if (internal[i].key.equals(key)) return i; 
    	}
    	
    	return null;
    }
    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
    	// Copy everything except the removed 
    	@SuppressWarnings("unchecked")
		Association<K,V>[] temp = (Association<K,V>[]) new Association[internal.length];
    	if (this.containsKey(key)) {
    		int index = indexOf(key);
    		for( int i = 0; i <= size; i++) {
    			if (i == index) {}
    			else if (i < index){ temp[i] = internal[i]; }
    			else { temp[i-1] = internal[i]; }
    		}
    	internal = temp;
    	size--;
        }
    }
    
    @Override
    public String toString() {
    	String toReturn = "[";
    	boolean prefix = false;
    	for (Association<K,V> item : internal) {
    		if (prefix)
    			toReturn += ", ";
    		toReturn += item;
    		prefix = true;
    	}
    	return toReturn +"]";
    }

}

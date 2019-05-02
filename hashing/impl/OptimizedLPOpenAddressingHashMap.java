package impl;

import java.util.Iterator;

/**
 * OptimizedLPOpenAddressingHashMap
 * 
 * An extension to open addressing that avoids using sentinel
 * deleted values when using the linear probing strategy.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * May 18, 2017
 * @param <K> The key-type of the map
 * @param <V> The value-type of the map
 */
public class OptimizedLPOpenAddressingHashMap<K,V> extends OpenAddressingHashMap<K, V> {

    /**
     * Actually unnecessary since the default constructor would
     * have the same effect, but this shows intentionality.
     */
    public OptimizedLPOpenAddressingHashMap() {
        super(1);
    }
    
    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    @Override  // now that's a REAL override
    public void remove(K key) {
		int gap = find(key);
	    if(gap != -1) {
	    	table[gap] = null;
	        int p = (gap + 1) % table.length;
	        int i;
	        while(table[p] != null) {
	            i = h.hash(table[p].key);
	            if ((gap >= i && gap < p) || (p < i && i <= gap) || (p < i && gap < p)) { 
	            	
		            table[gap] = table[p];
		            table[p] = null;
		            gap = p;
	            }
	            p = (p+1) % table.length; 
	        }
	        numPairs--;
	    } 
    }
}



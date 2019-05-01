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
    
    
    public void removeHelper(int gap, K key) {
    	int p = find(key);
    	int i = h.hash(key);

    	if (gap >= i && gap < p) { table[gap] = table[p]; table[p] = deleted; }
    	else if (p < i && i <= gap) { table[gap] = table[p]; table[p] = deleted; }
    	else if (p < i && gap < p) { table[gap] = table[p]; table[p] = deleted; }

    	if(p+1 < table.length) removeHelper(p,table[p+1].key);
    }
    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    @Override  // now that's a REAL override
    public void remove(K key) {
    	int k = find(key);
        if(k != -1) {
        	table[k] = deleted;
        	
        	Iterator<Integer> probe = prober.probe(key);
            int i;
            do {
                assert probe.hasNext();
                i = probe.next();
            } while(table[i] != null && ! key.equals(table[i].key));
            
        }  
    }
    
    
}



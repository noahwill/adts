package impl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import adt.Map;
import adt.Set;
import impl.ListSet;

/**
 * PerfectHashMap
 * 
 * Implementation of perfect hashing, that is, when the keys are known ahead of
 * time. Note that containsKey and get will work as expected if used with a key
 * that doesn't exist. However, we assume put will never be called using a key
 * that isn't supplied to the constructor; behavior is unspecified otherwise.
 * 
 * @author Thomas VanDrunen
 * CSCI 345, Wheaton College
 * March 17, 2015
 * @param <K> The key-type of the map
 * @param <V>The value-type of the map
 */

public class PerfectHashMap<K, V> implements Map<K, V> {

    /**
     * Secondary maps for the buckets
     */
    private class SecondaryMap implements Map<K, V> {

        /**
         * The keys in this secondary map. This is necessary to check when get
         * and containsKey are called on spurious keys and also for the
         * iterator.
         */
        K[] keys;

        /**
         * The values in the secondary map.
         */
        V[] values;

        /**
         * The number of slots in the arrays, computed as the square of the
         * number of keys that can go here.
         */
        int m;

        /**
         * The hash function, drawn from class Hpm
         */
        HashFunction<Object> h;

        /**
         * Constructor. Given a set of keys, make appropriately
         * size arrays and a hash set that makes no collisions.
         * @param givenKeys
         */
        @SuppressWarnings("unchecked")
        SecondaryMap(Set<K> givenKeys) {

            m = givenKeys.size();

            keys = (K[]) new Object[m];
            values = (V[]) new Object[m];

            h = HashFactory.universalHashFunction(p, m, mask);

            while (true) {
	            boolean check = true;
	            for (K k1 : givenKeys) {
		            for (K k2 : givenKeys) 
		                if (!k1.equals(k2) && h.hash(k1) == h.hash(k2)) check = false;
	            }
	            if (check) break;
	            else h = HashFactory.universalHashFunction(p, m, mask);
            }

        }

        /**
         * Add an association to the map. We assume the given
         * key was known ahead of time.
         * @param key The key to this association
         * @param val The value to which this key is associated
         */
        public void put(K key, V val) {
            int pos = h.hash(key);
            keys[pos] = key;
            values[pos] = val;
        }

        /**
         * Get the value for a key.
         * @param key The key whose value we're retrieving.
         * @return The value associated with this key, null if none exists
         */
       public V get(K key) {
            if (!containsKey(key))
                return null;
            return values[h.hash(key)];
        }

       /**
        * Test if this map contains an association for this key.
        * @param key The key to test.
        * @return true if there is an association for this key, false otherwise
        */
       public boolean containsKey(K key) {
            if (m == 0)
                return false;
            int pos = h.hash(key);
            return keys[pos] != null
                    // next part necessary only if we assume
                    // keys that can't be put may be tested
                    && keys[pos].equals(key);
        }

       /**
        * Remove the association for this key, if it exists.
        * @param key The key to remove
        */
       public void remove(K key) {
            if (containsKey(key))
                keys[h.hash(key)] = null;
        }

       /**
        * The iterator for this portion of the map.
        */
        public Iterator<K> iterator() { 
            int i = 0;
            while(i < keys.length && keys[i] == null) i++;
            final int finalI = i;
             return new Iterator<K>() {
            	int i = finalI;
				@Override
				public boolean hasNext() {
					return i < m;
				}

				@Override
				public K next() {
					K toReturn = keys[i];
					i++;
					while(i< keys.length && keys[i] == null) i++;
					return toReturn;
				}
            	 
             };
        }
        
    }

    /**
     * Secondary maps
     */
    private SecondaryMap[] secondaries;

    /**
     * A prime number greater than the greatest hash value
     */
    private int p;

    /**
     * A parameter to the hash function; here, the number of keys known ahead of
     * time.
     */
    private int m;

    /**
     * The primary hash function
     */
    private HashFunction<Object> h;

    /**
     * A bit mask to grab certain bits from the result of a
     * call to hashCode(). Recommended that this is one less than
     * a power of 2, and hence we will grab a certain number of
     * lower ordered bits. This is used to reduce the range
     * of the integer keys and hence allow for a smaller prime p.
     */
    private int mask;
    
    /**
     * Constructor. Takes the keys (all known ahead of time) to set things up to
     * guarantee no collisions.
     * 
     * @param keys
     */
    @SuppressWarnings("unchecked")
    public PerfectHashMap(K[] keys) {

        m = keys.length;
        int greatest = findMaskAndGreatestKey(keys);
        p = PrimeSource.nextOrEqPrime(greatest);
        h = HashFactory.universalHashFunction(p, m, mask);

        Set<K>[] buckets = new ListSet[m];
        // for each bucket create a set
        for (int i = 0; i < m; i++) {
                buckets[i] = new ListSet<K>();
        }

        for (int i = 0; i < m; i++) {
                int hash = h.hash(keys[i]);
                buckets[hash].add(keys[i]);
        }

        secondaries = (SecondaryMap[]) new PerfectHashMap.SecondaryMap[m];

        for (int i = 0; i < m; i++) {
                secondaries[i] = new SecondaryMap(buckets[i]);
        }

    }

    /**
     * Helper function (intended to be used by the constructor)
     * to find an appropriate mask for the keys and the greatest 
     * integer key using that mask. The mask, stored as an instance
     * variable, is the greatest value one less than a power of two
     * which will produce unique integers when bitwise-anded with
     * each key's hashcode.
     * @param keys The set of keys, given to the constructor.
     * @return The greatest value of any key's hashcode bitwise-anded
     * by the mask.
     */
    private int findMaskAndGreatestKey(K[] keys) {
        // The greatest code found so for the current mask
        int greatestCode;
        // Our current guess for the least mask
        mask = 31;
        // Do any keys' hashcodes have identical bits when
        // bitwise-anded with the current mask?
        boolean doubleHit; 

        // Repeatedly guess a mask until we find one
        // that gives no double hits.
        do {
            // Increase our mask guess to the next integer
            // that is one less than a power of two.
            // (Effectively 63 is our first guess.)
            mask = (mask << 1) + 1;
            // We have not found any double hits so far on this mask
            doubleHit = false;
            // hit[i] is true iff we have inspected a key whose
            // hashcode bitwise-anded with the mask equals i.
            // Note that mask itself is the greatest possible
            // result of bitwise-anding a value with mask.
            boolean[] hits = new boolean[mask+1];
            greatestCode = 0;
            for (K key : keys) {
                int code = (key.hashCode() & mask);
                if (code > greatestCode)
                    greatestCode = code;
                if (hits[code]) 
                    doubleHit = true;
                else hits[code] = true;
            }
        }
        while (doubleHit);
        return greatestCode;
    }

    /**
     * Add an association to the map. We assume the given
     * key was known ahead of time.
     * @param key The key to this association
     * @param val The value to which this key is associated
     */
    public void put(K key, V val) {
        secondaries[h.hash(key)].put(key, val);
    }

    /**
     * Get the value for a key.
     * @param key The key whose value we're retrieving.
     * @return The value associated with this key, null if none exists
     */
    public V get(K key) {
        return secondaries[h.hash(key)].get(key);
    }

   /**
    * Test if this map contains an association for this key.
    * @param key The key to test.
    * @return true if there is an association for this key, false otherwise
    */
    public boolean containsKey(K key) {
        return secondaries[h.hash(key)].containsKey(key);
    }

    /**
     * Remove the association for this key, if it exists.
     * @param key The key to remove
     */
    public void remove(K key) {
        secondaries[h.hash(key)].remove(key);
    }

    /**
     * Return an iterator over this map
     */
    public Iterator<K> iterator() {
        int i = 0;
        while (i < secondaries.length && !secondaries[i].iterator().hasNext()) i++;
        final int finalI = i;
    	
        // Dummy iterator - handles empty iterator
        if(i == secondaries.length) {
        	return new Iterator<K>() {
        		public boolean hasNext() { return false; }
        		public K next() { return null; }	
        	};
        }
        
    	return new Iterator<K>() {
        	int i = finalI;
        	Iterator<K> secondary = secondaries[i].iterator();
        	
			@Override
			public boolean hasNext() {
				return (i < secondaries.length);
			}

			@Override
			public K next() {
				K toReturn = secondary.next();
				
				if(!secondary.hasNext()) {
					do
						i++;
					while(i < secondaries.length && !secondaries[i].iterator().hasNext());
					if(i == secondaries.length) return toReturn;
					secondary = secondaries[i].iterator();
				}
				return toReturn;
			}
        	 
         };
    }

}

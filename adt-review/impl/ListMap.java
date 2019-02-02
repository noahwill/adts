package impl;

import java.util.Iterator;

import adt.List;
import adt.Map;

public class ListMap<K, V> implements Map<K, V> {
	
	private class Entry {
		K key; 
		V val;
		
		public Entry(K key, V val) {
			this.key = key;
			this.val = val;
		}
	}
	
	private List<Entry> internal;
	
	private Entry findEntry(K key) {
		Entry assoc = null;
		for (int i = 0; i < internal.size() && assoc == null; i++) {
			Entry current = internal.get(i);
			if (current.key.equals(key)) assoc = current;
		}
		
		return assoc;
	}
	
	
	public Iterator<K> iterator() {
		final Iterator<Entry> interIter = internal.iterator();
		return new Iterator<K>() {
			public boolean hasNext() { return interIter.hasNext(); }
			public K next() { return interIter.next().key; }
		};
	}

	
	public void put(K key, V val) {
		Entry old = findEntry(key); 
		if (old == null) internal.add(new Entry(key, val));
		else old.val = val;
	}

	
	public V get(K key) {
		Entry assoc = findEntry(key);
		if (assoc == null) return null;
		else return assoc.val;
	}

	
	public boolean containsKey(K key) {
		return findEntry(key) != null;
	}

	public void remove(K key) {
		int index = -1;
		for (int i = 0; i < internal.size() && index == -1; i++) {
			Entry current = internal.get(i);
			if (current.key.equals(key)) index = i;
		}
		if (index != -1) internal.remove(index);	
	}

}

package exercises;

public class Exercise1 {

    /**
     * Find the position of an item in a given array, if it is anywhere.
     * PRECONDITION: The array is sorted and contains no null elements.
     * @param array An array sorted by its elements' natural ordering
     * (as expressed by compareTo()).
     * @param item The item for which to search.
     * @return A position in the array which contains the item, or -1 if
     * it does not occur in the array (including if the array is empty or null)
     */
    public static <T extends Comparable<T>> int binarySearch(T[] array, T item) {
        
    	
    	
        int low = 0;
        int high;
        
        if (array == null) { high = 0; } 
        else { high = array.length; }
        
        while (high - low > 1) {
        	int mid = (low + high) / 2;
        	int c = array[mid].compareTo(item);
        	
        	if (c > 0) { 
        		high = mid; 
        	}
        	
        	else if (c < 0) { 
        		low = mid + 1; 
        	}
        	
        	else { 
        		low = mid;
        		high = mid + 1;
        	}
        }
        
        if (low < high && array[low].compareTo(item) == 0) { return low; }
      
        else { return -1; }
    }
    
}

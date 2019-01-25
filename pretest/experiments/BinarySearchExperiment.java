package experiments;

import java.util.Random;

public class BinarySearchExperiment {
    private static class MeasComp implements Comparable<MeasComp> {
        private Integer kernel;
        private static int comparisons;
        private static void resetComparisons() {
            comparisons = 0;
        }
        private static int getComparisons() {
            return comparisons;
        }
        public MeasComp(Integer num) {
            kernel = num;
        }
        @Override
        public boolean equals(Object o){
            comparisons++;
            return o instanceof MeasComp && kernel.equals(((MeasComp) o).kernel);
        }
        @Override
        public int compareTo(MeasComp anotherCC) {
            comparisons++;
            return kernel.compareTo(anotherCC.kernel);
        }
        @Override 
        public int hashCode() {
            throw new UnsupportedOperationException();
        }
    }


    public static <T extends Comparable<T>> int binarySearch0(T[] array, T item) {
        if (array == null) return -1;
        else {
            int low = 0,
                high = array.length,
                mid = (low + high) / 2,
                compar = -1; 
            while (low < high && (compar = item.compareTo(array[mid])) != 0) { 
                if (compar < 0)
                    high = mid;
                else
                    low = mid + 1;
                mid = (low + high) / 2;
            }
            if (low < high)
                return mid;
            else
                return -1;
        }
    }

    public static <T extends Comparable<T>> int binarySearch1(T[] array, T item) {
        if (array == null) return -1;
        else {
            int low = 0,
                high = array.length,
                mid = (low + high) / 2;
            while (low < high && !item.equals(array[mid])) {
                int compar = item.compareTo(array[mid]); 
                if (compar < 0)
                    high = mid;
                else
                    low = mid + 1;
                mid = (low + high) / 2;
            }
            if (low < high)
                return mid;
            else
                return -1;
        }
    }


    public static <T extends Comparable<T>> int binarySearch2(T[] array, T item) {
        if (array == null) return -1;
        else {
            int low = 0,
                high = array.length,
                mid = (low + high) / 2;
            while (low < high - 1) { 
                if (item.compareTo(array[mid]) < 0)
                    high = mid;  // throws away mid
                else
                    low = mid;   // keeps mid
                mid = (low + high) / 2;
            }
            if (low < high && item.equals(array[mid]))
                return mid;
            else
                return -1;
        }
    }


    public static void main(String[] args) {

        Random randy = new Random(System.currentTimeMillis());
        
        int compars0, compars1, compars2;
        long millis0, millis1, millis2, millisTemp;

        MeasComp[] array = new MeasComp[10000000];
        MeasComp[] searches = new MeasComp[100000];

        // --- Uniformly distributed dense ---
        compars0 = compars1 = compars2 = 0;
        millis0 = millis1 = millis2 = 0;        

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < array.length; i++)
                array[i] = new MeasComp(randy.nextInt(100000));
            java.util.Arrays.sort(array);
            for (int i = 0; i < searches.length; i++)
                searches[i] = new MeasComp(randy.nextInt(100000));

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch0(array, searches[i]);
            millis0 += System.currentTimeMillis() - millisTemp;
            compars0 += MeasComp.getComparisons();

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch1(array, searches[i]);
            millis1 += System.currentTimeMillis() - millisTemp;
            compars1 += MeasComp.getComparisons();

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch2(array, searches[i]);
            millis2 += System.currentTimeMillis() - millisTemp;
            compars2 += MeasComp.getComparisons();

        }
        System.out.println("Uniform dense");
        System.out.println("Version: \t\t0 \t\t\t1 \t\t\t2");
        System.out.println("Total comparisons:\t" + compars0 + "\t\t" + compars1 + "\t\t" + compars2);
        System.out.println("Total time:\t\t" + millis0 + "\t\t\t" + millis1 + "\t\t\t" + millis2);

        
        // --- Uniformly distributed sparse ---
        compars0 = compars1 = compars2 = 0;
        millis0 = millis1 = millis2 = 0;        

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < array.length; i++)
                array[i] = new MeasComp(randy.nextInt(100000000));
            java.util.Arrays.sort(array);
            for (int i = 0; i < searches.length; i++)
                searches[i] = new MeasComp(randy.nextInt(100000000));

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch0(array, searches[i]);
            millis0 += System.currentTimeMillis() - millisTemp;
            compars0 += MeasComp.getComparisons();

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch1(array, searches[i]);
            millis1 += System.currentTimeMillis() - millisTemp;
            compars1 += MeasComp.getComparisons();

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch2(array, searches[i]);
            millis2 += System.currentTimeMillis() - millisTemp;
            compars2 += MeasComp.getComparisons();

        }
        System.out.println("\t\tUniform sparse");
        System.out.println("Version: \t\t0 \t\t\t1 \t\t\t2");
        System.out.println("Total comparisons:\t" + compars0 + "\t\t" + compars1 + "\t\t" + compars2);
        System.out.println("Total time:\t\t" + millis0 + "\t\t\t" + millis1 + "\t\t\t" + millis2);

       
        // --- Normally distributed ---
        compars0 = compars1 = compars2 = 0;
        millis0 = millis1 = millis2 = 0;        

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < array.length; i++)
                array[i] = new MeasComp((int) (50000 + 30000 * randy.nextGaussian()));
            java.util.Arrays.sort(array);
            for (int i = 0; i < searches.length; i++)
                searches[i] = new MeasComp((int) (50000 + 30000 * randy.nextGaussian()));

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch0(array, searches[i]);
            millis0 += System.currentTimeMillis() - millisTemp;
            compars0 += MeasComp.getComparisons();

            MeasComp.resetComparisons();
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch1(array, searches[i]);
            millis1 += System.currentTimeMillis() - millisTemp;
            compars1 += MeasComp.getComparisons();

            MeasComp.resetComparisons();;
            millisTemp = System.currentTimeMillis();
            for (int i = 0; i < searches.length; i++) 
                binarySearch2(array, searches[i]);
            millis2 += System.currentTimeMillis() - millisTemp;
            compars2 += MeasComp.getComparisons();

        }
        System.out.println("\t\tNormal");
        System.out.println("Version: \t\t0 \t\t\t1 \t\t\t2");
        System.out.println("Total comparisons:\t" + compars0 + "\t\t" + compars1 + "\t\t" + compars2);
        System.out.println("Total time:\t\t" + millis0 + "\t\t\t" + millis1 + "\t\t\t" + millis2);
        
    }
    
}

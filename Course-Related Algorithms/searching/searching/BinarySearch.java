    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerscience.algorithms.search;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author EduardoPC
 */
public class BinarySearch {

    public static void main(String[] args) {
        int size = 10000000;
        int[] array = new int[size];

        for (int i = 0; i < size - 1; i++) {
            array[i] = i;
        }

        long startTime = System.nanoTime();
        try {
            binarySearch(array, size - 1);
        } finally {
            long endTime = System.nanoTime() - startTime;
            double totalTime = TimeUnit.NANOSECONDS.toMillis(endTime);
            System.out.println("The index search took " + totalTime + " milliseconds");
        }

        System.out.println("Element found at index: " + binarySearch(array, 1000000));
    }

    public static int binarySearch(int[] array, int searched) {

        int l = 0;
        int r = array.length - 1;
        
        while (l <= r){
            
            int m = (l + r)/2;
            
            if (array[m] == searched){
                return m;
            } else if (array[m] < searched){
                l = m + 1;          
            } else {
                r = m - 1;
            }
            
        }

        return -1;
    }
}

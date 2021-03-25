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
public class LinearSearch {

    public static void main(String[] args) {
        
        int size = 10000000;
        int[] array = new int[size];

        for (int i = 0; i < size - 1; i++) {
            array[i] = i;
        }

        long startTime = System.nanoTime();
        try {
            linearSearch(array, size);
        } finally {
            long endTime = System.nanoTime() - startTime;
            double totalTime = TimeUnit.NANOSECONDS.toMillis(endTime);
            System.out.println("The index search took " + totalTime + " milliseconds");
        }

        System.out.println("Element found at index: " + linearSearch(array, 1000000));
    }

    public static int linearSearch(int[] array, int searchedIndex) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == searchedIndex) {
                return i;
            }
        }
        return -1;

    }
}

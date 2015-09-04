package com.inno.max;

import java.util.List;

/**
 * Created by max on 01.09.15.
 */
public class SuperListTests {
    public static void main(String[] args) {
        int UPPER_BOUND = 10;

        List<Integer> integers = new SuperPolygonList<Integer>();

        for (int i = 0; i < UPPER_BOUND; i++) {
            // Add
            integers.add(i);
        }

        for (int i = 0; i < integers.size(); i++) {
            // Get
            System.out.println("Get: " + integers.get(i));
        }

        for (int i = 0; i < integers.size(); i++) {
            // Set
            System.out.println("Old value: " + integers.set(i, integers.size() - i));
        }

        for (int i = 0; i < integers.size(); i++) {
            // Get
            System.out.println("Get: " + integers.get(i));
        }

        integers.add(5, 99); // Add by index
        System.out.println("Removed(by index = " + 0 + "): " + integers.remove(0)); // Remove by index
        System.out.println("Removed(by value = " + 5 + "): " + integers.remove((Integer) 5)); // Remove by value


        for (final Integer i: integers) {
            // Iterator
            System.out.println(i);
        }


    }
}

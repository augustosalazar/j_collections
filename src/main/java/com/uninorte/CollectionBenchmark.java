package com.uninorte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import java.util.*;

public class CollectionBenchmark {

    private static final int NUM_ENTRIES = 100_000;

    public static void main(String[] args) {
        System.out.println("Benchmarking collections on java:");

        // List implementations 
        benchmarkList(new ArrayList<>(), "ArrayList (Integer)", false);
        benchmarkList(new ArrayList<>(), "ArrayList (String)", true);
        benchmarkList(new LinkedList<>(), "LinkedList (Integer)", false);
        benchmarkList(new LinkedList<>(), "LinkedList (String)", true);
        benchmarkList(new Vector<>(), "Vector (Integer)", false);
        benchmarkList(new Vector<>(), "Vector (String)", true);        

        
        // Set implementations 
        benchmarkSet(new HashSet<>(), "HashSet (Integer)", false);
        benchmarkSet(new HashSet<>(), "HashSet (String)", true);
        benchmarkSet(new LinkedHashSet<>(), "LinkedHashSet (Integer)", false);
        benchmarkSet(new LinkedHashSet<>(), "LinkedHashSet (String)", true);      
        benchmarkSet(new TreeSet<>(), "TreeSet (Integer)", false);
        benchmarkSet(new TreeSet<>(), "TreeSet (String)",     true);          

        // Map implementations 
        benchmarkMap(new HashMap<>(), "HashMap (Integer)", false);
        benchmarkMap(new HashMap<>(), "HashMap (String Key)", true);
      
        benchmarkMap(new LinkedHashMap<>(), "LinkedHashMap (Integer)", false);
        benchmarkMap(new LinkedHashMap<>(), "LinkedHashMap (String Key)", true);
     
        benchmarkMap(new TreeMap<>(), "TreeMap (Integer)", false);
        benchmarkMap(new TreeMap<>(), "TreeMap (String Key)", true);     

      

        // List implementations with String


        // Set implementations with String
        

        // Map implementations with String as keys

    }

    private static String generateRandomString() {
        int length = 10;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private static void benchmarkList(List<Object> list, String name, boolean useStrings) {
        System.out.println("Benchmarking " + name + " with " + (useStrings ? "String" : "Integer") + " values");

        Random random = new Random();

        // Create
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.add(useStrings ? generateRandomString() : i);
        }
        long endTime = System.nanoTime();
        System.out.println("Create: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Read
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.get(i);
        }
        endTime = System.nanoTime();
        System.out.println("Sequential Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Read
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.get(random.nextInt(NUM_ENTRIES));
        }
        endTime = System.nanoTime();
        System.out.println("Random Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Update
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.set(i, useStrings ? generateRandomString() : i + 1);
        }
        endTime = System.nanoTime();
        System.out.println("Sequential Update: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Update
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.set(random.nextInt(NUM_ENTRIES), useStrings ? generateRandomString() : i);
        }
        endTime = System.nanoTime();
        System.out.println("Random Update: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Delete
        startTime = System.nanoTime();
        list.clear();
        endTime = System.nanoTime();
        System.out.println("Sequential Delete: " + (endTime - startTime) / 1_000_000 + " ms\n");
    }

    private static void benchmarkSet(Set<Object> set, String name, boolean useStrings) {
        System.out.println("Benchmarking " + name + " with " + (useStrings ? "String" : "Integer") + " values");

        Random random = new Random();

        // Create
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            set.add(useStrings ? generateRandomString() : i);
        }
        long endTime = System.nanoTime();
        System.out.println("Create: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Read (contains)
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            set.contains(useStrings ? generateRandomString() : i);
        }
        endTime = System.nanoTime();
        System.out.println("Sequential Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Read (contains)
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            set.contains(useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES));
        }
        endTime = System.nanoTime();
        System.out.println("Random Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Delete
        startTime = System.nanoTime();
        set.clear();
        endTime = System.nanoTime();
        System.out.println("Sequential Delete: " + (endTime - startTime) / 1_000_000 + " ms\n");
    }

    private static void benchmarkMap(Map<Object, Object> map, String name, boolean useStrings) {
        System.out.println("Benchmarking " + name + " with " + (useStrings ? "String    " : "Integer") + " values");

        Random random = new Random();

        // Create
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            Object key = useStrings ? generateRandomString() : i;
            map.put(key, key);
        }
        long endTime = System.nanoTime();
        System.out.println("Create: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Read
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            map.get(useStrings ? generateRandomString() : i);
        }
        endTime = System.nanoTime();
        System.out.println("Sequential Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Read
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            map.get(useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES));
        }
        endTime = System.nanoTime();
        System.out.println("Random Read: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Update
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            Object key = useStrings ? generateRandomString() : i;
            map.put(key, key);
        }
        endTime = System.nanoTime();
        System.out.println("Sequential Update: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Update
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            Object key = useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES);
            map.put(key, key);
        }
        endTime = System.nanoTime();
        System.out.println("Random Update: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sequential Delete
        startTime = System.nanoTime();
        map.clear();
        endTime = System.nanoTime();
        System.out.println("Sequential Delete: " + (endTime - startTime) / 1_000_000 + " ms\n");
    }
}

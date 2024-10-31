package com.uninorte;

import java.util.*;

public class CollectionBenchmark {

    private static final int NUM_ENTRIES = 100_000;

    public static void main(String[] args) {
        System.out.println("Benchmarking collections in Java:");

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
        benchmarkSet(new TreeSet<>(), "TreeSet (String)", true);

        // Map implementations
        benchmarkMap(new HashMap<>(), "HashMap (Integer)", false);
        benchmarkMap(new HashMap<>(), "HashMap (String Key)", true);
        benchmarkMap(new LinkedHashMap<>(), "LinkedHashMap (Integer)", false);
        benchmarkMap(new LinkedHashMap<>(), "LinkedHashMap (String Key)", true);
        benchmarkMap(new TreeMap<>(), "TreeMap (Integer)", false);
        benchmarkMap(new TreeMap<>(), "TreeMap (String Key)", true);
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
        System.out.println("\nBenchmarking " + name + " with " + (useStrings ? "String" : "Integer") + " values");
        Random random = new Random();

        // Memory Usage
        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            list.add(useStrings ? generateRandomString() : i);
        }
        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Before memory: " + (beforeMemory / (1024 * 1024)) + " After memory: " + (afterMemory) / (1024 * 1024));
        System.out.println("Memory Usage: " + (afterMemory - beforeMemory) / (1024 * 1024) + " MB");

        // Insertions at Specific Positions
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            list.add(NUM_ENTRIES / 2, useStrings ? generateRandomString() : i);
        }
        long endTime = System.nanoTime();
        System.out.println("Insertions at Middle: " + (endTime - startTime) / 1_000_000 + " ms");

        // Removals at Specific Positions
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            list.remove(NUM_ENTRIES / 2);
        }
        endTime = System.nanoTime();
        System.out.println("Removals at Middle: " + (endTime - startTime) / 1_000_000 + " ms");

        // Iteration
        startTime = System.nanoTime();
        for (Object item : list) {
            // Just iterating
        }
        endTime = System.nanoTime();
        System.out.println("Iteration: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sorting Performance
        if (useStrings) {
            List<String> sortableList = new ArrayList<>();
            for (Object item : list) {
                sortableList.add((String) item);
            }
            startTime = System.nanoTime();
            Collections.sort(sortableList);
            endTime = System.nanoTime();
            System.out.println("Sorting Performance (String): " + (endTime - startTime) / 1_000_000 + " ms");
        } else {
            List<Integer> sortableList = new ArrayList<>();
            for (Object item : list) {
                sortableList.add((Integer) item);
            }
            startTime = System.nanoTime();
            Collections.sort(sortableList);
            endTime = System.nanoTime();
            System.out.println("Sorting Performance (Integer): " + (endTime - startTime) / 1_000_000 + " ms");
        }

        // Sub-List Operation
        startTime = System.nanoTime();
        List<Object> sublist = list.subList(0, NUM_ENTRIES / 2);
        endTime = System.nanoTime();
        System.out.println("Sub-List Creation: " + (endTime - startTime) / 1_000_000 + " ms");

        list.clear(); // Clear after benchmarking
    }

    private static void benchmarkSet(Set<Object> set, String name, boolean useStrings) {
        System.out.println("\nBenchmarking " + name + " with " + (useStrings ? "String" : "Integer") + " values");
        Random random = new Random();

        // Memory Usage
        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            set.add(useStrings ? generateRandomString() : i);
        }
        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Before memory: " + (beforeMemory / (1024 * 1024)) + " After memory: " + (afterMemory) / (1024 * 1024));        
        System.out.println("Memory Usage: " + (afterMemory - beforeMemory) / (1024 * 1024) + " MB");

        // Random Insertions
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            set.add(useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES));
        }
        long endTime = System.nanoTime();
        System.out.println("Random Insertions: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Removals
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            set.remove(useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES));
        }
        endTime = System.nanoTime();
        System.out.println("Random Removals: " + (endTime - startTime) / 1_000_000 + " ms");

        // Iteration
        startTime = System.nanoTime();
        for (Object item : set) {
            // Just iterating
        }
        endTime = System.nanoTime();
        System.out.println("Iteration: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sorting Performance (TreeSet only)
        if (set instanceof TreeSet) {
            startTime = System.nanoTime();
            Set<Object> sortedSet = new TreeSet<>(set); // TreeSet is naturally sorted
            endTime = System.nanoTime();
            System.out.println("Sorting Performance (TreeSet): " + (endTime - startTime) / 1_000_000 + " ms");
        }

        set.clear(); // Clear after benchmarking
    }

    private static void benchmarkMap(Map<Object, Object> map, String name, boolean useStrings) {
        System.out.println("\nBenchmarking " + name + " with " + (useStrings ? "String" : "Integer") + " keys");
        Random random = new Random();

        // Memory Usage
        long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        for (int i = 0; i < NUM_ENTRIES; i++) {
            Object key = useStrings ? generateRandomString() : i;
            map.put(key, key);
        }
        long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Before memory: " + (beforeMemory / (1024 * 1024)) + " After memory: " + (afterMemory) / (1024 * 1024));        
        System.out.println("Memory Usage: " + (afterMemory - beforeMemory) / (1024 * 1024) + " MB");

        // Random Insertions
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            Object key = useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES);
            map.put(key, key);
        }
        long endTime = System.nanoTime();
        System.out.println("Random Insertions: " + (endTime - startTime) / 1_000_000 + " ms");

        // Random Removals
        startTime = System.nanoTime();
        for (int i = 0; i < NUM_ENTRIES / 10; i++) {
            Object key = useStrings ? generateRandomString() : random.nextInt(NUM_ENTRIES);
            map.remove(key);
        }
        endTime = System.nanoTime();
        System.out.println("Random Removals: " + (endTime - startTime) / 1_000_000 + " ms");

        // Iteration over Entries
        startTime = System.nanoTime();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            // Just iterating
        }
        endTime = System.nanoTime();
        System.out.println("Iteration over Entries: " + (endTime - startTime) / 1_000_000 + " ms");

        // Sorting Performance (TreeMap only)
        if (map instanceof TreeMap) {
            startTime = System.nanoTime();
            Map<Object, Object> sortedMap = new TreeMap<>(map); // TreeMap is naturally sorted by key
            endTime = System.nanoTime();
            System.out.println("Sorting Performance (TreeMap): " + (endTime - startTime) / 1_000_000 + " ms");
        }

        // Sub-Map Operation (TreeMap only)
        if (map instanceof TreeMap) {
            startTime = System.nanoTime();
            Map<Object, Object> subMap = ((TreeMap<Object, Object>) map).subMap(0, NUM_ENTRIES / 10);
            endTime = System.nanoTime();
            System.out.println("Sub-Map Creation: " + (endTime - startTime) / 1_000_000 + " ms");
        }

        map.clear(); // Clear after benchmarking
    }
}

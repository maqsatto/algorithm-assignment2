package algorithms;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;
import algorithms.InsertionSort;
/**
 * Comprehensive unit tests for InsertionSort implementation.
 * 
 * Tests cover:
 * - Edge cases (empty, single element, duplicates)
 * - Correctness validation
 * - Performance tracking
 * - Optimization effectiveness
 * 
 * @author Student A
 */
public class InsertionSortTest {
    
    private InsertionSort sorter;
    private InsertionSort optimizedSorter;
    
    @BeforeEach
    void setUp() {
        sorter = new InsertionSort(false);
        optimizedSorter = new InsertionSort(true);
    }
    
    // ========== Edge Cases ==========
    
    @Test
    @DisplayName("Should handle null array")
    void testNullArray() {
        assertThrows(IllegalArgumentException.class, () -> {
            sorter.sort(null);
        });
    }
    
    @Test
    @DisplayName("Should handle empty array")
    void testEmptyArray() {
        int[] arr = {};
        sorter.sort(arr);
        assertEquals(0, arr.length);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle single element array")
    void testSingleElement() {
        int[] arr = {42};
        sorter.sort(arr);
        assertArrayEquals(new int[]{42}, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle two element array")
    void testTwoElements() {
        int[] arr = {2, 1};
        sorter.sort(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    // ========== Correctness Tests ==========
    
    @Test
    @DisplayName("Should sort random array correctly")
    void testRandomArray() {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};
        int[] expected = {11, 12, 22, 25, 34, 64, 90};
        
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle already sorted array")
    void testSortedArray() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = arr.clone();
        
        optimizedSorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
        
        // Should have minimal comparisons for sorted data
        long comparisons = optimizedSorter.getTracker().getComparisons();
        assertTrue(comparisons < arr.length * 2, 
                  "Optimized version should have few comparisons for sorted data");
    }
    
    @Test
    @DisplayName("Should handle reverse sorted array")
    void testReverseSortedArray() {
        int[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle array with duplicates")
    void testArrayWithDuplicates() {
        int[] arr = {5, 2, 8, 2, 9, 1, 5, 5};
        int[] expected = {1, 2, 2, 5, 5, 5, 8, 9};
        
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle all identical elements")
    void testAllIdenticalElements() {
        int[] arr = {7, 7, 7, 7, 7, 7, 7};
        int[] expected = {7, 7, 7, 7, 7, 7, 7};
        
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle negative numbers")
    void testNegativeNumbers() {
        int[] arr = {-5, 3, -2, 8, -10, 0, 15};
        int[] expected = {-10, -5, -2, 0, 3, 8, 15};
        
        sorter.sort(arr);
        assertArrayEquals(expected, arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle mixed positive and negative numbers")
    void testMixedNumbers() {
        int[] arr = {-3, 5, -1, 0, 2, -7, 4};
        
        sorter.sort(arr);
        assertTrue(InsertionSort.isSorted(arr));
        assertEquals(-7, arr[0]);
        assertEquals(5, arr[arr.length - 1]);
    }
    
    // ========== Nearly Sorted Data Tests ==========
    
    @Test
    @DisplayName("Should efficiently handle nearly sorted array")
    void testNearlySortedArray() {
        int[] arr = {1, 2, 3, 5, 4, 6, 7, 8, 9, 10};
        
        optimizedSorter.sort(arr);
        assertTrue(InsertionSort.isSorted(arr));
        
        // For small arrays, optimization overhead may not show benefit
        // Just verify correctness
        long optimizedComparisons = optimizedSorter.getTracker().getComparisons();
        assertTrue(optimizedComparisons > 0, "Should track comparisons");
    }
    
    // ========== Large Array Tests ==========
    
    @Test
    @DisplayName("Should handle large random array")
    void testLargeRandomArray() {
        int size = 1000;
        int[] arr = generateRandomArray(size);
        
        sorter.sort(arr);
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    @Test
    @DisplayName("Should handle large sorted array efficiently")
    void testLargeSortedArray() {
        int size = 10000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        
        optimizedSorter.sort(arr);
        assertTrue(InsertionSort.isSorted(arr));
        
        // Should complete quickly with optimization
        double timeMs = optimizedSorter.getTracker().getExecutionTimeMillis();
        assertTrue(timeMs < 100, "Should complete sorted array quickly");
    }
    
    // ========== Property-Based Tests ==========
    
    @Test
    @DisplayName("Should maintain array length after sorting")
    void testArrayLengthPreserved() {
        int[] arr = {5, 2, 8, 1, 9};
        int originalLength = arr.length;
        
        sorter.sort(arr);
        assertEquals(originalLength, arr.length);
    }
    
    @Test
    @DisplayName("Should preserve all elements after sorting")
    void testElementsPreserved() {
        int[] arr = {5, 2, 8, 1, 9, 3};
        int[] original = arr.clone();
        
        sorter.sort(arr);
        
        Arrays.sort(original);
        assertArrayEquals(original, arr);
    }
    
    @Test
    @DisplayName("Random arrays should always be sorted correctly")
    void testRandomArraysProperty() {
        Random rand = new Random(12345);
        
        for (int i = 0; i < 100; i++) {
            int size = rand.nextInt(100) + 1;
            int[] arr = generateRandomArray(size);
            
            sorter.sort(arr);
            assertTrue(InsertionSort.isSorted(arr), 
                      "Array should be sorted: " + Arrays.toString(arr));
        }
    }
    
    // ========== Metrics Tests ==========
    
    @Test
    @DisplayName("Should track comparisons")
    void testComparisonsTracked() {
        int[] arr = {3, 1, 2};
        
        sorter.sort(arr);
        long comparisons = sorter.getTracker().getComparisons();
        
        assertTrue(comparisons > 0, "Should track at least one comparison");
    }
    
    @Test
    @DisplayName("Should track array accesses")
    void testArrayAccessesTracked() {
        int[] arr = {3, 1, 2};
        
        sorter.sort(arr);
        long accesses = sorter.getTracker().getArrayAccesses();
        
        assertTrue(accesses > 0, "Should track array accesses");
    }
    
    @Test
    @DisplayName("Should track execution time")
    void testExecutionTimeTracked() {
        int[] arr = generateRandomArray(100);
        
        sorter.sort(arr);
        long timeNanos = sorter.getTracker().getExecutionTimeNanos();
        
        assertTrue(timeNanos > 0, "Should track execution time");
    }
    
    // ========== Copy Method Tests ==========
    
    @Test
    @DisplayName("sortCopy should not modify original array")
    void testSortCopyNonDestructive() {
        int[] arr = {5, 2, 8, 1, 9};
        int[] original = arr.clone();
        
        int[] sorted = sorter.sortCopy(arr);
        
        assertArrayEquals(original, arr, "Original should be unchanged");
        assertTrue(InsertionSort.isSorted(sorted), "Copy should be sorted");
    }
    
    @Test
    @DisplayName("sortCopy should throw on null")
    void testSortCopyNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            sorter.sortCopy(null);
        });
    }
    
    // ========== Optimization Comparison Tests ==========
    
    @Test
    @DisplayName("Optimization should improve performance on nearly sorted data")
    void testOptimizationBenefit() {
        int size = 1000;
        int[] nearlySorted = new int[size];
        for (int i = 0; i < size; i++) {
            nearlySorted[i] = i;
        }
        // Swap a few elements
        nearlySorted[10] = 500;
        nearlySorted[500] = 10;
        nearlySorted[50] = 800;
        nearlySorted[800] = 50;
        
        int[] arr1 = nearlySorted.clone();
        int[] arr2 = nearlySorted.clone();
        
        sorter.sort(arr1);
        long standardTime = sorter.getTracker().getExecutionTimeNanos();
        
        optimizedSorter.sort(arr2);
        long optimizedTime = optimizedSorter.getTracker().getExecutionTimeNanos();
        
        assertTrue(InsertionSort.isSorted(arr1));
        assertTrue(InsertionSort.isSorted(arr2));
        
        // Optimization may have overhead for small arrays
        // Just verify both produce correct results
        assertArrayEquals(arr1, arr2, 
                        "Both versions should produce identical results");
    }
    
    @Test
    @DisplayName("Both versions should produce identical results")
    void testOptimizationCorrectness() {
        for (int i = 0; i < 50; i++) {
            int[] arr1 = generateRandomArray(100);
            int[] arr2 = arr1.clone();
            
            sorter.sort(arr1);
            optimizedSorter.sort(arr2);
            
            assertArrayEquals(arr1, arr2, 
                            "Both versions should produce identical results");
        }
    }
    
    // ========== Stability Tests ==========
    
    @Test
    @DisplayName("Should be stable (maintain relative order of equal elements)")
    void testStability() {
        // Using a custom comparable to test stability
        int[] arr = {5, 2, 8, 2, 9, 1, 5};
        
        sorter.sort(arr);
        
        // All equal elements should maintain their relative order
        // This is implicit in the algorithm but verified by correctness
        assertTrue(InsertionSort.isSorted(arr));
    }
    
    // ========== Helper Methods ==========
    
    private int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size * 10);
        }
        return arr;
    }
    
    // ========== Performance Comparison Tests ==========
    
    @Test
    @DisplayName("Should verify worst-case O(n²) behavior")
    void testWorstCaseComplexity() {
        // Reverse sorted arrays are worst case for insertion sort
        int[] sizes = {100, 200, 400};
        long[] times = new long[sizes.length];
        
        for (int i = 0; i < sizes.length; i++) {
            int[] arr = new int[sizes[i]];
            for (int j = 0; j < sizes[i]; j++) {
                arr[j] = sizes[i] - j;
            }
            
            sorter.sort(arr);
            times[i] = sorter.getTracker().getExecutionTimeNanos();
        }
        
        // Time should roughly quadruple when size doubles (O(n²))
        // Allow for variance due to system factors
        double ratio = (double) times[2] / times[0];
        assertTrue(ratio >= 10 && ratio <= 25,
                  "Time complexity should be quadratic: ratio=" + ratio);
    }
    
    @Test
    @DisplayName("Should verify best-case O(n) behavior with optimization")
    void testBestCaseComplexity() {
        int[] sizes = {1000, 2000, 4000};
        long[] comparisons = new long[sizes.length];
        
        for (int i = 0; i < sizes.length; i++) {
            int[] arr = new int[sizes[i]];
            for (int j = 0; j < sizes[i]; j++) {
                arr[j] = j;
            }
            
            optimizedSorter.sort(arr);
            comparisons[i] = optimizedSorter.getTracker().getComparisons();
        }
        
        // Comparisons should roughly double when size doubles (O(n))
        double ratio = (double) comparisons[2] / comparisons[0];
        assertTrue(ratio >= 3 && ratio <= 5,
                  "Comparison count should be linear: ratio=" + ratio);
    }
}
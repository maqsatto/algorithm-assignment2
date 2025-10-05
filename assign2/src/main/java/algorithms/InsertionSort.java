package algorithms;

import metrics.PerformanceTracker;

/**
 * Optimized Insertion Sort implementation with support for nearly-sorted data.
 * 
 * This implementation includes:
 * - Binary search optimization for finding insertion position
 * - Early termination when element is already in correct position
 * - Comprehensive metrics tracking
 * 
 * Time Complexity:
 * - Best Case: O(n) when array is already sorted
 * - Average Case: O(n²) 
 * - Worst Case: O(n²) when array is reverse sorted
 * 
 * Space Complexity: O(1) - sorts in place
 * 
 * @author Student A
 */
public class InsertionSort {
    
    private PerformanceTracker tracker;
    private boolean useOptimization;
    
    /**
     * Creates an InsertionSort instance with default settings.
     */
    public InsertionSort() {
        this(true);
    }
    
    /**
     * Creates an InsertionSort instance.
     * 
     * @param useOptimization if true, uses binary search for nearly-sorted data
     */
    public InsertionSort(boolean useOptimization) {
        this.tracker = new PerformanceTracker();
        this.useOptimization = useOptimization;
    }
    
    /**
     * Sorts an array of integers in ascending order.
     * 
     * @param arr the array to sort
     * @throws IllegalArgumentException if array is null
     */
    public void sort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        tracker.reset();
        tracker.startTiming();
        
        if (arr.length <= 1) {
            tracker.stopTiming();
            return;
        }
        
        if (useOptimization) {
            sortWithBinarySearch(arr);
        } else {
            sortStandard(arr);
        }
        
        tracker.stopTiming();
    }
    
    /**
     * Standard insertion sort implementation.
     */
    private void sortStandard(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            tracker.incrementArrayAccess();
            int j = i - 1;
            
            // Move elements greater than key one position ahead
            while (j >= 0 && arr[j] > key) {
                tracker.incrementComparison();
                tracker.incrementArrayAccess(); // arr[j] read
                
                arr[j + 1] = arr[j];
                tracker.incrementSwap();
                tracker.incrementArrayAccess(); // arr[j+1] write
                
                j--;
            }
            
            // One final comparison when loop exits (except when j < 0)
            if (j >= 0) {
                tracker.incrementComparison();
                tracker.incrementArrayAccess();
            }
            
            // Insert key at correct position
            arr[j + 1] = key;
            tracker.incrementArrayAccess();
        }
    }
    
    /**
     * Optimized insertion sort using binary search for insertion position.
     * Works well for nearly-sorted data.
     */
    private void sortWithBinarySearch(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            tracker.incrementArrayAccess();
            
            // Early termination: if element is already in correct position
            if (arr[i - 1] <= key) {
                tracker.incrementComparison();
                tracker.incrementArrayAccess();
                continue;
            }
            tracker.incrementComparison();
            tracker.incrementArrayAccess();
            
            // Find insertion position using binary search
            int pos = binarySearchPosition(arr, key, 0, i - 1);
            
            // Shift elements to make space
            System.arraycopy(arr, pos, arr, pos + 1, i - pos);
            tracker.incrementArrayAccess(i - pos); // Count shifts
            
            // Insert key at correct position
            arr[pos] = key;
            tracker.incrementSwap();
            tracker.incrementArrayAccess();
        }
    }
    
    /**
     * Finds the correct insertion position using binary search.
     * 
     * @param arr the array
     * @param key the element to insert
     * @param left left boundary
     * @param right right boundary
     * @return the position where key should be inserted
     */
    private int binarySearchPosition(int[] arr, int key, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            tracker.incrementComparison();
            tracker.incrementArrayAccess();
            
            if (arr[mid] == key) {
                return mid + 1;
            } else if (arr[mid] < key) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
    
    /**
     * Sorts an array and returns a new sorted array (non-destructive).
     * 
     * @param arr the array to sort
     * @return a new sorted array
     */
    public int[] sortCopy(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        int[] copy = arr.clone();
        tracker.incrementMemoryAllocation();
        sort(copy);
        return copy;
    }
    
    /**
     * Gets the performance tracker for metrics analysis.
     * 
     * @return the performance tracker
     */
    public PerformanceTracker getTracker() {
        return tracker;
    }
    
    /**
     * Checks if the array is sorted in ascending order.
     * 
     * @param arr the array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Main method for basic testing.
     */
    public static void main(String[] args) {
        InsertionSort sorter = new InsertionSort(true);
        
        // Test case 1: Random array
        int[] arr1 = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original: " + java.util.Arrays.toString(arr1));
        sorter.sort(arr1);
        System.out.println("Sorted: " + java.util.Arrays.toString(arr1));
        System.out.println("Is sorted: " + isSorted(arr1));
        System.out.println(sorter.getTracker());
        
        // Test case 2: Nearly sorted array
        System.out.println("\n--- Nearly Sorted Test ---");
        int[] arr2 = {1, 2, 3, 4, 5, 7, 6, 8, 9, 10};
        sorter.sort(arr2);
        System.out.println("Sorted: " + java.util.Arrays.toString(arr2));
        System.out.println(sorter.getTracker());
    }
}
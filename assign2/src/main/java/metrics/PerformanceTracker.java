package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Tracks performance metrics for sorting algorithms.
 * 
 * Metrics tracked:
 * - Number of comparisons
 * - Number of swaps/moves
 * - Array accesses
 * - Memory allocations
 * - Execution time
 * 
 * @author Student A
 */
public class PerformanceTracker {
    
    private long comparisons;
    private long swaps;
    private long arrayAccesses;
    private long memoryAllocations;
    private long startTime;
    private long endTime;
    private boolean timing;
    
    /**
     * Creates a new performance tracker with all metrics set to zero.
     */
    public PerformanceTracker() {
        reset();
    }
    
    /**
     * Resets all metrics to zero.
     */
    public void reset() {
        comparisons = 0;
        swaps = 0;
        arrayAccesses = 0;
        memoryAllocations = 0;
        startTime = 0;
        endTime = 0;
        timing = false;
    }
    
    /**
     * Starts timing execution.
     */
    public void startTiming() {
        startTime = System.nanoTime();
        timing = true;
    }
    
    /**
     * Stops timing execution.
     */
    public void stopTiming() {
        if (timing) {
            endTime = System.nanoTime();
            timing = false;
        }
    }
    
    /**
     * Increments the comparison counter by 1.
     */
    public void incrementComparison() {
        comparisons++;
    }
    
    /**
     * Increments the comparison counter by a specific amount.
     * 
     * @param count the number to add
     */
    public void incrementComparison(long count) {
        comparisons += count;
    }
    
    /**
     * Increments the swap counter by 1.
     */
    public void incrementSwap() {
        swaps++;
    }
    
    /**
     * Increments the swap counter by a specific amount.
     * 
     * @param count the number to add
     */
    public void incrementSwap(long count) {
        swaps += count;
    }
    
    /**
     * Increments the array access counter by 1.
     */
    public void incrementArrayAccess() {
        arrayAccesses++;
    }
    
    /**
     * Increments the array access counter by a specific amount.
     * 
     * @param count the number to add
     */
    public void incrementArrayAccess(long count) {
        arrayAccesses += count;
    }
    
    /**
     * Increments the memory allocation counter by 1.
     */
    public void incrementMemoryAllocation() {
        memoryAllocations++;
    }
    
    // Getters
    
    public long getComparisons() {
        return comparisons;
    }
    
    public long getSwaps() {
        return swaps;
    }
    
    public long getArrayAccesses() {
        return arrayAccesses;
    }
    
    public long getMemoryAllocations() {
        return memoryAllocations;
    }
    
    /**
     * Gets execution time in nanoseconds.
     * 
     * @return execution time in nanoseconds
     */
    public long getExecutionTimeNanos() {
        return endTime - startTime;
    }
    
    /**
     * Gets execution time in milliseconds.
     * 
     * @return execution time in milliseconds
     */
    public double getExecutionTimeMillis() {
        return (endTime - startTime) / 1_000_000.0;
    }
    
    /**
     * Gets execution time in seconds.
     * 
     * @return execution time in seconds
     */
    public double getExecutionTimeSeconds() {
        return (endTime - startTime) / 1_000_000_000.0;
    }
    
    /**
     * Exports metrics to a CSV file.
     * 
     * @param filename the output filename
     * @param arraySize the size of the sorted array
     * @param dataType the type of input data (e.g., "random", "sorted", "reverse")
     * @throws IOException if file cannot be written
     */
    public void exportToCSV(String filename, int arraySize, String dataType) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Check if file is empty to write header
            if (new java.io.File(filename).length() == 0) {
                writer.println("ArraySize,DataType,Comparisons,Swaps,ArrayAccesses,MemoryAllocations,TimeNanos,TimeMillis");
            }
            
            writer.printf("%d,%s,%d,%d,%d,%d,%d,%.6f%n",
                    arraySize,
                    dataType,
                    comparisons,
                    swaps,
                    arrayAccesses,
                    memoryAllocations,
                    getExecutionTimeNanos(),
                    getExecutionTimeMillis());
        }
    }
    
    /**
     * Creates a formatted string with all metrics.
     * 
     * @return formatted metrics string
     */
    @Override
    public String toString() {
        return String.format(
            "Performance Metrics:\n" +
            "  Comparisons: %,d\n" +
            "  Swaps: %,d\n" +
            "  Array Accesses: %,d\n" +
            "  Memory Allocations: %,d\n" +
            "  Execution Time: %.6f ms (%.2f µs)",
            comparisons,
            swaps,
            arrayAccesses,
            memoryAllocations,
            getExecutionTimeMillis(),
            getExecutionTimeNanos() / 1000.0
        );
    }
    
    /**
     * Creates a compact one-line summary.
     * 
     * @return compact summary string
     */
    public String toCompactString() {
        return String.format(
            "comp=%d, swaps=%d, time=%.3fms",
            comparisons,
            swaps,
            getExecutionTimeMillis()
        );
    }
}
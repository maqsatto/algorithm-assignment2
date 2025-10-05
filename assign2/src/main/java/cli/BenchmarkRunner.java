package cli;

import algorithms.InsertionSort;
import metrics.PerformanceTracker;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Command-line interface for benchmarking Insertion Sort.
 * 
 * Supports multiple input types:
 * - Random data
 * - Sorted data
 * - Reverse sorted data
 * - Nearly sorted data
 * 
 * @author Student A
 */
public class BenchmarkRunner {
    
    private static final Random random = new Random(42); // Fixed seed for reproducibility
    
    /**
     * Generates an array of random integers.
     * 
     * @param size the size of the array
     * @return array with random integers
     */
    public static int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size * 10);
        }
        return arr;
    }
    
    /**
     * Generates a sorted array.
     * 
     * @param size the size of the array
     * @return sorted array
     */
    public static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }
    
    /**
     * Generates a reverse sorted array.
     * 
     * @param size the size of the array
     * @return reverse sorted array
     */
    public static int[] generateReverseSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i - 1;
        }
        return arr;
    }
    
    /**
     * Generates a nearly sorted array (95% sorted with 5% random swaps).
     * 
     * @param size the size of the array
     * @return nearly sorted array
     */
    public static int[] generateNearlySortedArray(int size) {
        int[] arr = generateSortedArray(size);
        int swaps = Math.max(1, size / 20); // 5% swaps
        
        for (int i = 0; i < swaps; i++) {
            int idx1 = random.nextInt(size);
            int idx2 = random.nextInt(size);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        
        return arr;
    }
    
    /**
     * Generates an array with many duplicates.
     * 
     * @param size the size of the array
     * @return array with duplicates
     */
    public static int[] generateArrayWithDuplicates(int size) {
        int[] arr = new int[size];
        int uniqueValues = Math.max(1, size / 10);
        
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(uniqueValues);
        }
        
        return arr;
    }
    
    /**
     * Runs a single benchmark.
     * 
     * @param size array size
     * @param dataType type of data
     * @param useOptimization whether to use optimization
     */
    public static void runBenchmark(int size, String dataType, boolean useOptimization) {
        int[] arr;
        
        switch (dataType.toLowerCase()) {
            case "random":
                arr = generateRandomArray(size);
                break;
            case "sorted":
                arr = generateSortedArray(size);
                break;
            case "reverse":
                arr = generateReverseSortedArray(size);
                break;
            case "nearlysorted":
                arr = generateNearlySortedArray(size);
                break;
            case "duplicates":
                arr = generateArrayWithDuplicates(size);
                break;
            default:
                System.err.println("Unknown data type: " + dataType);
                return;
        }
        
        InsertionSort sorter = new InsertionSort(useOptimization);
        sorter.sort(arr);
        
        PerformanceTracker tracker = sorter.getTracker();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.printf("Benchmark Results - %s data (n=%,d)%n", dataType, size);
        System.out.println("Optimization: " + (useOptimization ? "ENABLED" : "DISABLED"));
        System.out.println("=".repeat(60));
        System.out.println(tracker);
        System.out.println("Sorted correctly: " + InsertionSort.isSorted(arr));
        System.out.println("=".repeat(60));
        
        // Export to CSV
        try {
            String filename = "benchmark_results.csv";
            tracker.exportToCSV(filename, size, dataType + "_" + (useOptimization ? "opt" : "std"));
            System.out.println("Results exported to: " + filename);
        } catch (Exception e) {
            System.err.println("Failed to export CSV: " + e.getMessage());
        }
    }
    
    /**
     * Runs a comprehensive benchmark suite.
     * 
     * @param sizes array of sizes to test
     */
    public static void runComprehensiveBenchmark(int[] sizes) {
        String[] dataTypes = {"random", "sorted", "reverse", "nearlysorted", "duplicates"};
        boolean[] optimizations = {false, true};
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPREHENSIVE BENCHMARK SUITE");
        System.out.println("=".repeat(80));
        
        for (int size : sizes) {
            for (String dataType : dataTypes) {
                for (boolean useOpt : optimizations) {
                    runBenchmark(size, dataType, useOpt);
                    
                    // Small delay to allow GC
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("BENCHMARK SUITE COMPLETED");
        System.out.println("=".repeat(80));
    }
    
    /**
     * Displays the interactive menu.
     */
    public static void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INSERTION SORT BENCHMARK TOOL");
        System.out.println("=".repeat(60));
        System.out.println("1. Run single benchmark");
        System.out.println("2. Run comprehensive benchmark");
        System.out.println("3. Quick test (small array)");
        System.out.println("4. Compare optimization impact");
        System.out.println("5. Exit");
        System.out.println("=".repeat(60));
        System.out.print("Select option: ");
    }
    
    /**
     * Main method - CLI entry point.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Command-line argument parsing
        if (args.length > 0) {
            try {
                if (args[0].equals("--comprehensive")) {
                    int[] sizes = {100, 1000, 10000, 100000};
                    runComprehensiveBenchmark(sizes);
                    return;
                } else if (args[0].equals("--size") && args.length >= 3) {
                    int size = Integer.parseInt(args[1]);
                    String dataType = args[2];
                    boolean useOpt = args.length > 3 && args[3].equals("--optimize");
                    runBenchmark(size, dataType, useOpt);
                    return;
                }
            } catch (Exception e) {
                System.err.println("Invalid arguments: " + e.getMessage());
                printUsage();
                return;
            }
        }
        
        // Interactive mode
        boolean running = true;
        
        while (running) {
            displayMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter array size: ");
                        int size = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Data type (random/sorted/reverse/nearlysorted/duplicates): ");
                        String dataType = scanner.nextLine();
                        
                        System.out.print("Use optimization? (y/n): ");
                        boolean useOpt = scanner.nextLine().toLowerCase().startsWith("y");
                        
                        runBenchmark(size, dataType, useOpt);
                        break;
                        
                    case 2:
                        System.out.print("Enter sizes (comma-separated, e.g., 100,1000,10000): ");
                        String[] sizeStrings = scanner.nextLine().split(",");
                        int[] sizes = new int[sizeStrings.length];
                        for (int i = 0; i < sizeStrings.length; i++) {
                            sizes[i] = Integer.parseInt(sizeStrings[i].trim());
                        }
                        runComprehensiveBenchmark(sizes);
                        break;
                        
                    case 3:
                        int[] testArr = generateRandomArray(20);
                        System.out.println("\nOriginal: " + Arrays.toString(testArr));
                        InsertionSort sorter = new InsertionSort(true);
                        sorter.sort(testArr);
                        System.out.println("Sorted: " + Arrays.toString(testArr));
                        System.out.println(sorter.getTracker());
                        break;
                        
                    case 4:
                        System.out.print("Enter array size for comparison: ");
                        int compSize = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Data type: ");
                        String compDataType = scanner.nextLine();
                        
                        System.out.println("\n--- WITHOUT Optimization ---");
                        runBenchmark(compSize, compDataType, false);
                        
                        System.out.println("\n--- WITH Optimization ---");
                        runBenchmark(compSize, compDataType, true);
                        break;
                        
                    case 5:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                        
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        scanner.close();
    }
    
    /**
     * Prints usage information.
     */
    private static void printUsage() {
        System.out.println("\nUsage:");
        System.out.println("  java cli.BenchmarkRunner");
        System.out.println("  java cli.BenchmarkRunner --comprehensive");
        System.out.println("  java cli.BenchmarkRunner --size <n> <datatype> [--optimize]");
        System.out.println("\nExamples:");
        System.out.println("  java cli.BenchmarkRunner --size 10000 random --optimize");
        System.out.println("  java cli.BenchmarkRunner --comprehensive");
    }
}
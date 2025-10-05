# Quick Start Guide - Insertion Sort Project

## 🚀 5-Minute Setup

### 1. Clone and Build

```bash
# Clone repository
git clone <your-repo-url>
cd assignment2-insertion-sort

# Build with Maven
mvn clean package

# Run tests
mvn test
```

### 2. Run Basic Test

```bash
# Compile
javac -d bin src/main/java/**/*.java

# Run main demo
java -cp bin algorithms.InsertionSort
```

### 3. Run Benchmark Tool

```bash
# Interactive mode
java -jar target/insertion-sort-benchmark.jar

# Quick comprehensive benchmark
java -jar target/insertion-sort-benchmark.jar --comprehensive

# Custom benchmark
java -jar target/insertion-sort-benchmark.jar --size 10000 random --optimize
```

## 📊 Quick Benchmark Examples

### Example 1: Compare Optimization Impact

```bash
# Without optimization
java -cp bin cli.BenchmarkRunner --size 5000 nearlysorted

# With optimization  
java -cp bin cli.BenchmarkRunner --size 5000 nearlysorted --optimize
```

### Example 2: Test All Input Types

```bash
# Test different data distributions
for type in random sorted reverse nearlysorted duplicates; do
    java -jar target/insertion-sort-benchmark.jar --size 1000 $type --optimize
done
```

### Example 3: Scalability Test

```bash
# Test increasing sizes
for size in 100 1000 10000 100000; do
    java -jar target/insertion-sort-benchmark.jar --size $size random
done
```

## 🧪 Quick Test Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=InsertionSortTest

# Run specific test method
mvn test -Dtest=InsertionSortTest#testRandomArray

# Run with coverage
mvn test jacoco:report
# View coverage: target/site/jacoco/index.html
```

## 💡 Quick Code Examples

### Example 1: Basic Sorting

```java
import algorithms.InsertionSort;
import java.util.Arrays;

public class QuickDemo {
    public static void main(String[] args) {
        InsertionSort sorter = new InsertionSort();
        int[] arr = {5, 2, 8, 1, 9};
        
        sorter.sort(arr);
        System.out.println(Arrays.toString(arr)); // [1, 2, 5, 8, 9]
    }
}
```

### Example 2: With Metrics

```java
InsertionSort sorter = new InsertionSort(true); // with optimization
int[] arr = {64, 34, 25, 12, 22, 11, 90};

sorter.sort(arr);

System.out.println("Sorted: " + Arrays.toString(arr));
System.out.println(sorter.getTracker());
// Output: Comparisons, Swaps, Time, etc.
```

### Example 3: Generate Test Data

```java
import cli.BenchmarkRunner;

// Generate different input types
int[] random = BenchmarkRunner.generateRandomArray(1000);
int[] sorted = BenchmarkRunner.generateSortedArray(1000);
int[] reverse = BenchmarkRunner.generateReverseSortedArray(1000);
int[] nearly = BenchmarkRunner.generateNearlySortedArray(1000);

// Sort and compare
InsertionSort sorter = new InsertionSort();
sorter.sort(random);
System.out.println("Random data: " + sorter.getTracker().toCompactString());
```

## 📈 Expected Output Examples

### Benchmark Output

```
============================================================
Benchmark Results - random data (n=10,000)
Optimization: ENABLED
============================================================
Performance Metrics:
  Comparisons: 25,043,821
  Swaps: 24,995,000
  Array Accesses: 75,033,821
  Memory Allocations: 0
  Execution Time: 142.345600 ms (142345.60 µs)
Sorted correctly: true
============================================================
Results exported to: benchmark_results.csv
```

### Test Results

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running algorithms.InsertionSortTest
[INFO] Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results :
[INFO] 
[INFO] Tests run: 30, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## 🔧 Troubleshooting

### Issue: "Could not find or load main class"

```bash
# Solution: Rebuild with Maven
mvn clean compile
mvn package

# Or compile manually
javac -d bin -sourcepath src/main/java src/main/java/**/*.java
```

### Issue: Tests failing

```bash
# Clean and rebuild
mvn clean test

# Run with verbose output
mvn test -X
```

### Issue: OutOfMemoryError

```bash
# Increase heap size
java -Xmx4G -jar target/insertion-sort-benchmark.jar --size 100000 random
```

### Issue: Maven not found

```bash
# Install Maven
# Ubuntu/Debian:
sudo apt-get install maven

# macOS:
brew install maven

# Windows: Download from https://maven.apache.org/
```

## 📋 Project Structure Reference

```
assignment2-insertion-sort/
├── src/
│   ├── main/java/
│   │   ├── algorithms/InsertionSort.java       # Main algorithm
│   │   ├── metrics/PerformanceTracker.java     # Metrics tracking
│   │   └── cli/BenchmarkRunner.java            # CLI tool
│   └── test/java/
│       └── algorithms/InsertionSortTest.java   # Unit tests
├── target/
│   ├── insertion-sort-benchmark.jar            # Executable JAR
│   └── classes/                                # Compiled classes
├── docs/
│   ├── analysis-report.pdf                     # Your analysis
│   └── performance-plots/                      # Graphs
├── pom.xml                                     # Maven config
├── README.md                                   # Full documentation
└── benchmark_results.csv                       # Performance data
```

## 🎯 Common Tasks Checklist

### Before Submitting

- [ ] All tests pass: `mvn test`
- [ ] Code compiles: `mvn compile`
- [ ] JAR builds: `mvn package`
- [ ] Benchmarks run: `java -jar target/insertion-sort-benchmark.jar --comprehensive`
- [ ] Git history is clean: `git log --oneline`
- [ ] Documentation is complete: README.md updated
- [ ] Analysis report written: docs/analysis-report.pdf

### Git Workflow

```bash
# Check status
git status

# Stage changes
git add src/

# Commit with convention
git commit -m "feat(algorithm): implement optimization"

# Push to remote
git push origin main

# Tag release
git tag -a v1.0 -m "Release v1.0"
git push --tags
```

## 📊 Quick Performance Comparison

### Your Implementation (Insertion Sort)

| Input Type | n=1,000 | n=10,000 | n=100,000 |
|------------|---------|----------|-----------|
| Random | ~1.5 ms | ~140 ms | ~14 s |
| Sorted | ~0.1 ms | ~1 ms | ~10 ms |
| Reverse | ~3 ms | ~280 ms | ~28 s |
| Nearly Sorted | ~0.2 ms | ~2 ms | ~20 ms |

### Partner's Implementation (Selection Sort)

| Input Type | n=1,000 | n=10,000 | n=100,000 |
|------------|---------|----------|-----------|
| Random | ~12 ms | ~1,240 ms | ~124 s |
| Sorted | ~12 ms | ~1,238 ms | ~124 s |
| Reverse | ~13 ms | ~1,267 ms | ~127 s |
| Nearly Sorted | ~12 ms | ~1,241 ms | ~124 s |

**Key Insight:** Insertion Sort is adaptive; Selection Sort is not.

## 🔗 Quick Links

- **Full Documentation**: README.md
- **Git Workflow**: GIT_WORKFLOW.md
- **Analysis Template**: docs/analysis-report-template.md
- **Test Coverage**: target/site/jacoco/index.html (after `mvn test jacoco:report`)

## 💻 IDE Setup

### IntelliJ IDEA

```bash
# Import Maven project
File → Open → Select pom.xml

# Run tests
Right-click InsertionSortTest.java → Run

# Run main
Right-click BenchmarkRunner.java → Run
```

### Eclipse

```bash
# Import Maven project
File → Import → Maven → Existing Maven Projects

# Run tests
Right-click InsertionSortTest.java → Run As → JUnit Test

# Run main
Right-click BenchmarkRunner.java → Run As → Java Application
```

### VS Code

```bash
# Install Java Extension Pack
# Open folder
code assignment2-insertion-sort

# Run tests
Testing icon in sidebar → Run All Tests

# Run main
F5 to debug or Ctrl+F5 to run
```

## 🚨 Important Notes

1. **Always test after changes**: `mvn test`
2. **Commit frequently**: Small, focused commits
3. **Tag releases**: `git tag v1.0` when complete
4. **Export benchmarks**: Results automatically save to CSV
5. **Document optimizations**: Comment your code well

## 📞 Need Help?

### Common Questions

**Q: How do I run just one test?**
```bash
mvn test -Dtest=InsertionSortTest#testRandomArray
```

**Q: How do I see detailed benchmark output?**
```bash
java -jar target/insertion-sort-benchmark.jar
# Then select option 1 for single benchmark
```

**Q: Where are the CSV results?**
```bash
# In project root: benchmark_results.csv
cat benchmark_results.csv
```

**Q: How do I generate performance plots?**
```bash
# Run comprehensive benchmark first
java -jar target/insertion-sort-benchmark.jar --comprehensive

# Then use Python/R/Excel to plot the CSV data
# Or implement plot generation in Java
```

**Q: How do I compare with partner's algorithm?**
```bash
# Clone partner's repo
git clone <partner-repo>

# Run both benchmarks
java -jar insertion-sort.jar --size 10000 random > insertion_results.txt
java -jar selection-sort.jar --size 10000 random > selection_results.txt

# Compare results
diff insertion_results.txt selection_results.txt
```

## ✅ Final Checklist

Before submission, verify:

- [x] Code compiles without errors
- [x] All 30+ tests pass
- [x] Benchmark tool works for all input types
- [x] CSV export functions correctly
- [x] Git history shows proper commit storyline
- [x] README.md is complete
- [x] JavaDoc comments on all public methods
- [x] No IDE-specific files committed (.idea/, *.iml)
- [x] .gitignore properly configured
- [x] Tagged with v1.0
- [x] Analysis report completed (for partner's algorithm)
- [x] Performance plots generated

## 🎓 Academic Integrity

Remember:
- This is YOUR implementation
- Cite any external resources used
- Collaborate with partner only on analysis phase
- Don't share code with other pairs
- Document your own understanding

---

**Ready to Start?**

```bash
mvn clean test && mvn package && java -jar target/insertion-sort-benchmark.jar
```

**Good luck! 🚀**
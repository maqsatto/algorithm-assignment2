# Git Workflow Guide

This document outlines the Git workflow for the Insertion Sort implementation project.

## 📋 Branch Strategy

### Main Branches

- **`main`** - Production-ready code only
  - Only merge from feature branches after testing
  - Tag releases: `v0.1`, `v1.0`, etc.
  - Protected branch (require PR reviews if working in team)

### Feature Branches

Create specific branches for each major feature:

- `feature/algorithm` - Core sorting algorithm
- `feature/metrics` - Performance tracking system
- `feature/testing` - Unit tests
- `feature/cli` - Command-line interface
- `feature/optimization` - Performance improvements
- `feature/docs` - Documentation updates

## 🚀 Commit Convention

Follow conventional commits format:

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

### Types

- `feat` - New feature
- `fix` - Bug fix
- `test` - Adding or updating tests
- `docs` - Documentation changes
- `perf` - Performance improvements
- `refactor` - Code refactoring
- `style` - Code style changes (formatting, etc.)
- `chore` - Maintenance tasks
- `init` - Initial commit

### Examples

```bash
feat(algorithm): implement baseline insertion sort
feat(optimization): add binary search for nearly-sorted data
test(algorithm): add edge case tests for empty arrays
fix(edge-cases): handle null array input
docs(readme): add usage examples
perf(benchmark): optimize metrics collection
```

## 📝 Step-by-Step Workflow

### Initial Setup

```bash
# Create repository
mkdir assignment2-insertion-sort
cd assignment2-insertion-sort
git init

# Create initial structure
mkdir -p src/{main,test}/java/{algorithms,metrics,cli}
mkdir -p docs/performance-plots

# Initial commit
git add .
git commit -m "init: maven project structure, junit5, ci setup"

# Create main branch and tag
git tag v0.1
```

### Feature Development Workflow

#### 1. Create Feature Branch

```bash
# Start from main
git checkout main
git pull

# Create feature branch
git checkout -b feature/metrics
```

#### 2. Implement Feature

```bash
# Make changes to PerformanceTracker.java
# ...

# Stage and commit
git add src/main/java/metrics/PerformanceTracker.java
git commit -m "feat(metrics): performance counters and CSV export"
```

#### 3. Multiple Commits in Feature

```bash
# Add more functionality
git commit -m "feat(metrics): add execution time tracking"
git commit -m "feat(metrics): implement CSV export functionality"
git commit -m "test(metrics): add unit tests for tracker"
```

#### 4. Merge to Main

```bash
# Update from main first
git checkout main
git pull

# Merge feature branch
git merge feature/metrics --no-ff -m "Merge feature/metrics into main"

# Or use rebase for cleaner history
git checkout feature/metrics
git rebase main
git checkout main
git merge feature/metrics --ff-only
```

#### 5. Clean Up

```bash
# Delete feature branch after merge
git branch -d feature/metrics

# Push to remote
git push origin main
git push --tags
```

## 🔄 Complete Development Timeline

### Phase 1: Foundation (Week 1)

```bash
# Commit 1
git checkout -b feature/metrics
# Implement PerformanceTracker
git commit -m "feat(metrics): performance counters and CSV export"
git checkout main
git merge feature/metrics
git tag v0.1

# Commit 2
git checkout -b feature/algorithm
# Implement basic InsertionSort
git commit -m "feat(algorithm): baseline insertion-sort implementation"
git checkout main
git merge feature/algorithm
```

### Phase 2: Testing (Week 1-2)

```bash
# Commit 3
git checkout -b feature/testing
# Create InsertionSortTest
git commit -m "test(algorithm): comprehensive test suite with edge cases"
git commit -m "test(algorithm): add property-based tests"
git commit -m "test(metrics): validate performance tracking"
git checkout main
git merge feature/testing
```

### Phase 3: CLI & Benchmarking (Week 2)

```bash
# Commit 4
git checkout -b feature/cli
# Implement BenchmarkRunner
git commit -m "feat(cli): benchmark runner with configurable input sizes"
git commit -m "feat(cli): add interactive menu system"
git commit -m "feat(cli): support multiple input types"
git checkout main
git merge feature/cli
```

### Phase 4: Optimization (Week 2-3)

```bash
# Commit 5
git checkout -b feature/optimization
# Add binary search optimization
git commit -m "feat(optimization): binary search for nearly-sorted data"
git commit -m "feat(optimization): early termination for sorted arrays"
git commit -m "test(optimization): verify optimization correctness"
git checkout main
git merge feature/optimization
```

### Phase 5: Documentation (Week 3)

```bash
# Commit 6
git commit -m "docs(readme): usage instructions and complexity analysis"
git commit -m "docs(javadoc): comprehensive API documentation"
```

### Phase 6: Performance Tuning (Week 3)

```bash
# Commit 7
git checkout -b feature/optimization
git commit -m "perf(benchmark): JMH harness for accurate measurements"
git commit -m "perf(algorithm): reduce object allocations"
git checkout main
git merge feature/optimization
```

### Phase 7: Bug Fixes (Week 3-4)

```bash
# Commit 8
git commit -m "fix(edge-cases): handle empty and single-element arrays"
git commit -m "fix(metrics): correct swap counter in optimized version"
```

### Phase 8: Release (Week 4)

```bash
# Commit 9
git commit -m "chore: prepare for v1.0 release"
git commit -m "release: v1.0 with complete implementation"
git tag v1.0
git push --tags
```

## 📊 Expected Commit History

```
* release: v1.0 with complete implementation (HEAD -> main, tag: v1.0)
* fix(metrics): correct swap counter in optimized version
* fix(edge-cases): handle empty and single-element arrays
* perf(algorithm): reduce object allocations
* perf(benchmark): JMH harness for accurate measurements
*   Merge feature/optimization
|\
| * test(optimization): verify optimization correctness
| * feat(optimization): early termination for sorted arrays
| * feat(optimization): binary search for nearly-sorted data
|/
* docs(javadoc): comprehensive API documentation
* docs(readme): usage instructions and complexity analysis
*   Merge feature/cli
|\
| * feat(cli): support multiple input types
| * feat(cli): add interactive menu system
| * feat(cli): benchmark runner with configurable input sizes
|/
*   Merge feature/testing
|\
| * test(metrics): validate performance tracking
| * test(algorithm): add property-based tests
| * test(algorithm): comprehensive test suite with edge cases
|/
*   Merge feature/algorithm
|\
| * feat(algorithm): baseline insertion-sort implementation
|/
*   Merge feature/metrics (tag: v0.1)
|\
| * feat(metrics): performance counters and CSV export
|/
* init: maven project structure, junit5, ci setup
```

## 🔍 Useful Git Commands

### View History

```bash
# Pretty log
git log --oneline --graph --decorate --all

# Show commits by author
git log --author="Student A"

# Show commits in date range
git log --since="2 weeks ago"
```

### Branch Management

```bash
# List all branches
git branch -a

# Delete local branch
git branch -d feature/old-feature

# Delete remote branch
git push origin --delete feature/old-feature

# Rename branch
git branch -m old-name new-name
```

### Undoing Changes

```bash
# Undo last commit (keep changes)
git reset --soft HEAD~1

# Undo last commit (discard changes)
git reset --hard HEAD~1

# Amend last commit message
git commit --amend -m "new message"

# Discard working directory changes
git checkout -- file.java
```

### Stashing

```bash
# Save work in progress
git stash save "WIP: implementing feature"

# List stashes
git stash list

# Apply most recent stash
git stash pop

# Apply specific stash
git stash apply stash@{0}
```

## 🏷️ Tagging Strategy

### Creating Tags

```bash
# Lightweight tag
git tag v0.1

# Annotated tag (recommended)
git tag -a v1.0 -m "Release version 1.0 - Complete implementation"

# Tag specific commit
git tag -a v0.5 abc1234 -m "Mid-project checkpoint"
```

### Pushing Tags

```bash
# Push specific tag
git push origin v1.0

# Push all tags
git push --tags
```

### Version Numbering

- `v0.1` - Initial working version (metrics + basic algorithm)
- `v0.5` - Mid-project checkpoint (testing + CLI added)
- `v0.9` - Feature complete (all features, needs polish)
- `v1.0` - Final release (all deliverables complete)

## 🤝 Collaboration Tips

### Before Starting Work

```bash
git checkout main
git pull
git checkout -b feature/my-feature
```

### Before Committing

```bash
# Check what changed
git status
git diff

# Stage selectively
git add -p

# Verify staged changes
git diff --staged
```

### Code Review Process

```bash
# Create feature branch
git checkout -b feature/new-feature

# Make commits
git commit -m "feat: implement new feature"

# Push to remote
git push -u origin feature/new-feature

# Create pull request on GitHub
# After review and approval, merge to main
```

## ⚠️ Best Practices

### Do's ✅

- Commit early and often
- Write descriptive commit messages
- Keep commits focused (one logical change per commit)
- Test before committing
- Use branches for new features
- Tag releases
- Pull before push

### Don'ts ❌

- Don't commit generated files (*.class, target/)
- Don't commit IDE-specific files (.idea/, *.iml)
- Don't commit sensitive data
- Don't force push to main
- Don't commit broken code
- Don't use vague messages ("fixed stuff", "changes")

## 📚 Resources

- [Git Documentation](https://git-scm.com/doc)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)

---

**Last Updated:** October 2025  
**Version:** 1.0
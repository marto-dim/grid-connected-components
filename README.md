# grid-connected-components
Union–Find playground with **WQU_PC** and **SW_UF**

This project contains two complementary algorithms for counting connected components on large binary grids.  
They are chosen because each one is optimal under different memory constraints.

---

## Why Two Algorithms?

Connected components on large grids can't be handled efficiently by a single technique.  
Some grids fit fully into memory, others are several gigabytes in size and must be streamed.  
To cover all cases, the project includes two algorithms:

---

## 1. WQU_PC — Weighted Quick Union with Path Compression

A classic, full-grid Union–Find.

### Why use it?
- Supports `find(point)`
- Supports `connected(a, b)`
- Very fast thanks to path compression
- Gives exact component IDs

### When to use?
For grids that **fit in RAM** (up to ~200M cells).

### Why not always?
It stores a UF node for every marked cell.  
For very large grids (tens of millions of rows/columns), this becomes too large to hold in memory.

---

## 2. SW_UF — Sliding Window Union–Find (Streaming)

A streaming connected-components labeling algorithm.

### Why use it?
- Uses **O(COLS)** memory (small - hopefully)
- Can process multi–GB grids (10k×10k, 50k×50k, 100k×100k, etc.)
- Extremely fast for huge datasets

### Limitation
Only returns the **number of components**.  
Does **not** support `find()` or `connected()` queries because the grid is processed row-by-row and not stored.

---

## Summary Table

| Algorithm | Memory Usage | Query Support | Best For |
|----------|---------------|----------------|-----------|
| **WQU_PC** | High | ✔ Yes | Small/medium grids (≤ 200M cells) |
| **SW_UF** | Very Low (O(cols)) | ✘ No | Very large grids (billions of cells) |

---

## Why this design?

- **WQU_PC** provides full functionality.
- **SW_UF** provides extreme scalability.
- Together, they allow this tool to handle everything from tiny test inputs to multi-gigabyte grid files.

---

## Why this design?

- **WQU_PC** provides full functionality.
- **SW_UF** provides extreme scalability.
- Together, they allow this tool to handle everything from tiny test inputs to multi-gigabyte grid files.

---

# 📦 Full API Overview

This section explains all the public interfaces and how the system is structured.

---

## Grid Interfaces

Two different grid abstractions are supported.

### **1. Full Grid (for WQU_PC)**

```java
public interface Grid {
    int rows();
    int cols();
    boolean isMarked(int r, int c);
}
```

### **2. Streaming Grid (for SW_UF)**

```java
public interface StreamingGrid {
    int rows();
    int cols();
    boolean hasNextRow();
    boolean[] nextRow();
}
```

Reads the grid one row at a time.
Memory usage is exactly one row + auxiliary O(cols) label arrays.

# 🧮 Connected Components APIs

Two complementary algorithm-level APIs exist:

---

### **Basic counting-only API (SW_UF)**

```java
public interface ConnectedComponentsBasic {
    int count();
}
```

Used by streaming mode; provides only the total number of components.

### **Full query API (WQU_PC)**

```java
public interface ConnectedComponentsQuery 
        extends ConnectedComponentsBasic {

    int find(Point2D p);
    boolean connected(Point2D a, Point2D b);
}
```

This allows the user to:
 - ask which figure a cell belongs to
 - ask whether two cells belong to the same component

# ⚙️ Engine and Execution Flow
The Engine is responsible for:
 - Reading grid dimensions
 - Selecting the appropriate algorithm
 - Running it
 - Entering interactive mode (if WQU_PC was used)

### **Key API**
```java
public interface ComponentCounter {
GridSizeInfo inspect(String file);
ComponentResult compute(String file, boolean useWQU_PC);
}
```

### **Interactive Mode (WQU_PC only)**
After computing components using WQU_PC, the program enters an interactive shell.

Commands
 - find r c
 - connected r1 c1 r2 c2
 - exit

# ⚗️ Algorithm Details

## **WQU_PC — Full Grid Union–Find**
Layout
 - Each marked cell receives a unique ID:

```java
id[row][col] = next++
```

Unions are applied to:
 - the left neighbor
 - the upper neighbor

Core WQU_PC
 - parent[] - parent pointer
 - size[]   - weighted union
 - path compression inside findRoot()

Memory Usage
For N cells:
```java
parent[N] + size[N] + id[N]
```

## **SW_UF — Sliding Window Union–Find (Streaming)**
Maintain only:
 - prevRow[] – previous row’s bits
 - prevLab[] – previous row’s labels
 - currLab[] – current row’s labels
 - a small, dynamically growing Union–Find



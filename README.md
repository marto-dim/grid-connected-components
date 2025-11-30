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

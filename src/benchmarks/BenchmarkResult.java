package benchmarks;

public record BenchmarkResult(
        String algo,
        int size,
        double density,
        int components,
        double timeMs,
        long memoryMB
) { }

package benchmarks;

public final class BenchmarkUtils {

    public static void stabilizeGC() {
        System.gc();
        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
    }

    public static long usedBytes() {
        Runtime rt = Runtime.getRuntime();
        return rt.totalMemory() - rt.freeMemory();
    }

    public static double nanosToMs(long nanos) {
        return nanos / 1_000_000.0;
    }

    private static final class LongBox {
        long value;
    }

    public static <T> BenchmarkResult<T> measureWithPeak(MonitoredTask<T> task) {

        stabilizeGC();
        long startUsed = usedBytes();

        LongBox peak = new LongBox();
        peak.value = startUsed;

        long start = System.nanoTime();

        T result = task.run(() -> {
            long cur = usedBytes();
            if (cur > peak.value) peak.value = cur;
        });

        long end = System.nanoTime();
        stabilizeGC();
        long peakDelta = peak.value - startUsed;

        return new BenchmarkResult<>(result, end - start, peakDelta);
    }

    public interface MonitoredTask<T> {
        T run(PeakTracker tracker);
    }

    public interface PeakTracker {
        void check();
    }

    public record BenchmarkResult<T>(T result, long nanos, long peakBytes) {}
}

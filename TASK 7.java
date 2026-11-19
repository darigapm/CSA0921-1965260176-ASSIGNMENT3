import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SemesterResultProcessor {
    public static void main(String[] args) throws InterruptedException {
        int totalFiles = 10000;
        int poolSize = 4;

        ExecutorService pool = Executors.newFixedThreadPool(poolSize);
        ConcurrentHashMap<String, Double> results = new ConcurrentHashMap<>();
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        List<Callable<Boolean>> tasks = new ArrayList<>();
        for (int i = 1; i <= totalFiles; i++) {
            final int id = i;
            tasks.add(() -> {
                try {
                    if (id % 500 == 0) throw new java.io.FileNotFoundException("Missing script_" + id);
                    if (id % 777 == 0) throw new NumberFormatException("Corrupt score in script_" + id);
                    results.put("STU" + id, 85.0);
                    successCount.incrementAndGet();
                    return true;
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    return false;
                }
            });
        }

        long start = System.currentTimeMillis();
        pool.invokeAll(tasks);
        pool.shutdown();
        long elapsed = System.currentTimeMillis() - start;

        System.out.printf("Pool=%d | Time=%dms | Success=%d | Failed=%d%n",
                poolSize, elapsed, successCount.get(), failureCount.get());
    }
}

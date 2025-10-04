package solution.multithreading;

import java.util.concurrent.*;

/**
 * Uses a thread pool to count prime numbers in a large range.
 * Each worker counts primes in its sub-range.
 */
public class PrimeCounterExample {

    // A Callable task responsible for computing the number of primes in the given range
    private static class PrimeCounterTask implements Callable<Integer> {
        private final int start;
        private final int end;

        public PrimeCounterTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() {
            int count = 0;
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    count++;
                }
            }
            System.out.println("Worker " + Thread.currentThread().getName() + " responsible for the range (" + start + "-" + end + ") finished. Found " + count + " prime numbers");
            return count;
        }

        private boolean isPrime(int n) {
            if (n < 2) return false;
            if (n % 2 == 0 && n != 2) return false;
            for (int i = 3; i * i <= n; i += 2) {
                if (n % i == 0) return false;
            }
            return true;
        }
    }

    public static void main(String[] args) {
        int range = 100_000;  // total range
        int chunkSize = 10_000;
        int numChunks = range / chunkSize;

        ExecutorService exec = Executors.newFixedThreadPool(4);
        Future<Integer>[] results = new Future[numChunks];

        try {
            int start = 1;
            for (int i = 0; i < numChunks; i++) {
                int end = start + chunkSize - 1;
                results[i] = exec.submit(new PrimeCounterTask(start, end));
                start = end + 1;
            }

            int totalPrimes = 0;
            for (Future<Integer> f : results) {
                totalPrimes += f.get();  // waits for worker results
            }
            System.out.println("Total primes in range = " + totalPrimes);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            exec.shutdown();
            try {
                exec.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                System.out.println(e);;
            }
        }
    }
}
package multithreading.forkjoin;

import java.util.concurrent.*;

public class PrimeCounterForkJoin {

    static class PrimeCounterTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        public PrimeCounterTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            // if base case, compute directly
            // else create two subtasks of type PrimeCounterTask (for left and right half of the range)
            // run left task asynchronously using left.fork()
            // run right in this thread using .compute
            // Call int leftResult = leftTask.join() to wait for the result of the left task

            return 0; // return the count computed by leftTask + the count computed by right task
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
        ForkJoinPool pool = new ForkJoinPool();

        PrimeCounterTask task = new PrimeCounterTask(1, 100_000);
        int totalPrimes = pool.invoke(task);

        System.out.println("Total primes in range = " + totalPrimes);
        pool.shutdown();
    }
}
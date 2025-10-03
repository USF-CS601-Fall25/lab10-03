package multithreading;

import java.util.concurrent.*;

/**
 * Uses a thread pool to count prime numbers in a large range.
 * Each worker counts primes in its sub-range.
 */

public class PrimeCounterExample {
    // Write a static nested class PrimeCounterTask that implements Callable<Integer> interface.
    // It is responsible for computing the number of primes in the given range from start to end.

    // The constructor should take start and end values.
    // The call() method should return the number of prime numbers in that range.
    // Use the provided static isPrime(int n) method to test if the number if prime.

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0 && n != 2) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int range = 100000;  // total range
        int chunkSize = 10000; // size of one chunk
        int numChunks = range / chunkSize;
        // TODO:

        // Create a thread pool with 4 threads
        // Create an array of Future<Integer>[] to hold results from each task.
        // Submit one PrimeCounterTask for each chunk of the range (e.g., 1–10,000, 10,001–20,000, …) and store the returned Future in the array results.
        // Iterate over the results, call get() to wait for each task to finish.
        // and combine counts to get the total count.
        // Shutdown the pool (and call await termination).
    }
}
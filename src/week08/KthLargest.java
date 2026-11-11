package week08;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

// Idea: keep a min-heap of the k biggest numbers seen; whenever it grows past k, throw out the smallest.
// Mistake on the way: I used a max-heap at first, which kept the wrong numbers and needed every value stored.
// Complexity: sort version O(n log n) time; heap version O(n log k) time and O(k) space.
public class KthLargest {

    static void validate(int[] nums, int k) {
        if (k < 1 || k > nums.length) {
            throw new IllegalArgumentException("k must be between 1 and the array length");
        }
    }

    // Simple version: sort a copy and pick the index
    static int kthLargestSort(int[] nums, int k) {
        validate(nums, k);
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        return sorted[sorted.length - k];
    }

    // Heap version: min-heap that never holds more than k numbers
    static int kthLargest(int[] nums, int k) {
        validate(nums, k);
        PriorityQueue<Integer> heap = new PriorityQueue<>(); // smallest on top

        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k) {
                heap.poll(); // the smallest can't be in the top k
            }
        }
        return heap.peek(); // smallest of the k largest = kth largest
    }

    static void check(int[] nums, int k, Integer expected) {
        String want = (expected == null) ? "exception" : String.valueOf(expected);
        String got;
        try {
            int heap = kthLargest(nums, k);
            int sort = kthLargestSort(nums, k);
            got = (heap == sort) ? String.valueOf(heap) : "heap " + heap + " vs sort " + sort;
        } catch (IllegalArgumentException e) {
            got = "exception";
        }
        System.out.println((got.equals(want) ? "PASS" : "FAIL") + "  " + Arrays.toString(nums)
                + ", k=" + k + "  expected " + want + ", got " + got);
    }

    static void randomTest() {
        Random rng = new Random(42);
        int mismatches = 0;
        for (int t = 0; t < 100; t++) {
            int length = rng.nextInt(20) + 1;           // 1 to 20, so k always has a valid choice
            int[] nums = new int[length];
            for (int i = 0; i < length; i++) nums[i] = rng.nextInt(41) - 20;
            int k = rng.nextInt(length) + 1;            // 1 to length

            if (kthLargest(nums, k) != kthLargestSort(nums, k)) {
                mismatches++;
                System.out.println("ERROR: " + Arrays.toString(nums) + ", k=" + k);
            }
        }
        System.out.println("\nRandom test: " + (100 - mismatches) + "/100 matched");
    }

    public static void main(String[] args) {
        check(new int[]{3, 2, 1, 5, 6, 4}, 2, 5);
        check(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, 4);
        check(new int[]{1}, 1, 1);
        check(new int[]{7, 7, 7}, 2, 7);
        check(new int[]{-1, -2}, 2, -2);
        check(new int[]{1, 2}, 3, null);   // throws
        // Edge cases
        check(new int[]{1, 2, 3}, 0, null);         // k too small, throws
        check(new int[]{9, 1, 8, 2, 7}, 1, 9);      // k = 1 is just the maximum

        randomTest();
    }
}

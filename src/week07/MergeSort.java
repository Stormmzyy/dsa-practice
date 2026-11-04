package week07;

import java.util.Arrays;
import java.util.Random;

// Idea: split in half, sort each half by calling mergeSort, then merge the two sorted halves.
// Mistake on the way: I forgot the base case, so the recursion kept splitting empty arrays forever.
// Complexity: merge sort O(n log n) time, O(n) space; insertion sort O(n^2) time, O(1) extra space.
public class MergeSort {

    static int[] mergeSort(int[] nums) {
        if (nums.length <= 1) {
            return nums.clone(); // 0 or 1 element is already sorted
        }
        int mid = nums.length / 2;
        int[] left = mergeSort(Arrays.copyOfRange(nums, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(nums, mid, nums.length));
        return merge(left, right);
    }

    // Same merge as Week 5: always take the smaller front element
    static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) result[k++] = a[i++];
            else result[k++] = b[j++];
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    // Insertion sort: slide each element left until it's in place
    static int[] insertionSort(int[] input) {
        int[] nums = input.clone();
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > current) {
                nums[j + 1] = nums[j]; // shift bigger values right
                j--;
            }
            nums[j + 1] = current;
        }
        return nums;
    }

    static void check(int[] input, int[] expected) {
        int[] merged = mergeSort(input);
        int[] inserted = insertionSort(input);
        boolean pass = Arrays.equals(merged, expected) && Arrays.equals(inserted, expected);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(input)
                + "  expected " + Arrays.toString(expected) + ", got " + Arrays.toString(merged));
    }

    static void randomTest() {
        Random rng = new Random(42);
        int mismatches = 0;
        for (int t = 0; t < 200; t++) {
            int length = rng.nextInt(30);            // includes 0 and 1
            int[] nums = new int[length];
            for (int i = 0; i < length; i++) {
                nums[i] = rng.nextInt(21) - 10;      // -10 to 10, so lots of duplicates
            }
            int[] expected = nums.clone();
            Arrays.sort(expected);                   // only used to check the answer
            if (!Arrays.equals(mergeSort(nums), expected)) {
                mismatches++;
                System.out.println("ERROR: " + Arrays.toString(nums));
            }
        }
        System.out.println("\nRandom test: " + (200 - mismatches) + "/200 matched Arrays.sort");
    }

    static void timingTest() {
        Random rng = new Random(7);
        int[] nums = new int[20_000];
        for (int i = 0; i < nums.length; i++) nums[i] = rng.nextInt(1_000_000) - 500_000;

        long start = System.nanoTime();
        mergeSort(nums);
        long mergeTime = System.nanoTime() - start;

        start = System.nanoTime();
        insertionSort(nums);
        long insertionTime = System.nanoTime() - start;

        System.out.println("\nTiming with 20,000 numbers:");
        System.out.printf("Merge sort:     %.2f ms%n", mergeTime / 1_000_000.0);
        System.out.printf("Insertion sort: %.2f ms%n", insertionTime / 1_000_000.0);
    }

    public static void main(String[] args) {
        check(new int[]{5, 2, 9, 1, 5, 6}, new int[]{1, 2, 5, 5, 6, 9});
        check(new int[]{3, -1, 0, -7}, new int[]{-7, -1, 0, 3});
        check(new int[]{2, 2, 2}, new int[]{2, 2, 2});
        check(new int[]{1}, new int[]{1});
        check(new int[]{}, new int[]{});
        // Edge cases
        check(new int[]{5, 4, 3, 2, 1}, new int[]{1, 2, 3, 4, 5});   // reverse order
        check(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});   // already sorted

        randomTest();
        timingTest();
    }
}

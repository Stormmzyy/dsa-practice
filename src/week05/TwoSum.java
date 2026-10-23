package week05;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

// Pattern: trade space for time by remembering seen values in a HashMap.
// Version A: O(n^2) time, O(1) space. Version B: O(n) time, O(n) space.
public class TwoSum {

    // Version A: check every pair
    static int[] twoSumBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("No valid pair");
    }

    // Version B: one pass with a value -> index map
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int needed = target - nums[i];
            if (seen.containsKey(needed)) {
                return new int[]{seen.get(needed), i};
            }
            seen.put(nums[i], i);
        }
        throw new IllegalArgumentException("No valid pair");
    }

    static void test(int[] nums, int target) {
        System.out.println(Arrays.toString(nums) + ", " + target
                + " -> A: " + Arrays.toString(twoSumBrute(nums, target))
                + "  B: " + Arrays.toString(twoSum(nums, target)));
    }

    static void timingTask() {
        int n = 50_000;
        int[] nums = new int[n];
        for (int i = 0; i < n - 2; i++) {
            nums[i] = i * 2;          // even numbers, too small to reach the target
        }
        nums[n - 2] = 1_000_001;      // the only valid pair is the last two
        nums[n - 1] = 1_000_003;
        int target = 2_000_004;

        long start = System.nanoTime();
        int[] a = twoSumBrute(nums, target);
        long bruteTime = System.nanoTime() - start;

        start = System.nanoTime();
        int[] b = twoSum(nums, target);
        long mapTime = System.nanoTime() - start;

        System.out.println("\nTiming with " + n + " elements:");
        System.out.printf("Version A (nested loops): %.2f ms -> %s%n", bruteTime / 1_000_000.0, Arrays.toString(a));
        System.out.printf("Version B (HashMap):      %.2f ms -> %s%n", mapTime / 1_000_000.0, Arrays.toString(b));
    }

    public static void main(String[] args) {
        test(new int[]{2, 7, 11, 15}, 9);          // [0, 1]
        test(new int[]{3, 2, 4}, 6);               // [1, 2]
        test(new int[]{3, 3}, 6);                  // [0, 1]
        test(new int[]{-1, -2, -3, -4, -5}, -8);   // [2, 4]
        // Edge cases
        test(new int[]{0, 4, 3, 0}, 0);            // [0, 3] (zeros)
        test(new int[]{5, 1}, 6);                  // [0, 1] (smallest valid input)

        timingTask();
    }
}

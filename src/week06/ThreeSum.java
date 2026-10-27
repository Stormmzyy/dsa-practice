package week06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

// Idea: sort, fix one number, then use two pointers (Problem 2's trick) to find the other two.
// Complexity: slow version O(n^3) time, fast version O(n^2) time; O(n) extra space for the sorted copy.
// What I had to think about: skipping repeated values in three places (the fixed number,
// and both pointers after a match), otherwise the same triplet gets added more than once.
public class ThreeSum {

    // Slow version: try every triple, use a Set to drop duplicates
    static List<List<Integer>> threeSumBrute(int[] nums) {
        Set<List<Integer>> found = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        int[] triple = {nums[i], nums[j], nums[k]};
                        Arrays.sort(triple); // ascending, so duplicates look identical
                        found.add(List.of(triple[0], triple[1], triple[2]));
                    }
                }
            }
        }
        return new ArrayList<>(found);
    }

    // Fast version: sort, fix one number, two pointers for the rest
    static List<List<Integer>> threeSum(int[] input) {
        int[] nums = input.clone(); // sort a copy so the caller's array isn't changed
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // same fixed number as last time
            if (nums[i] > 0) break; // smallest number is positive, nothing can sum to zero

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;   // too small, need a bigger number
                } else if (sum > 0) {
                    right--;  // too big, need a smaller number
                } else {
                    result.add(List.of(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                }
            }
        }
        return result;
    }

    // Order of triplets doesn't matter, so compare as sets (and check for duplicates)
    static boolean sameResult(List<List<Integer>> a, List<List<Integer>> b) {
        Set<List<Integer>> setA = new HashSet<>(a);
        Set<List<Integer>> setB = new HashSet<>(b);
        return setA.equals(setB) && setA.size() == a.size() && setB.size() == b.size();
    }

    static void check(int[] nums, List<List<Integer>> expected) {
        List<List<Integer>> slow = threeSumBrute(nums);
        List<List<Integer>> fast = threeSum(nums);
        boolean pass = sameResult(slow, expected) && sameResult(fast, expected);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(nums)
                + "  expected " + expected + ", fast " + fast);
    }

    static void randomTest() {
        Random rng = new Random(42);
        int mismatches = 0;

        for (int t = 0; t < 200; t++) {
            int length = rng.nextInt(15);              // 0 to 14 numbers
            int[] nums = new int[length];
            for (int i = 0; i < length; i++) {
                nums[i] = rng.nextInt(21) - 10;        // -10 to 10
            }

            List<List<Integer>> slow = threeSumBrute(nums);
            List<List<Integer>> fast = threeSum(nums);
            if (!sameResult(slow, fast)) {
                mismatches++;
                System.out.println("ERROR: " + Arrays.toString(nums)
                        + "  slow " + slow + ", fast " + fast);
            }
        }
        System.out.println("\nRandom test: " + (200 - mismatches) + "/200 matched");
    }

    static void timingTest() {
        Random rng = new Random(7);
        int[] nums = new int[1500];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = rng.nextInt(2001) - 1000;        // -1000 to 1000
        }

        long start = System.nanoTime();
        List<List<Integer>> slow = threeSumBrute(nums);
        long slowTime = System.nanoTime() - start;

        start = System.nanoTime();
        List<List<Integer>> fast = threeSum(nums);
        long fastTime = System.nanoTime() - start;

        System.out.println("\nTiming with 1,500 numbers:");
        System.out.printf("Slow (three loops):   %.2f ms, %d triplets%n", slowTime / 1_000_000.0, slow.size());
        System.out.printf("Fast (two pointers):  %.2f ms, %d triplets%n", fastTime / 1_000_000.0, fast.size());
        System.out.println("Results match: " + sameResult(slow, fast));
    }

    public static void main(String[] args) {
        check(new int[]{-1, 0, 1, 2, -1, -4}, List.of(List.of(-1, -1, 2), List.of(-1, 0, 1)));
        check(new int[]{0, 1, 1}, List.of());
        check(new int[]{0, 0, 0}, List.of(List.of(0, 0, 0)));
        check(new int[]{-2, 0, 0, 2, 2}, List.of(List.of(-2, 0, 2)));
        check(new int[]{}, List.of());
        // Edge cases
        check(new int[]{0, 0, 0, 0, 0}, List.of(List.of(0, 0, 0)));   // many duplicates, one triplet
        check(new int[]{1, 2}, List.of());                             // fewer than three numbers

        randomTest();
        timingTest();
    }
}
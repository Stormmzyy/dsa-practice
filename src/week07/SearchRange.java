package week07;

import java.util.Arrays;

// Idea: binary search twice; when the target is found, keep searching left (for the first) or right (for the last).
// Mistake on the way: I stopped as soon as I found the target, which returned any match instead of the edges.
// Complexity: O(log n) time, O(1) space. The slow scan is O(n).
public class SearchRange {

    // Plain binary search: any index of target, or -1
    static int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; // avoids overflow of (left + right)
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    static int findFirst(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;     // remember it
                right = mid - 1;  // but look for an earlier one
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static int findLast(int[] nums, int target) {
        int left = 0, right = nums.length - 1, result = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                result = mid;     // remember it
                left = mid + 1;   // but look for a later one
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    static int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums, target), findLast(nums, target)};
    }

    // Slow version: walk the whole array
    static int[] searchRangeSlow(int[] nums, int target) {
        int first = -1, last = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) first = i;
                last = i;
            }
        }
        return new int[]{first, last};
    }

    static void check(int[] nums, int target, int[] expected) {
        int[] fast = searchRange(nums, target);
        int[] slow = searchRangeSlow(nums, target);
        boolean pass = Arrays.equals(fast, expected) && Arrays.equals(slow, expected);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(nums) + ", " + target
                + "  expected " + Arrays.toString(expected) + ", got " + Arrays.toString(fast));
    }

    public static void main(String[] args) {
        check(new int[]{5, 7, 7, 8, 8, 10}, 8, new int[]{3, 4});
        check(new int[]{5, 7, 7, 8, 8, 10}, 6, new int[]{-1, -1});
        check(new int[]{2, 2}, 2, new int[]{0, 1});
        check(new int[]{1}, 1, new int[]{0, 0});
        check(new int[]{}, 0, new int[]{-1, -1});
        // Edge cases
        check(new int[]{3, 3, 3, 3, 3}, 3, new int[]{0, 4});   // every element is the target
        check(new int[]{1, 2, 3}, 4, new int[]{-1, -1});       // target bigger than everything

        System.out.println("\nPlain binary search for 8: index " + binarySearch(new int[]{5, 7, 7, 8, 8, 10}, 8));
    }
}

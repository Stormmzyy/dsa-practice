package week07;

import java.util.Arrays;

// Idea: at every step one half is sorted; check if the target sits in that sorted half and drop the other.
// Mistake on the way: I used nums[left] < nums[mid] instead of <=, which broke [3,1] when left and mid were the same index.
// Complexity: O(log n) time, O(1) space. The slow scan is O(n).
public class RotatedSearch {

    static int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) return mid;

            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // target is in the sorted left half
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;  // target is in the sorted right half
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    // Slow version: check every element
    static int searchSlow(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) return i;
        }
        return -1;
    }

    static void check(int[] nums, int target, int expected) {
        int fast = search(nums, target);
        int slow = searchSlow(nums, target);
        boolean pass = fast == expected && slow == expected;
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(nums) + ", " + target
                + "  expected " + expected + ", got " + fast);
    }

    public static void main(String[] args) {
        check(new int[]{4, 5, 6, 7, 0, 1, 2}, 0, 4);
        check(new int[]{4, 5, 6, 7, 0, 1, 2}, 3, -1);
        check(new int[]{1}, 0, -1);
        check(new int[]{1}, 1, 0);
        check(new int[]{3, 1}, 1, 1);
        check(new int[]{5, 1, 3}, 5, 0);
        // Edge cases
        check(new int[]{1, 2, 3, 4, 5}, 4, 3);   // not rotated at all
        check(new int[]{}, 1, -1);               // empty array
    }
}

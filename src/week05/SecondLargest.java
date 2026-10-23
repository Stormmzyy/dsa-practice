package week05;

// Pattern: single pass, tracking the top two values as you go.
// Brute force: sort and walk back from the end, O(n log n). This: O(n) time, O(1) space.
public class SecondLargest {

    static int secondLargest(int[] nums) {
        // long sentinels so arrays containing Integer.MIN_VALUE still work
        long largest = Long.MIN_VALUE;
        long second = Long.MIN_VALUE;

        for (int n : nums) {
            if (n > largest) {
                second = largest;
                largest = n;
            } else if (n < largest && n > second) {
                second = n;
            }
            // n == largest is a duplicate and is skipped
        }

        if (second == Long.MIN_VALUE) {
            throw new IllegalArgumentException("Need at least two distinct values");
        }
        return (int) second;
    }

    static void test(int[] nums) {
        try {
            System.out.println(java.util.Arrays.toString(nums) + " -> " + secondLargest(nums));
        } catch (IllegalArgumentException e) {
            System.out.println(java.util.Arrays.toString(nums) + " -> throws: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        test(new int[]{4, 9, 2, 9, 7});   // 7
        test(new int[]{3, 1});            // 1
        test(new int[]{-2, -8, -5});      // -5
        test(new int[]{5, 5, 5});         // throws
        test(new int[]{});                // throws
        // Edge cases
        test(new int[]{Integer.MIN_VALUE, 0});  // -2147483648
        test(new int[]{1, 2, 3, 4, 5});         // 4 (ascending order)
    }
}

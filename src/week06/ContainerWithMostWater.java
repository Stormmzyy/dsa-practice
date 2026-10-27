package week06;

import java.util.Arrays;
import java.util.Random;

// Idea: two pointers starting at both ends; always move the shorter line inward.
// Complexity: slow version O(n^2) time, fast version O(n) time; both O(1) space.
// What I had to think about: why moving the taller line can never help. The shorter line
// caps the height, and moving inward only shrinks the width, so the area can only stay the same or drop.
public class ContainerWithMostWater {

    // Slow version: try every pair of lines
    static int maxAreaBrute(int[] heights) {
        int best = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    // Fast version: two pointers moving toward each other
    static int maxArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        int best = 0;

        while (left < right) {
            int area = Math.min(heights[left], heights[right]) * (right - left);
            best = Math.max(best, area);

            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    static void check(int[] heights, int expected) {
        int slow = maxAreaBrute(heights);
        int fast = maxArea(heights);
        boolean pass = slow == expected && fast == expected;
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(heights)
                + "  expected " + expected + ", slow " + slow + ", fast " + fast);
    }

    static void randomTest() {
        Random rng = new Random(42); // fixed seed so failures can be reproduced
        int mismatches = 0;

        for (int t = 0; t < 100; t++) {
            int length = rng.nextInt(50);          // 0 to 49 lines
            int[] heights = new int[length];
            for (int i = 0; i < length; i++) {
                // Small range on purpose, so equal heights are common. That is the case
                // where the fast version's else branch decides which pointer moves, and
                // a wider range would almost never generate it.
                heights[i] = rng.nextInt(5);       // heights 0 to 4
            }

            int slow = maxAreaBrute(heights);
            int fast = maxArea(heights);
            if (slow != fast) {
                mismatches++;
                System.out.println("ERROR: " + Arrays.toString(heights)
                        + "  slow " + slow + ", fast " + fast);
            }
        }

        System.out.println("\nRandom test: " + (100 - mismatches) + "/100 matched");
    }

    public static void main(String[] args) {
        check(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}, 49);
        check(new int[]{1, 1}, 1);
        check(new int[]{4, 3, 2, 1, 4}, 16);
        check(new int[]{1, 2, 1}, 2);
        check(new int[]{5}, 0);
        // Edge cases
        check(new int[]{}, 0);                 // no lines at all
        check(new int[]{0, 0, 0, 0}, 0);       // all heights zero

        randomTest();
    }
}
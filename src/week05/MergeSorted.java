package week05;

import java.util.Arrays;

// Pattern: two pointers, always taking the smaller front element.
// Brute force: join and sort, O((n+m) log(n+m)). This: O(n+m) time, O(n+m) space for the result.
public class MergeSorted {

    static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }
        // One array has run out; copy whatever is left of the other
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];

        return result;
    }

    static void test(int[] a, int[] b) {
        System.out.println(Arrays.toString(a) + " + " + Arrays.toString(b)
                + " -> " + Arrays.toString(merge(a, b)));
    }

    public static void main(String[] args) {
        test(new int[]{1, 3, 5}, new int[]{2, 4, 6, 8});   // [1, 2, 3, 4, 5, 6, 8]
        test(new int[]{}, new int[]{1, 2});                // [1, 2]
        test(new int[]{1, 1, 2}, new int[]{1, 3});         // [1, 1, 1, 2, 3]
        test(new int[]{-3, 0}, new int[]{-5, 4});          // [-5, -3, 0, 4]
        test(new int[]{}, new int[]{});                    // []
        // Edge cases
        test(new int[]{1, 2, 3}, new int[]{10, 20});       // [1, 2, 3, 10, 20] (no overlap)
        test(new int[]{7}, new int[]{7});                  // [7, 7] (equal single elements)
    }
}

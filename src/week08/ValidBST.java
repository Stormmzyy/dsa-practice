package week08;

import java.util.Arrays;

// Idea: pass an allowed range down the tree; going left lowers the upper limit, going right raises the lower limit.
// Mistake on the way: I only compared each node with its direct children, so [5,4,6,null,null,3,7] passed when it shouldn't.
// Complexity: O(n) time, O(h) space for the recursion.
public class ValidBST {

    static boolean isValidBST(TreeNode root) {
        // long limits, so a node holding Integer.MAX_VALUE still fits inside "no limit"
        return isValid(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    static boolean isValid(TreeNode node, long low, long high) {
        if (node == null) return true;                        // empty tree is valid
        if (node.val <= low || node.val >= high) return false; // strictly inside the range

        return isValid(node.left, low, node.val)     // left side: must be below this value
            && isValid(node.right, node.val, high);  // right side: must be above this value
    }

    static void check(Integer[] input, boolean expected) {
        boolean actual = isValidBST(TreeNode.fromArray(input));
        System.out.println((actual == expected ? "PASS" : "FAIL") + "  " + Arrays.toString(input)
                + "  expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        check(new Integer[]{2, 1, 3}, true);
        check(new Integer[]{5, 1, 4, null, null, 3, 6}, false);
        check(new Integer[]{5, 4, 6, null, null, 3, 7}, false);  // 3 is below the root 5
        check(new Integer[]{1, 1}, false);                       // equal is not allowed
        check(new Integer[]{2147483647}, true);
        check(new Integer[]{}, true);
        // Edge cases
        check(new Integer[]{-2147483648, null, 2147483647}, true); // both int extremes
        check(new Integer[]{10, 5, 15, 3, 7, 12, 20}, true);       // bigger valid tree
    }
}

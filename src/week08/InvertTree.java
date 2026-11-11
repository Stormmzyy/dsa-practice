package week08;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// Idea: at every node, swap its left and right children, then do the same for each child.
// Mistake on the way: in the queue version I added null children to the queue and then crashed reading their fields.
// Complexity: both O(n) time; recursion uses O(h) space (tree height), the queue uses O(w) (widest level).
public class InvertTree {

    // Recursive version
    static TreeNode invert(TreeNode root) {
        if (root == null) return null;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;

        invert(root.left);
        invert(root.right);
        return root;
    }

    // Queue version, no recursion
    static TreeNode invertWithQueue(TreeNode root) {
        if (root == null) return null;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;

            if (node.left != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
        }
        return root;
    }

    static void check(Integer[] input, Integer[] expected) {
        // Expected goes through the same fromArray/show round trip as the result, so both
        // strings come from one formatter and its separator can't cause a false FAIL.
        String want = TreeNode.show(TreeNode.fromArray(expected));
        String rec = TreeNode.show(invert(TreeNode.fromArray(input)));
        String queue = TreeNode.show(invertWithQueue(TreeNode.fromArray(input)));
        boolean pass = rec.equals(want) && queue.equals(want);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(input)
                + "  expected " + want + ", recursive " + rec + ", queue " + queue);
    }

    public static void main(String[] args) {
        check(new Integer[]{4, 2, 7, 1, 3, 6, 9}, new Integer[]{4, 7, 2, 9, 6, 3, 1});
        check(new Integer[]{2, 1, 3}, new Integer[]{2, 3, 1});
        check(new Integer[]{1, 2}, new Integer[]{1, null, 2});
        check(new Integer[]{1}, new Integer[]{1});
        check(new Integer[]{}, new Integer[]{});
        // Edge cases
        check(new Integer[]{1, null, 2}, new Integer[]{1, 2});            // only a right child
        check(new Integer[]{1, 2, null, 3}, new Integer[]{1, null, 2, null, 3}); // left-leaning chain
    }
}

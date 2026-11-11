package week08;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

// Idea: breadth-first search with a queue; the queue's size at the start of each round is the size of that level.
// Mistake on the way: I used queue.size() directly in the for loop condition, but it changes as I add children.
// Complexity: O(n) time, O(w) space for the queue (widest level).
public class LevelOrder {

    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // freeze the size before adding children
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            result.add(level);
        }
        return result;
    }

    static void check(Integer[] input, List<List<Integer>> expected) {
        List<List<Integer>> actual = levelOrder(TreeNode.fromArray(input));
        boolean pass = actual.equals(expected);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(input)
                + "  expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        check(new Integer[]{3, 9, 20, null, null, 15, 7},
                List.of(List.of(3), List.of(9, 20), List.of(15, 7)));
        check(new Integer[]{1, 2, 3, 4, null, null, 5},
                List.of(List.of(1), List.of(2, 3), List.of(4, 5)));
        check(new Integer[]{1}, List.of(List.of(1)));
        check(new Integer[]{}, List.of());
        // Edge cases
        check(new Integer[]{1, 2, null, 3}, List.of(List.of(1), List.of(2), List.of(3))); // one node per level
        check(new Integer[]{1, 2, 3, 4, 5, 6, 7},
                List.of(List.of(1), List.of(2, 3), List.of(4, 5, 6, 7)));                  // full tree
    }
}

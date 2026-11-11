package week08;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Helper for this week's three tree problems: build a tree from a level-by-level list,
// and print one back in the same form, so inputs and expected outputs can be written
// as one line each, the way the guide's tables write them.
public class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
        this.val = val;
    }

    // Integer, not int, so a slot can be null ("no child here").
    //
    // This is the level-order format, NOT the heap format where a node's children are always
    // at 2i+1 and 2i+2. Only real nodes go into the queue, so a null takes up its slot but
    // never gets children of its own. That's why in [5,1,4,null,null,3,6] the 3 and 6 belong
    // to 4: the two nulls used up 1's slots, and the next pair goes to the next real node.
    static TreeNode fromArray(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        // The parents still waiting for their children, in the order their level was read.
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNode node = queue.poll();
            if (vals[i] != null) {
                node.left = new TreeNode(vals[i]);
                queue.add(node.left);
            }
            i++;
            // The array may end after a left child, so check again before reading the right one.
            if (i < vals.length && vals[i] != null) {
                node.right = new TreeNode(vals[i]);
                queue.add(node.right);
            }
            i++;
        }
        return root;
    }

    // The reverse: a breadth-first walk that writes "null" for every missing child, so the
    // positions line up with what fromArray expects.
    static String show(TreeNode root) {
        if (root == null) return "[]";
        List<String> out = new ArrayList<>();
        // LinkedList, not ArrayDeque: the missing children have to go into the queue as null
        // so their position gets printed, and ArrayDeque throws on null.
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (n == null) {
                out.add("null");
                continue;
            }
            out.add(String.valueOf(n.val));
            q.add(n.left);
            q.add(n.right);
        }
        // The walk always ends on the empty children of the last level. They carry no
        // information, so trim them; otherwise show(fromArray(x)) would not give back x.
        while (out.get(out.size() - 1).equals("null")) {
            out.remove(out.size() - 1);
        }
        // "," with no space, as the guide's tables write trees. (ListNode.show in week07 uses ", ".)
        return "[" + String.join(",", out) + "]";
    }
}

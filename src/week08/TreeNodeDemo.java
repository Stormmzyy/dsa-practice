package week08;

// Quick check that the TreeNode helper works: each tree is built with fromArray and printed
// back with show, and the output has to match what went in. If the helper is wrong, every
// tree problem's checks are wrong with it, so it gets checked before any of them.
public class TreeNodeDemo {

    public static void main(String[] args) {
        // The guide's example: the two nulls are 1's children, so 3 and 6 go under 4.
        check(TreeNode.show(TreeNode.fromArray(5, 1, 4, null, null, 3, 6)), "[5,1,4,null,null,3,6]");
        // A full tree, no nulls at all.
        check(TreeNode.show(TreeNode.fromArray(4, 2, 7, 1, 3, 6, 9)), "[4,2,7,1,3,6,9]");
        // Only right children: a null in the middle of every level after the first.
        check(TreeNode.show(TreeNode.fromArray(1, null, 2, null, 3)), "[1,null,2,null,3]");
        // Trailing nulls in the input are dropped, because show trims them.
        check(TreeNode.show(TreeNode.fromArray(1, 2, null)), "[1,2]");
        check(TreeNode.show(TreeNode.fromArray(42)), "[42]");
        check(TreeNode.show(TreeNode.fromArray()), "[]");
        check(TreeNode.show(TreeNode.fromArray((Integer) null)), "[]");
    }

    static void check(String actual, String expected) {
        System.out.println((actual.equals(expected) ? "PASS  " : "FAIL  ") + actual
                + (actual.equals(expected) ? "" : "   (expected " + expected + ")"));
    }
}

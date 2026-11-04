package week07;

import java.util.Arrays;

// Idea: walk the list and flip each node's next arrow to point backwards, keeping hold of the node ahead first.
// Mistake on the way: I flipped curr.next before saving it, which lost the rest of the list.
// Complexity: both O(n) time; loop is O(1) space, recursion is O(n) space for the call stack.
public class ReverseList {

    // Loop version
    static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next; // save the rest before breaking the link
            curr.next = prev;          // flip the arrow
            prev = curr;               // step forward
            curr = next;
        }
        return prev; // prev ends on the old last node, the new head
    }

    // Recursive version
    static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head; // empty or single node is already reversed
        }
        ListNode newHead = reverseRecursive(head.next); // trust it reverses the rest
        head.next.next = head; // the node after me now points back to me
        head.next = null;      // I'm the new tail
        return newHead;
    }

    static void check(int[] input, int[] expected) {
        String want = Arrays.toString(expected);
        String loop = ListNode.show(reverse(ListNode.fromArray(input)));
        String rec = ListNode.show(reverseRecursive(ListNode.fromArray(input)));
        boolean pass = loop.equals(want) && rec.equals(want);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(input)
                + "  expected " + want + ", loop " + loop + ", recursive " + rec);
    }

    public static void main(String[] args) {
        check(new int[]{1, 2, 3, 4, 5}, new int[]{5, 4, 3, 2, 1});
        check(new int[]{1, 2}, new int[]{2, 1});
        check(new int[]{7}, new int[]{7});
        check(new int[]{}, new int[]{});
        // Edge cases
        check(new int[]{1, 1, 2}, new int[]{2, 1, 1});   // duplicate values
        check(new int[]{-3, 0, 3}, new int[]{3, 0, -3}); // negatives
    }
}

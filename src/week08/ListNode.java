package week08;

// A copy of week07/ListNode.java, on purpose. Problem 5 (merge k sorted lists) needs it.
// The two are not meant to stay in sync, and they already differ in one place, deliberately:
//
// - week07 is merged and reviewed, so this week's diff doesn't touch it, the same way Week 7
//   left Weeks 5 and 6 alone. Importing week07.ListNode isn't enough either: its fields,
//   constructor and helpers are package-private, so week08 could name the type but not use it,
//   and making them public would mean editing week07.
// - show() joins with "," and no space. week07's version uses ", " on purpose, to match
//   Arrays.toString. This one differs on purpose: the guide's TreeNode.show joins with ",", and
//   the guide writes problem 5's expected outputs that way ([1,1,2,3,4,4,5,6]), so both helpers
//   in week08 print the same way and a row from the guide's table can be compared directly.
// - There's no shared build here: each week compiles on its own, so the weeks are snapshots,
//   not modules that depend on each other. A copy keeps week08 complete by itself.
//
// Everything else is week07's code, unchanged.
public class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }

    // Build a normal list from an array
    static ListNode fromArray(int... values) {
        return fromArrayWithCycle(values, -1);
    }

    // Build a list, then point the last node back at position pos (-1 means no cycle)
    static ListNode fromArrayWithCycle(int[] values, int pos) {
        if (values.length == 0) return null;

        ListNode head = new ListNode(values[0]);
        ListNode tail = head;
        ListNode cycleTarget = (pos == 0) ? head : null;

        for (int i = 1; i < values.length; i++) {
            tail.next = new ListNode(values[i]);
            tail = tail.next;
            if (i == pos) cycleTarget = tail;
        }
        if (cycleTarget != null) tail.next = cycleTarget;
        return head;
    }

    // Walk index steps from head (used to find the node a cycle should start at)
    static ListNode nodeAt(ListNode head, int index) {
        ListNode current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current;
    }

    // Print like [1,2,3] (only for lists without a cycle). "," with no space, as TreeNode.show
    // and the guide's tables do; see the note at the top for why week07's uses ", ".
    static String show(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = head; n != null; n = n.next) {
            sb.append(n.val);
            if (n.next != null) sb.append(",");
        }
        return sb.append("]").toString();
    }
}

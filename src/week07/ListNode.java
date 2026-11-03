package week07;

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

    // Print like [1, 2, 3] (only for lists without a cycle)
    // The separator is ", " rather than the guide's "," so the output matches Arrays.toString
    // and the solutions can compare strings directly. ListNodeDemo now prints [1, 2, 3, 4, 5].
    static String show(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = head; n != null; n = n.next) {
            sb.append(n.val);
            if (n.next != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}

package week07;

public class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }

    static ListNode fromArray(int... vals) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int v : vals) {
            tail.next = new ListNode(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String show(ListNode head) { // e.g. [1,2,3]
        StringBuilder sb = new StringBuilder("[");
        for (ListNode n = head; n != null; n = n.next) {
            sb.append(n.val).append(n.next != null ? "," : "");
        }
        return sb.append("]").toString();
    }
}

package week07;

import java.util.HashSet;
import java.util.Set;

// Idea: slow and fast pointers meet inside the cycle; then a pointer from the head and one from the meeting
// point, both moving one step, meet exactly at the cycle start.
// Why that works: if the head is a steps from the start, and they meet b steps into a cycle of length c,
// slow walked a + b and fast walked twice that. The extra a + b is whole laps, so a + b is a multiple of c.
// That means walking a more steps from the meeting point finishes a lap and lands on the start.
// Mistake on the way: I compared node values instead of nodes, which is wrong when values repeat.
// Complexity: HashSet version O(n) time, O(n) space; two-pointer version O(n) time, O(1) space.
public class DetectCycle {

    // Simple version: remember every node visited
    static ListNode detectCycleSet(ListNode head) {
        Set<ListNode> visited = new HashSet<>();
        for (ListNode n = head; n != null; n = n.next) {
            if (!visited.add(n)) return n; // add returns false if already seen
        }
        return null;
    }

    // No extra memory: slow and fast pointers
    static ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                // They met inside the cycle; now find the start
                ListNode pointer = head;
                while (pointer != slow) {
                    pointer = pointer.next;
                    slow = slow.next;
                }
                return pointer;
            }
        }
        return null; // fast reached the end, so there's no cycle
    }

    static void check(int[] values, int pos) {
        ListNode head = ListNode.fromArrayWithCycle(values, pos);
        ListNode expected = (pos == -1) ? null : ListNode.nodeAt(head, pos);

        ListNode fromSet = detectCycleSet(head);
        ListNode fromPointers = detectCycle(head);
        boolean pass = fromSet == expected && fromPointers == expected; // same node, not same value

        String want = (expected == null) ? "null" : "node with value " + expected.val;
        String got = (fromPointers == null) ? "null" : "node with value " + fromPointers.val;
        System.out.println((pass ? "PASS" : "FAIL") + "  " + java.util.Arrays.toString(values)
                + ", pos " + pos + "  expected " + want + ", got " + got);
    }

    public static void main(String[] args) {
        check(new int[]{3, 2, 0, -4}, 1);
        check(new int[]{1, 2}, 0);
        check(new int[]{1}, 0);
        check(new int[]{1}, -1);
        check(new int[]{1, 2, 3}, -1);
        check(new int[]{}, -1);
        // Edge cases
        check(new int[]{5, 5, 5, 5}, 2);          // repeated values, so == really matters
        check(new int[]{1, 2, 3, 4, 5, 6}, 5);    // tail points to itself
    }
}

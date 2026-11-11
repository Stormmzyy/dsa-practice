package week08;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

// Idea: keep the front node of every list in a min-heap; repeatedly take the smallest and add its next node.
// Mistake on the way: in the random test I passed the same lists to both versions, but the first version relinks
// the nodes, so the second one got already-merged input. Each version now gets freshly built lists.
// Complexity: one-by-one version O(k * N) time; heap version O(N log k) time and O(k) space (N = total nodes).
public class MergeKLists {

    // Helper: merge two sorted lists by relinking nodes (no new nodes except the dummy)
    static ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b; // attach whatever is left
        return dummy.next;
    }

    // First version: merge the lists one at a time into a growing result
    static ListNode mergeKListsOneByOne(ListNode[] lists) {
        ListNode result = null;
        for (ListNode list : lists) {
            result = mergeTwo(result, list); // null lists are handled by mergeTwo
        }
        return result;
    }

    // Second version: a heap holding the current front node of every list
    static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
        for (ListNode list : lists) {
            if (list != null) heap.add(list); // skip empty lists
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            tail.next = smallest;
            tail = smallest;
            if (smallest.next != null) heap.add(smallest.next); // that list's next front node
        }
        return dummy.next;
    }

    static ListNode[] build(int[][] arrays) {
        ListNode[] lists = new ListNode[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            lists[i] = ListNode.fromArray(arrays[i]); // an empty array gives null
        }
        return lists;
    }

    static void check(int[][] arrays, int[] expected) {
        // Expected goes through the same fromArray/show round trip as the result, so both
        // strings come from one formatter and its separator can't cause a false FAIL.
        String want = ListNode.show(ListNode.fromArray(expected));
        String oneByOne = ListNode.show(mergeKListsOneByOne(build(arrays)));
        String heap = ListNode.show(mergeKLists(build(arrays)));
        boolean pass = oneByOne.equals(want) && heap.equals(want);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.deepToString(arrays)
                + "  expected " + want + ", one-by-one " + oneByOne + ", heap " + heap);
    }

    static void randomTest() {
        Random rng = new Random(42);
        int mismatches = 0;
        for (int t = 0; t < 100; t++) {
            int k = rng.nextInt(6);                     // 0 to 5 lists
            int[][] arrays = new int[k][];
            for (int i = 0; i < k; i++) {
                int length = rng.nextInt(6);            // each list 0 to 5 long
                arrays[i] = new int[length];
                for (int j = 0; j < length; j++) arrays[i][j] = rng.nextInt(21) - 10;
                Arrays.sort(arrays[i]);                 // inputs must be sorted
            }
            String a = ListNode.show(mergeKListsOneByOne(build(arrays)));
            String b = ListNode.show(mergeKLists(build(arrays)));
            if (!a.equals(b)) {
                mismatches++;
                System.out.println("ERROR: " + Arrays.deepToString(arrays) + "  " + a + " vs " + b);
            }
        }
        System.out.println("\nRandom test: " + (100 - mismatches) + "/100 matched");
    }

    public static void main(String[] args) {
        check(new int[][]{{1, 4, 5}, {1, 3, 4}, {2, 6}}, new int[]{1, 1, 2, 3, 4, 4, 5, 6});
        check(new int[][]{{2}, {1}, {3}}, new int[]{1, 2, 3});
        check(new int[][]{{}, {1}}, new int[]{1});
        check(new int[][]{{}}, new int[]{});
        check(new int[][]{}, new int[]{});
        // Edge cases
        check(new int[][]{{-5, -1}, {-3, 0}}, new int[]{-5, -3, -1, 0});   // negatives
        check(new int[][]{{1, 1}, {1}, {1, 1}}, new int[]{1, 1, 1, 1, 1}); // all equal

        randomTest();
    }
}

package week07;

// Quick check that the ListNode helper works: build lists from arrays and print them.
public class ListNodeDemo {

    public static void main(String[] args) {
        System.out.println(ListNode.show(ListNode.fromArray(1, 2, 3, 4, 5)));
        System.out.println(ListNode.show(ListNode.fromArray(42)));
        System.out.println(ListNode.show(ListNode.fromArray()));
    }
}

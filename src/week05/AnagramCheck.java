package week05;

// Pattern: frequency counting, add for one string and subtract for the other.
// Brute force: sort both and compare, O(n log n). This: O(n) time, O(1) space (fixed 26 slots).
public class AnagramCheck {

    static boolean isAnagram(String a, String b) {
        int[] counts = new int[26];

        for (char c : a.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') counts[c - 'a']++;
        }
        for (char c : b.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') counts[c - 'a']--;
        }

        for (int count : counts) {
            if (count != 0) return false;
        }
        return true;
    }

    static void test(String a, String b) {
        System.out.println("\"" + a + "\", \"" + b + "\" -> " + isAnagram(a, b));
    }

    public static void main(String[] args) {
        test("Listen", "Silent");          // true
        test("Dormitory", "dirty room");   // true
        test("hello", "world");            // false
        test("abc", "ab");                 // false
        test("", "");                      // true
        // Edge cases
        test("aab", "abb");                // false (same letters, different counts)
        test("  A  ", "a");                // true (only spaces and case differ)
    }
}

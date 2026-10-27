package week06;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Idea: sliding window. Grow it on the right; when a character repeats, jump the left edge past its last position.
// Complexity: slow version O(n^2) time, fast version O(n) time; both O(k) space, where k is the number of distinct characters.
// What I had to think about: in "abba", the last 'a' was seen before the window's left edge, so jumping
// straight to it would move the left edge backwards. Math.max stops that.
public class LongestSubstring {

    // Slow version: from every start position, extend until a repeat appears
    static int lengthOfLongestSubstringBrute(String s) {
        int best = 0;
        for (int start = 0; start < s.length(); start++) {
            Set<Character> seen = new HashSet<>();
            for (int end = start; end < s.length(); end++) {
                char c = s.charAt(end);
                if (seen.contains(c)) {
                    break; // every longer substring from this start also has the repeat
                }
                seen.add(c);
                best = Math.max(best, end - start + 1);
            }
        }
        return best;
    }

    // Fast version: sliding window with a map of each character's last index
    static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c)) {
                // Only move left forward, never backwards ("abba" bug)
                left = Math.max(left, lastSeen.get(c) + 1);
            }
            lastSeen.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    static void check(String s, int expected) {
        int slow = lengthOfLongestSubstringBrute(s);
        int fast = lengthOfLongestSubstring(s);
        boolean pass = slow == expected && fast == expected;
        System.out.println((pass ? "PASS" : "FAIL") + "  \"" + s + "\"  expected "
                + expected + ", slow " + slow + ", fast " + fast);
    }

    public static void main(String[] args) {
        check("abcabcbb", 3);   // "abc"
        check("bbbbb", 1);
        check("pwwkew", 3);     // "wke"
        check("", 0);
        check("abba", 2);       // the left-edge bug
        check("dvdf", 3);       // "vdf"
        // Edge cases
        check("abcdef", 6);     // no repeats at all
        check("a b", 3);        // spaces count as characters
    }
}
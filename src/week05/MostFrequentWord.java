package week05;

import java.util.HashMap;
import java.util.Map;

// Pattern: count with a HashMap, then one scan to pick the winner.
// Brute force: for each word, recount it across the sentence, O(n^2). This: O(n) time, O(n) space.
public class MostFrequentWord {

    static String mostFrequentWord(String sentence) {
        String cleaned = sentence.toLowerCase().replaceAll("[.,!?]", "").trim();
        if (cleaned.isEmpty()) {
            throw new IllegalArgumentException("No words");
        }

        Map<String, Integer> counts = new HashMap<>();
        for (String word : cleaned.split("\\s+")) {
            counts.merge(word, 1, Integer::sum);
        }

        String best = null;
        int bestCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            if (count > bestCount || (count == bestCount && word.compareTo(best) < 0)) {
                best = word;
                bestCount = count;
            }
        }
        return best;
    }

    static void test(String sentence) {
        try {
            System.out.println("\"" + sentence + "\" -> " + mostFrequentWord(sentence));
        } catch (IllegalArgumentException e) {
            System.out.println("\"" + sentence + "\" -> throws: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        test("the cat and the hat and the bat");   // the
        test("Go go GO! stop stop");               // go
        test("b a b a");                           // a (tie)
        test("Hello, hello, world.");              // hello
        test("   ");                               // throws
        // Edge cases
        test("?!.,");                              // throws (only punctuation)
        test("solo");                              // solo (single word)
    }
}

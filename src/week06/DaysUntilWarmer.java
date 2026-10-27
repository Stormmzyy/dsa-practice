package week06;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

// Idea: monotonic stack of indexes of days still waiting; a warmer day pops and answers every colder day on top.
// Complexity: slow version O(n^2) time, stack version O(n) time; O(n) space for the stack.
// What I had to think about: one warm day can finish several waiting days at once, so the pop
// has to be a while loop, not an if. And the stack holds indexes, because the answer is a distance.
public class DaysUntilWarmer {

    // Slow version: for each day, scan forward until a warmer one appears
    static int[] daysUntilWarmerBrute(int[] temps) {
        int[] answer = new int[temps.length];
        for (int i = 0; i < temps.length; i++) {
            for (int j = i + 1; j < temps.length; j++) {
                if (temps[j] > temps[i]) {
                    answer[i] = j - i;
                    break;
                }
            }
            // if no warmer day is found, answer[i] stays 0
        }
        return answer;
    }

    // Fast version: stack of indexes of days still waiting for a warmer day
    static int[] daysUntilWarmer(int[] temps) {
        int[] answer = new int[temps.length];
        Deque<Integer> waiting = new ArrayDeque<>();

        for (int today = 0; today < temps.length; today++) {
            // Today settles every waiting day that was colder
            while (!waiting.isEmpty() && temps[today] > temps[waiting.peek()]) {
                int earlier = waiting.pop();
                answer[earlier] = today - earlier;
            }
            waiting.push(today);
        }
        // Days left on the stack never found a warmer day, so they stay 0
        return answer;
    }

    static void check(int[] temps, int[] expected) {
        int[] slow = daysUntilWarmerBrute(temps);
        int[] fast = daysUntilWarmer(temps);
        boolean pass = Arrays.equals(slow, expected) && Arrays.equals(fast, expected);
        System.out.println((pass ? "PASS" : "FAIL") + "  " + Arrays.toString(temps)
                + "  expected " + Arrays.toString(expected) + ", fast " + Arrays.toString(fast));
    }

    public static void main(String[] args) {
        check(new int[]{73, 74, 75, 71, 69, 72, 76, 73}, new int[]{1, 1, 4, 2, 1, 1, 0, 0});
        check(new int[]{30, 40, 50, 60}, new int[]{1, 1, 1, 0});
        check(new int[]{30, 60, 90}, new int[]{1, 1, 0});
        check(new int[]{90, 80, 70}, new int[]{0, 0, 0});
        check(new int[]{}, new int[]{});
        // Edge cases
        check(new int[]{50, 50, 50}, new int[]{0, 0, 0});   // equal is not warmer
        check(new int[]{70}, new int[]{0});                 // single day
    }
}
package week06;

import java.util.ArrayDeque;
import java.util.Deque;

// Idea: push every opening bracket onto a stack; each closing bracket must match whatever is on top.
// What I had to think about: why "([)]" needs a stack at all, since the brackets balance by count
// and a simple depth counter would wrongly call it valid.
public class MatchingBrackets {

    static boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false; // closing bracket with nothing to close
                }
                char open = stack.pop();
                if ((c == ')' && open != '(') ||
                        (c == ']' && open != '[') ||
                        (c == '}' && open != '{')) {
                    return false; // wrong type of bracket
                }
            }
            // any other character is ignored
        }

        return stack.isEmpty(); // leftover openers were never closed
    }

    static void check(String s, boolean expected) {
        boolean actual = isValid(s);
        System.out.println((actual == expected ? "PASS" : "FAIL")
                + "  \"" + s + "\"  expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        check("()", true);
        check("()[]{}", true);
        check("{[]}", true);
        check("(]", false);
        check("([)]", false);
        check("((", false);
        check("]", false);
        check("", true);
        // Edge cases
        check("((((()))))", true);   // deep nesting
        check("())(", false);        // counts match, order doesn't
        // Stretch: other characters ignored
        check("a(b[c]d)e", true);
    }
}
# dsa-practice

Data Structures and Algorithms practice for the OpsDesk internship. Each week's problems live in their own package under `src/` (e.g. `src/week05/`). Every class has a `main` method that runs built-in test cases plus edge cases, so you can verify correctness immediately.

## How to compile and run

**Compile a week** (from the project root):

```
javac -d out src/week05/*.java
javac -d out src/week06/*.java
```

**Run each problem:**

```
java -cp out week05.SecondLargest
java -cp out week05.AnagramCheck
java -cp out week05.TwoSum
java -cp out week05.MostFrequentWord
java -cp out week05.MergeSorted

java -cp out week06.MatchingBrackets
java -cp out week06.ContainerWithMostWater
java -cp out week06.LongestSubstring
java -cp out week06.ThreeSum
```

## Week 5: Problems

| # | Class | Pattern | Time | Space |
|---|---|---|---|---|
| 1 | `SecondLargest` | Single pass tracking top two values | O(n) | O(1) |
| 2 | `AnagramCheck` | Frequency counting with a fixed 26-slot array | O(n) | O(1) |
| 3 | `TwoSum` | HashMap lookup (+ brute-force comparison) | O(n) / O(n^2) | O(n) / O(1) |
| 4 | `MostFrequentWord` | HashMap word counting, single scan for max | O(n) | O(n) |
| 5 | `MergeSorted` | Two pointers walking both sorted arrays | O(n+m) | O(n+m) |

### Problem details

**1. SecondLargest** — Walk the array once, maintaining the largest and second-largest values seen so far. Uses `long` sentinels so arrays containing `Integer.MIN_VALUE` still work. Skips duplicates of the current largest. Throws if fewer than two distinct values exist.

**2. AnagramCheck** — Increment a 26-slot count array for each letter of the first string, decrement for the second. If all counts end at zero, the strings are anagrams. Ignores case and non-letter characters, so `"Dormitory"` and `"dirty room"` match.

**3. TwoSum** — Two versions side by side. Version A checks every pair (O(n^2) time, O(1) space). Version B stores each seen value's index in a HashMap and checks for the complement in O(1), giving O(n) time at the cost of O(n) space. Includes a 50,000-element timing benchmark.

**4. MostFrequentWord** — Split the sentence into words, count each with `HashMap.merge()`, then scan the map for the highest count. Ties are broken alphabetically. Punctuation is stripped before counting.

**5. MergeSorted** — Two pointers, one per array, always copying the smaller front element into the result. When one array runs out, copy whatever remains of the other. Produces a fully sorted merged array in one pass.

## TwoSum timing results

With 50,000 elements and the valid pair placed at the very end of the array:

```
Version A (nested loops): 286.29 ms -> [49998, 49999]
Version B (HashMap):      15.54 ms -> [49998, 49999]
```

The HashMap version was roughly 18x faster because it does one O(n) pass with O(1) lookups, while the nested-loop version does O(n^2) comparisons scanning nearly every pair before finding the match at the end. Note that the brute-force version ran first, giving the JVM more time to JIT-compile hot paths before the HashMap version executed — so the real gap is likely even larger than these numbers suggest.

## Week 6: Problems

| # | Class | Pattern | Time | Space |
|---|---|---|---|---|
| 1 | `MatchingBrackets` | Stack of open brackets | O(n) | O(n) |
| 2 | `ContainerWithMostWater` | Two pointers from both ends (+ brute-force comparison) | O(n) / O(n^2) | O(1) |
| 3 | `LongestSubstring` | Sliding window with each character's last index (+ brute-force comparison) | O(n) / O(n^2) | O(k), k = distinct characters |
| 4 | `ThreeSum` | Sort, fix one number, two pointers for the rest (+ brute-force comparison) | O(n^2) / O(n^3) | O(n) for the sorted copy |

### Problem details

**1. MatchingBrackets** — Push every opening bracket onto a stack; each closing bracket must match the one on top. A simple depth counter isn't enough: `"([)]"` balances by count but is still invalid. Any other character is ignored.

**2. ContainerWithMostWater** — Start with one pointer at each end and always move the shorter line inward. Moving the taller one can never help: the shorter line caps the height, and moving inward only shrinks the width. Cross-checked against the brute-force version on 100 random arrays, using a small height range so equal heights (the case that decides which pointer moves) come up often.

**3. LongestSubstring** — Grow a window on the right; when a character repeats, jump the left edge past its last position. The left edge must only ever move forward: in `"abba"` the last `a` was seen before the window started, so `Math.max` keeps the left edge from jumping backwards.

**4. ThreeSum** — Sort a copy of the array, fix each number in turn, then find the other two with two pointers (Problem 2's trick). Repeated values are skipped in three places (the fixed number, and both pointers after a match), or the same triplet would be added more than once. Cross-checked against the brute-force version on 200 random arrays.

## ThreeSum timing results

With 1,500 random numbers between -1,000 and 1,000:

```
Slow (three loops):   610.63 ms, 71474 triplets
Fast (two pointers):  21.36 ms, 71474 triplets
Results match: true
```

Both versions found the same 71,474 triplets, and the two-pointer version was roughly 29x faster. The brute force checks every combination of three positions, about n^3/6, or roughly 560 million for n = 1,500, while the fast version sorts once and then does one linear pass of the two pointers for each fixed number, about n^2, or roughly 2.25 million steps. As with TwoSum, the brute-force version ran first and had more chance to be JIT-compiled before the fast version ran, so the real gap is wider than 29x.

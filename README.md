# dsa-practice

Data Structures and Algorithms practice for the OpsDesk internship. Each week's problems live in their own package under `src/` (e.g. `src/week05/`). Every class has a `main` method that runs built-in test cases plus edge cases, so you can verify correctness immediately.

## How to compile and run

**Compile all of Week 5** (from the project root):

```
javac -d out src/week05/*.java
```

**Run each problem:**

```
java -cp out week05.SecondLargest
java -cp out week05.AnagramCheck
java -cp out week05.TwoSum
java -cp out week05.MostFrequentWord
java -cp out week05.MergeSorted
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

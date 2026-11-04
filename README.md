# dsa-practice

Data Structures and Algorithms practice for the OpsDesk internship. Each week's problems live in their own package under `src/` (e.g. `src/week05/`). Every class has a `main` method that runs built-in test cases plus edge cases, so you can verify correctness immediately.

## How to compile and run

**Compile a week** (from the project root):

```
javac -d out src/week05/*.java
javac -d out src/week06/*.java
javac -d out src/week07/*.java
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
java -cp out week06.DaysUntilWarmer

java -cp out week07.ReverseList
java -cp out week07.SearchRange
java -cp out week07.RotatedSearch
java -cp out week07.MergeSort
java -cp out week07.DetectCycle
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
| 5 | `DaysUntilWarmer` | Monotonic stack of indexes of days still waiting (+ brute-force comparison) | O(n) / O(n^2) | O(n) |

### Problem details

**1. MatchingBrackets** — Push every opening bracket onto a stack; each closing bracket must match the one on top. A simple depth counter isn't enough: `"([)]"` balances by count but is still invalid. Any other character is ignored.

**2. ContainerWithMostWater** — Start with one pointer at each end and always move the shorter line inward. Moving the taller one can never help: the shorter line caps the height, and moving inward only shrinks the width. Cross-checked against the brute-force version on 100 random arrays, using a small height range so equal heights (the case that decides which pointer moves) come up often.

**3. LongestSubstring** — Grow a window on the right; when a character repeats, jump the left edge past its last position. The left edge must only ever move forward: in `"abba"` the last `a` was seen before the window started, so `Math.max` keeps the left edge from jumping backwards.

**4. ThreeSum** — Sort a copy of the array, fix each number in turn, then find the other two with two pointers (Problem 2's trick). Repeated values are skipped in three places (the fixed number, and both pointers after a match), or the same triplet would be added more than once. Cross-checked against the brute-force version on 200 random arrays.

**5. DaysUntilWarmer** — Keep a stack of the indexes of days still waiting for a warmer day, coldest on top. Each new day pops every waiting day that's colder than it, and the answer for each popped day is the distance between the two indexes. That's why the stack holds indexes, not temperatures, and why the pop is a `while` loop, not an `if`: one warm day can settle several waiting days at once. The `while` loop inside the `for` loop doesn't make it quadratic: each index is pushed once and popped at most once, so across the whole run there are at most n pushes and n pops, and the total work is O(n) (amortised). Days still on the stack at the end never found a warmer day and stay 0, and an equal temperature doesn't count as warmer.

## ThreeSum timing results

With 1,500 random numbers between -1,000 and 1,000:

```
Slow (three loops):   610.63 ms, 71474 triplets
Fast (two pointers):  21.36 ms, 71474 triplets
Results match: true
```

Both versions found the same 71,474 triplets, and the two-pointer version was roughly 29x faster. The brute force checks every combination of three positions, about n^3/6, or roughly 560 million for n = 1,500, while the fast version sorts once and then does one linear pass of the two pointers for each fixed number, about n^2, or roughly 2.25 million steps. As with TwoSum, the brute-force version ran first and had more chance to be JIT-compiled before the fast version ran, so the real gap is wider than 29x.

## Week 7: Problems

| # | Class | Pattern | Time | Space |
|---|---|---|---|---|
| 1 | `ReverseList` | Re-point each node's `next` backwards, as a loop and recursively | O(n) | O(1) loop / O(n) recursion (call stack) |
| 2 | `SearchRange` | Binary search twice, continuing left or right after a match (+ linear scan comparison) | O(log n) / O(n) | O(1) |
| 3 | `RotatedSearch` | Binary search where one half is always sorted, keeping the half that can hold the target (+ linear scan comparison) | O(log n) / O(n) | O(1) |
| 4 | `MergeSort` | Recursive split, then a two-pointer merge (+ insertion sort comparison) | O(n log n) / O(n^2) | O(n) / O(1) |
| 5 | `DetectCycle` | Slow and fast pointers meet in the cycle, then a walk from the head finds its start (+ HashSet comparison) | O(n) | O(1) / O(n) |

### Problem details

**1. ReverseList** — Walk the list and point each node's `next` back at the node before it. The node ahead has to be saved first: flip `curr.next` before saving it and the rest of the list is lost. The recursive version reverses everything after the head, then makes the node after the head point back to it and the head the new tail. Both are O(n) time; the loop needs O(1) space, while the recursion uses O(n) for its call stack.

**2. SearchRange** — Two binary searches. When one finds the target it doesn't stop: it remembers the index and keeps going left (for the first position) or right (for the last), so it returns the edges of the run rather than any match. O(log n) time against the linear scan's O(n), both O(1) space.

**3. RotatedSearch** — In a rotated sorted array, at every step at least one half is sorted. Check whether the target falls inside that sorted half's range; if it does, keep that half, otherwise keep the other. The comparison has to be `nums[left] <= nums[mid]`, not `<`: in `[3, 1]`, left and mid are the same index, and `<` picks the wrong half. O(log n) time against the linear scan's O(n), both O(1) space.

**4. MergeSort** — Split the array in half, sort each half by calling `mergeSort` again, then merge the two sorted halves with two pointers, the same merge as Week 5's `MergeSorted`. The base case (0 or 1 element is already sorted) is what stops the recursion. Cross-checked against `Arrays.sort` on 200 random arrays with lengths from 0 to 29 and many duplicates. O(n log n) time and O(n) space, against insertion sort's O(n^2) time and O(1) extra space.

**5. DetectCycle** — Slow moves one step and fast moves two; if there's a cycle they meet inside it. Then a pointer from the head and a pointer from the meeting point, both moving one step, meet exactly at the cycle's start. Why: if the head is a steps from the start, and they meet b steps into a cycle of length c, slow has walked a + b and fast twice that. The extra a + b that fast walked is whole laps, so a + b is a multiple of c, which means walking a more steps from the meeting point finishes a lap and lands on the start. Nodes are compared with `==`, not by value: `[5, 5, 5, 5]` shows why. O(n) time and O(1) space, against the HashSet version's O(n) space.

## MergeSort timing results

With 20,000 random numbers between -500,000 and 500,000:

```
Merge sort:     3.03 ms
Insertion sort: 59.07 ms
```

Merge sort was roughly 19.5x faster. It splits the array about 14 times (log2 of 20,000) and does n work at each level, roughly 286,000 steps, while insertion sort shifts each element past the larger ones before it, around 100 million operations on random input (about n^2/4). As in Week 6, there's a JIT caveat: merge sort ran first and had less chance to be optimised than insertion sort, which ran second, so if anything the gap is understated here rather than overstated. The direction differs from the Week 5 and Week 6 notes for a reason: there the slow version ran first and ran long enough (hundreds of milliseconds, hundreds of millions of operations) to be compiled partway through its own execution, so it was the one flattered. Here merge sort finishes in three milliseconds, far too short to self-optimise, so running first makes it the colder of the two. Either way the measured gap understates the real one.

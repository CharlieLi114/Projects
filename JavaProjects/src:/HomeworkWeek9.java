/**
 * Maximum Subarray: four algorithms with a unified tie break and simple timings.
 *
 * Tie break: larger sum is better; if equal, shorter length is better; if still equal, later start is better.
 *
 * @author Chengyu Li { @literal <lic24@wfu.edu>}
 * @version 0.1, Nov. 2, 2025
 */
import java.util.*;

public class HomeworkWeek9 {
    /** Inclusive start, exclusive end, and sum. */
    public static final class Res {
        public final int s, e, sum;
        public Res(int s, int e, int sum) { this.s = s; this.e = e; this.sum = sum; }
        private int len() { return e - s; }
    }

    /** True if a is better than b under the stated tie break. */
    private static boolean better(Res a, Res b) {
        if (b == null) return true;
        if (a.sum != b.sum) return a.sum > b.sum;
        if (a.len() != b.len()) return a.len() < b.len();
        return a.s > b.s;
    }

    /** O(n^3). */
    public static Res cubic(int[] a) {
        Res best = null;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j <= a.length; j++) {
                int s = 0;
                for (int k = i; k < j; k++) s += a[k];
                Res cur = new Res(i, j, s);
                if (better(cur, best)) best = cur;
            }
        }
        return best;
    }

    /** O(n^2). */
    public static Res quadratic(int[] a) {
        Res best = null;
        for (int i = 0; i < a.length; i++) {
            int s = 0;
            for (int j = i + 1; j <= a.length; j++) {
                s += a[j - 1];
                Res cur = new Res(i, j, s);
                if (better(cur, best)) best = cur;
            }
        }
        return best;
    }

    /** O(n log n). */
    public static Res divideConquer(int[] a) {
        return dc(a, 0, a.length);
    }
    private static Res dc(int[] a, int lo, int hi) {
        if (hi - lo == 1) return new Res(lo, hi, a[lo]);
        int mid = lo + (hi - lo) / 2;
        Res L = dc(a, lo, mid), R = dc(a, mid, hi), C = cross(a, lo, mid, hi);
        Res best = L;
        if (better(R, best)) best = R;
        if (better(C, best)) best = C;
        return best;
    }
    private static Res cross(int[] a, int lo, int mid, int hi) {
        int bestLeftSum = Integer.MIN_VALUE, leftSum = 0, bestLeftStart = mid - 1;
        for (int i = mid - 1; i >= lo; i--) {
            leftSum += a[i];
            Res cur = new Res(i, mid, leftSum);
            Res best = new Res(bestLeftStart, mid, bestLeftSum);
            if (better(cur, best)) { bestLeftSum = leftSum; bestLeftStart = i; }
        }
        int bestRightSum = Integer.MIN_VALUE, rightSum = 0, bestRightEnd = mid + 1;
        for (int j = mid; j < hi; j++) {
            rightSum += a[j];
            Res cur = new Res(mid, j + 1, rightSum);
            Res best = new Res(mid, bestRightEnd, bestRightSum);
            if (better(cur, best)) { bestRightSum = rightSum; bestRightEnd = j + 1; }
        }
        return new Res(bestLeftStart, bestRightEnd, bestLeftSum + bestRightSum);
    }

    /** O(n) Kadane with tie break. */
    public static Res kadane(int[] a) {
        Res best = new Res(0, 1, a[0]);
        int curStart = 0, curSum = a[0];
        for (int i = 1; i < a.length; i++) {
            int restart = a[i], extend = curSum + a[i];
            if (restart >= extend) { curSum = restart; curStart = i; } else { curSum = extend; }
            Res cur = new Res(curStart, i + 1, curSum);
            if (better(cur, best)) best = cur;
        }
        return best;
    }

    /** Utilities. */
    private static String slice(int[] a, int s, int e) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = s; i < e; i++) { if (i > s) sb.append(", "); sb.append(a[i]); }
        return sb.append("]").toString();
    }
    private static long timeMs(Runnable r) {
        long t0 = System.nanoTime(); r.run(); return (System.nanoTime() - t0) / 1_000_000;
    }
    private static int[] rand(Random g, int n, int lo, int hi) {
        int[] a = new int[n]; for (int i = 0; i < n; i++) a[i] = lo + g.nextInt(hi - lo + 1); return a;
    }




    private static long avgMs(int repeats, Runnable r) {
        long total = 0;
        for (int i = 0; i < repeats; i++) total += timeMs(r);
        return total / repeats;
    }

    private static void doMany(int times, Runnable algo) {
        for (int i = 0; i < times; i++) algo.run();
    }

    /** Brief JIT warm-up to stabilize timings. */
    private static void warmUp() {
        Random g = new Random(1);
        int[] a = rand(g, 5000, -50, 50);
        for (int i = 0; i < 5; i++) { kadane(a); divideConquer(a); quadratic(a); }
    }

    public static void main(String[] args) {
        warmUp();

        final int REPEATS = 5;  // average each timing over 5 runs
        final int MULT    = 50;
        final Random g = new Random(1129);


        int[] n3  = {180, 220, 260};             // O(n^3)
        int[] n2  = {3000, 4500, 6000};          // O(n^2)
        int[] nlg = {100_000, 200_000, 400_000}; // O(n log n)
        int[] n1  = {5_000_000, 10_000_000, 15_000_000}; // O(n)

        System.out.println("\nTimings (avg of " + REPEATS + " runs, each ×" + MULT + " work):");

        System.out.println("\nO(n^3):");
        for (int n : n3) {
            int[] a = rand(g, n, -50, 50);
            long ms = avgMs(REPEATS, () -> doMany(MULT, () -> cubic(a)));
            System.out.printf("n=%8d  %6d ms%n", n, ms);
        }

        System.out.println("\nO(n^2):");
        for (int n : n2) {
            int[] a = rand(g, n, -50, 50);
            long ms = avgMs(REPEATS, () -> doMany(MULT, () -> quadratic(a)));
            System.out.printf("n=%8d  %6d ms%n", n, ms);
        }

        System.out.println("\nO(n log n):");
        for (int n : nlg) {
            int[] a = rand(g, n, -50, 50);
            long ms = avgMs(REPEATS, () -> doMany(MULT, () -> divideConquer(a)));
            System.out.printf("n=%8d  %6d ms%n", n, ms);
        }

        System.out.println("\nO(n):");
        for (int n : n1) {
            int[] a = rand(g, n, -50, 50);
            long ms = avgMs(REPEATS, () -> doMany(MULT, () -> kadane(a)));
            System.out.printf("n=%10d  %6d ms%n", n, ms);
        }
    }

    private static void runAll(int[] a) {
        Res r1 = cubic(a), r2 = quadratic(a), r3 = divideConquer(a), r4 = kadane(a);
        System.out.println("Array: " + slice(a, 0, a.length));
        print("O(n^3)", r1, a);
        print("O(n^2)", r2, a);
        print("O(n log n)", r3, a);
        print("O(n)", r4, a);
    }
    private static void print(String tag, Res r, int[] a) {
        System.out.printf("%-10s sum=%d  range=[%d,%d)  %s%n", tag, r.sum, r.s, r.e, slice(a, r.s, r.e));
    }
}


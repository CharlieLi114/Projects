/**
 *Count the number of distinct ways to climb n steps if each step is either 1 step or 2 steps. Implement an efficient recursive solution so that
 * each sub-problem ways(k) is computed once.
 * @author Chengyu Li <lic24@wfu.edu></lic24@wfu.edu>
 * @version 1.0 Oct,12 2025
 */
import java.util.function.IntToLongFunction;

public class PracticeTwo {

    /**
     * Old recursive solution (demo version).
     * @param stairs number of steps (>= 0)
     * @return number of ways
     */
    public static long waysRecursive(int stairs) {
        if (stairs < 0) {
            return 0;
        } else if (stairs <= 2) {
            return stairs;
        } else {
            return waysRecursive(stairs - 1) + waysRecursive(stairs - 2);
        }
    }

    public static long waysIterative(int stairs) {
        if (stairs < 0) {
            return 0;
        } else if (stairs <= 2) {
            return stairs;
        } else {
            int[] results = new int[stairs + 1];
            results[0] = 0;
            results[1] = 1;
            results[2] = 2;
            for (int i = 3; i <= stairs; i++) {
                results[i] = results[i - 1] + results[i - 2];
            }
            return results[stairs];
        }
    }

    /**Returns the number of ways to climb n steps. */
    public static long waysMemo(int n){
         long[] memo = new long[n+1];
         return helper(n, memo);
    }

    private static long helper(int n, long[] memo) {
        if(n <=1) return 1;   // base cases
        if(memo[n] != 0) return memo[n];
        memo[n] = helper(n-1, memo) + helper(n-2, memo);
        return memo[n];
    }

    private static void timer(String s, IntToLongFunction f, int n) {
        long t0 = System.nanoTime();
        long result = f.applyAsLong(n);
        long t1 = System.nanoTime();
        double ms = (t1 - t0) / 1e6;
        System.out.println(s + "n=" + n + ", ways=" + result + ", time=" + String.format("%.3f", ms) +" ms");

    }

    private static long[] fibPair(long k) {
        if (k == 0) return new long[]{0, 1};
        long[] p = fibPair(k / 2);
        long a = p[0];
        long b = p[1];
        long c = a * (2*b - a);
        long d = a * a + b * b;

        if (k % 2 == 0) {
            return new long[]{c, d};
        }else{
            return new long[]{d, c + d};
        }
    }

    public static long waysDirect(int n) {
        return fibPair(n + 1)[0];
    }

    public static void main(String[] args) {
       int[] tests = new int[]{10,20,30,35,40};

       for(int n : tests) {
           if(n <= 35){
              timer("recursive ", PracticeTwo::waysRecursive, n);
           }
           else{
              System.out.println("recursive n=" + n + "(skipped: too slow)");
           }
           timer("iterative ", PracticeTwo::waysIterative, n);
           timer("memoized ", PracticeTwo::waysMemo, n);
           timer("direct ", PracticeTwo::waysDirect, n);
           System.out.println();
       }
    }
}

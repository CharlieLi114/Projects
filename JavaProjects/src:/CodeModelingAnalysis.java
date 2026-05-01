/**
 * This program models the count behavior and timing of two nested-loop fragments.
 *
 * @author Chengyu Li {@literal <lic24@wfu.edu>}
 * @version 0.1, Oct. 19, 2025
 */

public class CodeModelingAnalysis {

    /**
     * Counts the number of inner-loop increments for fragment 1.
     *
     * @param n upper bound for the outer loop (size parameter)
     * @return total number of increments performed
     */
    public static long frag1(int n) {
        long sum = 0;  // counts total number of increments
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i * i; j++) {
                for (int k = 0; k < j; k++) {
                    sum++;
                }
            }
        }
        return sum;   // exact count for fragment 1
    }


    /**
     * Counts the number of inner-loop increments for fragment 2.
     *
     * @param n upper bound for the outer loop (size parameter)
     * @return total number of increments performed
     */
    public static long frag2(int n) {
        long sum = 0;  // counts total number of increments
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < i * i; j++) {
                if (j % i == 0) {
                    for (int k = 0; k < j; k++) {
                        sum++;
                    }
                }
            }
        }
        return sum;  // exact count for fragment 2
    }

    /**
     * Averages runtime for fragment 1 over r trials using System.nanoTime().
     *
     * @param n problem size to run
     * @param r number of trials to average
     * @return average elapsed time in nanoseconds
     */
    private static long avgTimeFrag1(int n, int r) {
        frag1(n);
        long total = 0;  // accumulate elapsed time in nanoseconds
        for (int t = 0; t < r; t++) {
            long start = System.nanoTime();
            frag1(n);
            total += System.nanoTime() - start;
        }
        return total / r;   // average time per run (ns)
    }

    /**
     * Averages runtime for fragment 2 over r trials using System.nanoTime().
     *
     * @param n problem size to run
     * @param r number of trials to average
     * @return average elapsed time in nanoseconds
     */
    private static long avgTimeFrag2(int n, int r) {
        frag2(n);
        long total = 0;  // accumulate elapsed time in nanoseconds
        for (int t = 0; t < r; t++) {
            long start = System.nanoTime();
            frag2(n);
            total += System.nanoTime() - start;
        }
        return total / r;  // average time per run (ns)
    }



    /**
     * Program entry: prints CSV of n, counts, and average times.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        int[] ns = {40, 60, 80, 100};  // problem sizes to test
        int trials = 3;

        System.out.println("n,frag1_count,frag1_ns,frag2_count,frag2_ns");

        for (int n : ns) {
            final int N = n;

            long count1 = frag1(N);
            long time1 = avgTimeFrag1(N, trials);


            long count2 = frag2(N);
            long time2 = avgTimeFrag2(N, trials);

            System.out.println(n + "," + count1 + "," + time1 + "," + count2 + "," + time2);
        }
    }
}

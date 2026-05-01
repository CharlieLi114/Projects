import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class PracticeThree {

    public static long hanoiIterative(int n, int from, int to){
        if (n <= 0 || from == to) return 0;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{n, from, to, 0});
        long moves = 0;

        while(!stack.isEmpty()){
            int[] f = stack.pop();
            int disks = f[0];
            int src = f[1];
            int dest = f[2];
            int state = f[3];

            if(disks ==1){
                System.out.println(src + " --> " + dest);
                moves++;
                continue;
            }

            int aux = 6- src - dest;

            if(state==0){
                f[3] = 1;
                stack.push(f);
                stack.push(new int[]{disks -1, src, aux, 0});
            }else if(state==1){
                System.out.println(src + " --> " + dest);
                moves++;
                f[3] = 2;
                stack.push(f);
            }else{
                stack.push(new int[]{disks -1, aux, dest, 0});
            }
        }
        return moves;
    }

    public static long hanoiRecursiveCount(int n, int from, int to){
        if(n==1){
            return 1;
        }else{
            int aux = 6 - from - to;
            long left = hanoiRecursiveCount(n-1, from, aux);
            long here = 1;
            long right = hanoiRecursiveCount(n-1, aux, to);
            return left + here + right;
        }
    }

    public static long hanoiIterativeCount(int n, int from, int to){
        java.util.Deque<int[]> stack = new java.util.ArrayDeque<>();
        stack.push(new int[]{n, from, to, 0});
        long moves = 0;

        while(!stack.isEmpty()){
            int[] f = stack.pop();
            int disks = f[0];
            int src = f[1];
            int dest = f[2];
            int state = f[3];

            if(disks ==1){
                moves++;
                continue;
            }

            int aux = 6- src - dest;

            if(state==0){
                f[3] = 1;
                stack.push(f);
                stack.push(new int[]{disks -1, src, aux, 0});
            }else if(state==1){
                moves++;
                f[3] = 2;
                stack.push(f);
            }else{
                stack.push(new int[]{disks -1, aux, dest, 0});
            }
        }
        return moves;
    }

    private static void timer(String name, java.util.function.IntToLongFunction f, int n){
        long t0 = System.nanoTime();
        long ans = f.applyAsLong(n);
        long t1 = System.nanoTime();
        double ms = (t1 - t0) / 1e6;
        System.out.println(name+" n="+ n + " moves=" + ans+ " time=" + String.format("%.3f", ms) + " ms");
    }

    public static void main(String[] args) {
       int[] tests = {12,14,16,18};

       for(int n: tests){
           timer("recursive", x -> hanoiRecursiveCount(x,1,3), n);
           timer("iterative", x -> hanoiIterativeCount(x,1,3), n);
           System.out.println();
       }
    }
}

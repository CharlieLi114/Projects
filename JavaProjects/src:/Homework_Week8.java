/**
 * Generate random permutations via three methods and time them.
 *
 * @author Chengyu Li {@literal <lic24@wfu.edu>}
 * @version 0.1, Oct. 26, 2025
 */
import java.util.*;
import java.util.function.Function;
public class Homework_Week8 {
    /** Return a uniform integer in the half-open range [lowerBound, upperBound). */
    public static int randomIntInRange(Random randomGenerator, int lowerBound, int upperBound) {
        return lowerBound + randomGenerator.nextInt(upperBound - lowerBound);
    }

    /**
     * Algorithm 1: repeat random draws; check membership by scanning the prefix a[0..positionIndex-1].
     *
     * @param permutationSize size of the permutation
     * @param randomGenerator source of randomness
     * @return a permutation array of length permutationSize
     */
    public static int[] permuteAlgorithmOne(int permutationSize, Random randomGenerator) {
        int[] permutationArray = new int[permutationSize];

        for (int positionIndex = 0; positionIndex < permutationSize; positionIndex++) {
            int candidateValue;
            while (true) {
                candidateValue = 1 + randomGenerator.nextInt(permutationSize);
                boolean alreadyUsedInPrefix = false;

                for (int prefixIndex = 0; prefixIndex < positionIndex; prefixIndex++) {
                    if (permutationArray[prefixIndex] == candidateValue) {
                        alreadyUsedInPrefix = true;
                        break;
                    }
                }
                if (!alreadyUsedInPrefix) {
                    break;
                }
            }
            permutationArray[positionIndex] = candidateValue;
        }
        return permutationArray;
    }

    /**
     * Algorithm 2: same as Algorithm 1, but use a boolean array for O(1) membership tests.
     *
     * @param permutationSize size of the permutation
     * @param randomGenerator source of randomness
     * @return a permutation array of length permutationSize
     */
    public static int[] permuteAlgorithmTwo(int permutationSize, Random randomGenerator) {
        int[] permutationArray = new int[permutationSize];
        boolean[] isValueUsed = new boolean[permutationSize + 1]; // marks values 1..permutationSize

        for (int positionIndex = 0; positionIndex < permutationSize; positionIndex++) {
            int candidateValue;
            do {
                candidateValue = 1 + randomGenerator.nextInt(permutationSize);
            } while (isValueUsed[candidateValue]);

            isValueUsed[candidateValue] = true;
            permutationArray[positionIndex] = candidateValue;
        }
        return permutationArray;
    }

    /**
     * @param permutationSize size of the permutation
     * @param randomGenerator source of randomness
     * @return a permutation array of length permutationSize
     */
    public static int[] permuteAlgorithmThree(int permutationSize, Random randomGenerator) {
        int[] permutationArray = new int[permutationSize];

        for (int positionIndex = 0; positionIndex < permutationSize; positionIndex++) {
            permutationArray[positionIndex] = positionIndex + 1;
        }

        for (int positionIndex = 1; positionIndex < permutationSize; positionIndex++) {
            int swapIndex = randomIntInRange(randomGenerator, 0, positionIndex + 1);
            int tempValue = permutationArray[positionIndex];
            permutationArray[positionIndex] = permutationArray[swapIndex];
            permutationArray[swapIndex] = tempValue;
        }
        return permutationArray;
    }

    /** Compute average elapsed time in milliseconds over the given number of repetitions. */
    private static double averageMilliseconds(
            int permutationSize,
            int numberOfRepetitions,
            Random randomGenerator,
            Function<Integer, int[]> generatorFunction) {

        long totalElapsedNanoseconds = 0;

        for (int repetitionIndex = 0; repetitionIndex < numberOfRepetitions; repetitionIndex++) {
            long startTimeNanoseconds = System.nanoTime();
            int[] generatedPermutation = generatorFunction.apply(permutationSize);

            if (generatedPermutation.length == -1) {
                System.out.print("");
            }

            totalElapsedNanoseconds += System.nanoTime() - startTimeNanoseconds;
        }
        return totalElapsedNanoseconds / 1_000_000.0 / numberOfRepetitions;
    }

    public static void main(String[] args) {
        final int numberOfRepetitions = 10;
        Random randomGenerator = new Random(112L);

        System.out.println("Algorithm 1: N = 250, 500, 1K, 2K");
        for (int permutationSize : new int[]{250, 500, 1000, 2000}) {
            double averageMs = averageMilliseconds(
                    permutationSize,
                    numberOfRepetitions,
                    randomGenerator,
                    size -> permuteAlgorithmOne(size, randomGenerator));
            System.out.printf("n=%d  avg=%.3f ms%n", permutationSize, averageMs);
        }

        System.out.println("\nAlgorithm 2: N = 25K, 50K, 100K, 200K, 400K, 800K");
        for (int permutationSize : new int[]{25_000, 50_000, 100_000, 200_000, 400_000, 800_000}) {
            double averageMs = averageMilliseconds(
                    permutationSize,
                    numberOfRepetitions,
                    randomGenerator,
                    size -> permuteAlgorithmTwo(size, randomGenerator));
            System.out.printf("n=%d  avg=%.3f ms%n", permutationSize, averageMs);
        }

        System.out.println("\nAlgorithm 3: N = 100K, 200K, 400K, 800K, 1.6M, 3.2M, 6.4M");
        for (int permutationSize : new int[]{100_000, 200_000, 400_000, 800_000, 1_600_000, 3_200_000, 6_400_000}) {
            double averageMs = averageMilliseconds(
                    permutationSize,
                    numberOfRepetitions,
                    randomGenerator,
                    size -> permuteAlgorithmThree(size, randomGenerator));
            System.out.printf("n=%d  avg=%.3f ms%n", permutationSize, averageMs);
        }
    }
}

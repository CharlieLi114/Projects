/**
 * CSC 112 Lab Week11 (PizzaSearch)
 * This program simulates a student searching for pizza in
 * a randomly generated floor plan. The floor plan size and 
 * random number seed are user inputs.
 */
import java.util.Scanner;
import java.util.Random;


public class PizzaSearch {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in); // keyboard
        int seed = 0;      // seed for random number
        int matDimen = 0;  // number of rows and number of columns

        // read in the floor plan size and random number seed
        System.out.print("Enter size and seed -> ");
        matDimen = keyboard.nextInt();
        seed = keyboard.nextInt();

        // make certain user input is correct, otherwise fail gracefully
        if(matDimen < 2) {
           System.out.println("maze dimension is " + matDimen + ", must be >= 2 ");
           return;
        }
        else if(seed <= 0) {
           System.out.println("seed is " + seed + ", must be > 0 ");
           return;
        }

        // generate the random floor plan
        char[][] floor = generateFloorPlan(matDimen, seed);
        Stack<Move> steps = new LinkedList<Move>();  // stack for moves taken
        int numMoves = 0;  // total number of moves

        // search for that pizza...
        System.out.println("Original floor plan");  
        printFloorPlan(floor);  
        System.out.println("");  
        numMoves = findPath(floor, steps);
        printFinalPath(floor, steps, numMoves);  
    }


    /**
     * This method generates a random floor plan with dimensions 
     * matDimen by maxDimen.
     *
     * @param matDimen is the number of rows and number of columns
     * @param seed is the random number seed
     */
    public static char[][] generateFloorPlan(int matDimen, int seed) {
        char[][] floor = new char[matDimen][matDimen];
        Random random = new Random(seed);

        for(int i = 0; i < matDimen; i++)
            for(int j = 0; j < matDimen; j++)
                floor[i][j] = (random.nextInt()%2 == 0 ? '0' : '1');
        // make certain there is an entrance and exit
        floor[0][0] = floor[matDimen - 1][matDimen - 1] = '0';  
        return floor;
    }


    /**
     * This method will print the floor plan to the screen in matrix format.
     *
     * @param floor char matrix of the floor plan
     */
    public static void printFloorPlan(char[][] floor) {
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor.length; j++) {
                System.out.print(floor[i][j]);
                if (j < floor.length - 1)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }



    /**
     * This method will attempt to find a path given a floor plan.
     *
     * @param floor char matrix of the floor plan
     * @param path Stack<Move> of the moves taken
     */
    public static int findPath(char[][] floor, Stack<Move> path) {
        int n = floor.length;
        int row = 0;
        int col = 0;

        floor[0][0] = '2';
        path.push(new Move(0, 0));

        int numMoves = 0;
        boolean found = false;
        boolean backHome = false;

        while (!found && !backHome) {

            if (row == n - 1 && col == n - 1) {
                found = true;
                break;
            }

            // EAST
            if (col + 1 < n && floor[row][col + 1] == '0') {
                col++;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // NORTH-EAST
            else if (row - 1 >= 0 && col + 1 < n && floor[row - 1][col + 1] == '0') {
                row--;
                col++;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // NORTH
            else if (row - 1 >= 0 && floor[row - 1][col] == '0') {
                row--;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // NORTH-WEST
            else if (row - 1 >= 0 && col - 1 >= 0 && floor[row - 1][col - 1] == '0') {
                row--;
                col--;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // WEST
            else if (col - 1 >= 0 && floor[row][col - 1] == '0') {
                col--;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // SOUTH-WEST
            else if (row + 1 < n && col - 1 >= 0 && floor[row + 1][col - 1] == '0') {
                row++;
                col--;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // SOUTH
            else if (row + 1 < n && floor[row + 1][col] == '0') {
                row++;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }
            // SOUTH-EAST
            else if (row + 1 < n && col + 1 < n && floor[row + 1][col + 1] == '0') {
                row++;
                col++;
                floor[row][col] = '2';
                path.push(new Move(row, col));
                numMoves++;
                continue;
            }

            // NO MOVES → backtrack
            if (path.size() > 1) {
                path.pop();
                Move prev = path.peek();
                row = prev.getRow();
                col = prev.getCol();
                numMoves++;
            }
            else {
                // no path exists
                path.pop();   // empty the stack
                backHome = true;
            }
        }

        return numMoves;
    }



    /**
     * This method will print the final floor plan and path (if it exists).  
     *
     * @param floor char matrix of the floor plan
     * @param path Stack<Move> of the moves taken
     * @param numMoves int for the number of moves taken
     */
    public static void printFinalPath(char[][] floor, Stack<Move> path, int numMoves) {

        // if no path exists (stack is empty)
        if (path.isEmpty()) {
            System.out.println("No path exists!");
            System.out.println("Total number of moves made: " + numMoves);
            return;
        }

        // extract moves in correct order (start → end)
        java.util.ArrayList<Move> steps = new java.util.ArrayList<>();
        while (!path.isEmpty()) {
            steps.add(0, path.pop());     // reverse order
        }

        boolean[][] onPath = new boolean[floor.length][floor.length];
        for (Move m : steps)
            onPath[m.getRow()][m.getCol()] = true;

        System.out.println("Floor plan after experiment");

        // print floor with '*' marking the actual path
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor.length; j++) {
                if (onPath[i][j]) {
                    System.out.print("*");
                }
                else {
                    // 1 stays 1, everything else (0 or visited '2') prints as 0
                    if (floor[i][j] == '1') System.out.print("1");
                    else System.out.print("0");
                }

                if (j < floor.length - 1) System.out.print(" ");
            }
            System.out.println();
        }

        // print final path list
        System.out.print("Path: [");
        for (int i = 0; i < steps.size(); i++) {
            System.out.print(steps.get(i).toString());
            if (i < steps.size() - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("Path length: " + steps.size());
        System.out.println("Total number of moves made: " + numMoves);
    }

    /**
     * The method will pause the program until the user presses 
     * the "Return" key.
     */
    public static void pause() {
        System.out.println("Press Return key to continue... ");
        try {  System.in.read(); }  
        catch(Exception e){  } 
    }

}


/**
 * Class that stores a move (row and column indices)
 */
class Move {  
    private int row = 0;  // row location of a move
    private int col = 0;  // column location of a move
                          //
    public Move(int row, int col) {  this.row = row; this.col = col;  }
    public int getRow(){  return row;  }
    public int getCol(){  return col;  }

    @Override
    public String toString() {  return new String("(" + row + ", " + col + ")");  }
}



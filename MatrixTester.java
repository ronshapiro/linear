import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class MatrixTester{
  public static void main(String[] args){
    if (args.length < 1){
      System.err.println("Too few arguments. Please pass input_file.");
      System.exit(0);
    }
    
    /*
    Matrix a = new Matrix(new int[][]{{1, 2},
                                      {4, 9}});
    ColumnVector b = new ColumnVector(2);
    b.set(0, 5.0);
    b.set(1, 21.0);
    a.solveSystem(b);
    */

    Scanner inputFile = null;
    try {
      inputFile = new Scanner(new File(args[0]));
    } catch (FileNotFoundException e){
      System.err.println("IO Error: Input file not found");
      System.exit(0);
    }
    
    // Read input and initialize Matrix
    int SIZE = -1;
    int K = -1;
    Matrix A = null;
    ColumnVector b = null;
    int A_Index = 0;
    int bIndex = 0;
    for(int i = 0; inputFile.hasNextLine(); i++){
      String line = inputFile.nextLine();
      if (i == 0){
        SIZE = Integer.parseInt(line);
        A = new Matrix(SIZE, SIZE);
        b = new ColumnVector(SIZE);
      }
      else if (i == 1){
        K = Integer.parseInt(line);
      }
      else if (i - 2 < SIZE){
        String[] nums = line.split(" ");
        for (int j = 0; j < nums.length; j++)
          A.set(A_Index, j, Double.parseDouble(nums[j]));
        A_Index++;
      }
      else {
        b.set(bIndex, Double.parseDouble(line));
        bIndex++;
      }
    }

    Matrix regularSolution = A.solve(b);
    System.out.println(regularSolution + "\n");

    Matrix diagonalSolution = A.solveDiagonalSystem(b, K);
    System.out.println(diagonalSolution + "\n");
    
    System.out.println("Solutions equal? " + regularSolution.equals(diagonalSolution));
  }
}

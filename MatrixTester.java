import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MatrixTester{
  public static void main(String[] args){
    if (args.length < 1){
      System.err.println("Too few arguments. Please pass input_file.");
      System.exit(0);
    }

    Scanner inputFile = null;
    try {
      inputFile = new Scanner(new File(args[0]));
    } catch (FileNotFoundException e){
      System.err.println("IO Error: Input file not found");
      System.exit(0);
    }
    
    // Read input and initialize Matrix
    Matrix A = null;
    ColumnVector b = null;
    int A_Index = 0;
    int bIndex = 0;
    int m = 0, n = 0;
    for(int i = 0; inputFile.hasNextLine(); i++){
      String line = inputFile.nextLine();
      if (i == 0){
        m = Integer.parseInt(line);        
      }
      else if (i == 1){
        n = Integer.parseInt(line);
        A = new Matrix(m, n);
        b = new ColumnVector(m);
        
      }
      else if (i - 2 < m){
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

    Matrix Ab = A.augmentMatrix(b);
    //System.out.println(A.augmentMatrix(b).rref());
    Matrix rref = Ab.rref();
    System.out.println("RREF = \n" + rref);
    ArrayList<Solution> solutions = Matrix.findSolutions(rref);
    if (solutions.size() == 0){
      System.out.println("There are no solutions to this system");
      return;
    }
    
    Solution particular = null;
    Matrix nullspace = new Matrix(n, 0);
    for (Solution s : solutions){
      if (s.getType() == SolutionType.SPECIAL)
        nullspace = nullspace.augmentMatrix(s.getVector());
      else
        particular = s;
    }
    if (particular != null && nullspace.cols() == 0){
      System.out.println("There is only one solution:\n" + particular.getVector());
    }
    else {
      System.out.println("The particular solution is:\n" + particular.getVector());
      System.out.println("The nullspace of A (the set of special solutions) is:\n" + nullspace);
    }
  }
}

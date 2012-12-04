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

    Matrix A = new Matrix(new int[][]{
        {2,-1,0,0,0,0,0,0,0,0},
        {-1,2,-1,0,0,0,0,0,0,0},
        {0,-1,2,-1,0,0,0,0,0,0},
        {0,0,-1,2,-1,0,0,0,0,0},
        {0,0,0,-1,2,-1,0,0,0,0},
        {0,0,0,0,-1,2,-1,0,0,0},
        {0,0,0,0,0,-1,2,-1,0,0},
        {0,0,0,0,0,0,-1,2,-1,0},
        {0,0,0,0,0,0,0,-1,2,-1},
        {0,0,0,0,0,0,0,0,-1,2},
      });
    System.out.println(A.inverseIteration());
  }
}

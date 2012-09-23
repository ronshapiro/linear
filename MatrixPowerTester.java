import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MatrixPowerTester{
  public static void main(String[] args){
    if (args.length > 2){
      System.err.println("Too many arguments. Please pass input_file and output_file");
      System.exit(0);
    } else if (args.length < 2){
      System.err.println("Too few arguments. Please pass input_file and output_file");
      System.exit(0);
    }



    Scanner inputFile = new Scanner(System.in); //initialize so that compiler doesn't yell that the object wasn't initialized
    try {
      inputFile = new Scanner(new File(args[0]));
    } catch (FileNotFoundException e){
      System.err.println("File Not Found");
      System.exit(0);
    }
    
    /*
    for (int i = 0; i < args.length; i++) System.out.println(args[i]);
    Matrix m = new Matrix(new int[][]{{1, 2}, {3, 4}});
    Matrix identity = new Matrix(new int[][]{{1, 0}, {0, 1}});
    Matrix switcher = new Matrix(new int[][]{{0, 1}, {1, 0}});
    Matrix zero  = new Matrix(new int[][]{{0, 0}, {0, 0}});    

    Matrix twoA1 = new Matrix(new int[][]{{2,3},{5,1}});
    Matrix twoA2 = new Matrix(new int[][]{{4}, {2}});
    System.out.println(twoA1.multiply(twoA2));

    System.out.println(identity.scale(2.0));
    */

    /*
    Note cloning might not even be necessary - 
    suppose k = 4
    a a a a a a a a
    compute a*a once, then duplicate matrix 3 more times
    b = a * a;
    b b.clone() b.clone() b.clone()
    compute b*b once, then duplicate matrix 1 more time
    c = b * b
    c c.clone()
    compute d = c * c
    */
  }
}
  
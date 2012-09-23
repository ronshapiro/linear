import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class MatrixPowerTester{
  public static void main(String[] args){
    if (args.length > 2){
      System.err.println("Too many arguments. Please pass input_file and output_file");
      System.exit(0);
    } else if (args.length < 2){
      System.err.println("Too few arguments. Please pass input_file and output_file");
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
    int SIZE = -1;
    int K = -1;
    Matrix A = null;
    for(int i = 0; inputFile.hasNextLine(); i++){
      String line = inputFile.nextLine();
      if (i == 0){
        SIZE = Integer.parseInt(line);
        A = new Matrix(SIZE, SIZE);
      } else if (i == 1){
        K = Integer.parseInt(line);
      }
      else {
        String[] nums = line.split(" ");
        int index = i - 2; //2 is the amount of lines before the matrix begins
        for (int j = 0; j < nums.length; j++)
          A.set(index, j, Double.parseDouble(nums[j]));
      }
    }
    final Matrix INITIAL_MATRIX = A.clone();

    for (int times = K; times > 0; times--)
      A = A.multiply(A);

    PrintStream outputFile = null;
    try {
      outputFile = new PrintStream(new FileOutputStream(args[1]));
    } catch (FileNotFoundException e){
      System.err.println("IO Error: Output file not found");
      System.exit(0);
    }
    outputFile.println(A);
    
  }
}
  
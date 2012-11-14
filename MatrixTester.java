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

    int m = -1;
    double C = -1, D = -1, E = 1;
    for(int i = 0; inputFile.hasNextLine(); i++){
      String line = inputFile.nextLine();
      if (i == 0){
        m = Integer.parseInt(line);        
      }
      else if (i == 1){
        C = Double.parseDouble(line);
      }
      else if (i == 2){
        D = Double.parseDouble(line);
      }
      else if (i == 3){
        E = Double.parseDouble(line);
      }
    }

    Matrix A = new Matrix(m,3);
    ColumnVector b = new ColumnVector(m);
    for (int t = 0; t < m; t++){
      A.set(t, 0, 1);
      A.set(t, 1, t);
      A.set(t, 2, t*t);
      double perturbation = Math.random();
      if (Math.random() < .5) perturbation *= -1;
      b.set(t, (C+D*t+E*t*t + perturbation));
    }
    //System.out.println("A = " + A + "\n");
    //System.out.println(b);

    ColumnVector xhat = (A.transpose().multiply(A)).solve(A.transpose().multiply(b)).columnVector();
    System.out.println("Original values vs. reconstructed values:\nC: " + C + " -> " + xhat.get(0)
                       + "\nD: " + D + " -> " + xhat.get(1)
                       + "\nE: " + E + " -> " + xhat.get(2));
  }
}

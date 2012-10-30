import java.util.ArrayList;

public class Matrix{
  private double[][] mMatrix;
  private Integer rank = null;

  public static Matrix identity(int dimensions){
    Matrix identity = new Matrix(dimensions, dimensions);
    for (int i = 0; i < dimensions; i++)
      identity.set(i, i, 1);
    return identity;
  }
  
  public static double dot(RowVector r, ColumnVector c){
    if (r.length() != c.length()){
      System.err.println("Vectors are different lengths");
      System.exit(0);
    }
    double dotProduct = 0;
    for (int i = 0; i < r.length(); i++){
      dotProduct += r.get(i)*c.get(i);
    }
    return dotProduct;
  }

  public Matrix augmentMatrix(ColumnVector c){
    if (c.rows() != this.rows()){
      System.err.println("The rows of the matrix and vector don't match.");
      System.exit(0);
    }
    Matrix augmented = new Matrix(this.rows(), this.cols()+1);
    for (int i = 0; i < this.rows(); i++)
      for (int j = 0; j < this.cols(); j++)
        augmented.set(i,j, mMatrix[i][j]);
    for (int i = 0; i < this.rows(); i++){
      augmented.set(i, augmented.cols()-1, c.get(i));
    }

    return augmented;
  }
  public Matrix transpose(){
    Matrix transpose = new Matrix(cols(), rows());

    for (int i = 0; i < rows(); i++)
      for (int j = 0; j < cols(); j++)
        transpose.set(j, i, get(i,j));
    return transpose;
  }

  public Matrix(){
    System.err.println("Can't create empty matrix!");
    System.exit(0);
  }

  public Matrix(int rows, int cols){
    mMatrix = new double[rows][cols];
  }
  
  public Matrix(double[][] array){
    mMatrix = new double[array.length][array[0].length];
    for (int i = 0; i < array.length; i++)
      for (int j = 0; j < array[0].length; j++)
        this.set(i, j, array[i][j]);
  }

  public Matrix(int[][] array){
    mMatrix = new double[array.length][array[0].length];
    for (int i = 0; i < array.length; i++)
      for (int j = 0; j < array[0].length; j++)
        this.set(i, j, (double) array[i][j]);
  }
  
  public Matrix clone(){
    return new Matrix(mMatrix);
  }

  public void set(int n, int m, double value){
    if (value == -0.0) value = 0.0;
    mMatrix[n][m] = value;
  }

  public Double get(int n, int m){
    return new Double(mMatrix[n][m]);
  }

  public int rows(){
    return mMatrix.length;
  }

  public int cols(){
    return mMatrix[0].length;
  }

  public Matrix add(Matrix second){
    if (this.cols() != second.cols() ||
        this.rows() != second.rows()){
      System.err.println("Matrices don't have proper sizes to add");
      System.exit(0);
    }
    Matrix sum = new Matrix(this.cols(), this.rows());
    for (int i = 0; i < this.rows(); i++)
      for (int j = 0; j < this.cols(); j++)
        sum.set(i, j, this.get(i,j) + second.get(i,j));
    
    return sum;
  }
  
  public Matrix subtract(Matrix second){
    return add(second.clone().scale(-1));
  }

  public Matrix scale(double scalar){
    for (int i = 0; i < this.rows(); i++)
      for (int j = 0; j < this.cols(); j++)
        this.set(i, j, this.get(i, j) * scalar);
    return this;
  }

  public Matrix multiply(Matrix second){
    if (this.cols() != second.rows()){
      System.err.println("Matrices don't have proper sizes to multiply");
      System.exit(0);
    }
    Matrix product = new Matrix(this.rows(), second.cols());
    for (int i = 0; i < product.rows(); i++)
      for (int j = 0; j < product.cols(); j++){
        double dotProduct = 0;
        for (int k = 0; k < this.cols(); k++)
          dotProduct += this.get(i,k) * second.get(k,j);
        product.set(i,j, dotProduct);
      }
    return product;
  }

  public String toString(){
    String output = "";
    for (double[] row: mMatrix){
      for (double i: row){
        output += i + " ";
      }
      output += "\n";
    }
    return output.substring(0, output.length()-2);
  }

  public boolean equals(Matrix second){
    if (this.cols() != second.cols() ||
        this.rows() != second.rows()){
      System.err.println("Dimensions not equal");
      return false;
    }
    for (int i = 0; i < this.rows(); i++)
      for (int j = 0; j < this.cols(); j++)
        if (!this.get(i,j).equals(second.get(i,j))){
          return false;
        }
    return true;
  }

  public boolean isSquare(){
    return rows() == cols();
  }
  public boolean isColumn(){
    return cols() == 1;
  }

  public ColumnVector columnClone(int col){
    ColumnVector clone = new ColumnVector(rows());
    for (int i = 0; i < rows(); i++)
      clone.set(i, get(i, col));
    return clone;
  }

  public RowVector rowClone(int row){
    RowVector clone = new RowVector(cols());
    for (int i = 0; i < cols(); i++)
      clone.set(i, get(row, i));
    return clone;
  }

  public void swapRows(int row1, int row2){
    for (int i = 0; i < cols(); i++){
      double temp = get(row1, i);
      set(row1, i, get(row2, i));
      set(row2, i, temp);
    }
  }

  public void multiplyRow(int rowId, double multiplier){
    for (int i = 0; i < cols(); i++)
      set(rowId, i, get(rowId, i) * multiplier);
  }

  public ColumnVector solve(ColumnVector b){
    return solveDiagonalSystem(b, cols()*2+1);
  }
  
  public ColumnVector solveDiagonalSystem(ColumnVector b, int width){
    if (!b.isColumn()){
      System.err.println("b is not a column vector");
      System.exit(0);
    }
    if (!isSquare()){
      System.err.println("A is not a square");
      System.exit(0);
    }
    if (b.rows() != rows()){
      System.err.println("b does not have enough rows");
      System.exit(0);
    }
    int n = 0;

    int span = (width-1)/2; //the span is the movement in any direction of the diagonal. for example, if width is 5, span is 2; width = 3, span = 1
    
    Matrix U = clone(); //don't alter internal matrix data structure
    Matrix L = Matrix.identity(rows());

    if (U.get(0,0).equals(0.0))
      for (int i = 1; i < U.cols(); i++)
        if (!U.get(i,0).equals(0.0)){
          U.swapRows(0, i);
          break;
        }

    //A = LU
    for (int j = 0; j < U.cols(); j++){
      int pivotRow = j;
      double pivot = U.get(j, j);
      
      //Swap rows if necessary
      Double zero = new Double(0.0);
      if (zero.equals(pivot)){
        for (int s = j; s < U.cols(); s++){
          if (!zero.equals(U.get(s,s))){
            U.swapRows(j,s);
            break;
          }
        }
      }
      
      for (int i = j+1, spanCounter = 0;
           i < U.rows() && spanCounter < span;  //only look down at most span rows (if A is not diagonal, it will look everywhere, but if it is, it will not look at zeros
           i++, spanCounter++){
        double l = U.get(i,j)/pivot;
        L.set(i,j,l);
        for (int k = j; k < U.cols(); k++){
          n++;
          U.set(i, k, U.get(i, k) - U.get(pivotRow, k) * l);
        }
      }
    }

    //Lc = b
    ColumnVector c = new ColumnVector(b.rows());
    for (int i = 0; i < b.rows(); i++){
      double val = b.get(i);
      RowVector r = L.rowClone(i);
      int j = i - span; 
      if (j < 0) j = 0;
      for ( ; j < i; j++){
        n++;
        val -= r.get(j)*c.get(j);
      }
      c.set(i,val/r.get(i));
    }

    //Ux = c
    ColumnVector x = new ColumnVector(b.rows());
    int rowMax = b.rows()-1;
    for (int i = rowMax; i >= 0; i--){
      double val = c.get(i);
      RowVector r = U.rowClone(i);
      int j = rowMax+span;
      if (j > rowMax) j = rowMax;
      for (; j >= i; j--){
        n++;
        val -= r.get(j)*x.get(j);
      }
      x.set(i,val/r.get(i));
    }
    //System.out.println("n = " + n); //uncomment this line to see the amount of calculations necessary to solve
    return x;
  }

  public Matrix rref(){
    Matrix rref = clone();

    //perform row swap for first row if necessary
    if (rref.get(0,0).equals(0.0)){
      boolean successful = false;
      for (int i = 1; i < rref.cols(); i++)
        if (!rref.get(i,0).equals(0.0)){
          rref.swapRows(0, i);
          successful = true;
          break;
        }
      if (!successful){
        System.out.println("No row swap was available on the first row");
        System.exit(0);
      }
   } 

    //A -> U
    for (int i = 0; i < rref.cols() && i < rref.rows(); i++){
      for (int j = i + 1; j < rref.rows(); j++){
        Double factor = rref.get(j,i)/rref.get(i, i);
        if (factor.isNaN())
          factor = 1.0;
        for (int k = 0; k <  rref.cols(); k++)
          rref.set(j,k, rref.get(j,k) - factor*rref.get(i,k));
      }
    }

    ArrayList<Integer> pivotX = new ArrayList<Integer>();
    ArrayList<Integer> pivotY = new ArrayList<Integer>();
    int lastPivotColumn = 0;
    for (int i = 0; i < rref.rows(); i++){
      for (int j = lastPivotColumn; j < rref.cols(); j++){
        if (!rref.get(i,j).equals(0.0)){
          pivotX.add(i);
          pivotY.add(j);
          lastPivotColumn = j;
          break;
        }
      }
    }

    //create zeros above pivots
    for (int i = pivotX.size()-1; i >= 0; i--){
      Double pivot = rref.get(pivotX.get(i), pivotY.get(i));
      for (int row = 0; row < pivotX.get(i); row++){
        Double factor = rref.get(row, pivotY.get(i))/pivot;
        for (int col = 0; col < rref.cols(); col++){
          rref.set(row, col, rref.get(row,col) - factor*rref.get(pivotX.get(i), col));
        }
      }
    }
    
    //produce ones in all the pivots
    for (int i = 0; i < pivotX.size(); i++){
      int row = pivotX.get(i);
      double pivot = rref.get(pivotX.get(i), pivotY.get(i));
      for (int col = 0; col < rref.cols(); col++)
        rref.set(row, col, rref.get(row, col)/pivot);
    }

    rank = pivotX.size();

    return rref;
  }

  public static ArrayList<Solution> findSolutions(Matrix rref){
    ArrayList<Solution> solutions = new ArrayList<Solution>();
    for (int i = rref.rows()-1; i >= 0; i--){
      boolean zeroRow = true;
      for (int j = 0; j < rref.cols()-1; j++){
        if (!rref.get(i,j).equals(0.0)){
          zeroRow = false;
          break;
        }
      }
      if (zeroRow &&
          !rref.get(i, rref.cols()-1).equals(0.0)){
        //System.out.println("There are no solutions since there is a zero row in R but the corresponding row in vector d is not zero");
        return solutions;
      }
    }

    //find pivots
    ArrayList<Integer> pivotX = new ArrayList<Integer>();
    ArrayList<Integer> pivotY = new ArrayList<Integer>();
    ArrayList<ColumnVector> freeColumns = new ArrayList<ColumnVector>();
    ArrayList<ColumnVector> pivotColumns = new ArrayList<ColumnVector>();
    int lastPivotColumn = 0;
    for (int i = 0; i < rref.rows(); i++){
      for (int j = lastPivotColumn; j < rref.cols(); j++){
        if (!rref.get(i,j).equals(0.0)){
          pivotX.add(i);
          pivotY.add(j);
          lastPivotColumn = j;
          ColumnVector c = new ColumnVector(rref.rows());
          for (int k = 0; k < c.length(); k++){
            c.set(k, rref.get(k, j));
          }
          pivotColumns.add(c);
          break;
        }
      }
    }

    for (int i = 0; i < rref.cols(); i++){
      ColumnVector c = new ColumnVector(rref.rows());
      for (int j = 0; j < c.length(); j++){
        c.set(j, rref.get(j, i));
      }
      boolean found = false;
      for (int j = 0; j < pivotColumns.size(); j++){
        if (pivotColumns.get(j).equals(c))
          found = true;
      }
      if (!found)
        freeColumns.add(c);
    }

    for (int i = 0; i < freeColumns.size(); i++){
      ColumnVector c = new ColumnVector(rref.cols()-1);
      for (int j = 0; j < c.length(); j++) //Use NaN as a marker for unset spots in the vector
        c.set(j, Double.NaN);
      int pivotCounter = 0;
      for (int j = 0; j < pivotY.size(); j++)
        c.set(pivotY.get(j), -1*freeColumns.get(i).get(pivotCounter++));
      int nanCount = 0;
      for (int j = 0; j < c.length(); j++){
        if (Double.isNaN(c.get(j))){
          if (nanCount == i)
            c.set(j, 1);
          else
            c.set(j, 0);
          nanCount++;
        }
      }

      if (i == freeColumns.size()-1)
        solutions.add(new Solution(SolutionType.PARTICULAR, (ColumnVector)c.scale(-1)));
      else
        solutions.add(new Solution(SolutionType.SPECIAL, c));
    }

    return solutions;
  }

  public int rank(){
    if (rank == null)
      rref();
    return rank;
  }
  private void println(Object a){ System.out.println(a);}
}
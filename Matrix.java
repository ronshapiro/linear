public class Matrix{
  private double[][] mMatrix;

  public static Matrix identity(int dimensions){
    Matrix identity = new Matrix(dimensions, dimensions);
    for (int i = 0; i < dimensions; i++)
      identity.set(i, i, 1);
    return identity;
  }
  
  public static Matrix columnVector(int rows){
    return new Matrix(rows, 1);
  }

  public static Matrix rowVector(int columns){
    return new Matrix(1, columns);
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
          System.out.println("Something not equal " + this.get(i,j) + " " + second.get(i,j));
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

  public Matrix solveSystem(ColumnVector b){
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

    Matrix U = clone();
    Matrix L = Matrix.identity(rows());

    if (U.get(0,0).equals(0.0))
      for (int i = 1; i < U.cols(); i++)
        if (!U.get(i,0).equals(0.0)){
          U.swapRows(0, i);
          break;
        }

    //TODO: ROW SWAPS for rows after the first
    for (int j = 0; j < U.cols(); j++){
      int pivotRow = j;
      double pivot = U.get(j, j);
      for (int i = j+1; i < U.rows(); i++){
        double l = U.get(i,j)/pivot;
        L.set(i,j,l);
        for (int k = j; k < U.cols(); k++){
          U.set(i, k, U.get(i, k) - U.get(pivotRow, k) * l);
        }
      }
    }

    ColumnVector c = new ColumnVector(b.rows());
    for (int i = 0; i < b.rows(); i++){
      double val = b.get(i);
      RowVector r = L.rowClone(i);
      for (int j = 0; j < i; j++){
        val -= r.get(j)*c.get(j);
      }
      c.set(i,val/r.get(i));
    }

    ColumnVector x = new ColumnVector(b.rows());
    for (int i = b.rows()-1; i >= 0; i--){
      double val = c.get(i);
      RowVector r = U.rowClone(i);
      for (int j = b.rows()-1; j >= i; j--){
        val -= r.get(j)*x.get(j);
      }
      x.set(i,val/r.get(i));
    }
    
    return x;
  }

  public Matrix solve(ColumnVector b){
    return solveDiagonalSystem(b, cols()*2+1);
  }
  
  public Matrix solveDiagonalSystem(ColumnVector b, int width){
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
    
    Matrix U = clone();
    Matrix L = Matrix.identity(rows());

    if (U.get(0,0).equals(0.0))
      for (int i = 1; i < U.cols(); i++)
        if (!U.get(i,0).equals(0.0)){
          U.swapRows(0, i);
          break;
        }

    //TODO: ROW SWAPS for rows after the first
    //A = LU
    for (int j = 0; j < U.cols(); j++){
      int pivotRow = j;
      double pivot = U.get(j, j);
      for (int i = j+1, spanCounter = 0;
           i < U.rows() && spanCounter < span;
           i++, spanCounter++){
        double l = U.get(i,j)/pivot;
        L.set(i,j,l);
        for (int k = j; k < U.cols(); k++){
          n++;
          U.set(i, k, U.get(i, k) - U.get(pivotRow, k) * l);
        }
      }
    }
    System.out.println("L = \n" + L);
    System.out.println("U = \n" + U);    

    //Lc = b
    ColumnVector c = new ColumnVector(b.rows());
    for (int i = 0; i < b.rows(); i++){
      double val = b.get(i);
      RowVector r = L.rowClone(i);
      for (int j = 0; j < i; j++){
        n++;
        val -= r.get(j)*c.get(j);
      }
      c.set(i,val/r.get(i));
    }

    //Ux = c
    ColumnVector x = new ColumnVector(b.rows());
    for (int i = b.rows()-1; i >= 0; i--){
      double val = c.get(i);
      RowVector r = U.rowClone(i);
      for (int j = b.rows()-1; j >= i; j--){
        n++;
        val -= r.get(j)*x.get(j);
      }
      x.set(i,val/r.get(i));
    }
    System.out.println("n = " + n);
    return x;
  }
}


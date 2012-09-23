public class Matrix{
  private double[][] mMatrix;

  /*
    @param n number of rows
    @param m number of columns
   */
  public Matrix(int n, int m){
    mMatrix = new double[n][m];
  }
  
  public Matrix(double[][] array){
    //assert that all inner arrays are same length
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
    /* TODO create a new matrix with the same values*/
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
}


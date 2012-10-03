public class Matrix{
  private double[][] mMatrix;


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
      System.out.println("Dimensions not equal");
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
  
}

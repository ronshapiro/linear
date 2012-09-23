public class Matrix{
  private double[][] mMatrix;
  public Matrix(int n, int m){
    /* TODO initialize matrix */
    mMatrix = new double[n][m];
  }
  public Matrix(double[][] array){
    //assert that all inner arrays are same length
    mMatrix = new double[array.length][array[0].length];
    for (int i = 0; i < array.length; i++)
      for (int j = 0; j < array[0].length; j++)
        mMatrix[i][j] = array[i][j];
  }

  public Matrix(int[][] array){
    mMatrix = new double[array.length][array[0].length];
    for (int i = 0; i < array.length; i++)
      for (int j = 0; j < array[0].length; j++)
        mMatrix[i][j] = (double) array[i][j];
  }
  
  public Matrix clone(){
    /* TODO create a new matrix with the same values*/
    return new Matrix(mMatrix);
  }
}
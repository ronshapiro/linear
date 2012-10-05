public class RowVector extends Matrix{
  public RowVector(int cols){
    super(1, cols);
  }
  public void set(int col, double val){
    set(0, col, val);
  }
  public double get(int col){
    return get(0, col);
  }
  public int length(){
    return cols();
  }
}
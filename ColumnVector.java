public  class ColumnVector extends Matrix{
  public ColumnVector(int rows){
    super(rows, 1);
  }
  public void set(int row, double val){
    set(row, 0, val);
  }
  public double get(int row){
    return get(row, 0);
  }
  public int length(){
    return rows();
  }
}

public  class ColumnVector extends Matrix{
  public ColumnVector(int rows){
    super(rows, 1);
  }
  public void set(int row, double val){
    set(row, 0, val);
  }
  public Double get(int row){
    return get(row, 0);
  }
  public int length(){
    return rows();
  }
  public double norm(){
    double norm = 0.0;
    for (int i = 0; i < length(); i++){
      norm += get(i)*get(i);
    }
    return Math.sqrt(norm);
  }
  public double sum(){
    double sum = 0.0;
    for (int i = 0; i < length(); i++){
      sum += get(i);
    }
    return Math.sqrt(sum);
  }
}

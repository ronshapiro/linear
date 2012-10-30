public class Solution{
  private ColumnVector vector;
  private SolutionType type;
  public Solution(SolutionType t, ColumnVector c){
    type = t;
    vector = c;
  }
  public ColumnVector getVector(){ return vector;}
  public SolutionType getType(){ return type;}
}
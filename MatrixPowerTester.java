public class MatrixPowerTester{
  public static void main(String[] args){
    for (int i = 0; i < args.length; i++) System.out.println(args[i]);

    Matrix m = new Matrix(abc);
    System.out.println(m);
    /*
    Note cloning might not even be necessary - 
    suppose k = 4
    a a a a a a a a
    compute a*a once, then duplicate matrix 3 more times
    b = a * a;
    b b.clone() b.clone() b.clone()
    compute b*b once, then duplicate matrix 1 more time
    c = b * b
    c c.clone()
    compute d = c * c
    */
  }
}
  
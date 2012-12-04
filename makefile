MatrixTester.class: MatrixTester.java
	javac MatrixTester.java

Matrix.class: Matrix.java
	javac Matrix.java

run: Matrix.class MatrixTester.class
	java MatrixTester input.txt

clean:
	rm *.class

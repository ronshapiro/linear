compile: Matrix.java MatrixTester.java
	javac MatrixTester.java

run: Matrix.class MatrixTester.class
	java MatrixTester input.txt output.txt
	cat output.txt

clean:
	rm *.class output.txt
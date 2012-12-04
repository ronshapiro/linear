MatrixTester.class: Matrix.java MatrixTester.java
	javac MatrixTester.java

run: MatrixTester.class
	java MatrixTester input.txt

clean:
	rm *.class

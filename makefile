compile: Matrix.java MatrixPowerTester.java
	javac MatrixPowerTester.java

run: Matrix.class MatrixPowerTester.class
	java MatrixPowerTester input.txt output.txt

clean:
	rm *.class
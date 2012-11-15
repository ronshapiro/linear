Matrix.class: Matrix.java MatrixTester.java
	javac MatrixTester.java

run: Matrix.class
	java MatrixTester input.txt

clean:
	rm *.class
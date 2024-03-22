bin:
	javac -d bin -cp src\Experimentation.java src\GenericsKbAVLApp.java

doc:
	javadoc -d doc -cp src\Experimentation.java src\GenericsKbAVLApp.java

run-Experiment:
	java -cp bin Experimentation

run-Generics:
	java -cp bin GenericsKbAVLApp
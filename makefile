JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES : .java .class


CLASSES = crane.java

classes: $(CLASSES: .java=.class)
	$(JC) $(JFLAGS) $<

run: $(classes)
	$(JVM) crane

clean: 
	$(RM) *.class *~
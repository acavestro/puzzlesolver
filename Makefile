JFLAGS = -g
JC = javac
SRCDIR = src
BINDIR = bin
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java -d $(BINDIR)/

CLASSES = \
	$(SRCDIR)/PuzzleSolver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) $(BINDIR)/*.class

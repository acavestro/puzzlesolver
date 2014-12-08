JFLAGS = -g
JC = javac
SRC = ./src
BUILD = ./build

CLASSES = Tile \
		  PSTile \
		  Puzzle \
		  PuzzleSolver

SOURCES := $(addprefix $(BUILD)/, $(CLASSES))
SOURCES := $(addsuffix .java, $(SOURCES))


default: clean
	mkdir $(BUILD)
	find $(SRC) -type f -name "*.java" -exec cp {} $(BUILD) \;
	$(JC) $(JFLAGS) $(SOURCES)

clean:
	rm -rf $(BUILD)
SRC = ./src
BUILD = ./build
JFLAGS = -g -d $(BUILD)
JC = javac

CLASSES = Tile \
		  PSTile \
		  TileParser \
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
SRC = ./src
BUILD = ./build
JFLAGS = -g -d $(BUILD)
JC = javac

CLASSES = Tile \
		  PSTile \
		  IrregularTileLineException \
		  TileParser \
		  Puzzle \
		  PSPuzzle \
		  UnsolvablePuzzleException \
		  PuzzleBuilder \
		  PuzzleSolver

SOURCES := $(addprefix $(BUILD)/, $(CLASSES))
SOURCES := $(addsuffix .java, $(SOURCES))

default: puzzlesolver

puzzlesolver_old: clean
	mkdir $(BUILD)
	find $(SRC) -type f -name "*.java" -exec cp {} $(BUILD) \;
	$(JC) $(JFLAGS) $(SOURCES)
	find $(BUILD) -type f -name "*.java" -exec rm -rf {} \;
	cd $(BUILD); jar cfe ../PuzzleSolver.jar PuzzleSolver ./*

prepare_build:
	mkdir $(BUILD)
	cp -rv $(SRC)/* $(BUILD)/

clean_build:
	find $(BUILD) -type f -name "*.java" -exec rm -rf {} \;

puzzleclient:
	cd $(BUILD); $(JC) PuzzleSolverClient.java

puzzleserver:
	cd $(BUILD); $(JC) PuzzleSolverServer.java

puzzlesolver: clean prepare_build puzzleclient puzzleserver clean_build

clean:
	rm -rf $(BUILD)
	rm -rf PuzzleSolver.jar

generator: cleangen
	cd tools; javac PuzzleGenerator.java

cleangen:
	rm -f tools/PuzzleGenerator.class

report: cleanreport
	cd ./report; pdflatex relazione.tex; bibtex relazione.aux; pdflatex relazione.tex; pdflatex relazione.tex
	mv ./report/relazione.pdf ./relazione.pdf

cleanreport:
	rm -f relazione.pdf
	find ./report -type f -not -name "*.tex" -not -name "*.bib" -not -name "*.png" -exec rm -f {} \;

submit:
	make puzzlesolver
	make report
	rm -rf build report PuzzleSolver.sublime-workspace .DS_Store .git .gitignore

benchmark: puzzlesolver
	cp src/PuzzleBenchmark.java $(BUILD)
	cd $(BUILD); javac PuzzleBenchmark.java; jar cfe ../PuzzleBenchmark.jar PuzzleBenchmark ./*

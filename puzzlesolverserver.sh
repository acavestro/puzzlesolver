#!/bin/sh
cd build; rmiregistry &
java -cp build PuzzleSolverServer $@
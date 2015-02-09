#!/bin/sh
cd build; rmiregistry &
java PuzzleSolverServer $@

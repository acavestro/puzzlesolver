package unipd.cs.p3.puzzlesolver.netutils;

import java.io.Serializable;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;

public interface SolverResponse extends Serializable {

  public SolutionStatus getStatus();

  public Puzzle getSolution();

  public Exception getError();
}

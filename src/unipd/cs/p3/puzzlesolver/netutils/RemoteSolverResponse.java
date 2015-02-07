package unipd.cs.p3.puzzlesolver.netutils;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;

public class RemoteSolverResponse implements SolverResponse {

  private final Puzzle puzzle;
  private final SolutionStatus status;
  private final Exception error;

  public RemoteSolverResponse(SolutionStatus s, Puzzle p, Exception e) {
    status = s;
    puzzle = p;
    error = e;
  }

  @Override
  public SolutionStatus getStatus() {
    return status;
  }

  @Override
  public Puzzle getSolution() {
    return puzzle;
  }

  @Override
  public Exception getError() {
    return error;
  }
}

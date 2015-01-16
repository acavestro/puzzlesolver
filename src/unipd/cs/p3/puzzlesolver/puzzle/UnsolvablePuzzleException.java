package unipd.cs.p3.puzzlesolver.puzzle;

import java.lang.Exception;

public class UnsolvablePuzzleException extends Exception {

  public UnsolvablePuzzleException() {
    super();
  }

  public UnsolvablePuzzleException(String message) {
    super(message);
  }
}
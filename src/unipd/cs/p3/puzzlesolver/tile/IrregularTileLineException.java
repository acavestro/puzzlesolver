package unipd.cs.p3.puzzlesolver.tile;

import java.lang.Exception;

public class IrregularTileLineException extends Exception {
  public IrregularTileLineException() {
    super();
  }

  public IrregularTileLineException(String message) {
    super(message);
  }
}
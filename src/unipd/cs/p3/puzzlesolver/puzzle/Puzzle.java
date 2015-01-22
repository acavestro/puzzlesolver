package unipd.cs.p3.puzzlesolver.puzzle;

import java.io.Serializable;

public interface Puzzle extends Serializable {

  public int getRows();

  public int getCols();

  public Object toLine();

  public String toMatrix();

}
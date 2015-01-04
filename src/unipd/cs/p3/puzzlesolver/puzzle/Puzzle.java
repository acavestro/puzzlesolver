package unipd.cs.p3.puzzlesolver.puzzle;

public interface Puzzle {

  public int getRows();

  public int getCols();

  public Object toLine();

  public String toMatrix();

}
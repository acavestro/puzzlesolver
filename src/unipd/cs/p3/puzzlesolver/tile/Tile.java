package unipd.cs.p3.puzzlesolver.tile;

public interface Tile {

  public String getUp();
  public String getDown();
  public String getLeft();
  public String getRight();
  public String getID();
  public Object toSymbol();

}
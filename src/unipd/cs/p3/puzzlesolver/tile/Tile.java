package unipd.cs.p3.puzzlesolver.tile;

import java.io.Serializable;

public interface Tile extends Serializable {

  public String getUp();
  public String getDown();
  public String getLeft();
  public String getRight();
  public String getID();
  public Object toSymbol();

}
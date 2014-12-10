package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleBuilder {

  private HashMap<String, Tile> unsolvedTiles;

  public PuzzleBuilder(HashMap<String, Tile> tiles) {
    this.unsolvedTiles = tiles;
  }

  private Tile[] solveFirstColumn(){

    ArrayList<Tile> firstColumn = new ArrayList<Tile>();
    // Search for the first item in top left corner
    Iterator i = unsolvedTiles.keySet().iterator();
    boolean found = false;
    String currentKey = null;
    Tile currentTile = null;
    while(i.hasNext() && !found) {

      currentKey = i.next().toString();
      currentTile = unsolvedTiles.get(currentKey);

      if (currentTile.getLeft().equals("VUOTO") && 
        currentTile.getUp().equals("VUOTO")) {

        firstColumn.add(currentTile);
        unsolvedTiles.remove(currentKey);
        found = true;

      }

    }

    // Search the others until the end of line 
    Tile nextTile = null;
    currentKey = firstColumn.get(0).getDown();

    while (!currentKey.equals("VUOTO")) {
    
      nextTile = unsolvedTiles.get(currentKey);
      firstColumn.add(nextTile);
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getDown();

    } 

    return firstColumn.toArray(new Tile[firstColumn.size()]);

  }

  }
  }

}
package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

public class PuzzleBuilder {

  private HashMap<String, Tile> tiles;

  public PuzzleBuilder(HashMap<String, Tile> tiles) {
    this.unsolvedTiles = tiles;
  }

  private void solveFirstRow(){

    ArrayList<Tile> firstRow = new ArrayList<Tile>();
    // Search for the first item in top left corner
    Iterator i = unsolvedTiles.keySet().iterator();
    boolean found = false;
    String currentKey = null;
    Tile currentTile = null;
    while(i.hasNext() && !found) {

      currentKey = i.next().toString();
      currentTile = unsolvedTiles.get(currentKey);

      if (currentTile.getLeft().equals("VUOTO") && 
        currentTile.getTop().equals("VUOTO")) {

        firstRow.add(currentTile);
        unsolvedTiles.remove(currentKey);
        found = true;

      }

    }

    // Search the others until the end of line 
    Tile nextTile = null;
    currentKey = firstRow.get(0).getRight();

    while (!currentKey.equals("VUOTO")) {
    
      nextTile = unsolvedTiles.get(currentKey);
      firstRow.add(nextTile);
      currentKey = nextTile.getRight();
      unsolvedTiles.remove(currentKey);

    } 

  }

}
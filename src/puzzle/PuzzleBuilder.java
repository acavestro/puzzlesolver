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
    // TODO: controllare se firstColumn contiene un elemento
    currentKey = firstColumn.get(0).getDown();

    while (!currentKey.equals("VUOTO")) {
    
      nextTile = unsolvedTiles.get(currentKey);
      firstColumn.add(nextTile);
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getDown();

    } 

    return firstColumn.toArray(new Tile[firstColumn.size()]);

  }

  private Tile[] solveRow(Tile start) {

    ArrayList<Tile> row = new ArrayList<Tile>();
    row.add(start);

    Tile nextTile = null;
    String currentKey = start.getRight();

    while (!currentKey.equals("VUOTO")) {

      nextTile = unsolvedTiles.get(currentKey);
      row.add(nextTile);
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getRight();

    }

    return row.toArray(new Tile[row.size()]);

  }

  public Puzzle solvePuzzle() {
    Tile[] firstColumn = solveFirstColumn();
    //TODO: Check che ci sia un elemento qui
    Tile[] firstRow = solveRow(firstColumn[0]);

    Tile[][] solution = new Tile[firstColumn.length][firstRow.length];
    solution[0] = firstRow;

    for (int i = 1; i < firstColumn.length; i++){

      solution[i] = solveRow(firstColumn[i]);

    }
    return new PSPuzzle(solution);
  }

}
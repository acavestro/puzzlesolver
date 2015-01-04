package unipd.cs.p3.puzzlesolver.puzzle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import unipd.cs.p3.puzzlesolver.tile.Tile;

public class PuzzleBuilder {

  private final HashMap<String, Tile> unsolvedTiles;

  public PuzzleBuilder(HashMap<String, Tile> tiles) {
    unsolvedTiles = tiles;
  }

  private Tile[] solveFirstColumn() throws UnsolvablePuzzleException {

    final ArrayList<Tile> firstColumn = new ArrayList<Tile>();
    // Search for the first item in top left corner
    final Iterator<String> i = unsolvedTiles.keySet().iterator();
    boolean found = false;
    String currentKey = null;
    Tile currentTile = null;
    while (i.hasNext() && !found) {

      currentKey = i.next().toString();
      currentTile = unsolvedTiles.get(currentKey);

      if (currentTile.getLeft().equals("VUOTO")
          && currentTile.getUp().equals("VUOTO")) {

        firstColumn.add(currentTile);
        unsolvedTiles.remove(currentKey);
        found = true;

      }

    }

    // If firstColumn is empty -> there isn't a top left tile -> puzzle broken
    if (firstColumn.size() == 0) {

      throw new UnsolvablePuzzleException();

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

  private Tile[] solveRow(Tile start) throws UnsolvablePuzzleException {

    final ArrayList<Tile> row = new ArrayList<Tile>();
    row.add(start);

    Tile nextTile = null;
    String currentKey = start.getRight();

    while (!currentKey.equals("VUOTO")) {

      nextTile = unsolvedTiles.get(currentKey);

      // if nexTile is null -> there isn't a right piece -> puzzle broken
      if (nextTile == null) {

        throw new UnsolvablePuzzleException();

      }
      row.add(nextTile);
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getRight();

    }

    return row.toArray(new Tile[row.size()]);

  }

  public Puzzle solvePuzzle() throws UnsolvablePuzzleException {
    final Tile[] firstColumn = solveFirstColumn();
    // firstColumn has at least one element, for sure. Otherwise,
    // UnsolvablePuzzleException would be thrown by solveFirstColumn().
    final Tile[] firstRow = solveRow(firstColumn[0]);

    final Tile[][] solution = new Tile[firstColumn.length][firstRow.length];
    solution[0] = firstRow;

    for (int i = 1; i < firstColumn.length; i++) {

      solution[i] = solveRow(firstColumn[i]);

    }
    return new PSPuzzle(solution);
  }

}
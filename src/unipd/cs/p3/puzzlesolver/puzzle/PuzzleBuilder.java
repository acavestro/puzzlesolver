package unipd.cs.p3.puzzlesolver.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.tile.Tile;

public class PuzzleBuilder {

  private final ConcurrentHashMap<String, Tile> unsolvedTiles;

  public PuzzleBuilder(ConcurrentHashMap<String, Tile> tiles) {
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

  private class RowSolverThread extends Thread {
    private Tile start;
    private Tile[] result;
    private boolean error;

    public RowSolverThread(Tile start) {
      this.start = start;
      result = null;
      error = false;
    }

    @Override
    public void run() {
      try {
        result = solveRow(start);
      } catch (UnsolvablePuzzleException e) {
        error = true;
      }
    }

    public Tile[] getRow() {
      return result;
    }

    public boolean hasFailed() {
      return error;
    }
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
      // ConcurrentHashMap is thread-safe
      // If the puzzle is well-formed, a deletion of a Tile cannot interfere
      // with other thread. Otherwise, a thread wouldn't manage to retrieve a
      // Tile and it would throw a UnsolvablePuzzleException.
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getRight();

    }

    return row.toArray(new Tile[row.size()]);

  }

  public Puzzle solvePuzzle() throws UnsolvablePuzzleException {
    final Tile[] firstColumn = solveFirstColumn();

    RowSolverThread[] rowWorkers = new RowSolverThread[firstColumn.length];

    for (int i = 0; i < rowWorkers.length; i++) {
      rowWorkers[i] = new RowSolverThread(firstColumn[i]);
      rowWorkers[i].start();
      try {
        rowWorkers[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    final Tile[][] solution = new Tile[firstColumn.length][rowWorkers[0]
        .getRow().length];

    for (int i = 0; i < rowWorkers.length; i++) {
      if (rowWorkers[i].hasFailed()) {
        throw new UnsolvablePuzzleException();
      }
      solution[i] = rowWorkers[i].getRow();
    }

    return new PSPuzzle(solution);
  }

}
package unipd.cs.p3.puzzlesolver.puzzle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import unipd.cs.p3.puzzlesolver.tile.Tile;

public class PuzzleBuilder {

  private final ConcurrentHashMap<String, Tile> unsolvedTiles;

  public PuzzleBuilder(ConcurrentHashMap<String, Tile> tiles) {
    unsolvedTiles = tiles;
  }

  private Tile findTopLeftTile() throws UnsolvablePuzzleException {

    final Iterator<String> i = unsolvedTiles.keySet().iterator();
    boolean found = false;
    String currentKey = null;
    Tile currentTile = null;

    while (i.hasNext() && !found) {
      currentKey = i.next().toString();
      currentTile = unsolvedTiles.get(currentKey);

      if (currentTile.getLeft().equals("VUOTO")
          && currentTile.getUp().equals("VUOTO")) {

        unsolvedTiles.remove(currentKey);
        found = true;
      }
    }

    if (found) {
      return currentTile;
    } else {
      throw new UnsolvablePuzzleException();
    }
  }

  private Tile[] solveColumn(Tile start)
      throws UnsolvablePuzzleException {

    final ArrayList<Tile> column = new ArrayList<Tile>();
    column.add(start);
    String currentKey = null;
    Tile nextTile = null;
    currentKey = start.getDown();

    while (!currentKey.equals("VUOTO")) {

      nextTile = unsolvedTiles.get(currentKey);
      column.add(nextTile);
      unsolvedTiles.remove(currentKey);
      currentKey = nextTile.getDown();

    }

    return column.toArray(new Tile[column.size()]);

  }

  private class RowSolverTask implements Callable<Tile[]> {
    private Tile start;

    public RowSolverTask(Tile start) {
      this.start = start;
    }

    @Override
    public Tile[] call() throws UnsolvablePuzzleException {
      Tile[] result = null;

      result = solveRow(start);
      return result;
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
    final Tile[] firstColumn = solveColumn(findTopLeftTile());

    // Best practices evidence that an optimum number of threads is NCPU + 1
    ExecutorService rowWorkers = Executors.newFixedThreadPool(Runtime
        .getRuntime().availableProcessors() + 1);
    List<Callable<Tile[]>> rowsToSolve = new ArrayList<Callable<Tile[]>>();

    for (Tile element : firstColumn) {
      rowsToSolve.add(new RowSolverTask(element));
    }

    List<Future<Tile[]>> results;
    List<Tile[]> solution = new ArrayList<Tile[]>();

    try {
      results = rowWorkers.invokeAll(rowsToSolve);

      for (Future<Tile[]> res : results) {
        solution.add(res.get());
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      if (e.getCause() instanceof UnsolvablePuzzleException) {
        throw new UnsolvablePuzzleException();
      }
    }

    return new PSPuzzle(
        solution.toArray(new Tile[firstColumn.length][solution.get(0).length]));
  }
}
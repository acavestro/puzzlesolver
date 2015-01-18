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
      currentKey = i.next();
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
      throw new UnsolvablePuzzleException("Top left element not found");
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
      if (nextTile == null) {
        throw new UnsolvablePuzzleException(
            "No next tile found in column solving");
      }
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

  private class ColumnSolverTask implements Callable<Tile[]> {
    private Tile start;

    public ColumnSolverTask(Tile start) {
      this.start = start;
    }

    @Override
    public Tile[] call() throws UnsolvablePuzzleException {
      Tile[] result = null;

      result = solveColumn(start);
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

        throw new UnsolvablePuzzleException(
            "No next tile found in rows solving");

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
    final Tile[] firstRow = solveRow(firstColumn[0]);

    // Best practices evidence that an optimum number of threads is NCPU + 1
    ExecutorService solveWorkers = Executors.newFixedThreadPool(Runtime
        .getRuntime().availableProcessors() + 1);
    List<Callable<Tile[]>> linesToSolve = new ArrayList<Callable<Tile[]>>();
    Tile[][] solution = new Tile[firstColumn.length][firstRow.length];

    if (firstColumn.length >= firstRow.length) {
      // It's more efficient to solve rows
      for (int i = 1; i < firstColumn.length; i++) {
        linesToSolve.add(new RowSolverTask(firstColumn[i]));
      }
    } else {
      // solution.add(firstColumn);
      for (int i = 1; i < firstRow.length; i++) {
        linesToSolve.add(new ColumnSolverTask(firstRow[i]));
      }
    }

    List<Future<Tile[]>> results;

    try {
      results = solveWorkers.invokeAll(linesToSolve);
      solveWorkers.shutdown();

      if (firstColumn.length >= firstRow.length) {
        solution[0] = firstRow;
        for (int i = 1; i < firstColumn.length; i++) {
          solution[i] = results.get(i - 1).get();
        }
      } else {
        for (int i = 0; i < firstColumn.length; i++) {
          solution[i][0] = firstColumn[i];
        }
        Tile[] t;
        for (int i = 1; i < firstRow.length; i++) {
          t = results.get(i - 1).get();
          for (int j = 0; j < firstColumn.length; j++) {
            solution[j][i] = t[j];
          }
        }
      }

    } catch (InterruptedException e) {
      System.out
      .println("Interrupted exception happened in the thread pool");
      e.printStackTrace();
      System.exit(1);
    } catch (ExecutionException e) {
      if (e.getCause() instanceof UnsolvablePuzzleException) {
        throw new UnsolvablePuzzleException(e.getMessage());
      } else {
        System.out.println("Unrecognized exception happened!");
        System.out.println(e.getCause().getMessage());
        e.getCause().printStackTrace();
        System.exit(1);
      }
    }

    solveWorkers.shutdownNow();
    return new PSPuzzle(solution);

  }
}
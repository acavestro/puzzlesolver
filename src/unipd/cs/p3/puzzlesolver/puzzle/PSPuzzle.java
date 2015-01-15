package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile;

public class PSPuzzle implements Puzzle {

  private Tile[][] grid;

  PSPuzzle(Tile[][] tiles) {

    grid = tiles;

  }

  @Override
  public int getRows() {

    return grid.length;

  }

  @Override
  public int getCols() {

    if (grid.length > 0) {

      return grid[0].length;

    } else {

      return 0;

    }

  }

  @Override
  public String toMatrix() {

    StringBuffer b = new StringBuffer();

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        b.append(grid[i][j].toSymbol());

      }

      b.append("\n");

    }

    return b.toString();

  }

  @Override
  public String toLine() {

    StringBuffer b = new StringBuffer();

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        b.append(grid[i][j].toSymbol());

      }

    }

    return b.toString();

  }

  @Override
  public String toString() {

    return toLine();

  }

}
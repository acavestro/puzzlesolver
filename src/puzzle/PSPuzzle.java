package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 

public class PSPuzzle implements Puzzle {

  private Tile[][] grid;

  PSPuzzle (Tile[][] tiles) {

    grid = tiles;

  }

  public int getRows() {

    return grid.length;

  }

  public int getCols() {

    if (grid.length > 0) {

      return grid[0].length;

    } else {

      return 0;

    }

  }

  public String toMatrix() {

    String result = "";

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        result = result + grid[i][j].toSymbol();

      }

      result = result + "\n";

    }

    return result;

  }

  public String toLine() {

    String result = "";

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        result = result + grid[i][j].toSymbol();

      }

    }

    return result;

  }

  public String toString() {

    return toLine();

  }

}
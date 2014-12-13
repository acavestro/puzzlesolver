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

    // TODO: occhio che esista un elemento in grid
    return grid[0].length;

  }

  public String toMatrix() {

    String result = "";

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        result = result + grid[i][j];

      }

      result = result + "\n";

    }

    return result;

  }

  public String toLineString() {

    String result = "";

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        result = result + grid[i][j];

      }

    }

    return result;

  }

  public String toString() {

    return toLineString();

  }

}
package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 

public class PSPuzzle implements Puzzle {

  private Tile[][] grid;

  public Puzzle (Tile[][] tiles) {

    grid = tiles;

  }

  public int getRows() {

    return grid.length;

  }

  public int getCols() {

    // TODO: occhio che esista un elemento in grid
    return grid[0].length;

  }

  public Tile[][] toMatrix() {

    return grid;

  }

  public String toString() {

    String result = null;

    for (int i = 0; i < getRows(); i++) {

      for (int j = 0; j < getCols(); j++) {

        result = result + grid[i][j];

      }

    }

    return result;

  }

}
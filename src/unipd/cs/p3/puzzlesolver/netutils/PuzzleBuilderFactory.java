package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.Remote;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.tile.Tile;

public enum PuzzleBuilderFactory implements Remote {
  INSTANCE;

  public PuzzleBuilder getBuilder(ConcurrentHashMap<String, Tile> tiles) {
    return new PuzzleBuilder(tiles);
  }

}

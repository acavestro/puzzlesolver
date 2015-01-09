import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.puzzle.UnsolvablePuzzleException;
import unipd.cs.p3.puzzlesolver.tile.IrregularTileLineException;
import unipd.cs.p3.puzzlesolver.tile.Tile;
import unipd.cs.p3.puzzlesolver.tile.TileParser;

public class PuzzleBuilderTest {

  @Test
  public void solvePuzzleThatShouldWorks() {

    ConcurrentHashMap<String, Tile> tiles = new ConcurrentHashMap<String, Tile>();

    try {
      tiles = new TileParser("./samples/input_100x100.txt").getTiles();
    } catch (final IrregularTileLineException e) {
      fail(e.getStackTrace().toString());
    }

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    Puzzle p = null;

    try {
      p = pb.solvePuzzle();
    } catch (final UnsolvablePuzzleException e) {
      fail(e.getStackTrace().toString());
    }

    if (p != null) {
      assertTrue(p.getCols() == 100);
      assertTrue(p.getRows() == 100);
    } else {
      fail("Puzzle is null");
    }

  }

  @Test
  public void solvePuzzleShouldRefuseWrongCoordinatesInput() {

    ConcurrentHashMap<String, Tile> tiles = new ConcurrentHashMap<String, Tile>();

    try {
      tiles = new TileParser("./samples/input_wrong_coordinates.txt")
          .getTiles();
    } catch (final IrregularTileLineException e) {
      fail(e.getStackTrace().toString());
    }

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    boolean passed = false;

    try {
      pb.solvePuzzle();
    } catch (final UnsolvablePuzzleException e) {
      passed = true;
    }

    assertTrue(passed);

  }

  @Test
  public void solvePuzzleShouldRefuseInputWithoutFirstTile() {

    ConcurrentHashMap<String, Tile> tiles = new ConcurrentHashMap<String, Tile>();

    try {
      tiles = new TileParser("./samples/input_no_first_tile.txt").getTiles();
    } catch (final IrregularTileLineException e) {
      fail(e.getStackTrace().toString());
    }

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    boolean passed = false;

    try {
      pb.solvePuzzle();
    } catch (final UnsolvablePuzzleException e) {
      passed = true;
    }

    assertTrue(passed);

  }

}

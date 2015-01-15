import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.puzzle.UnsolvablePuzzleException;
import unipd.cs.p3.puzzlesolver.tile.IrregularTileLineException;
import unipd.cs.p3.puzzlesolver.tile.Tile;
import unipd.cs.p3.puzzlesolver.tile.TileParser;

public class PuzzleBuilderTest {

  @Test
  public void solvePuzzleThatShouldWorks() {

    final String outName = "./puzzlesolvershouldwork.txt";
    final String inputName = "./samples/input_100x100.txt";
    final String solutionName = "./samples/solution_100x100.txt";

    // Invoco main

    PuzzleSolver.main(new String[] { inputName, outName });

    // Controllo il risultato.

    final Path outPath = Paths.get(outName);
    final Path solutionPath = Paths.get(solutionName);

    try {
      assertEquals(Files.readAllLines(outPath),
          Files.readAllLines(solutionPath));
    } catch (final IOException e) {
      fail(Arrays.toString(e.getStackTrace()));
    }

  }

  @Test
  public void solvePuzzleShouldRefuseWrongCoordinatesInput() {

    ConcurrentHashMap<String, Tile> tiles = null;

    try {
      tiles = new TileParser("./samples/input_wrong_coordinates.txt")
      .getTiles();
    } catch (final IrregularTileLineException e) {
      fail(Arrays.toString(e.getStackTrace()));
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

    ConcurrentHashMap<String, Tile> tiles = null;

    try {
      tiles = new TileParser("./samples/input_no_first_tile.txt")
          .getTiles();
    } catch (final IrregularTileLineException e) {
      fail(Arrays.toString(e.getStackTrace()));
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

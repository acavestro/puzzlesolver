import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import unipd.cs.p3.puzzlesolver.netutils.PuzzleBuilderFactory;
import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.puzzle.UnsolvablePuzzleException;
import unipd.cs.p3.puzzlesolver.tile.IrregularTileLineException;
import unipd.cs.p3.puzzlesolver.tile.Tile;
import unipd.cs.p3.puzzlesolver.tile.TileParser;

public class PuzzleSolverClient {
  public static void main(String args[]) throws NamingException {
    if (args.length != 2) {
      System.out.println("Usage: PuzzleSolver INPUT_FILE OUTPUT_FILE");
      return;
    }

    final TileParser tp = new TileParser(args[0]);
    ConcurrentHashMap<String, Tile> m;

    try {

      m = tp.getTiles();

    } catch (final IrregularTileLineException itle) {

      System.out
      .println("ERROR: Some tiles are in a irregular format. "
          + "Check input file.\nAborting..");
      System.out.println(itle.getMessage());
      return;

    }

    final Context namingContext = new InitialContext();
    // TODO l'IP va passato come parametro
    final String url = "rmi://localhost/puzzlebuilderfactory";
    final PuzzleBuilderFactory psFactory = (PuzzleBuilderFactory) namingContext
        .lookup(url);

    final PuzzleBuilder pb = psFactory.getBuilder(m);

    Puzzle out;

    try {

      out = pb.solvePuzzle();

    } catch (final UnsolvablePuzzleException upe) {

      System.out.println("ERROR: Missing tiles or wrong coordinates. "
          + "Unsolvable puzzle. Check input file.\nAborting... ");
      System.out.println(upe.getMessage());
      return;

    }

    final String solution = out.toLine() + "\n" + "\n" + out.toMatrix()
        + "\n" + out.getRows() + " " + out.getCols();

    final Path outputFile = Paths.get(args[1]);
    final Charset charset = StandardCharsets.UTF_8;

    try (BufferedWriter writer = Files.newBufferedWriter(outputFile,
        charset)) {

      writer.write(solution);

    } catch (final IOException e) {

      System.err.println(e);

    }
  }
}

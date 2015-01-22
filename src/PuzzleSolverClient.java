import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.NamingException;

import unipd.cs.p3.puzzlesolver.netutils.Solver;
import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.tile.IrregularTileLineException;
import unipd.cs.p3.puzzlesolver.tile.Tile;
import unipd.cs.p3.puzzlesolver.tile.TileParser;

public class PuzzleSolverClient {
  public static void main(String args[]) throws NamingException,
      RemoteException, MalformedURLException, NotBoundException {
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

    // TODO l'IP va passato come parametro
    final String url = "rmi://localhost/remotesolver";
    final Solver remoteSolver = (Solver) Naming.lookup(url);

    // TODO questa istruzione c'ha un remote exception da gestire
    final Puzzle out = remoteSolver.solvePuzzle(m);

    /*
     * TODO Questa eccezione è da gestire try {
     *
     * out = pb.solvePuzzle();
     *
     * } catch (final UnsolvablePuzzleException upe) {
     *
     * System.out.println("ERROR: Missing tiles or wrong coordinates. " +
     * "Unsolvable puzzle. Check input file.\nAborting... ");
     * System.out.println(upe.getMessage()); return;
     *
     * }
     */

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

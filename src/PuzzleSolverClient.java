import java.io.BufferedWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.netutils.PuzzleExceptionBuffer;
import unipd.cs.p3.puzzlesolver.netutils.RemoteExceptionBuffer;
import unipd.cs.p3.puzzlesolver.netutils.Solver;
import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.tile.IrregularTileLineException;
import unipd.cs.p3.puzzlesolver.tile.Tile;
import unipd.cs.p3.puzzlesolver.tile.TileParser;

public class PuzzleSolverClient {
  public static void main(String args[]) {
    if (args.length != 3) {
      System.out.println("Usage: ./puzzlesolverclient.sh "
          + "INPUT_FILE OUTPUT_FILE SERVER_NAME");
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

    final String serverName = args[2] + ":1099";
    final String url = "rmi://" + serverName + "/remotesolver";
    Solver remoteSolver = null;
    try {
      remoteSolver = (Solver) Naming.lookup(url);
    } catch (final NotBoundException nbe) {
      System.out
      .println("Critical error: remotesolver object is not bound on the"
          + " rmi server. \n Exiting..");
      System.exit(-1);
    } catch (final AccessException ae) {
      System.out
      .println("Critical error: client can't contact rmi server due"
          + " to a permission error. \n Exiting..");
      System.exit(-1);
    } catch (final RemoteException re) {
      System.out
      .println("Error: rmi registry could not be contacted..");
      // TODO implementare meccanismo di retry.
    } catch (final MalformedURLException mue) {
      System.out
      .println("Critical error: client is using a malformed url to"
          + " contact rmi registry. \n Exiting..");
      System.exit(-1);
    }

    PuzzleExceptionBuffer exceptionBuffer = null;
    int clientId = -1;
    try {
      exceptionBuffer = new RemoteExceptionBuffer();
      clientId = remoteSolver.attachExceptionBuffer(exceptionBuffer);
    } catch (final RemoteException re) {
      // TODO gestire questa eccezione
      re.printStackTrace();
    }

    Puzzle out = null;
    try {
      out = remoteSolver.solvePuzzle(m, clientId);
    } catch (final RemoteException re) {
      System.out
      .println("Error: connection to the server has ben lost while"
          + " it was solving the puzzle..");
      // TODO implementare meccanismo di retry
    }

    if (out == null) {
      try {
        System.out.println("Critical error: "
            + exceptionBuffer.getMessage());
      } catch (final RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("Exiting..");
      System.exit(-1);
    }

    try {
      UnicastRemoteObject.unexportObject(exceptionBuffer, true);
    } catch (final NoSuchObjectException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
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

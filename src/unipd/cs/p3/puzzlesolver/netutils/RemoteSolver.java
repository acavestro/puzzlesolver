package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.puzzle.UnsolvablePuzzleException;
import unipd.cs.p3.puzzlesolver.tile.Tile;

public class RemoteSolver extends UnicastRemoteObject implements Solver {
  // TODO Questa classe andrebbe singleton, ma non trovo modi carini per farlo
  public RemoteSolver() throws RemoteException {
  }

  @Override
  public Puzzle solvePuzzle(ConcurrentHashMap<String, Tile> tiles)
      throws RemoteException {
    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    // TODO capire che fare con questo try-catch
    try {
      return pb.solvePuzzle();
    } catch (final UnsolvablePuzzleException e) {
      e.printStackTrace();
      return null;
    }

  }

}

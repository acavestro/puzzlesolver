package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.puzzle.UnsolvablePuzzleException;
import unipd.cs.p3.puzzlesolver.tile.Tile;

// TODO Questa classe andrebbe singleton, ma non trovo modi carini per farlo
public class RemoteSolver extends UnicastRemoteObject implements Solver {

  private final ArrayList<PuzzleErrorMessageBuffer> errorMessageBuffers;

  public RemoteSolver() throws RemoteException {
    errorMessageBuffers = new ArrayList<PuzzleErrorMessageBuffer>();
  }

  @Override
  public Puzzle solvePuzzle(ConcurrentHashMap<String, Tile> tiles,
      int clientId) throws RemoteException {

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    try {
      final Puzzle p = pb.solvePuzzle();

      synchronized (errorMessageBuffers) {
        errorMessageBuffers.remove(clientId);
      }
      return p;
    } catch (final UnsolvablePuzzleException e) {
      synchronized (errorMessageBuffers) {
        errorMessageBuffers.get(clientId).setMessage(e.getMessage());
      }
      return null;
    }

  }

  @Override
  public int attachErrorMessageBuffer(PuzzleErrorMessageBuffer pexb)
      throws RemoteException {
    int clientId = -1;
    synchronized (errorMessageBuffers) {
      errorMessageBuffers.add(pexb);
      clientId = errorMessageBuffers.indexOf(pexb);
    }
    return clientId;
  }

}

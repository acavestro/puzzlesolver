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

  private final ArrayList<PuzzleExceptionBuffer> exceptionBuffers;

  public RemoteSolver() throws RemoteException {
    exceptionBuffers = new ArrayList<PuzzleExceptionBuffer>();
  }

  @Override
  public Puzzle solvePuzzle(ConcurrentHashMap<String, Tile> tiles,
      int clientId) throws RemoteException {

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    try {
      final Puzzle p = pb.solvePuzzle();

      synchronized (exceptionBuffers) {
        exceptionBuffers.remove(clientId);
      }
      return p;
    } catch (final UnsolvablePuzzleException e) {
      synchronized (exceptionBuffers) {
        exceptionBuffers.get(clientId).pushException(e);
      }
      return null;
    }

  }

  @Override
  public int attachExceptionBuffer(PuzzleExceptionBuffer pexb)
      throws RemoteException {
    int clientId = -1;
    synchronized (exceptionBuffers) {
      exceptionBuffers.add(pexb);
      clientId = exceptionBuffers.indexOf(pexb);
    }
    return clientId;
  }

}

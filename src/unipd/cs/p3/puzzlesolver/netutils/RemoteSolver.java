package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.puzzle.PuzzleBuilder;
import unipd.cs.p3.puzzlesolver.tile.Tile;

// TODO Questa classe andrebbe singleton, ma non trovo modi carini per farlo
public class RemoteSolver extends UnicastRemoteObject implements Solver {

  public RemoteSolver() throws RemoteException {
  }

  @Override
  public SolverResponse solvePuzzle(
      ConcurrentHashMap<String, Tile> tiles)
          throws RemoteException {

    final PuzzleBuilder pb = new PuzzleBuilder(tiles);
    try {
      final Puzzle p = pb.solvePuzzle();
      return new RemoteSolverResponse(SolutionStatus.SOLVED, p, null);
    } catch (final Exception e) {
      return new RemoteSolverResponse(SolutionStatus.ERROR, null, e);
    }

  }

}

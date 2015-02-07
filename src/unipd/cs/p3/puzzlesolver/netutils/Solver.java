package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.puzzle.Puzzle;
import unipd.cs.p3.puzzlesolver.tile.Tile;

public interface Solver extends Remote {

  public Puzzle solvePuzzle(ConcurrentHashMap<String, Tile> tiles,
      int clientId) throws RemoteException;

  public int attachErrorMessageBuffer(PuzzleErrorMessageBuffer pexb)
      throws RemoteException;
}

package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;

import unipd.cs.p3.puzzlesolver.tile.Tile;

public interface Solver extends Remote {

  public SolverResponse solvePuzzle(
      ConcurrentHashMap<String, Tile> tiles)
      throws RemoteException;

}

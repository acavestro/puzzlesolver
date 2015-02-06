package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PuzzleExceptionBuffer extends Remote {
  public void pushException(Exception e) throws RemoteException;

  public String getMessage() throws RemoteException;
}

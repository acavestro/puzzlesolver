package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PuzzleErrorMessageBuffer extends Remote {
  public void setMessage(String e) throws RemoteException;

  public String getMessage() throws RemoteException;
}

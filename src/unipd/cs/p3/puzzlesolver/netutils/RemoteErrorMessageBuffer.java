package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteErrorMessageBuffer extends UnicastRemoteObject
    implements PuzzleErrorMessageBuffer {

  private String buffer;

  public RemoteErrorMessageBuffer() throws RemoteException {
  }

  @Override
  public void setMessage(String e) throws RemoteException {
    buffer = e;
  }

  @Override
  public String getMessage() throws RemoteException {
    return buffer;
  }

}

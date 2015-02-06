package unipd.cs.p3.puzzlesolver.netutils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteExceptionBuffer extends UnicastRemoteObject implements PuzzleExceptionBuffer {

  private Exception buffer;

  public RemoteExceptionBuffer() throws RemoteException {
  }

  @Override
  public void pushException(Exception e) throws RemoteException {
    buffer = e;
  }

  @Override
  public String getMessage() throws RemoteException {
    return buffer.getMessage();
  }

}

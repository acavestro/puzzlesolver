import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import unipd.cs.p3.puzzlesolver.netutils.RemoteSolver;
import unipd.cs.p3.puzzlesolver.netutils.Solver;

public class PuzzleSolverServer {
  public static void main(String args[]) throws NamingException,
  RemoteException, MalformedURLException {

    if (args.length != 1) {
      System.out.println("Usage: ./puzzlesolverserver.sh SERVER_NAME");
      return;
    }

    final String serverName = args[0] + ":1099";

    // TODO questo coso deve gestire RemoteException e MalformedURL
    // Capire se posso lasciarla in throws o gestirla.
    final Solver ns = new RemoteSolver();
    Naming.rebind("rmi://" + serverName + "/remotesolver", ns);
  }
}

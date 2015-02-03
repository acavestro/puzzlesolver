import java.net.MalformedURLException;
import java.rmi.AccessException;
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
    try {
      Naming.rebind("rmi://" + serverName + "/remotesolver", ns);
    } catch (final AccessException ae) {
      System.out
      .println("Critical error: server can't contact rmi server due"
          + " to a permission error. \n Exiting..");
      System.exit(-1);
    } catch (final RemoteException re) {
      System.out
          .println("Error: rmi registry could not be contacted..");
      // TODO implementare meccanismo di retry.
    } catch (final MalformedURLException mue) {
      System.out
      .println("Critical error: server is using a malformed url to"
          + " contact rmi registry. \n Exiting..");
      System.exit(-1);
    }
  }
}

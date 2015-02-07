import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import unipd.cs.p3.puzzlesolver.netutils.RemoteSolver;
import unipd.cs.p3.puzzlesolver.netutils.Solver;

public class PuzzleSolverServer {
  public static void main(String args[]) {

    if (args.length != 1) {
      System.out.println("Usage: ./puzzlesolverserver.sh SERVER_NAME");
      return;
    }

    final String serverName = args[0];

    try {
      final Solver ns = new RemoteSolver();
      Naming.rebind("rmi://localhost/" + serverName, ns);
    } catch (final AccessException ae) {
      System.out
      .println("Critical error: server can't contact rmi server due"
              + " to a permission error.");
      System.out.println("Aborting..");
      System.exit(-1);
    } catch (final RemoteException re) {
      System.out
      .println("Error: rmi registry could not be contacted..");
      // TODO implementare meccanismo di retry.
    } catch (final MalformedURLException mue) {
      System.out
      .println("Critical error: server is using a malformed url to"
              + " contact rmi registry.");
      System.out.println("Aborting..");
      System.exit(-1);
    }
  }
}

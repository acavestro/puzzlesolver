import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.naming.NamingException;

import unipd.cs.p3.puzzlesolver.netutils.RemoteSolver;
import unipd.cs.p3.puzzlesolver.netutils.Solver;

public class PuzzleSolverServer {
  public static void main(String args[]) throws NamingException,
  RemoteException, MalformedURLException {

    // TODO questo coso deve gestire RemoteException e MalformedURL
    // Capire se posso lasciarla in throws o gestirla.
    final Solver ns = new RemoteSolver();
    // TODO sto coso dovrà accettare come parametro il suo fottuto host
    Naming.rebind("rmi://localhost/remotesolver", ns);
  }
}

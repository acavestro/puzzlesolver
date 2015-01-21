import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import unipd.cs.p3.puzzlesolver.netutils.PuzzleBuilderFactory;

public class PuzzleSolverServer {
  public static void main(String args[]) throws NamingException {
    final Context namingContext = new InitialContext();
    namingContext.bind("rmi:puzzlebuilderfactory",
        PuzzleBuilderFactory.INSTANCE);
  }
}

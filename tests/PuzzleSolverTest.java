import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class PuzzleSolverTest {

  @Test
  public void puzzleSolverShouldWork() {

    final String outName = "./puzzlesolvershouldwork.txt";
    final String inputName = "./samples/input_100x100.txt";
    final String solutionName = "./samples/solution_100x100.txt";

    // Invoco main

    PuzzleSolver.main(new String[] { inputName, outName });

    // Controllo il risultato.

    final Path outPath = Paths.get(outName);
    final Path solutionPath = Paths.get(solutionName);

    try {
      assertEquals(Files.readAllLines(outPath),
          Files.readAllLines(solutionPath));
    } catch (final IOException e) {
      fail(e.getStackTrace().toString());
    }

  }

}

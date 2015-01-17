import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

public class PuzzleBenchmark {
  public static void main(String args[]) {
    File benchmarkDir = new File("./benchmark");

    FilenameFilter textFilter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.toLowerCase().startsWith("input_")
            && name.toLowerCase().endsWith(".txt");
      }
    };

    String[] inputFiles = benchmarkDir.list(textFilter);
    System.out.println(Arrays.toString(inputFiles));

    long startTime;
    long endTime;
    for (int i = 0; i < inputFiles.length; i++) {
      System.out.println("Solving puzzle #" + (i + 1) + " ("
          + inputFiles[i] + ")");
      startTime = System.currentTimeMillis();
      PuzzleSolver.main(new String[] {
          benchmarkDir.toString() + "/" + inputFiles[i], "out.txt" });
      endTime = System.currentTimeMillis() - startTime;
      System.out.println("Time: " + (double) endTime / 1000 + "s");
    }

  }
}

import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PuzzleGenerator {

  public static void main(String[] args) {

    if (args.length != 4) {
      System.out
          .println("Usage: PuzzleGenerator ROWS COLS OUTPUT OUTPUT_SOL");
      System.exit(-1);
    }
    final String alphabet = "abcdefghilmnopqrstuvz1234567890_-";
    final int n = alphabet.length();
    Random r = new Random();

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    char[][] tiles = new char[rows][cols];
    int[][] ids = new int[rows][cols];

    StringBuffer matrixPuzzle = new StringBuffer();
    StringBuffer linePuzzle = new StringBuffer();
    int indexCounter = 0;
    int lineLength;

    for (int i = 0; i < rows; i++) {

      lineLength = 0;
      for (int j = 0; j < cols; j++) {

        tiles[i][j] = alphabet.charAt(r.nextInt(n));
        System.out.print(tiles[i][j]);
        linePuzzle.append(tiles[i][j]);

        ids[i][j] = indexCounter;
        indexCounter++;
        lineLength++;

      }

      matrixPuzzle.append(
          linePuzzle.substring(indexCounter - lineLength)).append("\n");
      System.out.println();

    }

    StringBuffer inputContent = new StringBuffer();
    StringBuffer currentLine = new StringBuffer();
    int cont = 1;
    String nord, est, sud, ovest;

    for (int i = 0; i < rows; i++) {

      for (int j = 0; j < cols; j++) {

        currentLine.setLength(0);

        nord = i == 0 ? "VUOTO" : String.valueOf(ids[i - 1][j]);
        est = j == cols - 1 ? "VUOTO" : String.valueOf(ids[i][j + 1]);
        sud = i == rows - 1 ? "VUOTO" : String.valueOf(ids[i + 1][j]);
        ovest = j == 0 ? "VUOTO" : String.valueOf(ids[i][j - 1]);

        currentLine.append(ids[i][j]).append("\t").append(tiles[i][j])
            .append("\t").append(nord).append("\t").append(est)
            .append("\t").append(sud).append("\t").append(ovest)
            .append("\n");

        System.out.print(cont + " " + currentLine);
        cont++;
        inputContent.append(currentLine);

      }

    }

    try {

      Files.write(Paths.get(args[2]), inputContent.toString()
          .getBytes(), StandardOpenOption.CREATE);

    } catch (IOException ioe) {

      System.out.println("Non sono riuscito a creare il file di input");
      System.out.println(ioe.getMessage());

    }

    StringBuffer solutionFileContent = new StringBuffer();
    solutionFileContent.append(linePuzzle).append("\n").append("\n")
        .append(matrixPuzzle).append("\n").append(rows).append(" ")
        .append(cols);

    try {

      Files.write(Paths.get(args[3]), solutionFileContent.toString()
          .getBytes(), StandardOpenOption.CREATE);

    } catch (IOException ioe) {

      System.out
          .println("Non sono riuscito a creare il file di soluzione");
      System.out.println(ioe.getMessage());

    }

  }

}
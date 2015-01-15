import java.util.ArrayList;
import java.util.List;
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
    final long startTime = System.currentTimeMillis();
    final String alphabet = "abcdefghilmnopqrstuvz1234567890_-";
    final int n = alphabet.length();
    Random r = new Random();

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    int total = rows * cols;
    List<RawTile> tiles = new ArrayList<RawTile>();

    StringBuffer matrixPuzzle = new StringBuffer();
    StringBuffer linePuzzle = new StringBuffer();
    int indexCounter = 0;
    int lineLength;

    System.out.print("Generating symbols..");
    int cont = 1;

    char tempSymbol;
    int tempID;

    for (int i = 0; i < rows; i++) {

      lineLength = 0;
      for (int j = 0; j < cols; j++) {

        tempSymbol = alphabet.charAt(r.nextInt(n));
        tempID = indexCounter;
        tiles.add(new RawTile(String.valueOf(tempID), tempSymbol));
        linePuzzle.append(tempSymbol);

        System.out.print("\rGenerating symbols.. (" + cont + "/"
            + total + ")");

        cont++;
        indexCounter++;
        lineLength++;

      }

      matrixPuzzle.append(
          linePuzzle.substring(indexCounter - lineLength)).append("\n");

    }

    System.out.println();
    System.out.print("Generating tiles..");
    RawTile[][] deckTile = new RawTile[rows][cols];
    String nord, est, sud, ovest;
    cont = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        deckTile[i][j] = tiles.get(cont);
        cont++;
      }
    }
    cont = 1;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        nord = i == 0 ? "VUOTO" : deckTile[i - 1][j].getID();
        est = j == cols - 1 ? "VUOTO" : deckTile[i][j + 1].getID();
        sud = i == rows - 1 ? "VUOTO" : deckTile[i + 1][j].getID();
        ovest = j == 0 ? "VUOTO" : deckTile[i][j - 1].getID();

        deckTile[i][j].setUp(nord);
        deckTile[i][j].setDown(sud);
        deckTile[i][j].setLeft(est);
        deckTile[i][j].setRight(ovest);

        System.out.print("\rGenerating tiles.. (" + cont + "/" + total
            + ")");
        cont++;
      }
    }

    System.out.println();
    System.out.print("Shuffling tiles..");
    RawTile[][] shuffledDeckTile = new RawTile[rows][cols];
    int randI = r.nextInt(rows);
    int randJ = r.nextInt(cols);
    cont = 1;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        while (deckTile[randI][randJ] == null) {
          randI = r.nextInt(rows);
          randJ = r.nextInt(cols);
        }
        shuffledDeckTile[i][j] = deckTile[randI][randJ];
        deckTile[randI][randJ] = null;
        System.out.print("\rShuffling tiles.. (" + cont + "/" + total
            + ")");
        cont++;
      }
    }

    System.out.println();
    System.out.print("Generating input file..");
    StringBuffer inputContent = new StringBuffer();
    StringBuffer currentLine = new StringBuffer();
    cont = 1;

    for (int i = 0; i < rows; i++) {

      for (int j = 0; j < cols; j++) {

        currentLine.setLength(0);

        currentLine.append(shuffledDeckTile[i][j].getID()).append("\t")
            .append(shuffledDeckTile[i][j].getSymbol()).append("\t")
            .append(shuffledDeckTile[i][j].getUp()).append("\t")
            .append(shuffledDeckTile[i][j].getLeft()).append("\t")
            .append(shuffledDeckTile[i][j].getDown()).append("\t")
            .append(shuffledDeckTile[i][j].getRight()).append("\n");

        System.out.print("\rGenerating input file.. (" + cont + "/"
            + total + ")");
        cont++;
        inputContent.append(currentLine);

      }

    }
    System.out.println();
    System.out.println("Writing input file...");

    try {

      Files.write(Paths.get(args[2]), inputContent.toString()
          .getBytes(), StandardOpenOption.CREATE);

    } catch (IOException ioe) {

      System.out
          .println("I got some problems while I was creating the input file :(");
      System.out.println(ioe.getMessage());

    }

    StringBuffer solutionFileContent = new StringBuffer();
    solutionFileContent.append(linePuzzle).append("\n").append("\n")
        .append(matrixPuzzle).append("\n").append(rows).append(" ")
        .append(cols);

    System.out.println("Writing solution file..");

    try {

      Files.write(Paths.get(args[3]), solutionFileContent.toString()
          .getBytes(), StandardOpenOption.CREATE);

    } catch (IOException ioe) {

      System.out
          .println("I got some problems while I was creating the solution file :(");
      System.out.println(ioe.getMessage());

    }

    final long endTime = System.currentTimeMillis() - startTime;
    System.out.println("Done (" + endTime / 1000 + "s)");

  }

}
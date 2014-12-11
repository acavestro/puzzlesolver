import java.util.Random;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class PuzzleGenerator {

  public static void main (String[] args) {

    final String alphabet = "abcdefghilmnopqrstuvz1234567890_-";
    final int n = alphabet.length(); 
    Random r = new Random();

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    char[][] tiles = new char[rows][cols];
    String[][] ids = new String[rows][cols];
    HashMap<String, Integer> idGen = new HashMap<String, Integer>();

    String currentElement;

    for (int i = 0; i < rows; i++) {

      for (int j = 0; j < cols; j++) {

        tiles[i][j] = alphabet.charAt(r.nextInt(n));
        currentElement = String.valueOf(tiles[i][j]);
        System.out.print(currentElement);

        Integer currentIndex = idGen.get(currentElement);

        if (currentIndex != null) {

          int intCurrentIndex = currentIndex.intValue();
          idGen.put(currentElement, new Integer(intCurrentIndex + 1));
          ids[i][j] = currentElement + Integer.toString(intCurrentIndex + 1);

        } else {

          idGen.put(currentElement, new Integer(1));
          ids[i][j] = currentElement + "1";

        }

        //System.out.print("(" + ids[i][j] + ") ");

      }

      System.out.println();

    }

    String toInput = "";

    for (int i = 0; i < rows; i++) {

      for (int j = 0; j < cols; j++) {

        String nord = (i == 0) ? "VUOTO" : ids[i-1][j];
        String est = (j == cols-1) ? "VUOTO" : ids[i][j+1];
        String sud = (i == rows-1) ? "VUOTO" : ids[i+1][j];
        String ovest = (j == 0) ? "VUOTO" : ids[i][j-1];

        toInput = toInput + 
                  ids[i][j] + "\t" +
                  tiles[i][j] + "\t" + 
                  nord + "\t" +
                  est + "\t" +
                  sud + "\t" +
                  ovest + "\n";



      }

    }

    try {

      Files.write(Paths.get(args[2]), toInput.getBytes(), 
      StandardOpenOption.CREATE);

    } catch (IOException ioe) {

      System.out.println("Non sono riuscito a creare il file");
      System.out.println(ioe.getMessage());

    }

  }

}
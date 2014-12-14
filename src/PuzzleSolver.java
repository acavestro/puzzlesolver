import unipd.cs.p3.puzzlesolver.tile.*;
import unipd.cs.p3.puzzlesolver.puzzle.*;
import java.util.HashMap;
import java.util.Iterator;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.IOException;

public class PuzzleSolver {

	public static void main(String[] args) {

    //TODO: scrivi nella relazione che controllo il numero di parametri
    if(args.length != 2) {
      System.out.println("Usage: PuzzleSolver INPUT_FILE OUTPUT_FILE");
      return;
    }

    TileParser tp = new TileParser(args[0]);
    HashMap<String, Tile> m;

    try {

        m = tp.getTiles();

    } catch(IrregularTileLineException itle) {

      System.out.println("ERROR: Some tiles are in a irregular format. " +
                          "Check input file.\nAborting..");
      return;

    }

    PuzzleBuilder pb = new PuzzleBuilder(m);
    Puzzle out;

    try {

      out = pb.solvePuzzle();

    } catch(UnsolvablePuzzleException upe) {

      System.out.println("ERROR: Missing tiles or wrong coordinates. " +
                        "Unsolvable puzzle. Check input file.\nAborting... ");
      return;

    }

    String solution = out.toLine() + "\n" +
                      "\n" +
                      out.toMatrix() +
                      "\n" +
                      out.getRows() + " " +
                      out.getCols();

    Path outputFile = Paths.get(args[1]);
    Charset charset = StandardCharsets.UTF_8;

    try (BufferedWriter writer = Files.newBufferedWriter(outputFile, charset)) {

      writer.write(solution);

    } catch (IOException e) {

      System.err.println(e);

    }


	}

}

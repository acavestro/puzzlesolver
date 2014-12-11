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

    // TODO: check sul primo argomento
    TileParser tp = new TileParser(args[0]);
    HashMap<String, Tile> m = tp.getTiles();

    PuzzleBuilder pb = new PuzzleBuilder(m);
    Puzzle out = pb.solvePuzzle();

    String solution = out + "\n" +
                      "\n" +
                      out.toMatrix() +
                      "\n" +
                      out.getRows() + " " +
                      out.getCols();

    // TODO: check sul secondo argomento
    Path outputFile = Paths.get(args[1]);
    Charset charset = StandardCharsets.UTF_8;

    try (BufferedWriter writer = Files.newBufferedWriter(outputFile, charset)) {

      writer.write(solution);

    } catch (IOException e) {

      System.err.println(e);

    }


	}

}

package unipd.cs.p3.puzzlesolver.tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;

public class TileParser {

  private final static Charset CHARSET = StandardCharsets.UTF_8;
  private static final String DELIM = "\t";
  private Path inputFile;
  private final ConcurrentHashMap<String, Tile> tiles;

  public TileParser(String inputFile) {
    this.inputFile = Paths.get(inputFile);
    /*
     * An initial capacity of 16 ensures a reasonably good number of elements
     * before resizing happens. Load factor of 0.9 ensures a dense packaging
     * inside ConcurrentHashMap which will optimize memory use. And
     * concurrencyLevel set to 1 will ensure that only one shard is created and
     * maintained.
     */
    tiles = new ConcurrentHashMap<String, Tile>(16, 0.9f, 1);
  }

  public ConcurrentHashMap<String, Tile> getTiles()
      throws IrregularTileLineException {
    Tile t;
    try (BufferedReader reader = Files.newBufferedReader(inputFile,
        CHARSET)) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        t = parseTile(line);
        tiles.put(t.getID(), t);
      }
    } catch (IOException e) {
      System.err.println(e);
    }
    return tiles;
  }

  private Tile parseTile(String line) throws IrregularTileLineException {
    String[] rawTile = line.split(DELIM);

    if (rawTile.length != 6) {
      throw new IrregularTileLineException(
          "Missing informations about a tile");
    }
    String id = rawTile[0];
    return new PSTile(id, rawTile[1], rawTile[2], rawTile[3],
        rawTile[4], rawTile[5]);

  }
}
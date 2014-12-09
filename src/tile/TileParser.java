package unipd.cs.p3.puzzlesolver.tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TileParser {

  private final static Charset CHARSET = StandardCharsets.UTF_8;
  private final String DELIM = "\t";
  private Path inputFile;

  public TileParser(String inputFile) {
    this.inputFile = Paths.get(inputFile);
  }

  public void getTiles() {
    StringBuilder content = new StringBuilder();
    try (BufferedReader reader = Files.newBufferedReader(inputFile, CHARSET)) {
          String line = null;
          while ((line = reader.readLine()) != null) {
            parseTile(line);
          }
    } catch (IOException e) {
      System.err.println(e);
    }
    System.out.println(content.toString());
  }

  private void parseTile(String line){
    String[] rawTile = line.split(DELIM);
    for (int i = 0; i < rawTile.length; i++) {
      System.out.println(rawTile[i]);
    }
    //return (Tile) new Object();
  }
}
import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;

import unipd.cs.p3.puzzlesolver.tile.*;

public class TileParserTest {

  @Test
  public void getTilesThatShouldPass() {
    
    String okFilePath = "./samples/input_100x100.txt";
    TileParser tpTest = new TileParser(okFilePath);
    boolean passed = false;
    
    try {
      ConcurrentHashMap<String, Tile> result = tpTest.getTiles();
      assertTrue(result.size() == 10000);
      passed = true;
    } catch (IrregularTileLineException e) {
      passed = false;
    }
    
    assertTrue(passed);
    
  }

  @Test
  public void getTilesThatShouldRefuseWrongFormatTiles() {

    boolean passed = false;
    TileParser tpTestFormat = new TileParser(
                                            "./samples/input_wrong_format.txt");

    try {
      tpTestFormat.getTiles();
      passed = false;
    } catch (IrregularTileLineException e) {
      passed = true;
    }

    assertTrue(passed);

  }

}

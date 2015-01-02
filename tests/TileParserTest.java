import static org.junit.Assert.*;

import java.lang.reflect.*;

import org.junit.Test;

import unipd.cs.p3.puzzlesolver.tile.*;

public class TileParserTest {

  // Test su metodo privato che avviene tramite Reflection
  
  @Test
  public void parseTileThatShouldPass() throws ClassNotFoundException,
                                               InstantiationException, 
                                               IllegalAccessException, 
                                               NoSuchMethodException, 
                                               SecurityException, 
                                               IllegalArgumentException, 
                                               InvocationTargetException {
    String correct = "h2\th\tb1\te3\ti8\t99";
    
    Class<?> parserClass = 
        Class.forName("unipd.cs.p3.puzzlesolver.tile.TileParser");
    Constructor<?> c = parserClass.getConstructor(String.class);
    Object parser = c.newInstance("fakefile.txt");
    Class<?>[] parseTileArguments = new Class[1];
    parseTileArguments[0] = String.class;
    Method parseTile = parser.getClass().getDeclaredMethod("parseTile", 
                                         parseTileArguments);
    parseTile.setAccessible(true);
    Tile result = (Tile) parseTile.invoke(parser, correct);
    
    assertEquals("h2", result.getID());
    assertEquals("h", result.toSymbol());
    assertEquals("b1", result.getUp());
    assertEquals("e3", result.getRight());
    assertEquals("i8", result.getDown());
    assertEquals("99", result.getLeft());
    
  }
  
  @Test
  public void parseTileThatShouldRefuseIllegalString() throws 
                                               ClassNotFoundException,
                                               InstantiationException, 
                                               IllegalAccessException, 
                                               NoSuchMethodException, 
                                               SecurityException, 
                                               IllegalArgumentException {
    String failed = "hello!";
    
    Class<?> parserClass = 
        Class.forName("unipd.cs.p3.puzzlesolver.tile.TileParser");
    Constructor<?> c = parserClass.getConstructor(String.class);
    Object parser = new Object();
    try {
      parser = c.newInstance("fakefile.txt");
    } catch (InvocationTargetException ite) {
      ite.printStackTrace();
    }
    Class<?>[] parseTileArguments = new Class[1];
    parseTileArguments[0] = String.class;
    Method parseTile = parser.getClass().getDeclaredMethod("parseTile", 
                                         parseTileArguments);
    parseTile.setAccessible(true);
    boolean failedCheck = false;
    
    try {
      parseTile.invoke(parser, failed);
    } catch (InvocationTargetException ite) {
      failedCheck = true;
    }
    
    assertTrue(failedCheck);
    
  }

}

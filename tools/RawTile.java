public class RawTile {
  private String id;
  private char symbol;
  private String up;
  private String down;
  private String left;
  private String right;

  public RawTile(String id, char symbol) {
    this.id = id;
    this.symbol = symbol;
  }

  public String getID() {
    return id;
  }

  public char getSymbol() {
    return symbol;
  }

  public String getUp() {
    return up;
  }

  public void setUp(String up) {
    this.up = up;
  }

  public String getDown() {
    return down;
  }

  public void setDown(String down) {
    this.down = down;
  }

  public String getLeft() {
    return left;
  }

  public void setLeft(String left) {
    this.left = left;
  }

  public String getRight() {
    return right;
  }

  public void setRight(String right) {
    this.right = right;
  }

}
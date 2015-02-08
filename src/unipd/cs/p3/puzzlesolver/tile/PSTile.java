package unipd.cs.p3.puzzlesolver.tile;

class PSTile implements Tile {
	private final String id;
	private final String symbol;
	private final String idUp;
	private final String idDown;
	private final String idLeft;
	private final String idRight;

	PSTile(String id, String symbol, String idUp, String idRight, String idDown,
		String idLeft) {

    this.id = id;
    this.symbol = symbol;
    this.idUp = idUp;
    this.idDown = idDown;
    this.idLeft = idLeft;
    this.idRight = idRight;    
  } 

  @Override
  public String getUp() {
    return idUp;
  }
  @Override
  public String getDown(){
    return idDown;
  }
  @Override
  public String getLeft(){
    return idLeft;
  }
  @Override
  public String getRight(){
    return idRight;
  }

  @Override
  public String getID(){
    return id;
  }

  @Override
  public String toSymbol(){
    return symbol;
  }  

  @Override
  public String toString(){
    return toSymbol();
  }

  @Override
  public boolean equals(Object o){
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tile)) {
      return false;
    }
    
    final Tile t = (Tile) o;
    return id.equals(t.getID());
  }

  @Override
  public int hashCode(){
    return id.hashCode();
  }
}
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

  public String getUp() {
    return idUp;
  }
  public String getDown(){
    return idDown;
  }
  public String getLeft(){
    return idLeft;
  }
  public String getRight(){
    return idRight;
  }

  public String getID(){
    return id;
  }

  public String toSymbol(){
    return symbol;
  }  

  public String toString(){
    return toSymbol();
  }
  
  // TODO inserire in changelog
  // Attieni metodo equals a contratto definito da Java

  @Override
  public boolean equals(Object o){
    if (this == o)
      return true;
    if (!(o instanceof Tile))
      return false;
    
    Tile t = (Tile) o;
    return id.equals(t.getID());
  }

  public int hashCode(){
    return id.hashCode();
  }
}
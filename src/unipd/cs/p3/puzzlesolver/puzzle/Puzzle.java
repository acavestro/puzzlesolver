package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 

public interface Puzzle {

	public int getRows();
	public int getCols();
	public Object toLine();
	public String toMatrix();

}
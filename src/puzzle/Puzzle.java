package unipd.cs.p3.puzzlesolver.puzzle;

import unipd.cs.p3.puzzlesolver.tile.Tile; 

public interface Puzzle {

	public int getRows();
	public int getCols();
	//TODO: aggiornare uml e relazione
	//TODO: ho fatto puntare toString a questo qua. Valuta se cambiarlo ovunque.
	public String toLineString();
	public String toMatrix();
}
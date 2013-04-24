package rendering.models;

import rendering.Tile;

public interface GridModelListener {

	public void gridChanged(int x, int y, Tile oldTile, Tile newTile);
}

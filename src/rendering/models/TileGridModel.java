package rendering.models;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;

import rendering.Tile;
import rendering.TileType;

public class TileGridModel implements Serializable {

	private Tile[] grid;
	private int tilesX;
	private int tilesY;
	
	private ArrayList<GridModelListener> listeners;
	public TileGridModel(int tilesX,int tilesY){
		listeners = new ArrayList<GridModelListener>();
		grid = new Tile[tilesX*tilesY];
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		for(int i = 0; i < grid.length;i++){
			grid[i] = new Tile(TileType.NONE);
		}
	}
	
	public void addGridChangeListener(GridModelListener listener){
		this.listeners.add(listener);
	}
	
	private void fireChangeEvent(int x, int y, Tile oldTile, Tile newTile){
		for(GridModelListener listener: listeners){
			listener.gridChanged(x, y, oldTile, newTile);
		}
	}
	public void setTile(int x,int y,Tile tile){
		int index = grid.length - (x + y*tilesX) -1;
		Tile old = grid[index];
		grid[index] = tile;
		fireChangeEvent(x, y, old, tile);
		
	}
	public Tile getTile(int x,int y,Tile tile){
		return grid[grid.length - (x + y*tilesX) -1];
	}
	public TileType getTypeAt(int x,int y){
		return grid[grid.length - (x + y*tilesX) -1].getType();
	}
	
	public int getNumTiles(){
		return grid.length;
	}
	public int getNumTilesX(){
		return tilesX;
	}
	public int getNumTilesY(){
		return tilesY;
	}
	
}

package rendering;

import java.io.Serializable;

public class TileGrid implements Serializable {

	private Tile[] grid;
	private int tilesX;
	private int tilesY;
	public TileGrid(int tilesX,int tilesY){
		grid = new Tile[tilesX*tilesY];
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		for(int i = 0; i < grid.length;i++){
			grid[i] = new Tile(TileType.NONE);
		}
	}
	
	public void setTile(int x,int y,Tile tile){
		
		grid[grid.length - (x + y*tilesX) -1] = tile;
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

package world.gameobject;

import java.io.Serializable;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public enum RenderPropertyName implements Serializable {
	TEXTURE("texture"),
	TILES_X("tilesX"),
	TILES_Y("tilesY"),
	TILE_GRID("tileGrid");
	private String name;
	private RenderPropertyName(String name) {
		this.name = name;
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString(){
		return  this.name;
	}
}

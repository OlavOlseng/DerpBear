package rendering;

import java.io.Serializable;

public class Tile implements Serializable {

	private TileType type;
	public Tile(TileType type){
		this.type = type;
		
	}
	public TileType getType() {
		return type;
	}
	
	public String toString(){
		return ""+type.ordinal();
	}
	
	
}

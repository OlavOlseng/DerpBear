package rendering;

public enum TileType {
	
	NONE(0),GRASS(2),DIRT(1),PLANKS(4),GRAVEL(3);
	
	private int type;
	TileType(int type){
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
}

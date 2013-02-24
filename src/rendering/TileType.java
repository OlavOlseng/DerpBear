package rendering;

public enum TileType {
	
	NONE(0),GRASS(1),DIRT(2),PLANKS(3),GRAVEL(4);
	
	private int type;
	TileType(int type){
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
}

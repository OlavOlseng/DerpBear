package rendering;

public enum TileType {
	
	NONE(0),GRASS(2),DIRT(1),DIRT_WALL(4,true),GRAVEL(3);
	
	private int type;
	private boolean _isWall;
	TileType(int type, boolean isWall){
		this.type = type;
		this._isWall = isWall;
	}
	TileType(int type){
		this(type,false);
	}
	public boolean isWall(){
		return this._isWall;
	}
	public int getType(){
		return this.type;
	}
}

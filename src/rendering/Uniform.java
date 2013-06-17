package rendering;

public enum Uniform {
	MVP("mvp"),MODEL_MATRIX("modelMatrix"),TILES_X("tilesX"),TILES_Y("tilesY"),
	STEP_SIZE_X("stepSizeX"),STEP_SIZE_Y("stepSizeY"),DEPTH("depth"),FRAME("frame"),OFFSET("offset");
	
	private String name;
	Uniform(String name){
		this.name = name;
	}
	public String toString(){
		return this.name;
	}
}

package rendering;

public enum Attribute {
	COORD3D("coord3d"),TEXCOOR2D("texcoord2d"),COORD2D("coord2d"),
	COLOR3D("color3d");
	
	private String name;
	Attribute(String name){
		this.name = name;
		
	}
	public String toString(){
		return this.name;
	}
}

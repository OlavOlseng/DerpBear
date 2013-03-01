package rendering;

import org.newdawn.slick.opengl.Texture;

public class TileAtlas {

	private int tilesY;
	private int tilesX;
	private Texture texture;
	public TileAtlas(Texture texture,int tilesX, int tilesY){
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		this.texture = texture;
		
		
		
		
	}
	
	public void bind(){
		texture.bind();
	}
	public int getTilesY() {
		return tilesY;
	}
	
	public int getTilesX() {
		return tilesX;
	}
	
	
	
	
}

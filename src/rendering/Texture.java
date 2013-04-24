package rendering;

import java.io.IOException;
import java.io.Serializable;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import rendering.models.TextureModel;
import rendering.models.TextureModelListener;

public class Texture implements TextureModelListener, Serializable {

	
	private TextureModel model;
	private transient org.newdawn.slick.opengl.Texture internalTexture;
	private boolean ready;
	public Texture(TextureModel model){
		this.model = model;
		this.model.addTextureListener(this);
		this.ready = false;
		
	}
	
	public void load() {
		try {
			this.internalTexture = TextureLoader.getTexture(model.getFormat(), ResourceLoader.getResourceAsStream("res\\" + model.getName()),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.ready = true;
	}
	public org.newdawn.slick.opengl.Texture getInternalTexture(){
		if(!this.ready)
			load();
		return this.internalTexture;
	}
	
	public void bind(){
		if(!this.ready)
			load();
		this.internalTexture.bind();
	}
	
	public int getWidth(){
		return getInternalTexture().getTextureWidth();
	}
	
	public int getHeight(){
		return getInternalTexture().getTextureHeight();
	}
	@Override
	public void textureChanged(String oldName, String newName) {
			load();
	}

}

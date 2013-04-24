package rendering.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TextureModel implements Serializable {

	private String name;
	private String format;
	private ArrayList<TextureModelListener> listeners;
	public TextureModel(String name){
		this.name = name;
		this.listeners = new ArrayList<TextureModelListener>();
		this.format = name.substring(name.indexOf('.'));
	}
	
	public void addTextureListener(TextureModelListener listener){
		this.listeners.add(listener);
	}
	
	private void fireTextureChange(String oldName, String newName){
		for (TextureModelListener listener: listeners){
			listener.textureChanged(oldName, newName);
		}
	}
	public String getName(){
		return this.name;
	}
	public String getFormat(){
		return this.format;
	}
	public void setName(String name){
		String old = this.name;
		this.name = name;
		this.fireTextureChange(old, name);
	}
}

package rendering;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class ResourceManager {

	private static  HashMap<String, Shader> shaders = new HashMap<String,Shader>();
	private static HashMap<String, Texture> textures = new HashMap<String,Texture>();
	
	public static Shader getShader(String name){
		Shader shader = shaders.get(name);
		if(shader == null){
			shader = new Shader("res\\shaders\\" +name);
			shaders.put(name, shader);
		}
		return shader;
		
	}
	
	public static Texture getTexture(String format,String name){
		Texture texture = textures.get(name);
		if(texture == null){
			try {
				texture = TextureLoader.getTexture(format, ResourceLoader.getResourceAsStream("res\\" + name),true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			textures.put(name, texture);
		}
		return texture;
		
	}
}

package rendering;

import java.util.HashMap;

public class ProgramManager {

	private static  HashMap<String, Shader> shaders = new HashMap<String,Shader>();
	public static Shader getShader(String name){
		Shader shader = shaders.get(name);
		if(shader == null){
			shader = new Shader("res\\" +name);
			shaders.put(name, shader);
		}
		return shader;
		
	}
}

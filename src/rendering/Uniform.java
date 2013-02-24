package rendering;

public enum Uniform {
	MVP("mvp"),MODEL_MATRIX("modelMatrix");
	
	private String name;
	Uniform(String name){
		this.name = name;
	}
	public String toString(){
		return this.name;
	}
}

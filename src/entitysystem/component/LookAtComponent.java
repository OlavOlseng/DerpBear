package entitysystem.component;

import org.jbox2d.common.Vec2;



public class LookAtComponent extends Component {
	
	private Vec2 target;
	public LookAtComponent(){
		target = new Vec2();
	}
	public void lookAt(float x,float y){
		target.x = x;
		target.y = y;
	}
	
	public Vec2 getTarget(){
		return this.target;
	}
	

}

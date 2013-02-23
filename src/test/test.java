package test;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class test {
	public static void main(String argv[]){
		World world = new World(new Vec2(0.0f, 0.0f), true);
		
		Vec2 v = new Vec2();
		
		System.out.println(v);
	}
}

package world.gameObject;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import component.base.Component;
import component.base.ControllerComponent;
import component.base.GraphicsComponent;
import component.base.PhysicsComponent;
import component.container.ComponentContainer;

public class GameObject {
	private ComponentContainer container;
	private PhysicsComponent physicsComponent;
	private ControllerComponent controllerComponent;
	private GraphicsComponent graphicsComponent;
	private Body body;
	private FixtureDef fixtureDef;
	private BodyDef bodyDef;
	
	public GameObject(PhysicsComponent physicsComponent, ControllerComponent controllerComponent, GraphicsComponent graphicsComponent){
		
		this.graphicsComponent = graphicsComponent;
		this.physicsComponent = physicsComponent;
		this.controllerComponent = controllerComponent;
		container = new ComponentContainer();
		container.addComponent(graphicsComponent);
		container.addComponent(controllerComponent);
		container.addComponent(physicsComponent);
		
	}
	
	public void update(double dt){
		for (Component component: container){
			component.onUpdate(this, dt);
			
		}
	}
	
}

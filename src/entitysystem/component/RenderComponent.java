package entitysystem.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.Renderer;

import network.Syncable;

import rendering.Node;
import rendering.Pipeline;
import rendering.ResourceManager;
import rendering.Sprite;
import rendering.TileAtlas;
import rendering.TileGridRenderer;
import rendering.models.TileGridModel;
import util.GLWorker;
import world.gameobject.RenderPropertyName;
import world.gameobject.RenderingMethod;

public class RenderComponent extends Component implements Serializable, Syncable, Iterable<Node> {
	private ArrayList<Node> nodes;
	private boolean ready;
	private boolean didChange;
	private boolean needsInit = true;
	/**
	 * 
	 * @param sprite - node to be rendered
	 */
	public RenderComponent( Node ...nodes){
		this.nodes = new ArrayList<Node>();
		for(int i = 0; i < nodes.length; i++){
			this.nodes.add(nodes[i]);
			
		}
		ready = false;
		needsInit = true;
		
	}
	
	public void init(){
		GLWorker.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				for(Node node:nodes){
					node.init();
				}
				ready = true;
				
			}
		});
	}
	
	public ArrayList<Node> getNodes(){
		return this.nodes;
	}

	
	public boolean isReady() {
		return ready;
	}

	@Override
	public boolean didChange() {
	
		return didChange;
	}

	@Override
	public Object onRead(Object object) {
		if(needsInit)
			init();
		return this;
	}

	@Override
	public Object onWrite(Object object) {
		didChange = false;
		return null;
	}

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}
}

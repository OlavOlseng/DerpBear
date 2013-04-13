package entitysystem.component;

import java.io.Serializable;
import java.util.HashMap;

import javax.swing.Renderer;

import network.Syncable;

import rendering.Node;
import rendering.Pipeline;
import rendering.ResourceManager;
import rendering.Sprite;
import rendering.TileAtlas;
import rendering.TileGrid;
import rendering.TileGridRenderer;
import util.GLWorker;
import world.gameobject.RenderPropertyName;
import world.gameobject.RenderingMethod;

public class RenderComponent extends Component implements Serializable, Syncable {
	private transient Node node;
	private RenderingMethod renderMetod;
	private HashMap<String, Object> rendererParameters;
	private boolean ready;
	private boolean didChange;
	/**
	 * @deprecated 
	 * @param sprite - node to be rendered
	 */
	public RenderComponent( Node sprite){
		this.node = sprite;
		ready = true;
		
	}
	
	public RenderComponent(RenderingMethod renderer, HashMap<String, Object> parameters){
		this.renderMetod = renderer;
		this.rendererParameters = parameters;
		didChange = true;
		ready = false;
	}
	
	public void init(){
		switch (this.renderMetod) {
		case TILE_GRID_RENDERER: 
			final String textureName = (String) this.rendererParameters.get(RenderPropertyName.TEXTURE.getName());
			final Integer tilesX = (Integer) this.rendererParameters.get(RenderPropertyName.TILES_X.getName());
			final Integer tilesY = (Integer) this.rendererParameters.get(RenderPropertyName.TILES_Y.getName());
			GLWorker.invokeLater(new Runnable() {
				@Override
				public void run() {
					TileAtlas atlas = new TileAtlas(ResourceManager.getTexture("PNG", textureName), tilesX, tilesY);
					TileGrid grid = (TileGrid) rendererParameters.get(RenderPropertyName.TILE_GRID.getName());
					TileGridRenderer renderer = new TileGridRenderer(atlas, grid);
					renderer.setSize(50*64, 50*64);
					node = renderer;
					ready = true;
				}
			});
			break;

		default:
			break;
		}
	}
	public RenderingMethod getRenderMetod() {
		return renderMetod;
	}

	public Object getRendererParameters() {
		return rendererParameters;
	}
	
	public Node getNode(){
		return this.node;
	}

	
	public boolean isReady() {
		return ready;
	}

	@Override
	public boolean didChange() {
		if(didChange){
			didChange = false;
			return true;
		}
		return true;
	}

	@Override
	public Object sync(Object object) {
		init();
		return this;
	}
}

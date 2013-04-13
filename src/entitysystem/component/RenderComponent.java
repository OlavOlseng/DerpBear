package entitysystem.component;

import rendering.Node;
import rendering.Pipeline;
import rendering.Sprite;

public class RenderComponent extends Component {
	private Node node;
	public RenderComponent( Node sprite){
		this.node = sprite;
	}
	
	public Node getNode(){
		return this.node;
	}
}

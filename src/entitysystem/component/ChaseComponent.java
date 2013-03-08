package entitysystem.component;

import entitysystem.Entity;

public class ChaseComponent extends Component {

	
		private Entity target;
		public ChaseComponent(){
			
		}
		
		public void setTarget(Entity target){
			this.target = target;
		}
		
		public Entity getTarget(){
			return this.target;
		}
}

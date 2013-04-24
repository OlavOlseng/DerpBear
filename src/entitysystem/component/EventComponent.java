package entitysystem.component;

import java.util.ArrayList;

import entitysystem.event.Event;

public abstract class EventComponent extends Component{
	
	private ArrayList<Event> events;
	
	public EventComponent() {
		events = new ArrayList<Event>();
	}
	
	public void addEvent(Event evt) {
		events.add(evt);
		evt.setOwner(this);
	}
	
	public void fireEvents(float dt) {
		ArrayList<Event> toBeRemoved = new ArrayList<Event>();
		for(Event e : events) {
			e.fire(dt);
		}
		for(Event e : toBeRemoved) {
			events.remove(e);
		}
	}
}

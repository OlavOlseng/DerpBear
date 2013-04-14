package entitysystem.event;

import java.util.ArrayList;

import entitysystem.component.Component;

public abstract class Event {

	protected boolean triggered = false;
	protected Component owner;
	protected boolean oneShot;
	protected ArrayList<Event> subEvents;

	public Event(boolean oneShot) {
		this.oneShot = oneShot;
		subEvents = new ArrayList<Event>();
	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setOwner(Component c) {
		this.owner = c;
	}

	public void addSubEvent(Event e) {
		this.subEvents.add(e);
	}


	public void resetEvent() {
		triggered = false;
		for (Event e : subEvents) {
			e.resetEvent();
		}
	}

	/**
	 * This method triggers the event
	 */
	public void fire() {
		if(!triggered){
			onFire();
			for (Event e : subEvents) {
				e.fire();
			}
		}
		if (oneShot) {
			triggered = true;
		}
	}

	/**
	 * This method is called when the event is fired;
	 */
	public abstract void onFire();

}

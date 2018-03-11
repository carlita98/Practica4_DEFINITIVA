package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;

abstract public class Event {
	protected int time;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public abstract void execute(RoadMap m);
}

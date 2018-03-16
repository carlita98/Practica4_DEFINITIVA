package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;

abstract public class Event {
	protected int time;
	public Event(int time) {
		this.time = time;
	}
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public abstract void execute(RoadMap m) throws SimulatorException;

	
}

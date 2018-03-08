package es.ucm.fdi.model.events;

import java.util.ArrayList;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.simulatedObjects.*;

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

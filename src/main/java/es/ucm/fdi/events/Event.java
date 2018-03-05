package es.ucm.fdi.events;

import java.util.ArrayList;

import es.ucm.fdi.launcher.RoadMap;
import es.ucm.fdi.simulatedObjects.*;

abstract public class Event {
	private int time;

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public abstract void execute(RoadMap m);
}

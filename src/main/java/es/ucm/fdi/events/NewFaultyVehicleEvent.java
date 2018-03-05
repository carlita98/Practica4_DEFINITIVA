package es.ucm.fdi.events;

import es.ucm.fdi.launcher.RoadMap;
import es.ucm.fdi.simulatedObjects.Vehicle;

public class NewFaultyVehicleEvent extends Event {
	private String id;
	private int duration;
	public NewFaultyVehicleEvent(int t, String i, int d) {
		time = t;
		id = i;
		duration = d;
	}
	
	public void execute(RoadMap m) {
		m.
	}
}

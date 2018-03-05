package es.ucm.fdi.events;

import es.ucm.fdi.launcher.RoadMap;
import es.ucm.fdi.simulatedObjects.*;

public class NewJunctionEvent extends Event {
	private String id;
	public NewJunctionEvent(int t, String i) {
		time = t;
		id = i;
	}
	public void execute(RoadMap m) {
		
	}
} 

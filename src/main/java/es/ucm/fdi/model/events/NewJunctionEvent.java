package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.simulatedObjects.*;

public class NewJunctionEvent extends Event {
	private String id;
	public NewJunctionEvent(int t, String i) {
		time = t;
		id = i;
	}
	public void execute(RoadMap m) {
		try{
			m.addJunction(new Junction(time, id));
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}
} 

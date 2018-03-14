package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;

public class NewJunctionEvent extends Event {
	private String id;
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}
	public void execute(RoadMap m) {
		try{
			m.addJunction(new Junction( id));
		}catch(IllegalArgumentException e){
			throw new IllegalArgumentException("There has been a problem while adding Junction ", e);
		}
	}
} 

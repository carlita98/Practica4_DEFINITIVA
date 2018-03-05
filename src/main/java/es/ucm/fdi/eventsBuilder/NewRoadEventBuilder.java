package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewRoadEvent;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.launcher.RoadMap;

public class NewRoadEventBuilder implements EventBuilder{
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road")) return null;
		return new NewRoadEvent(sec.getValue("time"), sec.getValue("id"), sec.getValue("src"),
				sec.getValue("dest"), sec.getValue("max_speed"), sec.getValue("lenght"));
	}
	public boolean isValidId (RoadMap m, String id){
		if (m.getVehicle (id) == null && m.getJunction(id)== null && m.getRoad(id)== null) return true;
		else throw new IllegalArgumentException ("This ID already exist");
	}
}

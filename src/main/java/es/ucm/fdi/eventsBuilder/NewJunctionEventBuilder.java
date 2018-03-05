package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewJunctionEvent;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.launcher.RoadMap;

public class NewJunctionEventBuilder implements EventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_junction")) return null;
		return new NewJunctionEvent(sec.getValue("time"), sec.getValue("id"));
	}
	public boolean isValidId (RoadMap m, String id){
		if (m.getVehicle (id) == null && m.getJunction(id)== null && m.getRoad(id)== null) return true;
		else throw new IllegalArgumentException ("This ID already exist");
	}
}

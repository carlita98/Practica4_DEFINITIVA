package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewVehicleEvent;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.launcher.RoadMap;

public class NewVehicleEventBuilder implements EventBuilder {
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_vehicle")) return null;
		return new NewVehicleEvent(sec.getValue("time"), sec.getValue("id"), sec.getValue("max_speed"),
				sec.getValue("itinerary"));
	}

	
}

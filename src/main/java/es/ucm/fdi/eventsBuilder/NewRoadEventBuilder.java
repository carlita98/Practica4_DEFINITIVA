package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewRoadEvent;
import es.ucm.fdi.ini.IniSection;

public class NewRoadEventBuilder {
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road")) return null;
		return new NewRoadEvent(sec.getValue("time"), sec.getValue("id"), sec.getValue("src"),
				sec.getValue("dest"), sec.getValue("max_speed"), sec.getValue("lenght"));
	}
}

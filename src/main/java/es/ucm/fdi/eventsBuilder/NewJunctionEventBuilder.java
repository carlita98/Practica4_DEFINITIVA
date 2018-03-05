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

}

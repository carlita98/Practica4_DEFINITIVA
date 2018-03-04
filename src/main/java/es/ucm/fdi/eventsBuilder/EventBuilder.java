package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.ini.IniSection;

public interface EventBuilder {
	EventBuilder []bs = new EventBuilder[] {new NewVehicleEventBuilder(), new NewRoadEventBuilder(),
	new NewFaultyVehicleEventBuilder(), new NewJunctionEventBuilder ()};
	
	public Event parse(IniSection sec);
	public default Event parseSection (IniSection sec) {
		Event e = null;
		for (EventBuilder eb: bs){
			if (eb.parse(sec) != null) {
				e = eb.parse(sec);
				break;
			}
		}
		return e;
	}
}

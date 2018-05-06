package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewLanesEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a
 * NewHighWayEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewLanesEventBuilder extends NewRoadEventBuilder {
	public Event parse(IniSection sec) {
		//Make sure the title and the type are equal to the IniSection title and type
		if (!"new_road".equals(sec.getTag()) || !"lanes".equals(sec.getValue("type"))) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewLanesEvent(
					parseInt(sec, "time", 0), 
					sec.getValue("id"), 
					sec.getValue("src"),
					sec.getValue("dest"), 
					parseInt(sec, "max_speed", 1), 
					parseInt(sec, "length", 1),
					sec.getValue("type"), 
					parseInt(sec, "lanes", 1));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException(
					"There has been a problem creating NewHighWayEvent", i);
		}
	}
}

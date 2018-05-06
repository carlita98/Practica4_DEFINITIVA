package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewDirtEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a NewPathEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewDirtEventBuilder extends NewRoadEventBuilder {
	public Event parse(IniSection sec) {
		//Make sure the title and the type are equal to the IniSection title and type
		if (!"new_road".equals(sec.getTag()) || !"dirt".equals(sec.getValue("type"))) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewDirtEvent(
					parseInt(sec, "time", 0), 
					sec.getValue("id"), 
					sec.getValue("src"),
					sec.getValue("dest"), 
					parseInt(sec, "max_speed", 1), 
					parseInt(sec, "length", 1),
					sec.getValue("type"));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException(
					"There has been a problem creating NewPathEvent", i);
		}
	}
}

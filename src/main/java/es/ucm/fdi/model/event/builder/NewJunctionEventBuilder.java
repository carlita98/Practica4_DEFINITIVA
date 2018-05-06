package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewJunctionEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a
 * NewJunctionEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewJunctionEventBuilder implements EventBuilder {
	public Event parse(IniSection sec) {
		//Make sure the title is equal to the IniSection title
		if (!"new_junction".equals(sec.getTag())) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewJunctionEvent(
					parseInt(sec, "time", 0), 
					sec.getValue("id"));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException(
					"There has been a problem creating NewJunctionEvent", i);
		}
	}
}

package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewRoundRobinEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a
 * NewRoundRobinEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewRoundRobinEventBuilder extends NewJunctionEventBuilder {
	public Event parse(IniSection sec) {
		//Make sure the title and the type are equal to the IniSection title and type
		if (!"new_junction".equals(sec.getTag()) || !"rr".equals(sec.getValue("type"))) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewRoundRobinEvent(parseInt(sec, "time", 0), sec.getValue("id"),
					parseInt(sec, "max_time_slice", 1), parseInt(sec, "min_time_slice", 1));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException("There has been a problem creating NewRoundRobinEvent", i);
		}
	}
}

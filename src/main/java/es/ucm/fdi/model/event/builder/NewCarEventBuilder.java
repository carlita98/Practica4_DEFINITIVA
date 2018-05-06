package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewCarEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a NewCarEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewCarEventBuilder extends NewVehicleEventBuilder {
	public Event parse(IniSection sec) {
		//Make sure the title and the type are equal to the IniSection title and type
		if (!"new_vehicle".equals(sec.getTag()) || !"car".equals(sec.getValue("type"))) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewCarEvent(
					parseInt(sec, "time", 0), 
					sec.getValue("id"), 
					parseInt(sec, "max_speed", 1),
					parseIdList(sec, "itinerary"), 
					sec.getValue("type"), 
					parseInt(sec, "resistance", 1),
					parseDoub(sec, "fault_probability", 0), 
					parseInt(sec, "max_fault_duration", 1),
					parseInt(sec, "seed", 1));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException(
					"There has been a problem creating NewCarEvent", i);
		}
	}
}

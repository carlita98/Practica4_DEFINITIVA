package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewVehicleEvent;

/**
 * Implements EventBuilder, says if an IniSection correspond to a
 * NewVehicleEvent
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewVehicleEventBuilder implements EventBuilder {

	public Event parse(IniSection sec) {
		//Make sure the title is equal to the IniSection title
		if (!"new_vehicle".equals(sec.getTag())) {
			return null;
		}
		try {
			//Creates a new Event
			return new NewVehicleEvent(
					parseInt(sec, "time", 0), 
					sec.getValue("id"),
					parseInt(sec, "max_speed", 1),
					parseIdList(sec, "itinerary"));
		} catch (IllegalArgumentException i) {
			throw new IllegalArgumentException(
					"There has been a problem creating NewVehicleEvent", i);
		}
	}

}

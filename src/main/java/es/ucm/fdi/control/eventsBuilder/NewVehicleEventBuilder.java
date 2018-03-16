package es.ucm.fdi.control.eventsBuilder;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewVehicleEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewVehicleEvent
 * @author Carla Martínez
 *
 */
public class NewVehicleEventBuilder implements EventBuilder {
	
	public Event parse(IniSection sec) {
		if(!sec.getTag().equals("new_vehicle") || sec.getValue("type") != null) return null;
		try{
			return new NewVehicleEvent( parseInt (sec,"time", 0),  sec.getValue("id"),
				parseInt (sec, "max_speed", 1), parseIdList (sec, "itinerary"));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewVehicleEvent", i);
		}
	}

	
}

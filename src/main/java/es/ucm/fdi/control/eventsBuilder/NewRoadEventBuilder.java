package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewRoadEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewRoadEvent
 * @author Carla Martínez
 *
 */
public class NewRoadEventBuilder implements EventBuilder{
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road")|| sec.getValue("type") != null) return null;
		try{
			return new NewRoadEvent (parseInt (sec, "time", 0), sec.getValue("id"),
					sec.getValue("src"), sec.getValue("dest"),  parseInt (sec, "max_speed", 1),
					parseInt (sec, "length", 1));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoadEvent", i);
		}
	}

}

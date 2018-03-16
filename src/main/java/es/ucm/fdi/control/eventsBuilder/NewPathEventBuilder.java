package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewPathEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewPathEvent
 * @author Carla Martínez
 *
 */
public class NewPathEventBuilder extends NewRoadEventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road") || !"dirt".equals(sec.getValue("type"))) return null;
		try{
			return new NewPathEvent (parseInt (sec, "time", 0),  sec.getValue("id"),  
					sec.getValue("src"), sec.getValue("dest"),  parseInt (sec, "max_speed", 1), 
					parseInt (sec, "length", 1), sec.getValue("type"));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewPathEvent", i);
		}
	}
}

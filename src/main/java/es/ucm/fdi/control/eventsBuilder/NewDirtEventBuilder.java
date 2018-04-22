package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewDirtEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewPathEvent
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewDirtEventBuilder extends NewRoadEventBuilder{
	public Event parse(IniSection sec) {
		if( ! "new_road".equals(sec.getTag()) || !"dirt".equals(sec.getValue("type"))) {
			return null;
		}
		try{
			return new NewDirtEvent (parseInt (sec, "time", 0),  sec.getValue("id"),  
					sec.getValue("src"), sec.getValue("dest"),  parseInt (sec, "max_speed", 1), 
					parseInt (sec, "length", 1), sec.getValue("type"));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewPathEvent", i);
		}
	}
}

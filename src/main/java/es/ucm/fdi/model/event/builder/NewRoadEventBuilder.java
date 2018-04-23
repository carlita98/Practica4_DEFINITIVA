package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewRoadEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewRoadEvent
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewRoadEventBuilder implements EventBuilder{
	
	public Event parse(IniSection sec) {
		if( ! "new_road".equals(sec.getTag())) {
			return null;
		}
		try{
			return new NewRoadEvent (parseInt (sec, "time", 0), sec.getValue("id"),
					sec.getValue("src"), sec.getValue("dest"),  parseInt (sec, "max_speed", 1),
					parseInt (sec, "length", 1));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoadEvent", i);
		}
	}

}

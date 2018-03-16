package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewJunctionEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewJunctionEvent
 * @author Carla Martínez
 *
 */
public class NewJunctionEventBuilder implements EventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_junction")) return null;
		try{
			return new NewJunctionEvent (parseInt (sec, "time", 0), sec.getValue("id"));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewJunctionEvent", i);
		}
	}
}

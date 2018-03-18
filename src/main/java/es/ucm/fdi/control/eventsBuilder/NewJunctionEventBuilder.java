package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewJunctionEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewJunctionEvent
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewJunctionEventBuilder implements EventBuilder{
	public Event parse(IniSection sec) {
		if( ! "new_junction".equals(sec.getTag())) return null;
		try{
			return new NewJunctionEvent (parseInt (sec, "time", 0), sec.getValue("id"));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewJunctionEvent", i);
		}
	}
}

package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewMostCrowedEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewMostCrowedEventBuilder
 * @author Carla Martínez
 *
 */
public class NewMostCrowedEventBuilder extends NewJunctionEventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_junction")|| !"mc".equals(sec.getValue("type"))) return null;
		try{
			return new NewMostCrowedEvent (parseInt (sec, "time", 0), sec.getValue("id"));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoundRobinEvent", i);
		}
	}
}

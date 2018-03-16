package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewRoundRobinEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewRoundRobinEvent
 * @author Carla Martínez
 *
 */
public  class NewRoundRobinEventBuilder extends NewJunctionEventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_junction")|| !"rr".equals(sec.getValue("type"))) return null;
		try{
			return new NewRoundRobinEvent (parseInt (sec, "time", 0), sec.getValue("id"), 
					parseInt(sec, "max_time_slice", 1),parseInt(sec, "min_time_slice", 1));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoundRobinEvent", i);
		}
	}
}

package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewMostCrowedEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewMostCrowedEventBuilder
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewMostCrowedEventBuilder extends NewJunctionEventBuilder{
	public Event parse(IniSection sec) {
		if( ! "new_junction".equals(sec.getTag())|| !"mc".equals(sec.getValue("type"))) {
			return null;
		}
		try{
			return new NewMostCrowedEvent (parseInt (sec, "time", 0), sec.getValue("id"));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoundRobinEvent", i);
		}
	}
}

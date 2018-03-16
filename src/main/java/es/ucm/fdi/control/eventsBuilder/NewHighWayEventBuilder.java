package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewHighWayEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewHighWayEvent
 * @author Carla Martínez
 *
 */
public class NewHighWayEventBuilder extends NewRoadEventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road") || !"lanes".equals(sec.getValue("type"))) return null;
		try{
			int t = parseInt (sec, "time", 0);
			int mS = parseInt (sec, "max_speed", 1);
			int l = parseInt (sec, "length", 1);
			int la = parseInt(sec, "lanes", 1);

			String id = sec.getValue("id");
			String idJI = sec.getValue("src");
			String idJD = sec.getValue("dest");
			if (isValidId(id) && isValidId (idJI) && isValidId(idJD))
				return new NewHighWayEvent (t, id, idJI, idJD, mS, l, sec.getValue("type"), la);
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewHighWayEvent", i);
		}
		return null;
	}
}

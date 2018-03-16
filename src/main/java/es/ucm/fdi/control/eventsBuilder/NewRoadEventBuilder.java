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
			int t = parseInt (sec, "time", 0);
			int mS = parseInt (sec, "max_speed", 1);
			int l = parseInt (sec, "length", 1);

			String id = sec.getValue("id");
			String idJI = sec.getValue("src");
			String idJD = sec.getValue("dest");
			if (isValidId(id) && isValidId (idJI) && isValidId(idJD))
				return new NewRoadEvent (t, id, idJI, idJD, mS, l);
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewRoadEvent", i);
		}
		return null;
	}

}

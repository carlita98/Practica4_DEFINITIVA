package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewHighWayEvent;
import es.ucm.fdi.model.events.NewPathEvent;

public class NewPathEventBuilder extends NewRoadEventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_road") || !"dirt".equals(sec.getValue("type"))) return null;
		try{
			int t = parseInt (sec, "time", 0);
			int mS = parseInt (sec, "max_speed", 1);
			int l = parseInt (sec, "length", 1);

			String id = sec.getValue("id");
			String idJI = sec.getValue("src");
			String idJD = sec.getValue("dest");
			if (isValidId(id) && isValidId (idJI) && isValidId(idJD))
				return new NewPathEvent (t, id, idJI, idJD, mS, l, sec.getValue("type"));
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewPathEvent", i);
		}
		return null;
	}
}

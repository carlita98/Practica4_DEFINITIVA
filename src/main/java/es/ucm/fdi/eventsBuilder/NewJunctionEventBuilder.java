package es.ucm.fdi.eventsBuilder;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewJunctionEvent;
import es.ucm.fdi.ini.IniSection;

public class NewJunctionEventBuilder implements EventBuilder{
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_junction")) return null;
		try{
			int t = parseInt (sec, "time", 0);
			String id = sec.getValue("id");
			if (isValidId (id)) return new NewJunctionEvent (t, id);
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewJunctionEvent", i);
		}
	
		return null;
	}
		

}

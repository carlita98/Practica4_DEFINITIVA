package es.ucm.fdi.control.eventsBuilder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewJunctionEvent;

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

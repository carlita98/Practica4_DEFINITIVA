package es.ucm.fdi.control.eventsBuilder;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewBikeEvent;
import es.ucm.fdi.model.events.NewVehicleEvent;

public class NewBikeEventBuilder implements EventBuilder{

	@Override
	public Event parse(IniSection sec) {
			if(!sec.getTag().equals("new_vehicle") || !"bike".equals(sec.getValue("type"))) return null;
			try{
				int t = parseInt (sec,"time", 0);
				int mS = parseInt (sec, "max_speed", 1);
			
				String id = sec.getValue("id");
				if(isValidId(id)){
					ArrayList <String> idList = parseIdList (sec, "itinerary");
					return new NewBikeEvent (t, id, mS, idList, sec.getValue("type"));
				}
			}catch (IllegalArgumentException i){
				throw new IllegalArgumentException("There has been a problem creating NewBikeEvent", i);
			}
			return null;
	}

}

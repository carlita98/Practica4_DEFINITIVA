package es.ucm.fdi.eventsBuilder;

import java.util.ArrayList;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewVehicleEvent;
import es.ucm.fdi.ini.IniSection;

public class NewVehicleEventBuilder implements EventBuilder {
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("new_vehicle")) return null;
		try{
			int t = parseInt (sec,"time", 0);
			int mS = parseInt (sec, "max_speed", 1);
			try{
				String id = sec.getValue("id");
				ArrayList <String> idList = parseIdList (sec, "itinerary");
				return new NewVehicleEvent(t, id, mS, idList);
			}catch (IllegalArgumentException i){
				i.getMessage();
			}
		}catch(NumberFormatException e){
			e.getMessage();
		}
		return null;
	}

	
}

package es.ucm.fdi.control.eventsBuilder;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.NewCarEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewCarEvent
 * @author Carla Martínez
 *
 */
public class NewCarEventBuilder extends NewVehicleEventBuilder{
	public Event parse(IniSection sec) {
		if(!sec.getTag().equals("new_vehicle") || !"car".equals(sec.getValue("type"))) return null;
		try{
			int t = parseInt (sec,"time", 0);
			int mS = parseInt (sec, "max_speed", 1);
			int r = parseInt(sec, "resistance", 1);
			double fp = parseDoub(sec, "fault_probability", 0);
			int mfd = parseInt(sec, "max_fault_duration", 1);
			long s = parseInt(sec, "seed", 1);
			
			String id = sec.getValue("id");
			if(isValidId(id)){
				ArrayList <String> idList = parseIdList (sec, "itinerary");
				return new NewCarEvent (t, id, mS, idList, sec.getValue("type"), r, fp, mfd, s);
			}
		}catch (IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating NewCarEvent", i);
		}
		return null;
}
}

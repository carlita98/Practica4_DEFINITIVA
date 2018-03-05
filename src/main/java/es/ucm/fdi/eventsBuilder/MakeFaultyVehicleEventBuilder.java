package es.ucm.fdi.eventsBuilder;
import java.util.ArrayList;

import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.MakeFaultyVehicleEvent;
import es.ucm.fdi.ini.IniSection;
public class MakeFaultyVehicleEventBuilder implements EventBuilder{
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("make_vehicle_faulty")) return null;
		try{
			int t = parseInt (sec, "time", 0);
			int duracion = parseInt (sec, "duration", 1);
			try{
				ArrayList <String> idList = parseIdList (sec, "vehicles");
				return new MakeFaultyVehicleEvent(t, idList, duracion);
				
			}catch(IllegalArgumentException i){
				i.getMessage();
			}
		}catch(NumberFormatException e){
			e.getMessage();
		}
		return null;
	}
	
	
}

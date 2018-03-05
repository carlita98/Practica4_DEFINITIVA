package es.ucm.fdi.eventsBuilder;
import es.ucm.fdi.events.Event;
import es.ucm.fdi.events.NewFaultyVehicleEvent;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.launcher.*;
public class NewFaultyVehicleEventBuilder implements EventBuilder{
	
	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("make_vehicle_faulty")) return null;
		//return new NewFaultyVehicleEvent(sec.getValue("time"), sec.getValue("vehicles"), sec.getValue("duration"));
	}
	
	
}

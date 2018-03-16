package es.ucm.fdi.control.eventsBuilder;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.events.MakeFaultyVehicleEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a MakeFaultyVehicleEvent
 * @author Carla Martínez
 *
 */
public class MakeFaultyVehicleEventBuilder implements EventBuilder{

	public Event parse(IniSection sec) {
		if( ! sec.getTag().equals("make_vehicle_faulty")) return null;
		try{
			return new MakeFaultyVehicleEvent(parseInt (sec, "time", 0), parseIdList (sec, "vehicles"), parseInt (sec, "duration", 1));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating MakeFaultyVehicleEvent", i);
		}
	}
	
	
}

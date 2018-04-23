package es.ucm.fdi.model.event.builder;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.MakeFaultyVehicleEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a MakeFaultyVehicleEvent
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class MakeFaultyVehicleEventBuilder implements EventBuilder{

	public Event parse(IniSection sec) {
		if( ! "make_vehicle_faulty".equals(sec.getTag())) {
			return null;
		}
		try{
			return new MakeFaultyVehicleEvent(parseInt (sec, "time", 0), parseIdList (sec, "vehicles"), parseInt (sec, "duration", 1));
		}catch(IllegalArgumentException i){
			throw new IllegalArgumentException("There has been a problem creating MakeFaultyVehicleEvent", i);
		}
	}
	
	
}

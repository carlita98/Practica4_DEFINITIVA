package es.ucm.fdi.model.event.builder;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.event.NewBikeEvent;
/**
 * Implements EventBuilder, says if an IniSection correspond to a NewBikeEvent
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewBikeEventBuilder implements EventBuilder{

	@Override
	public Event parse(IniSection sec) {
			if(!"new_vehicle".equals(sec.getTag()) || !"bike".equals(sec.getValue("type"))) {
				return null;
			}
			try{
				return new NewBikeEvent (parseInt(sec,"time", 0), sec.getValue("id"),
					parseInt(sec, "max_speed", 1), parseIdList (sec, "itinerary"), sec.getValue("type"));
			}catch (IllegalArgumentException i){
				throw new IllegalArgumentException("There has been a problem creating NewBikeEvent", i);
			}
	}

}

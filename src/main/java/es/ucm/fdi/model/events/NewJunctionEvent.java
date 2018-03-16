package es.ucm.fdi.model.events;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;
/**
 * Introduce a new Junction in the RoadMap
 * @author Carla Martínez
 *
 */
public class NewJunctionEvent extends Event {
	protected String id;
	/**
	 * Constructor
	 * @param time
	 * @param id
	 */
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}
	/**
	 * Introduce a new Junction in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		try{
			m.addJunction(new Junction( id));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding Junction ", e);
		}
	}
} 

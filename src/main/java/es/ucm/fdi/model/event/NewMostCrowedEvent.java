package es.ucm.fdi.model.event;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.MostCrowdedJunction;

/**
 * Introduce a new MostCrowed in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class NewMostCrowedEvent extends NewJunctionEvent{
	
	/**
	 * Constructor
	 * @param time
	 * @param id
	 */
	public NewMostCrowedEvent(int time, String id) {
		super(time, id);
	}
	
	/**
	 * Introduce a new MostCrowed in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		try{
			m.addJunction(new MostCrowdedJunction( id, "mc"));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding RoundRobin ", e);
		}
	}
}

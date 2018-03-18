package es.ucm.fdi.model.events;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.MostCrowed;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;
/**
 * Introduce a new MostCrowed in the RoadMap
 * @author Carla Martínez
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
			m.addJunction(new MostCrowed( id, "mc"));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding RoundRobin ", e);
		}
	}
}

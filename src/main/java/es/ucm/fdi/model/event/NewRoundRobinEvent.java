package es.ucm.fdi.model.event;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.RoundRobinJunction;

/**
 * Introduce a new RoundRobin in the RoadMap
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewRoundRobinEvent extends NewJunctionEvent {
	private int maxTimeSlice;
	private int minTimeSlice;

	/**
	 * Constructor
	 * 
	 * @param time
	 * @param id
	 * @param maxTimeSlice
	 * @param minTimeSlice
	 */

	public NewRoundRobinEvent(int time, String id, int maxTimeSlice, int minTimeSlice) {
		super(time, id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}

	/**
	 * Introduce a new RoundRobin in the RoadMap
	 */

	public void execute(RoadMap m) throws SimulatorException {
		try {
			m.addJunction(new RoundRobinJunction(id, maxTimeSlice, minTimeSlice, "rr"));
		} catch (NoSuchElementException e) {
			throw new SimulatorException("There has been a problem while adding RoundRobin ", e);
		}
	}
}

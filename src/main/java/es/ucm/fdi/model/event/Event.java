package es.ucm.fdi.model.event;

import java.util.Map;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.view.Describable;

/**
 * Common factor of the different Event classes
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public abstract class Event implements Describable {
	protected int time;
	
	/**
	 * Constructor
	 * @param time
	 */
	public Event(int time) {
		this.time = time;
	}

	public int getTime() {
		return time;
	}
	
	/**
	 * Setter
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * Introduce a new element in the RoadMap
	 * @param m
	 * @throws SimulatorException
	 */
	public abstract void execute(RoadMap m) throws SimulatorException;

}

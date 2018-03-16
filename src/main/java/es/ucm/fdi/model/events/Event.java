package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;
/**
 * Common factor of the different Event classes
 * @author Carla Martínez
 *
 */
abstract public class Event {
	protected int time;
	/**
	 * Constructor
	 * @param time
	 */
	public Event(int time) {
		this.time = time;
	}
	/*
	 * Getter
	 */
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

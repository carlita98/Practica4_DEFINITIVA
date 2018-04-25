package es.ucm.fdi.model.event;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.*;
import es.ucm.fdi.view.Describable;

/**
 * Introduce a new Vehicle in the RoadMap
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewVehicleEvent extends Event implements Describable {
	protected String id;
	protected int maxSpeed;
	protected ArrayList<String> itinerary = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param time
	 * @param id
	 * @param maxSpeed
	 * @param itinerary
	 */
	public NewVehicleEvent(int time, String id, int maxSpeed, ArrayList<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}

	/**
	 * Introduce a new Vehicle in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		ArrayList<Junction> jList = new ArrayList<>();
		for (int i = 0; i < itinerary.size(); i++) {
			jList.add(m.getJunction(itinerary.get(i)));
		}
		try {
			m.addVehicle(new Vehicle(id, maxSpeed, jList));
		} catch (NoSuchElementException e) {
			throw new SimulatorException("There has been a problem while adding Vehicle ", e);
		}
	}

	/**
	 * Describes the event to insert it into the interface events table
	 */
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		out.put("Type", "New Vehicle " + id);
	}
}

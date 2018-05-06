package es.ucm.fdi.model.event;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.view.Describable;

/**
 * Make a vehicle in the RoadMap faulty
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class MakeFaultyVehicleEvent extends Event implements Describable {

	private ArrayList<String> id = new ArrayList<>();
	private int duration;

	/**
	 * Constructor
	 * 
	 * @param time
	 * @param id
	 * @param duration
	 */
	public MakeFaultyVehicleEvent(int time, ArrayList<String> id, int duration) {
		super(time);
		this.id = id;
		this.duration = duration;
	}

	/**
	 * Make an existing vehicle in the RoadMap faulty
	 */
	public void execute(RoadMap m) throws SimulatorException {
		for (int i = 0; i < id.size(); i++) {
			try {
				//Set faulty time to the selected vehicle
				m.getVehicle(id.get(i)).setFaultyTime(duration);
				m.getVehicle(id.get(i)).setActualSpeed(0);
			} catch (NoSuchElementException e) {
				throw new SimulatorException(
						"There has been a problem while making vehicle faulty ", e);
			}
		}
	}

	/**
	 * Describes the event to insert it into the interface events table
	 */
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		StringBuilder sb = new StringBuilder();
		sb.append("Break Vehicles " + "[");
		boolean bucle = false;

		for (String it : id) {
			sb.append(it);
			sb.append(",");
			bucle = true;
		}

		if (bucle) {
			sb.delete(sb.length() - 1, sb.length());
		}

		sb.append("]");
		out.put("Type", sb.toString());
	}
}

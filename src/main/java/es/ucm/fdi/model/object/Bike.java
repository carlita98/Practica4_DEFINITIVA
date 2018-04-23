package es.ucm.fdi.model.object;

import java.util.ArrayList;
import java.util.Map;
/**
 * All the necessary methods for the Simulated Object Bike
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Bike extends Vehicle{
	
	private String type;
	/**
	 * Constructor
	 * @param id
	 * @param maxSpeed
	 * @param itinerary
	 * @param type
	 */
	public Bike(String id, int maxSpeed, ArrayList<Junction> itinerary, String type) {
		super(id, maxSpeed, itinerary);
		this.type = type;
	}
	/**
	 * Advance a car into its currentRoad or push it into the Incoming Road
	 */
	public void moveForward() {
		if(actualSpeed <= maxSpeed / 2) {
			setFaultyTime(0);
		}
		super.moveForward();
	}
	/**
	 * Fill a Map with the Bike data
	 */
	public void fillReportDetails(Map <String, String> out) {
		out.put("type", type);
		super.fillReportDetails(out);
	}
}

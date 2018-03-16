package es.ucm.fdi.model.simulatedObjects;

import java.util.ArrayList;
import java.util.Map;

public class Bike extends Vehicle{
	
	private String type;

	public Bike(String id, int maxSpeed, ArrayList<Junction> itinerary, String type) {
		super(id, maxSpeed, itinerary);
		this.type = type;
	}
	
	public void moveForward() {
		if(actualSpeed <= maxSpeed / 2) {
			setFaultyTime(0);
		}
		super.moveForward();
	}
	
	public void fillReportDetails(Map <String, String> out) {
		out.put("type", type);
		super.fillReportDetails(out);
	}
}

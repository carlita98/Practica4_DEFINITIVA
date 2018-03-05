package es.ucm.fdi.events;

import java.util.ArrayList;

import es.ucm.fdi.launcher.RoadMap;
import es.ucm.fdi.simulatedObjects.*;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private ArrayList <String>itinerary = new ArrayList<>();
	public NewVehicleEvent (int t, String i, int maxSpeed, ArrayList<String> it) {
		time = t;
		id = i;
		itinerary = it;
	}
	
	public void execute(RoadMap m) {
		ArrayList <Junction> jList = new ArrayList <>();
		for (int i = 0; i < itinerary.size(); i++){
			jList.add(m.getJunction(itinerary.get(i))) ;
		}
		m.addVehicle(new Vehicle (time, id, maxSpeed, jList));
		
	}
}

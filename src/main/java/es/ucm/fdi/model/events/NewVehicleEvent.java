package es.ucm.fdi.model.events;

import java.util.ArrayList;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.simulatedObjects.Vehicle;

public class NewVehicleEvent extends Event{
	private String id;
	private int maxSpeed;
	private ArrayList <String>itinerary = new ArrayList<>();
	public NewVehicleEvent (int t, String i, int mS, ArrayList<String> it) {
		time = t;
		id = i;
		itinerary = it;
		maxSpeed = mS;
	}
	
	public void execute(RoadMap m) {
		ArrayList <Junction> jList = new ArrayList <>();
		for (int i = 0; i < itinerary.size(); i++){
			jList.add(m.getJunction(itinerary.get(i))) ;
		}
		try{
			m.addVehicle(new Vehicle (time, id, maxSpeed, jList));
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}
}

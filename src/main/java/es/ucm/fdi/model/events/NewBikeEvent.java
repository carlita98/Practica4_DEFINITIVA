package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Bike;
import es.ucm.fdi.model.simulatedObjects.Junction;

public class NewBikeEvent extends NewVehicleEvent{
	private String type;
	public NewBikeEvent (int time, String id, int maxSpeed, ArrayList<String> itinerary, String type) {
		super(time, id, maxSpeed, itinerary);
		this.type = type;
	}
	
	public void execute(RoadMap m) {
		ArrayList <Junction> jList = new ArrayList <>();
		try{
			for (int i = 0; i < itinerary.size(); i++){
				jList.add(m.getJunction(itinerary.get(i))) ;
			}
			m.addVehicle(new Bike(id, maxSpeed, jList, type));
		}catch(NoSuchElementException e){
			throw new IllegalArgumentException("There has been a problem while adding bike ", e);
		}
	}
}

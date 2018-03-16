package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.*;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;

public class NewVehicleEvent extends Event{
	protected String id;
	protected int maxSpeed;
	protected ArrayList <String>itinerary = new ArrayList<>();
	
	public NewVehicleEvent (int time, String id, int maxSpeed, ArrayList<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
	}
	
	public void execute(RoadMap m) throws SimulatorException {
		ArrayList <Junction> jList = new ArrayList <>();
		for (int i = 0; i < itinerary.size(); i++){
			jList.add(m.getJunction(itinerary.get(i))) ;
		}
		try{
			m.addVehicle(new Vehicle ( id, maxSpeed, jList));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding Vehicle ", e);
		}
	}
}

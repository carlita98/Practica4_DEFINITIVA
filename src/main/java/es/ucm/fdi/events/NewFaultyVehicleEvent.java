package es.ucm.fdi.events;

import es.ucm.fdi.simulatedObjects.Vehicle;

public class NewFaultyVehicleEvent extends Event {
	public NewFaultyVehicleEvent(String time, String id, String duration) {
		//EventList.add(new Vehicle(Integer.parseInt(time), id, Integer.parseInt(duration)));
	}
	
	public void execute() {
		
	}
}

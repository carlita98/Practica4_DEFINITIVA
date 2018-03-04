package es.ucm.fdi.events;

import java.util.ArrayList;

import es.ucm.fdi.simulatedObjects.*;

abstract public class Event {
	ArrayList <SimulatedObject> EventList;
	ArrayList <Vehicle> VehicleList;
	ArrayList <Junction> JunctionList;
	ArrayList <Road> RoadList;
	public Event(){
		EventList = new ArrayList<SimulatedObject>();
	}
}

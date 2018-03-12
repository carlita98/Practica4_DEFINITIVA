package es.ucm.fdi.model.events;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Vehicle;

public class MakeFaultyVehicleEvent extends Event {
	private ArrayList<String> id = new ArrayList<>();
	private int duration;
	public MakeFaultyVehicleEvent(int t, ArrayList<String> i, int d) {
		time = t;
		id = i;
		duration = d;
	}
	
	public void execute(RoadMap m) {
		for(int i = 0; i < id.size(); i++){
			try{
				m.getVehicle(id.get(i)).setFaultyTime(duration);
				m.getVehicle(id.get(i)).setActualSpeed(0);
			}catch(NoSuchElementException e){
				throw new IllegalArgumentException("While making vehicle faulty ", e);
			}
		}
	}
}

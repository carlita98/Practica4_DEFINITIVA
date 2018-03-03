package es.ucm.fdi.model;

import java.util.*;

public class Vehicle {
	
	private String vehicleId;
	private int faulty;
	private Road actualRoad;
	private int roadLocation;
	private int maxSpeed;
	private int actualSpeed;
	private ArrayList<Junction> itinerary;
	private boolean arrived;
	
	public void setFaultyTime (int t) {
		faulty += t;
	}
	public void setActualSpeed (int s) {
		if (s > maxSpeed) actualSpeed = maxSpeed;
		else actualSpeed = s;
	}
	public void moveForward () {
		if (faulty != 0) {
			roadLocation += actualSpeed;
			//if (roadLocation >= actualRoad.getLength()){
				//roadLocation = actualRoad.getLength();
				//itinerary.remove(0);
				//itinerary.get(0).pushVehicle(this);
			//}
		}
		else faulty --;
	}
	public void moveToNextRoad() {
		
	}
	
	public void generateInform (){
		
	}
}
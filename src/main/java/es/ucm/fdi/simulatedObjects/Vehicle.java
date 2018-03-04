package es.ucm.fdi.simulatedObjects;

import java.util.*;

public class Vehicle extends SimulatedObject{

	private int kilometrage;
	private int faulty;
	private Road actualRoad;
	private int roadLocation;
	private int maxSpeed;
	private int actualSpeed;
	private ArrayList<Junction> itinerary;
	private boolean arrived;
	
	
	public Road getActualRoad() {
		return actualRoad;
	}
	public int getFaulty () {
		return faulty;
	}
	public void setFaultyTime (int t) {
		faulty += t;
	}
	public void setActualSpeed (int s) {
		if (s > maxSpeed) actualSpeed = maxSpeed;
		else actualSpeed = s;
	}
	public int getKilometrage() {
		return kilometrage;
	}
	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}
	public int getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(int roadLocation) {
		this.roadLocation = roadLocation;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public ArrayList<Junction> getItinerary() {
		return itinerary;
	}
	public void setItinerary(ArrayList<Junction> itinerary) {
		this.itinerary = itinerary;
	}
	public boolean isArrived() {
		return arrived;
	}
	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
	public int getActualSpeed() {
		return actualSpeed;
	}
	public void setActualRoad(Road actualRoad) {
		this.actualRoad = actualRoad;
	}

	public void moveForward () {
		if (faulty == 0) {
			roadLocation += actualSpeed;
			if (roadLocation >= actualRoad.getLength()){
				kilometrage += actualRoad.getLength();
				roadLocation = actualRoad.getLength();
				itinerary.get(0).carIntoIR(this);
			}
			else kilometrage += actualSpeed;
		}
		else {
			faulty --;
		}
	}

	public void moveToNextRoad() {
		if (itinerary.size() > 0) {
		for (Road r: itinerary.get(0).getOutgoingRoadList()) {
			for (Road r2: itinerary.get(1).getIncomingRoadList()) {
				if (r == r2) {
					actualRoad.popVehicle(this);
					actualRoad = r;
					itinerary.remove(0);
					actualRoad.pushVehicle(this);
					roadLocation = 0;
					break;
				}
			}
		}
		}
		else {
			itinerary.remove(0);
			roadLocation = 0;
			arrived = true;
		}
	}
	protected  String getReportHeader() {
		return "vehicle_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		out.put("speed", String.valueOf(actualSpeed));
		out.put("kilometrage", String.valueOf(kilometrage));
		out.put("faulty", String.valueOf(faulty));
		if (!arrived)
			out.put("location","(" + actualRoad.getID() + roadLocation + ")" );
		else
			out.put("location", "arrived");
	}
}
package es.ucm.fdi.model.simulatedObjects;

import java.util.*;

public class Vehicle extends SimulatedObject{

	private int kilometrage;
	private int faulty;
	private Road currentRoad;
	private int junctionCounter;
	private int roadLocation;
	private int maxSpeed;
	private int actualSpeed;
	private ArrayList<Junction> itinerary = new ArrayList <>();
	private boolean arrived;
	
	public Road getActualRoad() {
		return currentRoad;
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
		this.currentRoad = actualRoad;
	}

	public Vehicle (int t, String i, int mS, ArrayList <Junction> j){
		time = t;
		Id = i;
		maxSpeed = mS;
		itinerary = j;
		
		faulty = 0;
		kilometrage = 0;
		junctionCounter = 0;
		roadLocation = 0;
		arrived = false;
		this.moveToNextRoad();
		actualSpeed = 0;
	}
	public void moveForward () {
		if (faulty == 0) {
			roadLocation += actualSpeed;
			if (roadLocation >= currentRoad.getLength()){
				kilometrage += currentRoad.getLength();
				roadLocation = currentRoad.getLength();
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
		for (Road r: itinerary.get(junctionCounter).getOutgoingRoadList()) {
			for (Road r2: itinerary.get(junctionCounter).getIncomingRoadList()) {
				if (r == r2) {
					currentRoad.popVehicle(this);
					currentRoad = r;
					currentRoad.pushVehicle(this);
					roadLocation = 0;
					junctionCounter++;
					break;
				}
			}
		}
		}
		else {
			roadLocation = 0;
			arrived = true;
		}
	}
	protected  String getReportHeader() {
		return "vehicle_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		out.put("speed", "" + actualSpeed);
		out.put("kilometrage", "" + kilometrage);
		out.put("faulty", "" + faulty);
		if (!arrived)
			out.put("location","(" + currentRoad.getID() + roadLocation + ")" );
		else
			out.put("location", "arrived");
	}
}
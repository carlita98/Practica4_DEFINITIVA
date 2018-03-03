package es.ucm.fdi.simulatedObjects;

import java.util.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.ini.SimulatedObject;

public class Vehicle extends SimulatedObject{
	//No estamos muy seguras de si se necesita el primer y el segundo atributo
	private int simulationTime;
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
	public int getroadLocation () {
		return roadLocation;
	}
	public void setFaultyTime (int t) {
		faulty += t;
	}
	public void setActualSpeed (int s) {
		if (s > maxSpeed) actualSpeed = maxSpeed;
		else actualSpeed = s;
	}
	
	public boolean moveForward () {
		if (faulty == 0) {
			roadLocation += actualSpeed;
			if (roadLocation >= actualRoad.getLength()){
				kilometrage += actualRoad.getLength();
				roadLocation = actualRoad.getLength();
				itinerary.get(0).carIntoIR(this);
			}
			else kilometrage += actualSpeed;
			return true;
		}
		else {
			faulty --;
			return false;
		}
	}
	public void moveToNextRoad() {
		if (itinerary.size() > 0) {
		for (Road r: itinerary.get(0).getOutgoingRoadList()) {
			for (Road r2: itinerary.get(1).getIncomingRoadList()) {
				if (r == r2) {
					actualRoad = r;
					itinerary.remove(0);
					break;
				}
			}
		}
		}
		else {
			itinerary.remove(0);
			arrived = true;
		}
	}
	protected  String getReportHeader() {
		
	}
	protected void fillReportDetails (Map <String, String> out) {
		
	}
	/*
	public String generateInform (){
		String report;
		report = "[vehicle_report]"+ "id = " + Id + "time = " + simulationTime + "speed = " 
				+ actualSpeed + "kilometrage = " + kilometrage + "faulty = " + faulty + "location = ";
		if (!arrived) report += "(" + actualRoad.getID() + roadLocation + ")";
		else report += "arrived";
		return report;
	}*/
}
package es.ucm.fdi.model.simulatedObjects;


import java.util.Map;

import es.ucm.fdi.util.*;
public class Road  extends SimulatedObject{

	private int length;
	private int  maxSpeed;
	//La vehicleList está ordenada decrecientemente por la longitud de la carretera
	//Implementar la constructora con comparador (a, b) -> a-b
	private MultiTreeMap <Integer, Vehicle> vehicleList = new MultiTreeMap <>((a,b)->(a-b));
	//Juntion donde termina la carretera

	
	public Road(int t, String i, int ms, int l){
		time = t;
		Id = i;
		maxSpeed = ms;
		length = l;
	}
	
	int getLength () {
		return length;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public MultiTreeMap<Integer, Vehicle> getVehicleList() {
		return vehicleList;
	}
	public void setVehicleList(MultiTreeMap<Integer, Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}
	public void setLength(int length) {
		this.length = length;
	}

	public void pushVehicle(Vehicle v){
		vehicleList.putValue(v.getRoadLocation(), v);
	}
	
	public void popVehicle(Vehicle v){
		vehicleList.removeValue(v.getRoadLocation(), v);
	}
	
	public void moveForward(){
		int baseSpeed;
		int maxVehicles;
		if (vehicleList.sizeOfValues() > 0)
			maxVehicles = vehicleList.sizeOfValues();
		else 
			maxVehicles = 1;
		
		if (maxSpeed > ((maxSpeed/maxVehicles) + 1)) 
			baseSpeed = ((maxSpeed/maxVehicles) + 1);
		else 
			baseSpeed = maxSpeed;

		boolean faultycar = false;
		MultiTreeMap <Integer, Vehicle> updated = new MultiTreeMap <Integer, Vehicle> ((a,b)->(a-b));
		for (Vehicle v: vehicleList.innerValues()){
			if (v.getFaulty () > 0) faultycar= true;
			if (faultycar) v.setActualSpeed(baseSpeed/2);
			else v.setActualSpeed(baseSpeed);
			v.moveForward();
			updated.putValue(v.getRoadLocation(), v);
		}
		vehicleList = updated;
	}
	

	protected  String getReportHeader() {
		return "road_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		String report = "";
		int counter =0;
		for (Vehicle v: vehicleList.innerValues()) {
			if (counter < vehicleList.size() -1)report += "(" + v.getID() + "," + v.getRoadLocation() + ") , ";
			else report += "(" + v.getID() + "," + v.getRoadLocation() + ")";
			counter++;
		}
		out.put("state", report);
	}

}
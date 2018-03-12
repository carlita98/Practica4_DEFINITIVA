package es.ucm.fdi.model.simulatedObjects;


import java.util.Collections;
import java.util.Map;

import es.ucm.fdi.util.*;
public class Road  extends SimulatedObject{

	private int length;
	private int  maxSpeed;
	//La vehicleList está ordenada decrecientemente por la longitud de la carretera
	//Implementar la constructora con comparador (a, b) -> a-b
	private MultiTreeMap <Integer, Vehicle> vehicleList = new MultiTreeMap <>((a,b) -> a-b /*Collections.reverseOrder()*/);
	//Juntion donde termina la carretera

	
	public Road(String id, int maxSpeed, int length){
		super(id);
		this.maxSpeed = maxSpeed;
		this.length = length;
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
		if (!vehicleList.isEmpty())vehicleList.removeValue(v.getRoadLocation(), v);
	}
	
	public void moveForward(){
		int baseSpeed;
		baseSpeed = Math.min (maxSpeed, (maxSpeed/ Math.max (vehicleList.sizeOfValues(), 1) +1));

		boolean faultycar = false;
		boolean thisCar = false;
		MultiTreeMap <Integer, Vehicle> updated = new MultiTreeMap <Integer, Vehicle> ((a,b) -> a-b/*Collections.reverseOrder()*/);
		for (Vehicle v: vehicleList.innerValues()){
			if (v.getFaulty () > 0){
				faultycar = true;
				thisCar = true;
			}
			if (faultycar && !thisCar) 
				v.setActualSpeed(baseSpeed/2);
			else if (!faultycar && !thisCar)
				v.setActualSpeed(baseSpeed);
			
			v.moveForward();
			updated.putValue(v.getRoadLocation(), v);
			thisCar = false;
		}
		vehicleList = updated;
	}
	

	protected  String getReportHeader() {
		return "road_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		String report = "";
		for (Vehicle v: vehicleList.innerValues()) {
			report += "(" + v.getId() + "," + v.getRoadLocation() + "),";
		}
		if (vehicleList.sizeOfValues()!= 0)out.put("state", report.substring(0, report.length()-1));
		else out.put("state", report);
	}

}
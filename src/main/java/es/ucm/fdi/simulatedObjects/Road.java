package es.ucm.fdi.simulatedObjects;


import java.util.Map;

import es.ucm.fdi.ini.SimulatedObject;
import es.ucm.fdi.util.*;
public class Road  extends SimulatedObject{
	//No estamos muy seguras de si se necesita el primer y el segundo atributo
	private int simulationTime;
	private int length;
	private int  maxSpeed;
	//La vehicleList está ordenada decrecientemente por la longitud de la carretera
	//Implementar la constructora con comparador (a, b) -> a-b
	private MultiTreeMap <Integer, Vehicle> vehicleList; 
	//Juntion donde termina la carretera

	int getLength () {
		return length;
	}

	public void pushVehicle(Vehicle v){
		vehicleList.putValue(0, v);
	}
	
	public void popVehicle(Vehicle v){
		vehicleList.remove(0, v);
	}
	
	public void moveForward(){
		int baseSpeed;
		int maxVehicles;
		if (vehicleList.sizeOfValues() > 1)
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
			updated.putValue(v.getroadLocation(), v);
		}
		vehicleList = updated;
	}
	
	protected  String getReportHeader() {
		
	}
	protected void fillReportDetails (Map <String, String> out) {
		
	}
	/*
	public String generateInform(){
		String report;
		report = "[road_report]" + "id = " + Id + "time = " + simulationTime + "state = ";
		for (Vehicle v: vehicleList.innerValues()) {
			report += "(" + v.getID() + "," + v.getroadLocation() + ") , ";
		}
		return report;
	}*/
}
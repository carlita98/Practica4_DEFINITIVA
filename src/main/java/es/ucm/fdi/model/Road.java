package es.ucm.fdi.model;


import es.ucm.fdi.util.*;
public class Road {
	//No estamos muy seguras de si se necesita el primer y el segundo atributo
	private int simulationTime;
	private String roadId;
	private int length;
	private int  maxSpeed;
	//La vehicleList está ordenada decrecientemente por la longitud de la carretera
	//Implementar la constructora con comparador (a, b) -> a-b
	private MultiTreeMap <Integer, Vehicle> vehicleList; 


	int getLength () {
		return length;
	}
	String getID() {
		return roadId;
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
	public String generateInform(){
		String report;
		report = "[road_report]" + "id = " + roadId + "time = " + simulationTime + "state = ";
		for (Vehicle v: vehicleList.innerValues()) {
			report += "(" + v.getvehicleId() + "," + v.getroadLocation() + ") , ";
		}
		return report;
	}
}
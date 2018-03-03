package es.ucm.fdi.model;
import java.util.*;
import java.io.*;

import es.ucm.fdi.util.*;
public class Road {
	private String roadId;
	private int length;
	private int  maxSpeed;
	//La vehicleList está ordenada decrecientemente por la longitud de la carretera
	//Implementar la constructora con comparador (a, b) -> a-b
	private MultiTreeMap <Integer, Vehicle> vehicleList; 
	
	public void pushVehicle(Vehicle v){
		vehicleList.putValue(0, v);
	}
	
	public void popVehicle(Vehicle v){
		vehicleList.remove(0, v);
	}
	
	public void moveForward(){
		long baseSpeed;
		long maxVehicles;
		if (vehicleList.sizeOfValues() > 1)
			maxVehicles = vehicleList.sizeOfValues();
		else 
			maxVehicles = 1;
		
		if (maxSpeed > ((maxSpeed/maxVehicles) + 1)) 
			baseSpeed = ((maxSpeed/maxVehicles) + 1);
		else 
			baseSpeed = maxSpeed;

		for (Vehicle v: vehicleList.innerValues()){
			//MultiTreeMap updated = new MultiTreeMap ((a, b) -> a-b);
		}
	}
	public void generateInform(){
		
		
	}
}
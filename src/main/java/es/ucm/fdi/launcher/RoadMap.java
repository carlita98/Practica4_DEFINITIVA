package es.ucm.fdi.launcher;

import java.util.*;

import es.ucm.fdi.simulatedObjects.*;

public class RoadMap {
	private Map <String, SimulatedObject> simObjects;
	private List <Junction> junctions = new ArrayList<>();
	private List <Road> roads = new ArrayList<>();
	private List <Vehicle> vehicles = new ArrayList <>();
	
	public RoadMap (){
		simObjects = new HashMap <String, SimulatedObject>();
	}
	
	
	public Junction getJunction (String id){
		for (Junction j: junctions){
			if (id == j.getID()){
				return j;
			}
		}
		return null;
		//throw new NoSuchElementException("A junction with that ID does not exist");
	}
	
	public Road getRoad(String road){
		for (Road r: roads){
			if (road == r.getID()){
				return r;
			}
		}
		return null;
		//throw new NoSuchElementException ("A road with that ID does not exist");
	}
	
	public Vehicle getVehicle(String vehicle){
		for (Vehicle v: vehicles){
			if (vehicle == v.getID()){
				return v;
			}
		}
		return null;
		//throw new NoSuchElementException ("A vehicle with that ID does not exist");
	}
	
	public List <Junction> getJunctions (){
		return junctions;
	}
	
	public List <Road> getRoads(){
		return roads;
	}
	
	public List <Vehicle> getVehicles(){
		return vehicles;
	}
	
	void addJunction (Junction j){
		
		junctions.add(j);
		simObjects.put(j.getID(), j);
	}
	
	void addRoad (Road r){
		roads.add(r);
		simObjects.put(r.getID(), r);
	}
	
	void addVehicle (Vehicle v){
		vehicles.add(v);
		simObjects.put(v.getID(), v);
	}
}

package es.ucm.fdi.model.RoadMap;

import java.util.*;

import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.simulatedObjects.SimulatedObject;
import es.ucm.fdi.model.simulatedObjects.Vehicle;

public class RoadMap {
	private Map <String, SimulatedObject> simObjects = new HashMap <String, SimulatedObject>();;
	private List <Junction> junctions = new ArrayList<>();
	private List <Road> roads = new ArrayList<>();
	private List <Vehicle> vehicles = new ArrayList <>();
	
	//Lo utilizaremos en la siguiente práctica
	private List <Junction> junctionsRO = Collections.unmodifiableList(junctions);
	private List <Road> roadsRO = Collections.unmodifiableList(roads);
	private List <Vehicle> vehiclesRO = Collections.unmodifiableList (vehicles);
	
	public Junction getJunction (String id){
		if (simObjects.containsKey (id) && simObjects.get(id) instanceof Junction){
			return (Junction) simObjects.get(id);
		}
		else throw new NoSuchElementException("A junction with that ID does not exist");
	}
	
	public Road getRoad(String id){
		if (simObjects.containsKey (id) && simObjects.get(id) instanceof Road){
			return (Road) simObjects.get(id);
		}
		else throw new NoSuchElementException ("A road with that ID does not exist");
	}
	
	public Vehicle getVehicle(String id){
		if (simObjects.containsKey (id) && simObjects.get(id) instanceof Vehicle){
			return (Vehicle) simObjects.get(id);
		}
		throw new NoSuchElementException ("A vehicle with that ID does not exist");
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
	public Map <String, SimulatedObject> getMap (){
		return simObjects;
	}
	public void addJunction (Junction j){
		if (simObjects.containsKey (j.getId())){
			throw new IllegalArgumentException ("This ID already exist");
		}
		else{
			junctions.add(j);
			simObjects.put(j.getId(), j);
		}
	}
	
	public void addRoad (Road r){
		if (simObjects.containsKey (r.getId())){
			throw new IllegalArgumentException ("This ID already exist");
		}
		else{
		roads.add(r);
		simObjects.put(r.getId(), r);
		}
	}
	
	public void addVehicle (Vehicle v){
		if (simObjects.containsKey (v.getId())){
			throw new IllegalArgumentException ("This ID already exist");
		}
		else{
		vehicles.add(v);
		simObjects.put(v.getId(), v);
		}
	}
}

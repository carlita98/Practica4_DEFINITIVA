package es.ucm.fdi.model.trafficSimulator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.events.*;
import es.ucm.fdi.model.simulatedObjects.*;
import es.ucm.fdi.util.MultiTreeMap;
/**
 * The main class, it controls the performance of the TrafficSimulator
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Simulator {
	//Ordenada por tiempo
	private MultiTreeMap <Integer, Event> eventList = new MultiTreeMap<> ();
	private int simulatorTime;
	private RoadMap m = new RoadMap ();
	/**
	 * 
	 * Constructor
	 */
	public Simulator (){
		simulatorTime = 0;
	}
	/**
	 * Inserts a new Event into the eventList
	 * @param e
	 */
	public void insertEvent(Event e){
		if(e.getTime() >= simulatorTime){
			eventList.putValue(e.getTime(), e);
		}
	}
	/**
	 * Execute the simulation and threats the Exception
	 * @param simulatorSteps
	 * @param file
	 */
	public void execute(int simulatorSteps, OutputStream file){
		int timeLimit = simulatorTime + simulatorSteps - 1;
		try{
			while(simulatorTime <= timeLimit){
				actualTimeExecute();
				moveForward();
				simulatorTime++;
				generateInform(file);
			}
		}catch(SimulatorException e){
			Exception c = e;
			System.out.println(c.getMessage() + ".It happened at time: " + timeLimit + ".");
			while (c != null) {
				c = (Exception) c.getCause();
				if (c != null)
					System.out.println("Caused by: " + c.getMessage() + ".");	
			}
		}
	}
	/**
	 * Execute the corresponding events to that time
	 * @throws SimulatorException
	 */
	public void actualTimeExecute() throws SimulatorException{
		if (eventList.containsKey(simulatorTime)) {
			for(Event e: eventList.get(simulatorTime)){
				e.execute(m);
			}
		}
	}	
	/**
	 * Call moveForward method for roads and junctions into the RoadMap
	 */
	public void moveForward(){
		for(Road r: m.getRoads()){
			r.moveForward();
		}
		for(Junction j: m.getJunctions()){
			j.moveForward();
		}
	}
	/**
	 * Changes the Map <String, String> from report method to an IniSection
	 * @param map
	 * @return IniSection
	 */
	public IniSection changeToIni (LinkedHashMap<String, String> map){
		IniSection s = new IniSection (map.get(""));
		int counter =0;
		for (String key : map.keySet()){
			if (counter > 0) s.setValue (key, map.get(key));
			counter ++;
		}
		return s;
	}
	/**
	 * Generate the inform using an IniSection, first generate the Junctions reports, then the Roads and  finally
	 * Vehicles
	 * @param file
	 */
	public void generateInform(OutputStream file) {
		try{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		for(SimulatedObject j : m.getJunctions()){
			j.report(map);
			map.put("time", "" +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(SimulatedObject r: m.getRoads()){
			r.report(map);
			map.put("time", "" +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(SimulatedObject v: m.getVehicles()){
			v.report(map);
			map.put("time", "" +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}


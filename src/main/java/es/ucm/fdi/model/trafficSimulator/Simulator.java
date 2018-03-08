package es.ucm.fdi.model.trafficSimulator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.events.*;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.simulatedObjects.Vehicle;
import es.ucm.fdi.simulatedObjects.*;
import es.ucm.fdi.util.MultiTreeMap;

public class Simulator {
	//Ordenada por tiempo
	private MultiTreeMap <Integer, Event> eventList;
	private int simulatorTime;
	private RoadMap m;

	public void insertEvent(Event e){
		if(e.getTime() > simulatorTime){
		eventList.putValue(e.getTime(), e);
		}
	}
	
	public void execute(int simulatorSteps, OutputStream file){
		int timeLimit = simulatorTime + simulatorSteps - 1;
		while(simulatorTime <= timeLimit){
			actualTimeExecute();
			moveForward();
			simulatorTime++;
			generateInform(file);
		}
	}
	
	
	public void actualTimeExecute(){
		for(Event e: eventList.get(simulatorTime)){
			e.execute(m);
		}
	}	
	
	public void moveForward(){
		for(Road r: m.getRoads()){
			r.moveForward();
		}
		for(Junction j: m.getJunctions()){
			j.moveForward();
		}
	}
	
	public IniSection changeToIni (TreeMap<String, String> map){
		IniSection s = new IniSection (map.get(""));
		for (String key : map.keySet()){
			s.setValue (key, map.get(key));
		}
		return s;
	}
	public void generateInform(OutputStream file) {
		try{
		TreeMap<String, String> map = new TreeMap<String, String>();
		
			for(Junction j: m.getJunctions()){
			j.report(map);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(Road r: m.getRoads()){
			r.report(map);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(Vehicle v: m.getVehicles()){
			v.report(map);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}


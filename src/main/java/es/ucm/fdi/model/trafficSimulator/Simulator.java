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
import es.ucm.fdi.util.MultiTreeMap;

public class Simulator {
	//Ordenada por tiempo
	private MultiTreeMap <Integer, Event> eventList = new MultiTreeMap<> ();
	private int simulatorTime;
	private RoadMap m = new RoadMap ();

	public Simulator (){
		simulatorTime = 0;
	}
	public void insertEvent(Event e){
		if(e.getTime() >= simulatorTime){
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
		if (eventList.containsKey(simulatorTime)) {
			for(Event e: eventList.get(simulatorTime)){
				e.execute(m);
			}
		}
	}	
	
	public void moveForward(){
		for(Road r: m.getRoads()){
			r.moveForward();
		}
		int contador = 0;
		for(Junction j: m.getJunctions()){
			if (contador > 0)j.moveForward();
			contador ++;
		}
	}
	
	public IniSection changeToIni (LinkedHashMap<String, String> map){
		IniSection s = new IniSection (map.get(""));
		int counter =0;
		for (String key : map.keySet()){
			if (counter > 0) s.setValue (key, map.get(key));
			counter ++;
		}
		return s;
	}
	public void generateInform(OutputStream file) {
		try{
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
			for(Junction j: m.getJunctions()){
			j.report(map);
			map.put("time", " " +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(Road r: m.getRoads()){
			r.report(map);
			map.put("time", " " +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		for(Vehicle v: m.getVehicles()){
			v.report(map);
			map.put("time", " " +simulatorTime);
			if (file != null)changeToIni (map).store(file);
			map.clear();
		}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}


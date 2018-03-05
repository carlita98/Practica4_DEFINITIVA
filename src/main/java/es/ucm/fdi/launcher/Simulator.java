package es.ucm.fdi.launcher;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import es.ucm.fdi.events.*;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simulatedObjects.*;

public class Simulator {
	//Ordenada por tiempo
	private TreeMap <Integer, Event> eventList;

	int simulatorTime;
	
	void insertVehicle (Vehicle v) {
		vehicleList.add(v);
	}
	void insertRoad(Road r) {
		roadList.add(r);
	}
	void insertJunction (Junction j) {
		junctionList.add(j);
	}
	void insertEvent(Event e) {
		if (e.getTime() >= simulatorTime)
			eventList.put(e.getTime(), e);
	}
	void writeReport(Map<String, String> report, OutputStream file) {
		if (file != null) {
			Iterator <Map.Entry <String, String>> it = report.entrySet().iterator();
			IniSection sec = new IniSection (it.next().getValue());
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				sec.setValue(entry.getKey(), entry.getValue());
			}
			try {
				sec.store(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void ejecuta (int simulationSteps, OutputStream file) {
		int LimitTime = simulatorTime + simulationSteps -1;
		while (simulatorTime <= LimitTime) {
			Iterator <Map.Entry <Integer, Event>> it = eventList.entrySet().iterator();
			while (it.hasNext()) {
				if (it.next().getValue().getTime() == simulatorTime) {
					//it.next().getValue().execute();
				}
			}
			for (Vehicle v: vehicleList) {
				v.moveForward();
			}
			for (Junction j: junctionList) {
				j.moveForward();
			}
			
			simulatorTime++;
		}	
		
		for (Junction j: junctionList) {
			TreeMap <String, String> m = new TreeMap <String, String>();
			j.report(m);
			writeReport(m,file);
		}
		
		for (Road r: roadList) {
			TreeMap <String, String> m = new TreeMap <String, String>();
			r.report(m);
			writeReport(m,file);
		}

		for (Vehicle v: vehicleList) {
			TreeMap <String, String> m = new TreeMap <String, String>();
			v.report(m);
			writeReport(m,file);
		}
		
		
	}
}

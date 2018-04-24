package es.ucm.fdi.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.model.object.Junction;
import es.ucm.fdi.model.object.Road;
import es.ucm.fdi.model.object.SimulatedObject;
import es.ucm.fdi.util.MultiTreeMap;

/**
 * The main class, it controls the performance of the TrafficSimulator
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class Simulator {
	
	private MultiTreeMap<Integer, Event> eventList = new MultiTreeMap<>();
	private int simulatorTime;
	private RoadMap roadMap = new RoadMap();
	private List<Listener> listeners = new ArrayList<>();

	public List<Listener> getListeners() {
		return listeners;
	}

	public MultiTreeMap<Integer, Event> getEventList() {
		return eventList;
	}

	public RoadMap getRoadMap() {
		return roadMap;
	}

	public int getSimulatorTime() {
		return simulatorTime;
	}
	
	public void setSimulatorTime(int i) {
		simulatorTime = i;
	}
	
	public void setEventList(MultiTreeMap<Integer, Event> l) {
		eventList = l;
	}
	
	/**
	 * 
	 * Constructor
	 */
	public Simulator() {
		simulatorTime = 0;
	}

	/**
	 * Inserts a new Event into the eventList
	 * 
	 * @param e
	 */
	public void insertEvent(Event e) {
		if (e.getTime() >= simulatorTime) {
			eventList.putValue(e.getTime(), e);
			fireUpdateEvent(EventType.NEW_EVENT, null);
		}
	}

	/**
	 * Execute the simulation and threats the Exception
	 * 
	 * @param simulatorSteps
	 * @param file
	 */
	public void execute(int simulatorSteps, OutputStream file) {
		int timeLimit = simulatorTime + simulatorSteps - 1;
		
		try {
			while (simulatorTime <= timeLimit) {
				actualTimeExecute();
				moveForward();
				simulatorTime++;
				generateReport(file,roadMap.getJunctions());
				generateReport(file,roadMap.getRoads());
				generateReport(file,roadMap.getVehicles());
				fireUpdateEvent(EventType.ADVANCED, null);
			}
			} catch (SimulatorException e) {
				
			Exception c = e;
			System.out.println(c.getMessage() + ".It happened at time: " + timeLimit + ".");
			
			while (c != null) {
				c = (Exception) c.getCause();
				if (c != null){
					System.out.println("Caused by: " + c.getMessage() + ".");
				}
			}
			}
	}

	/**
	 * Execute the corresponding events to that time
	 * 
	 * @throws SimulatorException
	 */
	public void actualTimeExecute() throws SimulatorException {
		if (eventList.containsKey(simulatorTime)) {
			for (Event e : eventList.get(simulatorTime)) {
				e.execute(roadMap);
			}
		}
	}

	/**
	 * Call moveForward method for roads and junctions into the RoadMap
	 */
	public void moveForward() {
		
		for (Road r : roadMap.getRoads()) {
			r.moveForward();
		}
		
		for (Junction j : roadMap.getJunctions()) {
			j.moveForward();
		}
	}

	/**
	 * Changes the Map <String, String> from report method to an IniSection
	 * 
	 * @param map
	 * @return IniSection
	 */
	public IniSection changeToIni(LinkedHashMap<String, String> map) {
		IniSection s = new IniSection(map.get(""));
		for (String key : map.keySet()) {
			if ( ! key.isEmpty()) {
				s.setValue(key, map.get(key));
			}
		}
		return s;
	}

	/**
	 * Generate the inform using an IniSection, first generate the Junctions
	 * reports, then the Roads and finally Vehicles
	 * 
	 * @param output
	 */
	public void generateReport(OutputStream output, List <? extends SimulatedObject> l) {
		try {
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			
			Ini ini = new Ini();
			for (SimulatedObject j : l) {
				j.report(map);
				map.put("time", "" + simulatorTime);
				
				if (output != null) {
					ini.addsection(changeToIni(map));
				}
				
				map.clear();
			}
	ini.store(output);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Reset the simulator
	 */
	public void reset() {
		simulatorTime = 0;
		roadMap = new RoadMap();
		eventList = new MultiTreeMap<>();
		fireUpdateEvent(EventType.RESET, null);
	}

	/**
	 * Interface implemented by the listeners
	 *
	 */
	public interface Listener {
		void registered(UpdateEvent ue);

		void reset(UpdateEvent ue);

		void newEvent(UpdateEvent ue);

		void advanced(UpdateEvent ue);

		void error(UpdateEvent ue, String error);
	}

	/**
	 * Different type of listeners
	 *
	 */
	public enum EventType {
		REGISTERED, RESET, NEW_EVENT, ADVANCED;
	}
	
	/**
	 * Provides the listeners the necessary information of the simulator
	 *
	 */
	public class UpdateEvent {
		EventType type;

		public UpdateEvent(EventType type) {
			super();
			this.type = type;
		}

		public EventType getEvent() {
			return type;
		}

		public RoadMap getRoadMap() {
			return roadMap;
		}

		public List<Event> getEventQueue() {
			return eventList.valuesList();
		}

		public int getCurrentTime() {
			return simulatorTime;
		}
	}

	/**
	 * Adds the listener to the simulator list
	 * @param l
	 */
	public void addSimulatorListener(Listener l) {
		listeners.add(l);
		UpdateEvent ue = new UpdateEvent(EventType.REGISTERED);
		SwingUtilities.invokeLater(() -> l.registered(ue));
	}
	
	/**
	 * Removes the listener from the simulator list
	 * @param l
	 */
	public void removeListener(Listener l) {
		listeners.remove(l);
	}
	
	/**
	 * Calls the necessary methods from the listeners
	 * @param type
	 * @param error
	 */
	private void fireUpdateEvent(EventType type, String error) {
		
		UpdateEvent ue = new UpdateEvent(type);
		
		if (type == EventType.RESET) {
			
			for (Listener e : listeners) {
				SwingUtilities.invokeLater(() -> e.reset(ue));
			}
			
		} else if (type == EventType.NEW_EVENT) {
			
			for (Listener e : listeners) {
				SwingUtilities.invokeLater(() -> e.newEvent(ue));
			}
			
		} else if (type == EventType.ADVANCED) {
			
			for (Listener e : listeners) {
				SwingUtilities.invokeLater(() -> e.advanced(ue));
			}
		}
	}
	
}

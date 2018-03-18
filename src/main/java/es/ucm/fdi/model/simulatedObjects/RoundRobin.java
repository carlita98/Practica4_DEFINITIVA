package es.ucm.fdi.model.simulatedObjects;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import es.ucm.fdi.model.simulatedObjects.Junction.IR;
import es.ucm.fdi.model.simulatedObjects.MostCrowed.IrTime;
/**
 * All the necessary methods for the Junction type RoundRobin
 * @author Carla Martínez
 *
 */
public class RoundRobin extends Junction{
	private int maxTimeSlice;
	private int minTimeSlice;
	private String type;
	private Map <Road, IrTime> incomingQueues = new LinkedHashMap<>();     
	public RoundRobin(String id, int maxTimeSlice, int minTimeSlice, String type) {
		super(id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
		this.type = type;
	}

	public class IrTime extends IR{
		int timeInterval = maxTimeSlice;
		int timeUnits;
		int numCar;
	}
	/**
	 * Get the Road with the green traffic light
	 * @author Carla Martínez
	 *
	 */
	public IrTime currentIR() {
		//Devuelve la IR de la carretera que tiene el semáforo en verde
		return incomingQueues.get(incomingRoadList.get(currentIncoming));
	}
	public void addInRoadQueue(Road r) {
		incomingQueues.put(r, new IrTime());
	}
	/**
	 * Choose the next road with green traffic light
	 */
	public void updateLights () {
		IrTime ir = currentIR();
		if (ir.timeInterval == ir.timeUnits) {
			if (ir.numCar == ir.timeUnits) 
				ir.timeInterval = Math.min (ir.timeInterval + 1,maxTimeSlice );
			else if (ir.numCar == 0)
				ir.timeInterval = Math.max (ir.timeInterval - 1, minTimeSlice);
			ir.timeUnits = 0;
			try{
				currentIncoming = (currentIncoming + 1) % incomingRoadList.size();
			}catch (ArithmeticException e){
				currentIncoming = 0;
			}
		}
	}
	/**
	 * Move the first car in the queue if the traffic light is green
	 */
	
	public void moveForward () {
		
		
		//Move first car in the queue
		if (!incomingRoadList.isEmpty() && !incomingQueues.get(incomingRoadList.get(currentIncoming)).queue.isEmpty()) {
			IrTime ir = currentIR();
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0){
				v.moveToNextRoad();
				ir.queue.removeFirst();
				ir.timeUnits++;
				ir.numCar++;
			}
			else {
				v.setFaultyTime(v.getFaulty()-1);
				ir.timeUnits++;
			}
		}
		//Update lights
			updateLights();

	}
	/**
	 * Fill a Map with the Junction MostCrowed data
	 */
	protected void fillReportDetails (Map <String, String> out) {

		String report = "";
		for (Entry<Road, IrTime> entry: incomingQueues.entrySet()){
			report += "(" + entry.getKey().getId() + "," ;
			if(entry.getKey().equals(incomingRoadList.get(currentIncoming ))) {
				int dif = (currentIR().timeInterval - currentIR().timeUnits);
				report += "green:" + dif + ",";
			}
			else report += "red,";
			report += "[";
			int counter = 0;
			for (Vehicle v: entry.getValue().queue) {
				if(counter != entry.getValue().queue.size() -1)report += v.getId() + ",";
				else report += v.getId();
				counter ++;
			}
	
			report += "]),";
		}
		if (!incomingRoadList.isEmpty())out.put("queues", report.substring(0, report.length()-1));
		else out.put("queues", report);
		out.put("type", type);
	}

}

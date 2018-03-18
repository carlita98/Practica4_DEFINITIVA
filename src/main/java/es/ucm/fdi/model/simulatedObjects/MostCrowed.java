package es.ucm.fdi.model.simulatedObjects;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.ucm.fdi.model.simulatedObjects.Junction.IR;
/**
 *  All the necessary methods for the Junction type MostCrowed
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class MostCrowed extends Junction{
	private String type;
	private Map <Road, IrTime> incomingQueues = new LinkedHashMap<>();         
	public MostCrowed(String id, String type) {
		super(id);
		this.type = type;
	}
	
	public class IrTime extends IR{
		int timeInterval;
		int timeUnits;
	}
	
	public IrTime currentIR() {
		//Devuelve la IR de la carretera que tiene el semáforo en verde
		return incomingQueues.get(incomingRoadList.get(currentIncoming));
	}
	public void addInRoadQueue(Road r) {
		incomingQueues.put(r, new IrTime());
	}
	public void addIncoming(Road r) {
		int n = this.getIncomingRoadList().size(); 
		getIncomingRoadList().add(n, r);
		currentIncoming = incomingQueues.size();
	}
	public void addOutcoming(Road r) {
		int n = this.getOutgoingRoadList().size(); 
		getOutgoingRoadList().add(n, r);
	}
	/**
	 * Choose the next road with green traffic light
	 */
	public void updateLights () {
		IrTime ir = currentIR();
		if (ir.timeInterval == ir.timeUnits) {
			ir.timeUnits = 0;
			int max = -1;
			int BefCurrentIncoming = currentIncoming;
			//We know there is a problem in this method but we cant find it
			for (Entry<Road, IrTime> entry: incomingQueues.entrySet()){
				if (max < entry.getValue().queue.size() &&  incomingRoadList.indexOf(entry.getKey()) != BefCurrentIncoming) {
					max = entry.getValue().queue.size();
					currentIncoming = incomingRoadList.indexOf(entry.getKey());
				}
			}
			IrTime irUpdate = currentIR();
			irUpdate.timeInterval = Math.max(max/2, 1);
		}
	}
	public void moveForward () {

		//Move first car in the queue
		if (!incomingRoadList.isEmpty() && !incomingQueues.get(incomingRoadList.get(currentIncoming)).queue.isEmpty()) {
			IrTime ir = currentIR();
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0){
				v.moveToNextRoad();
				ir.queue.removeFirst();
				ir.timeUnits++;
			}
			else {
				ir.timeUnits++;
				v.setFaultyTime(v.getFaulty()-1);
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

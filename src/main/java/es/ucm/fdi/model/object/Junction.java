package es.ucm.fdi.model.object;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.view.Describable;
/**
 * All the necessary methods for the Junction
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class Junction  extends SimulatedObject implements Describable{
	
	public class IR{
		protected ArrayDeque<Vehicle> queue = new ArrayDeque<>();
	}
	
	protected  int currentIncoming;
	private Map <Road, IR> incomingQueues = new LinkedHashMap<>();
	protected List <Road> incomingRoadList = new ArrayList<>();
	protected  List <Road> outgoingRoadList = new ArrayList<>();
	/**
	 * Constructor
	 * @param id
	 */
	public Junction(String id){
		super(id);
		this.currentIncoming = incomingQueues.size();
	}
	/**
	 * Get the Road with the green traffic light
	 * @return
	 */
	public IR currentIR() {
		//Devuelve la IR de la carretera que tiene el semáforo en verde
		return incomingQueues.get(incomingRoadList.get(currentIncoming));
	}
	
	public void addOutcoming(Road r) {
		int n = this.getOutgoingRoadList().size(); 
		getOutgoingRoadList().add(n, r);
	}
	public void addIncoming(Road r) {
		int n = this.getIncomingRoadList().size(); 
		getIncomingRoadList().add(n, r);
		currentIncoming = incomingQueues.size();
	}
	public void addInRoadQueue(Road r) {
		incomingQueues.put(r, new IR());
	}
	public Map<Road, IR> getRoadQueue() {
		return incomingQueues;
	}
	public void setRoadQueue(Map<Road, IR> roadQueue) {
		incomingQueues = roadQueue;
	}
	public List<Road> getIncomingRoadList() {
		return incomingRoadList;
	}
	public void setIncomingRoadList(List<Road> incomingRoadList) {
		this.incomingRoadList = incomingRoadList;
	}
	public List<Road> getOutgoingRoadList() {
		return outgoingRoadList;
	}
	public void setOutgoingRoadList(List<Road> outgoingRoadList) {
		this.outgoingRoadList = outgoingRoadList;
	}
	/**
	 * 
	 * @param v
	 */
	public void carIntoIR(Vehicle v) {
		if (!incomingQueues.get(v.getActualRoad()).queue.contains(v))
			incomingQueues.get(v.getActualRoad()).queue.addLast(v);
	}
	/**
	 * Choose the next road with green traffic light
	 */

	public void updateLights () {
		try{
			currentIncoming = (currentIncoming + 1) % incomingRoadList.size();
		}catch (ArithmeticException e){
			currentIncoming = 0;
		}
	}
	/**
	 * Move the first car in the queue if the traffic light is green
	 */
	
	public void moveForward () {

		//Move first car in the queue
		if (!incomingRoadList.isEmpty() && !incomingQueues.get(incomingRoadList.get(currentIncoming)).queue.isEmpty()) {
			IR ir = currentIR();
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0){
				v.moveToNextRoad();
				ir.queue.removeFirst();
			}
			else {
				v.setFaultyTime(v.getFaulty()-1);
			}
		}
		//Update lights
		updateLights();
		
	}
	/**
	 * Returns Junction IniSection header
	 */
	protected  String getReportHeader() {
		return "junction_report";
	}
	/**
	 * Fill a Map with the Junction data
	 */
	protected void fillReportDetails (Map <String, String> out) {
		
		String report = "";
		for (Map.Entry <Road , IR> entry: incomingQueues.entrySet()){
			report += "(" + entry.getKey().getId() + "," ;
			if(entry.getKey().equals(incomingRoadList.get(currentIncoming))) report += "green,";
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
	}
	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		/**
		 * Faltaaaaaaa!!!!!!
		 */
	}

}
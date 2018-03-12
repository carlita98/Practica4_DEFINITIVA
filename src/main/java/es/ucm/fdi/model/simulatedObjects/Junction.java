package es.ucm.fdi.model.simulatedObjects;
import java.util.*;

public class Junction  extends SimulatedObject{
	
	public class IR{
		protected ArrayDeque<Vehicle> queue = new ArrayDeque<>();
	}
	
	private int currentIncoming;
	private Map <Road, IR> incomingQueues = new LinkedHashMap<>();
	private List <Road> incomingRoadList = new ArrayList<>();
	private List <Road> outgoingRoadList = new ArrayList<>();

	public Junction(String id){
		super(id);		
		this.currentIncoming = 0;	
	}
	private IR currentIR() {
		//Devuelve la IR de la carretera que tiene el semáforo en verde
		return incomingQueues.get(incomingRoadList.get(currentIncoming));
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

	public void carIntoIR(Vehicle v) {
		if (!incomingQueues.get(v.getActualRoad()).queue.contains(v))
			incomingQueues.get(v.getActualRoad()).queue.addLast(v);
	}
	
	
	
	public void moveForward () {
		// advance to next
		try{
			currentIncoming = (currentIncoming + 1) % incomingRoadList.size();
		}catch (ArithmeticException e){
			currentIncoming = 0;
		}
		// move car
		if (!incomingRoadList.isEmpty() && !incomingQueues.get(incomingRoadList.get(currentIncoming)).queue.isEmpty()) {
			IR ir = currentIR();
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0){
				v.moveToNextRoad();
				ir.queue.removeFirst();
			}
			else v.setFaultyTime(v.getFaulty()-1);
		}
		

	}
	
	protected  String getReportHeader() {
		return "junction_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		
		String report = "";
		for (Map.Entry <Road , IR> entry: incomingQueues.entrySet()){
			report += "(" + entry.getKey().getId() + "," ;
			if(entry.getKey().equals(incomingRoadList.get((currentIncoming + 1) % incomingRoadList.size()))) report += "green,";
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

}
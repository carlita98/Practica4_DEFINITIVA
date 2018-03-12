package es.ucm.fdi.model.simulatedObjects;
import java.util.*;

public class Junction  extends SimulatedObject{
	
	public class IR{
		protected boolean isGreen;
		protected ArrayDeque<Vehicle> queue = new ArrayDeque<>();
		
		public IR() {
			isGreen = true;
		}
	}
	
	private int currentIncoming;
	private Map <Road, IR> incomingQueues = new LinkedHashMap<>();
	private List <Road> IncomingRoadList = new ArrayList<>();
	private List <Road> OutgoingRoadList = new ArrayList<>();

	
	private IR currentIR() {
		return incomingQueues.get(IncomingRoadList.get(currentIncoming));
	}
	
	public Map<Road, IR> getRoadQueue() {
		return incomingQueues;
	}
	public void setRoadQueue(Map<Road, IR> roadQueue) {
		incomingQueues = roadQueue;
	}
	public List<Road> getIncomingRoadList() {
		return IncomingRoadList;
	}
	public void setIncomingRoadList(List<Road> incomingRoadList) {
		IncomingRoadList = incomingRoadList;
	}
	public List<Road> getOutgoingRoadList() {
		return OutgoingRoadList;
	}
	public void setOutgoingRoadList(List<Road> outgoingRoadList) {
		OutgoingRoadList = outgoingRoadList;
	}

	public void carIntoIR(Vehicle v) {
		incomingQueues.get(v.getActualRoad()).queue.addLast(v);
	}
	
	public Junction(String id){
		super(id);		
		currentIncoming = 0;	
	}
	
	public void moveForward () {
		IR ir = currentIR();

		// move car
		if (!ir.queue.isEmpty()) {
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0){
				v.moveToNextRoad();
				ir.queue.removeFirst();
			}
			else v.setFaultyTime(v.getFaulty()-1);
		}
		// advance to next
		ir.isGreen = false;
		currentIncoming = (currentIncoming + 1) % IncomingRoadList.size();
		incomingQueues.get(IncomingRoadList.get(currentIncoming)).isGreen = true;
	}
	
	protected  String getReportHeader() {
		return "junction_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		
		String report = "";
		for (Map.Entry <Road , IR> entry: incomingQueues.entrySet()){
			report += "(" + entry.getKey().getId() + "," ;
			if(entry.getValue().isGreen) report += "green,";
			else report += "red,";
			report += "[";
			int counter = 0;
			for (Vehicle v: entry.getValue().queue) {
				if(counter != entry.getValue().queue.size() -1)report += v.getId() + ",";
				else report += v.getId();
				counter ++;
			}
	
			report += "])";
		}
		out.put("queues", report);
	}

}
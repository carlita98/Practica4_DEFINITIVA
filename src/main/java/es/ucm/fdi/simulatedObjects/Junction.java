package es.ucm.fdi.simulatedObjects;
import java.util.*;

public class Junction  extends SimulatedObject{
	
	public class IR{
		protected boolean isGreen;
		protected ArrayDeque<Vehicle> queue = new ArrayDeque<>();
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
		incomingQueues.get(v.getActualRoad()).queue.add(v);
	}
	
	public Junction(int t, String i){
		time = t;
		Id = i;
		
		currentIncoming = 0;	
	}
	
	public void moveForward () {
		IR ir = currentIR();

		// move car
		Vehicle v = ir.queue.peek();
		v.moveForward();
		if (v.getFaulty() == 0){
			v.moveToNextRoad();
			ir.queue.remove (0);
		}
		
		// advance to next
		ir.isGreen = false;
		currentIncoming = (currentIncoming + 1) % IncomingRoadList.size();
	}
	
	protected  String getReportHeader() {
		return "junction_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		
		String report = "";
		for (Map.Entry <Road , IR> entry: incomingQueues.entrySet()){
			report += "(" + entry.getKey().getID() + "," ;
			if(entry.getValue().isGreen) report += "green,";
			else report += "red,";
			report += "[";
			for (Vehicle v: entry.getValue().queue) {
				report += v.getID() + ",";
			}
			report += "]";
		}
		out.put("queues", report);
	}

}
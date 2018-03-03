package es.ucm.fdi.model;
import java.util.*;

public class Junction {
	private int simulationTime;
	private String junctionId;
	public class IR{
		boolean light;
		ArrayList <Vehicle> vehicleListIR;
		IR (){
			light = false;
			vehicleListIR = new ArrayList <Vehicle>();
		}
		IR(boolean b, ArrayList<Vehicle> vl) {
			// TODO Auto-generated method stub
			light = b;
			vehicleListIR = vl;
		}
	}
	private Map <Road, IR> RoadQueue;
	private List <Road> IncomingRoadList;
	private List <Road> OutgoingRoadList;
	
	public List <Road> getIncomingRoadList(){
		return IncomingRoadList;
	}
	public List <Road> getOutgoingRoadList(){
		return OutgoingRoadList;
	}
	public void carIntoIR(Vehicle v) {
		RoadQueue.get(v.getActualRoad()).vehicleListIR.add(v);
	}
	
	public void moveForward () {
		Iterator <Map.Entry<Road, IR>> it = RoadQueue.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Road, IR> entry = it.next();
			if(entry.getValue().light) {
				Map.Entry<Road, IR> aux = it.next();
				aux.setValue(new IR(true, aux.getValue().vehicleListIR));
				if (entry.getValue().vehicleListIR.get(0).moveForward()) {
					entry.getValue().vehicleListIR.get(0).moveToNextRoad();
					entry.getValue().vehicleListIR.remove(0);
				}
				entry.setValue(new IR(false, entry.getValue().vehicleListIR));
				break;
			}
		}
	}
	

	public String generateInform() {
		String report;
		report = "[junction_report]" + "id = " + junctionId + "time = " + simulationTime + "queues = ";
		Iterator <Map.Entry<Road, IR>> it = RoadQueue.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Road, IR> entry = it.next();
			report += "(" + entry.getKey().getID() + "," ;
			if(entry.getValue().light) report += "green,";
			else report += "red,";
			report += "[";
			for (Vehicle v: entry.getValue().vehicleListIR) {
				report += v.getvehicleId() + ",";
			}
			report += "]";
		}
		return report;
	}
}
package es.ucm.fdi.simulatedObjects;
import java.util.*;

public class Junction  extends SimulatedObject{
	
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

	public Map<Road, IR> getRoadQueue() {
		return RoadQueue;
	}
	public void setRoadQueue(Map<Road, IR> roadQueue) {
		RoadQueue = roadQueue;
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
		RoadQueue.get(v.getActualRoad()).vehicleListIR.add(v);
	}
	
	public void moveForward () {
		Iterator <Map.Entry<Road, IR>> it = RoadQueue.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Road, IR> entry = it.next();
			if(entry.getValue().light) {
				Map.Entry<Road, IR> aux = it.next();
				aux.setValue(new IR(true, aux.getValue().vehicleListIR));
					entry.getValue().vehicleListIR.get(0).moveForward();
					//Si no está averiado
					if (entry.getValue().vehicleListIR.get(0).getFaulty() == 0) {
					entry.getValue().vehicleListIR.get(0).moveToNextRoad();
					entry.getValue().vehicleListIR.remove(0);
					}
					//Poner el semáforo en rojo de la carretera 
				entry.setValue(new IR(false, entry.getValue().vehicleListIR));
				break;
			}
		}
	}
	
	protected  String getReportHeader() {
		return "junction_report";
	}
	protected void fillReportDetails (Map <String, String> out) {
		String report = "";
		Iterator <Map.Entry<Road, IR>> it = RoadQueue.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Road, IR> entry = it.next();
			report += "(" + entry.getKey().getID() + "," ;
			if(entry.getValue().light) report += "green,";
			else report += "red,";
			report += "[";
			for (Vehicle v: entry.getValue().vehicleListIR) {
				report += v.getID() + ",";
			}
			report += "]";
		}
		out.put("queues", report);
	}

}
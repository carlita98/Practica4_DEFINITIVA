package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;

public class NewRoadEvent extends Event{
	private String id;
	private String idJunctionIni;
	private String idJunctionDest;
	private int maxSpeed;
	private int length;
	public NewRoadEvent(int t, String i, String idJI, String idJD, int mS, int l) {
		time = t;
		id = i;
		idJunctionIni = idJI;
		idJunctionDest = idJD;
		maxSpeed = mS;
		length = l;
	}
	public void execute(RoadMap m) {
		try{
			Road r = new Road(time, id, maxSpeed, length);
			m.addRoad(r);
			m.getJunction(idJunctionIni).getOutgoingRoadList().add(r);
			m.getJunction(idJunctionDest).getIncomingRoadList().add(r);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}
}

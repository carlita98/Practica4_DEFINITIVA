package es.ucm.fdi.model.events;

import java.util.ArrayDeque;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.simulatedObjects.Junction.IR;
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
			Road r = new Road(id, maxSpeed, length);
			m.addRoad(r);
			int n = m.getJunction(idJunctionIni).getOutgoingRoadList().size(); 
			m.getJunction(idJunctionIni).getOutgoingRoadList().add(n, r);
			
			n = m.getJunction(idJunctionDest).getIncomingRoadList().size(); 
			m.getJunction(idJunctionDest).getIncomingRoadList().add(n, r);
			
			m.getJunction(idJunctionDest).getRoadQueue().put(r, m.getJunction(idJunctionDest).new IR());		
			}catch(IllegalArgumentException e){
			e.printStackTrace();
		}
	}
}

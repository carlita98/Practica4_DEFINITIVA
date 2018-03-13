package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;

public class NewRoadEvent extends Event{
	protected String id;
	protected String idJunctionIni;
	protected String idJunctionDest;
	protected int maxSpeed;
	protected int length;
	public NewRoadEvent(int time, String id, String idJunctionIni, String idJunctionDest, int maxSpeed, int length) {
		super(time);
		this.id = id;
		this.idJunctionIni = idJunctionIni;
		this.idJunctionDest = idJunctionDest;
		this.maxSpeed = maxSpeed;
		this.length = length;
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
				throw new IllegalArgumentException("While adding Road ", e);
		}
	}
}

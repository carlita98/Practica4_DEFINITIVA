package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.simulatedObjects.HighWay;

public class NewHighWayEvent extends NewRoadEvent {
	private String type;
	private int lanes;
	
	public NewHighWayEvent(int time, String id, String idJunctionIni, String idJunctionDest, int maxSpeed, 
			int length, String type, int lanes) {
		super(time, id, idJunctionIni, idJunctionDest, maxSpeed, length);
		this.type = type;
		this.lanes = lanes;
	}
	
	public void execute(RoadMap m) {
		try{
			Road r = new HighWay(id, maxSpeed, length, type, lanes);
			m.addRoad(r);

			m.getJunction(idJunctionIni).addOutcoming(r);
			m.getJunction(idJunctionDest).addIncoming(r);
			m.getJunction(idJunctionDest).addInRoadQueue(r);			
			}catch(IllegalArgumentException e){
				throw new IllegalArgumentException("There has been a problem while adding HighWay", e);
		}
	}
	
}

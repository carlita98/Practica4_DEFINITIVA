package es.ucm.fdi.model.events;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.simulatedObjects.Path;

public class NewPathEvent extends NewRoadEvent{
	private String type;
	public NewPathEvent(int time, String id, String idJunctionIni, String idJunctionDest, int maxSpeed,
			int length, String type) {
		super(time, id, idJunctionIni, idJunctionDest, maxSpeed, length);
		this.type = type;
	}
	public void execute(RoadMap m) {
		try{
			Road r = new Path(id, maxSpeed, length, type);
			m.addRoad(r);
			int n = m.getJunction(idJunctionIni).getOutgoingRoadList().size(); 
			m.getJunction(idJunctionIni).getOutgoingRoadList().add(n, r);
			
			n = m.getJunction(idJunctionDest).getIncomingRoadList().size(); 
			m.getJunction(idJunctionDest).getIncomingRoadList().add(n, r);
			
			m.getJunction(idJunctionDest).getRoadQueue().put(r, m.getJunction(idJunctionDest).new IR());		
			}catch(IllegalArgumentException e){
				throw new IllegalArgumentException("While adding Path", e);
		}
	}

}

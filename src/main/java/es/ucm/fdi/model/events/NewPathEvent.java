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
			m.getJunction(idJunctionIni).addOutcoming(r);
			m.getJunction(idJunctionDest).addIncoming(r);
			m.getJunction(idJunctionDest).addInRoadQueue(r);		
			}catch(IllegalArgumentException e){
				throw new IllegalArgumentException("There has been a problem while adding Path", e);
		}
	}

}

package es.ucm.fdi.model.events;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;
import es.ucm.fdi.model.simulatedObjects.Dirt;
/**
 * Introduce a new Path in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewDirtEvent extends NewRoadEvent{
	private String type;
	/**
	 * Constructor
	 * @param time
	 * @param id
	 * @param idJunctionIni
	 * @param idJunctionDest
	 * @param maxSpeed
	 * @param length
	 * @param type
	 */
	public NewDirtEvent(int time, String id, String idJunctionIni, String idJunctionDest, int maxSpeed,
			int length, String type) {
		super(time, id, idJunctionIni, idJunctionDest, maxSpeed, length);
		this.type = type;
	}
	/**
	 * Introduce a new Path in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		try{
			Road r = new Dirt(id, maxSpeed, length, type);
			m.addRoad(r);
			m.getJunction(idJunctionIni).addOutcoming(r);
			m.getJunction(idJunctionDest).addIncoming(r);
			m.getJunction(idJunctionDest).addInRoadQueue(r);		
			}catch(NoSuchElementException e){
				throw new SimulatorException("There has been a problem while adding Path", e);
		}
	}

}

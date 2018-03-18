package es.ucm.fdi.model.events;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Road;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;
/**
 * Introduce a new Road in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewRoadEvent extends Event{
	protected String id;
	protected String idJunctionIni;
	protected String idJunctionDest;
	protected int maxSpeed;
	protected int length;
	/**
	 * Constructor
	 * @param time
	 * @param id
	 * @param idJunctionIni
	 * @param idJunctionDest
	 * @param maxSpeed
	 * @param length
	 */
	public NewRoadEvent(int time, String id, String idJunctionIni, String idJunctionDest, int maxSpeed, int length) {
		super(time);
		this.id = id;
		this.idJunctionIni = idJunctionIni;
		this.idJunctionDest = idJunctionDest;
		this.maxSpeed = maxSpeed;
		this.length = length;
	}
	/**
	 * Introduce a new Road in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		try{
			Road r = new Road(id, maxSpeed, length);
			m.addRoad(r);
			m.getJunction(idJunctionIni).addOutcoming(r);
			m.getJunction(idJunctionDest).addIncoming(r);
			m.getJunction(idJunctionDest).addInRoadQueue(r);		
			}catch(NoSuchElementException e){
				throw new SimulatorException("There has been a problem while adding Road ", e);
		}
	}
}

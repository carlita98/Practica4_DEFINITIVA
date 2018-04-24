package es.ucm.fdi.model.event;

import java.util.Map;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.Road;
import es.ucm.fdi.view.Describable;
/**
 * Introduce a new Road in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewRoadEvent extends Event implements Describable{
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
			Road r = new Road(id, maxSpeed, length,m.getJunction(idJunctionIni), m.getJunction(idJunctionDest));
			m.addRoad(r);
			m.getJunction(idJunctionIni).addOutcoming(r);
			m.getJunction(idJunctionDest).addIncoming(r);
			m.getJunction(idJunctionDest).addInRoadQueue(r);		
			}catch(NoSuchElementException e){
				throw new SimulatorException("There has been a problem while adding Road ", e);
		}
	}
	/**
	 * Describes the event to insert it into the interface events table
	 */
	public void describe(Map<String, String> out) {
		out.put("Time", "" + getTime());
		out.put("Type", "New Road "+ id);
	}
	
}

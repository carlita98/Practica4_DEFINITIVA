package es.ucm.fdi.model.event;

import java.util.Map;
import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.Junction;
import es.ucm.fdi.view.Describable;
/**
 * Introduce a new Junction in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewJunctionEvent extends Event implements Describable{
	protected String id;
	/**
	 * Constructor
	 * @param time
	 * @param id
	 */
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}
	/**
	 * Introduce a new Junction in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		try{
			m.addJunction(new Junction( id));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding Junction ", e);
		}
	}
	@Override
	public void describe(Map<String, String> out) {
		out.put("Time", ""+getTime());
		out.put("Type", "New Junction "+ id);
	}
} 

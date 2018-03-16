package es.ucm.fdi.model.events;

import java.util.NoSuchElementException;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.simulatedObjects.Junction;
import es.ucm.fdi.model.trafficSimulator.SimulatorException;

public class NewJunctionEvent extends Event {
	private String id;
	public NewJunctionEvent(int time, String id) {
		super(time);
		this.id = id;
	}
	public void execute(RoadMap m) throws SimulatorException {
		try{
			m.addJunction(new Junction( id));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding Junction ", e);
		}
	}
} 

package es.ucm.fdi.model.event;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.SimulatorException;
import es.ucm.fdi.model.object.*;
/**
 * Introduce a new Car in the RoadMap
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class NewCarEvent extends NewVehicleEvent{
	private String type;
	private int resistance;
	private double faultyProbability;
	private int maxFaultDuration;
	private long seed;
	/**
	 * Constructor
	 * @param time
	 * @param id
	 * @param maxSpeed
	 * @param itinerary
	 * @param type
	 * @param resistance
	 * @param faultyProbability
	 * @param maxFaultDuration
	 * @param seed
	 */
	public NewCarEvent(int time, String id, int maxSpeed, ArrayList<String> itinerary, String type,
			 int resistance, double faultyProbability, int maxFaultDuration, long seed) {
		super(time, id, maxSpeed, itinerary);
		this.type = type;
		this.resistance = resistance;
		this.faultyProbability = faultyProbability;
		this.maxFaultDuration = maxFaultDuration;
		if (seed == System.currentTimeMillis()) {
			this.seed = new Random().nextInt (1000);
		}
		else this.seed = seed;
	}
	/**
	 * Introduce a new Car in the RoadMap
	 */
	public void execute(RoadMap m) throws SimulatorException {
		ArrayList <Junction> jList = new ArrayList <>();
		try{
			for (int i = 0; i < itinerary.size(); i++){
				jList.add(m.getJunction(itinerary.get(i))) ;
			}
			m.addVehicle(new Car ( id, maxSpeed, jList, type, resistance, faultyProbability, maxFaultDuration, seed));
		}catch(NoSuchElementException e){
			throw new SimulatorException("There has been a problem while adding Car ", e);
		}
	}

}

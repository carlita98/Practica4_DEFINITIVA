package es.ucm.fdi.model.simulatedObjects;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Car extends Vehicle{
	private String type;
	private int resistance;
	private double faultyProbability;
	private int maxFaultDuration;
	private long seed;
	private Random randomNum; 
	private int distanceSinceFaulty; 
	
	public Car(String id, int maxSpeed, ArrayList<Junction> itinerary, String type, int resistance, 
			double faultyProbability, int maxFaultDuration, long seed) {
		super(id, maxSpeed, itinerary);
		this.type = type;
		this.resistance = resistance;
		this.faultyProbability = faultyProbability;
		this.maxFaultDuration = maxFaultDuration;
		if (seed == System.currentTimeMillis()) {
			this.seed = new Random().nextInt (1000);
		}
		else this.seed = seed;
		randomNum = new Random (this.seed); 
		distanceSinceFaulty = 0;
	}
	public void moveForward() {
		if (faulty > 0) {
			distanceSinceFaulty = 0;
			super.moveForward();
		}
		else if (faulty == 0 && distanceSinceFaulty > resistance &&  randomNum.nextDouble() < faultyProbability) {		
			super.setFaultyTime(randomNum.nextInt(maxFaultDuration) + 1);
			distanceSinceFaulty = 0;
			super.moveForward();
		}
		else {
			int distanceA = super.getKilometrage();
			super.moveForward();
			int distanceB = super.getKilometrage();
			distanceSinceFaulty += (distanceB - distanceA);
		}
	}
	
	public void fillReportDetails(Map <String, String> out) {
		out.put("type", type);
		super.fillReportDetails(out);
	}
}

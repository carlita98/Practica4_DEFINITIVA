package es.ucm.fdi.model.object;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;
import es.ucm.fdi.view.Describable;

/**
 * All the necessary methods for the Simulated Object HighWay
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Lanes extends Road implements Describable {

	private String type;
	private int lanes;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param maxSpeed
	 * @param length
	 * @param type
	 * @param lanes
	 */
	public Lanes(String id, int maxSpeed, int length, String type, int lanes, Junction source, Junction target) {
		super(id, maxSpeed, length, source, target);
		this.type = type;
		this.lanes = lanes;
	}

	/**
	 * Calculate the Base Speed
	 * 
	 * @return entire
	 */
	public int calculateBaseSpeed() {
		return Math.min(maxSpeed, (maxSpeed * lanes) / Math.max(vehicleList.sizeOfValues(), 1) + 1);
	}

	/**
	 * Execute moveForward for each vehicle into the HighWay
	 */
	public void executeMoveForward(int baseSpeed) {
		int counter = 0;
		MultiTreeMap<Integer, Vehicle> updated = new MultiTreeMap<Integer, Vehicle>(
				(a, b) -> b - a/* Collections.reverseOrder() */);
		for (Vehicle v : vehicleList.innerValues()) {
			if (v.getFaulty() > 0) {
				counter++;
			} else {
				if (counter > lanes) {
					v.setActualSpeed(baseSpeed / 2);
				} else {
					v.setActualSpeed(baseSpeed);
				}
			}

			v.moveForward();
			updated.putValue(v.getRoadLocation(), v);
		}
		vehicleList = updated;
	}

	/**
	 * Fill a Map with the HighWay data
	 */
	protected void fillReportDetails(Map<String, String> out) {
                   out.put("type", type);
		super.fillReportDetails(out);
	}
}

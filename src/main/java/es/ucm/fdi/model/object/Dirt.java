package es.ucm.fdi.model.object;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;
/**
 * All the necessary methods for the Simulated Object Path
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Dirt extends Road{
	private String type;
	/**
	 * Constructor
	 * @param id
	 * @param maxSpeed
	 * @param length
	 * @param type
	 */
	public Dirt(String id, int maxSpeed, int length, String type,Junction source, Junction target) {
		super(id, maxSpeed, length, source, target);
		this.type = type;
	}
	/**
	 * Calculate the Base Speed 
	 * @return entire
	 */
	public int calculateBaseSpeed() {
		return maxSpeed;
	}
	/**
	 * Execute moveForward for each vehicle into the Path
	 * @param baseSpeed
	 */
	public void executeMoveForward(int baseSpeed) {
		int counter = 0;
		MultiTreeMap <Integer, Vehicle> updated = new MultiTreeMap <Integer, Vehicle> ((a,b) -> b-a);
		for (Vehicle v: vehicleList.innerValues()){
			if (v.getFaulty () > 0){
				counter++;
			}
			else {
				v.setActualSpeed(baseSpeed/(1 + counter));
			}
			v.moveForward();
			updated.putValue(v.getRoadLocation(), v);
		}
		vehicleList = updated;
	}
	/**
	 * Fill a Map with the Path data
	 */
	protected void fillReportDetails (Map <String, String> out) {
		out.put("type", type);
		super.fillReportDetails(out);
	}
}

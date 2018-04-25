package es.ucm.fdi.model.object;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;
import es.ucm.fdi.view.Describable;

/**
 * All the necessary methods for the Simulated Object Road
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Road extends SimulatedObject implements Describable {

	protected int length;
	protected int maxSpeed;
	protected Junction source;
	protected Junction target;
	// La vehicleList está ordenada decrecientemente por la longitud de la carretera
	// Implementar la constructora con comparador (a, b) -> a-b
	protected MultiTreeMap<Integer, Vehicle> vehicleList = new MultiTreeMap<>((a, b) -> b - a);

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param maxSpeed
	 * @param length
	 */

	public Road(String id, int maxSpeed, int length, Junction source, Junction target) {
		super(id);
		this.maxSpeed = maxSpeed;
		this.length = length;
		this.source = source;
		this.target = target;
	}

	public int getLength() {
		return length;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public MultiTreeMap<Integer, Vehicle> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(MultiTreeMap<Integer, Vehicle> vehicleList) {
		this.vehicleList = vehicleList;
	}

	public Junction getSource() {
		return source;
	}

	public void setSource(Junction source) {
		this.source = source;
	}

	public Junction getTarget() {
		return target;
	}

	public void setTarget(Junction target) {
		this.target = target;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Introduce a new Vehicle into this Road
	 * 
	 * @param v
	 */
	public void pushVehicle(Vehicle v) {
		vehicleList.putValue(0, v);
	}

	/**
	 * Erase an existing vehicle from this Road
	 * 
	 * @param v
	 */
	public void popVehicle(Vehicle v) {
		// if (!vehicleList.isEmpty())
		vehicleList.removeValue(v.getRoadLocation(), v);
	}

	/**
	 * Calculate the Base Speed
	 * 
	 * @return entire
	 */
	public int calculateBaseSpeed() {
		return Math.min(maxSpeed, (maxSpeed / Math.max(vehicleList.sizeOfValues(), 1) + 1));
	}

	/**
	 * Execute moveForward for each vehicle into the Road
	 * 
	 * @param baseSpeed
	 */
	public void executeMoveForward(int baseSpeed) {
		boolean faultycar = false;
		boolean thisCar = false;
		MultiTreeMap<Integer, Vehicle> updated = new MultiTreeMap<Integer, Vehicle>((a, b) -> b - a);
		for (Vehicle v : vehicleList.innerValues()) {
			if (v.getFaulty() > 0) {
				faultycar = true;
				thisCar = true;
			} else if (faultycar && !thisCar) {
				v.setActualSpeed(baseSpeed / 2);
			} else if (!faultycar && !thisCar) {
				v.setActualSpeed(baseSpeed);
			}

			v.moveForward();
			updated.putValue(v.getRoadLocation(), v);
			thisCar = false;
		}
		vehicleList = updated;
	}

	/**
	 * Using calculateBaseSpeed and executeMoveForward achive the goal of move
	 * Forward the road
	 */
	public void moveForward() {
		executeMoveForward(calculateBaseSpeed());
	}

	/**
	 * Returns Road IniSection header
	 * 
	 * @return String
	 */
	protected String getReportHeader() {
		return "road_report";
	}

	/**
	 * Fill a Map with the Road data
	 */
	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder sb = new StringBuilder();
		for (Vehicle v : vehicleList.innerValues()) {
			sb.append("(");
			sb.append(v.getId());
			sb.append(",");
			sb.append(v.getRoadLocation());
			sb.append("),");
		}
		if (vehicleList.sizeOfValues() != 0) {
			sb.delete(sb.length() - 1, sb.length());
		}
		out.put("state", sb.toString());
	}

	/**
	 * Describes the road to insert it into the interface roads table
	 */
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		out.put("Source", "" + getSource().getId());
		out.put("Target", "" + getTarget().getId());
		out.put("Length", "" + getLength());
		out.put("Max Speed", "" + getMaxSpeed());
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean bucle = false;
		for (Vehicle it : vehicleList.innerValues()) {
			sb.append(it.getId());
			sb.append(",");
			bucle = true;
		}
		if (bucle) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		out.put("Vehicles", sb.toString());

	}

}
package es.ucm.fdi.model.simulatedObjects;

import java.util.Map;

import es.ucm.fdi.util.MultiTreeMap;

public class Path extends Road{
	private String type;
	
	public Path(String id, int maxSpeed, int length, String type) {
		super(id, maxSpeed, length);
		this.type = type;
	}
	public int calculateBaseSpeed() {
		return maxSpeed;
	}

	public void executeMoveForward(int baseSpeed) {
		int counter = 0;
		MultiTreeMap <Integer, Vehicle> updated = new MultiTreeMap <Integer, Vehicle> ((a,b) -> b-a/*Collections.reverseOrder()*/);
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
	
	protected void fillReportDetails (Map <String, String> out) {
		out.put("type", type);
		super.fillReportDetails(out);
	}
}

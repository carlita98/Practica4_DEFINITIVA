package es.ucm.fdi.model.object;

import java.util.Map.Entry;

import es.ucm.fdi.model.object.JunctionWithTimeSlice.IRWithTimeSlice;

public class RoundRobinJunction extends JunctionWithTimeSlice {

	private int maxTimeSlice;
	private int minTimeSlice;

	public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice, String string) {
		super(id, string);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	public void addInRoadQueue(Road r) {
		incomingQueues.put(r, new IRWithTimeSlice(maxTimeSlice));
	}

	public void updatedLights() {
		IRWithTimeSlice ir = currentIR();
		if (ir.timeInterval == ir.timeUnits) {
			if (numVehicles(ir) >= ir.timeUnits) {
				ir.timeInterval = Math.min(ir.timeInterval + 1, maxTimeSlice);
			} else if (numVehicles(ir) == 0) {
				ir.timeInterval = Math.max(ir.timeInterval - 1, minTimeSlice);
			}
			ir.timeUnits = 0;
			super.updateLights();
		}
	}

	public int numVehicles(IRWithTimeSlice ir) {
		return ir.getQueue().size();
	}
}

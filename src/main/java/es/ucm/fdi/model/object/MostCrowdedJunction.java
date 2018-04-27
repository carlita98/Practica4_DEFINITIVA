package es.ucm.fdi.model.object;

import java.util.Map.Entry;

import es.ucm.fdi.model.object.JunctionWithTimeSlice.IRWithTimeSlice;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id, String string) {
		super(id, string);
	}

	public void addInRoadQueue(Road r) {
		incomingQueues.put(r, new IRWithTimeSlice(1, -1));
	}

	public void updatedLights() {
		IRWithTimeSlice ir = currentIR();
		if (ir.timeInterval == ir.timeUnits) {
			ir.timeUnits = 0;
			int max = -1;
			int BefCurrentIncoming = currentIncoming;
			for (Entry<Road, IRWithTimeSlice> entry : incomingQueues.entrySet()) {
				if (max < entry.getValue().queue.size()
						&& incomingRoadList.indexOf(entry.getKey()) != BefCurrentIncoming) {
					max = entry.getValue().queue.size();
					currentIncoming = incomingRoadList.indexOf(entry.getKey());
				}
			}
			currentIR().timeInterval = Math.max(max / 2, 1);
		}
		/*
		 * if (ir.timeUnits == -1) { int max =
		 * incomingQueues.get(incomingRoadList.get(0)).getQueue().size();
		 * ir.timeInterval = Math.max(max / 2, 1); }
		 */
	}
}

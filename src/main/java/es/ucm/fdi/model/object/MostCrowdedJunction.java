package es.ucm.fdi.model.object;

import java.util.Map.Entry;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id, String string) {
		super(id, string);
	}

	public void updateLights() {
		IRWithTimeSlice ir = currentIR();
		if (ir.timeInterval == ir.timeUnits) {
			ir.timeUnits = 0;
			int max = -1;
			int BefCurrentIncoming = currentIncoming;
			// We know there is a problem in this method but we cant find it
			for (Entry<Road, IRWithTimeSlice> entry : incomingQueues.entrySet()) {
				if (max < entry.getValue().queue.size()
						&& incomingRoadList.indexOf(entry.getKey()) != BefCurrentIncoming) {
					max = entry.getValue().queue.size();
					currentIncoming = incomingRoadList.indexOf(entry.getKey());
				}
			}
			IRWithTimeSlice irUpdate = currentIR();
			irUpdate.timeInterval = Math.max(max / 2, 1);
		}
	}
}

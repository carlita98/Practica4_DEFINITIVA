package es.ucm.fdi.model.object;

public class RoundRobinJunction extends JunctionWithTimeSlice {

	private int maxTimeSlice;
	private int minTimeSlice;

	public RoundRobinJunction(String id, int maxTimeSlice, int minTimeSlice, String string) {
		super(id, string);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice = minTimeSlice;
	}
	
	public void addInRoadQueue(Road r) {
		incomingMap.put(r, new IncomingRoadWithTimeSlice(maxTimeSlice, 0));
	}
	
	/**
	 * Changes the traffic lights
	 */
	public void switchLights() {
		IncomingRoadWithTimeSlice ir = currentIR();
		if (ir.timeIsOver()){
            // red light
			// FullyUsed
            if(ir.isFullyUsed()){
                ir.intervalTime = Math.min(ir.intervalTime + 1, maxTimeSlice);
            }
            // No vehicles through the road
            else if(!ir.isPartiallyUsed()){ 
                ir.intervalTime = Math.max(ir.intervalTime-1, minTimeSlice);
            }
            // else do nothing
			ir.timeUnits = 0;
		}
	}
}

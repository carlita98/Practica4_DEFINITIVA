package es.ucm.fdi.model.object;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.ucm.fdi.model.object.JunctionWithTimeSlice.IncomingRoadWithTimeSlice;

public class JunctionWithTimeSlice extends Junction {
        
	protected String type;
	protected Map<Road, IncomingRoadWithTimeSlice> incomingMap = new LinkedHashMap<>();

	public Map<Road, IncomingRoadWithTimeSlice> getIncomingQueues() {
		return incomingMap;
	}

	public void setIncomingQueues(Map<Road, IncomingRoadWithTimeSlice> incomingQueues) {
		this.incomingMap = incomingQueues;
	}

	public JunctionWithTimeSlice(String id, String type) {
		super(id);
		this.type = type;
	}

	protected class IncomingRoadWithTimeSlice extends IncomingRoad {
		public int intervalTime;
		public int timeUnits;
                private boolean fullyUsed = true;
                private boolean partiallyUsed = false;
		public IncomingRoadWithTimeSlice(int timeInterval, int timeUnits) {
			this.intervalTime = timeInterval;
			this.timeUnits = timeUnits;
		}
                public boolean moveForward() {
                    boolean moved = super.moveForward();
                    partiallyUsed = partiallyUsed || moved;
                    fullyUsed = fullyUsed && moved;
                    timeUnits++;
                    return moved;
                }
                public void reset(){
                    partiallyUsed = false;
                    fullyUsed = true;
                }
                public boolean isFullyUsed(){
                    return fullyUsed;
                }
                public boolean isPartiallyUsed(){
                    return partiallyUsed;
                }
                
                public boolean timeIsOver(){
                    return timeUnits >= intervalTime;
                }
	}

	public IncomingRoadWithTimeSlice currentIR() {
		// Devuelve la IncomingRoad de la carretera que tiene el semáforo en verde
		return incomingMap.get(incomingRoadList.get(currentIncoming));
	}
         public void moveForward() {
            if(!incomingRoadList.isEmpty()){
            switchLights();
           incomingMap.get(incomingRoadList.get(currentIncoming)).moveForward();

            }
    }

	protected void fillReportDetails(Map<String, String> out) {

		StringBuilder sb = new StringBuilder();
		for (Entry<Road, IncomingRoadWithTimeSlice> entry : incomingMap.entrySet()) {
			sb.append("(");
			sb.append(entry.getKey().getId());
			sb.append(",");
			if (entry.getKey().equals(incomingRoadList.get(currentIncoming))) {
                            // we count the current as the green light (so we have to add 1)
				int dif = (currentIR().intervalTime - currentIR().timeUnits + 1); 
				sb.append("green:");
				sb.append(dif);
				sb.append(",");
			} else {
				sb.append("red,");
			}
			sb.append("[");
			int counter = 0;
			for (Vehicle v : entry.getValue().queue) {
				if (counter != entry.getValue().queue.size() - 1) {
					sb.append(v.getId());
					sb.append(",");
				} else {
					sb.append(v.getId());
				}
				counter++;
			}
			sb.append("]),");
		}
		if (!incomingRoadList.isEmpty()) {
			sb.delete(sb.length() - 1, sb.length());
		}
		out.put("queues", sb.toString());
		out.put("type", type);
	}
}

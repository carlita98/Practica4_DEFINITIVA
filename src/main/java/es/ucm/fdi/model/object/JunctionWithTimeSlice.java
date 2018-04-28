package es.ucm.fdi.model.object;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import es.ucm.fdi.model.object.JunctionWithTimeSlice.IRWithTimeSlice;

public class JunctionWithTimeSlice extends Junction {

	protected String type;
	protected Map<Road, IRWithTimeSlice> incomingQueues = new LinkedHashMap<>();

	public Map<Road, IRWithTimeSlice> getIncomingQueues() {
		return incomingQueues;
	}

	public void setIncomingQueues(Map<Road, IRWithTimeSlice> incomingQueues) {
		this.incomingQueues = incomingQueues;
	}

	public JunctionWithTimeSlice(String id, String type) {
		super(id);
		this.type = type;
	}

	public class IRWithTimeSlice extends IR {
		int timeInterval;
		int timeUnits;

		public IRWithTimeSlice() {
			super();
		}

		public IRWithTimeSlice(int timeInterval, int timeUnits) {
			super();
			this.timeInterval = timeInterval;
			this.timeUnits = timeUnits;
		}

		public int getTimeInterval() {
			return timeInterval;
		}

		public void setTimeInterval(int timeInterval) {
			this.timeInterval = timeInterval;
		}

		public int getTimeUnits() {
			return timeUnits;
		}

		public void setTimeUnits(int timeUnits) {
			this.timeUnits = timeUnits;
		}
	}

	public IRWithTimeSlice currentIR() {
		// Devuelve la IR de la carretera que tiene el semáforo en verde
		return incomingQueues.get(incomingRoadList.get(currentIncoming));
	}

	public void updatedLights() {
	}

	public void moveForward() {
		IRWithTimeSlice ir = currentIR();
		// Moves first car in the queue
		if (!incomingRoadList.isEmpty() && !incomingQueues.get(incomingRoadList.get(currentIncoming)).queue.isEmpty()) {
			Vehicle v = ir.queue.peek();
			if (v.getFaulty() == 0) {
				v.moveToNextRoad();
				ir.queue.removeFirst();
			} else {
				v.setFaultyTime(v.getFaulty() - 1);
			}
		}
		// Update lights
		ir.timeUnits++;
		updatedLights();
	}

	protected void fillReportDetails(Map<String, String> out) {

		StringBuilder sb = new StringBuilder();
		for (Entry<Road, IRWithTimeSlice> entry : incomingQueues.entrySet()) {
			sb.append("(");
			sb.append(entry.getKey().getId());
			sb.append(",");
			if (entry.getKey().equals(incomingRoadList.get(currentIncoming))) {
				int dif = (currentIR().timeInterval - currentIR().timeUnits);
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

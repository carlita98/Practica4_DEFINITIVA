package es.ucm.fdi.model.object;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import es.ucm.fdi.view.Describable;

/**
 * All the necessary methods for the Junction
 * 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */

public class Junction extends SimulatedObject implements Describable {
        
        private final String HEADER = "junction_report";
    
    /**
     * Internal class for the queue of vehicles in every single 
     * incomingRoad.
     */
	protected class IncomingRoad {
            
		protected ArrayDeque<Vehicle> queue = new ArrayDeque<>();

		public ArrayDeque<Vehicle> getQueue() {
			return queue;
		}
        /**
         * Get the number of vehicles into an incomingRoad
         * @return int
         */
        public int getNumberOfVehicles(){
            return queue.size();
        }
        /**
         * Gets the first vehicle into the vehicle queue.
         * If it is not faulty it calls moveToNextRoad of that vehicle.
         * If it is faulty it decrease the faulty time.   
         * @return true if the first car was move to the next road.
         */
        public boolean moveForward() {
            boolean moved = false;
            if (!queue.isEmpty()) {
                Vehicle v = queue.peek();
                if (v.getFaulty() == 0) {
                    moved = true;
                    v.moveToNextRoad();
                    queue.removeFirst();
                } else {
                    v.setFaultyTime(v.getFaulty() - 1);
                }
            }
            return moved;
        }


	}

	protected int currentIncoming = 0;
	private Map<Road, IncomingRoad> incomingMap = new LinkedHashMap<>();
	protected List<Road> incomingRoadList = new ArrayList<>();
	protected List<Road> outgoingRoadList = new ArrayList<>();

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public Junction(String id) {
		super(id);
	}
        
	/**
	 * Get the Road with the green traffic light
	 * @return
	 */
	public IncomingRoad currentIR() {
		//Returns the IncomingRoad of the Road with the green traffic light
		return incomingMap.get(incomingRoadList.get(currentIncoming));
	}

	public void addOutcoming(Road r) {
		int n = this.getOutgoingRoadList().size();
		getOutgoingRoadList().add(n, r);
	}

	public void addIncoming(Road r) {
		incomingRoadList.add(outgoingRoadList.size(), r);
        currentIncoming = incomingMap.size();
	}

	public void addInRoadQueue(Road r) {
		incomingMap.put(r, new IncomingRoad());
	}

	public Map<Road, IncomingRoad> getIncomingMap() {
		return incomingMap;
	}

	public void setIncomingMap(Map<Road, IncomingRoad> newMap) {
		incomingMap = newMap;
	}

	public List<Road> getIncomingRoadList() {
		return incomingRoadList;
	}

	public void setIncomingRoadList(List<Road> incomingRoadList) {
		this.incomingRoadList = incomingRoadList;
	}

	public int getCurrentIncoming() {
		return currentIncoming;
	}

	public void setCurrentIncoming(int currentIncoming) {
		this.currentIncoming = currentIncoming;
	}

	public List<Road> getOutgoingRoadList() {
		return outgoingRoadList;
	}

	public void setOutgoingRoadList(List<Road> outgoingRoadList) {
		this.outgoingRoadList = outgoingRoadList;
	}

	/**
	 * Adds a vehicle into the vehicle queue of its ActualRoad
	 * @param v
	 */
	public void carIntoIR(Vehicle v) {
		if (!incomingMap.get(v.getActualRoad()).queue.contains(v) ) {
			incomingMap.get(v.getActualRoad()).queue.addLast(v);
		}
	}

	/**
	 * Choose the next road with green traffic light
	 */
	public void switchLights() {
		try {
			currentIncoming = (currentIncoming + 1) % incomingRoadList.size();
		} catch (ArithmeticException e) {
			currentIncoming = 0;
		}
	}

	/**
	 * Move the first car in the queue if the traffic light is green.
	 * Changes the lights.
	 */
    public void moveForward() {
        if(!incomingRoadList.isEmpty()){
            incomingMap.get(incomingRoadList.get(currentIncoming)).moveForward();
            switchLights();
        }
    }
   

	/**
	 * Returns Junction IniSection header
	 */
	protected String getReportHeader() {
		return HEADER;
           }

	/**
	 * Fill a Map with the Junction data
	 */
	protected void fillReportDetails(Map<String, String> out) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Road, IncomingRoad> entry : incomingMap.entrySet()) {
			sb.append("(");
			sb.append(entry.getKey().getId());
			sb.append(",");
			if (entry.getKey().equals(incomingRoadList.get(currentIncoming))) {
				sb.append("green,");
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
	}

	/**
	 * Describes the junction to insert it into the interface junction table
	 */
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		StringBuilder sbGreen = new StringBuilder();
		StringBuilder sbRed = new StringBuilder();
		sbGreen.append("[");
		sbRed.append("[");
		for (Map.Entry<Road, IncomingRoad> entry : incomingMap.entrySet()) {

			if (entry.getKey().equals(incomingRoadList.get(currentIncoming))) {
				sbGreen.append(describeGreenRed("Green", entry));
			}
			else {
				sbRed.append(describeGreenRed("Red", entry));
			}
		}
		sbGreen.append("]");
		sbRed.append("]");
		out.put("Green", sbGreen.toString());
		out.put("Red", sbRed.toString());
	}

	/**
	 * Describes the junction to show then into the interface Table
	 * @param color
	 * @param entry
	 * @return
	 */
	public StringBuilder describeGreenRed(String color, Map.Entry<Road, IncomingRoad> entry) {
		StringBuilder sb= new StringBuilder();
		sb.append("(");
		sb.append(entry.getKey().getId());
		if (color == "Green") {
			sb.append(",green,");
		}
		else {
			sb.append(",red,");
		}
		sb.append("[");
		for (Vehicle v : entry.getValue().queue) {
			sb.append(v.getId());
			sb.append(",");
		}
		if (!entry.getValue().queue.isEmpty()) {
			sb.delete(sb.length() - 1, sb.length());
		}
		sb.append("]");
		sb.append(")");
		return sb;
	}
}
package es.ucm.fdi.model.object;

import java.util.ArrayList;
import java.util.Map;

import es.ucm.fdi.view.Describable;
/**
 * All the necessary methods for the Simulated Object Vehicle
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class Vehicle extends SimulatedObject implements Describable {

	protected int kilometrage;
	protected int faulty;
	protected Road currentRoad;
	protected int junctionCounter;
	protected int roadLocation;
	protected int maxSpeed;
	protected int actualSpeed;
	protected ArrayList<Junction> itinerary = new ArrayList <>();
	protected boolean arrived;
	/**
	 * Constructor
	 * @param id
	 * @param maxSpeed
	 * @param itinerary
	 */

	public Vehicle(String id, int maxSpeed, ArrayList<Junction> itinerary) {
		super(id);
		this.maxSpeed = maxSpeed;
		this.itinerary = itinerary;
		moveToNextRoad();
	}
	public Road getActualRoad() {
		return currentRoad;
	}
	public int getFaulty () {
		return faulty;
	}
	public void setFaultyTime (int t) {
		faulty += t;
		setActualSpeed(0);
	}
	public void setActualSpeed (int s) {
		if (s > maxSpeed) actualSpeed = maxSpeed;
		else actualSpeed = s;
	}
	public int getKilometrage() {
		return kilometrage;
	}
	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}
	public int getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(int roadLocation) {
		this.roadLocation = roadLocation;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public ArrayList<Junction> getItinerary() {
		return itinerary;
	}
	public void setItinerary(ArrayList<Junction> itinerary) {
		this.itinerary = itinerary;
	}
	public boolean isArrived() {
		return arrived;
	}
	public void setArrived(boolean arrived) {
		this.arrived = arrived;
	}
	public int getActualSpeed() {
		return actualSpeed;
	}
	public void setActualRoad(Road actualRoad) {
		this.currentRoad = actualRoad;
	}
	/**
	 * Advance a vehicle into its currentRoad or push it into the Incoming Road 
	 */
	public void moveForward () {

		if (faulty == 0 && !arrived ) {
			
			int previousK = roadLocation;
			roadLocation += actualSpeed;
			
			if (roadLocation >= currentRoad.getLength()){
				kilometrage += (currentRoad.getLength() - previousK);
				roadLocation = currentRoad.getLength();
				itinerary.get(junctionCounter).carIntoIR(this); 
				actualSpeed = 0;
			}
			else kilometrage += actualSpeed;
			
		}
		
		else if (faulty != 0 && !arrived){
			faulty --;
		}
	}
	/**
	 * Move a vehicle into its next Road
	 */
	public void moveToNextRoad() {
		if (itinerary.size() - 1 > junctionCounter) {	
			for (Road r: itinerary.get(junctionCounter).getOutgoingRoadList()) {		
				for (Road r2: itinerary.get(junctionCounter + 1).getIncomingRoadList()) {
					if (r == r2) {
						if (currentRoad != null) {
							currentRoad.popVehicle(this);
						}
						currentRoad = r;   
						currentRoad.pushVehicle(this);
						roadLocation = 0;
						junctionCounter++;
						break;
					}
				}
				if (itinerary.size() - 1 <=  junctionCounter) {
					break;
				}
			}
		}
		else {
			arrived = true;
			actualSpeed = 0;
			currentRoad.popVehicle(this);		
		}
	}
	public Road getCurrentRoad() {
		return currentRoad;
	}
	public void setCurrentRoad(Road currentRoad) {
		this.currentRoad = currentRoad;
	}
	/**
	 * Returns Vehicle IniSection header
	 * @return String
	 */
	protected  String getReportHeader() {
		return "vehicle_report";
	}
	/**
	 * Fill a Map with the Vehicle data
	 */
	protected void fillReportDetails (Map <String, String> out) {
		out.put("speed", "" + actualSpeed);
		out.put("kilometrage", "" + kilometrage);
		out.put("faulty", "" + faulty);
		if (!arrived)
			out.put("location","(" + currentRoad.getId() +","+ roadLocation + ")" );
		else
			out.put("location", "arrived");
	}
	@Override
	public void describe(Map<String, String> out) {
		out.put("ID", getId());
		out.put("Road", ""+getCurrentRoad().getId());
		out.put("Location", ""+ getRoadLocation());
		out.put("Speed", ""+ getActualSpeed());
		out.put("Km", ""+ getKilometrage());
		out.put("Faulty Units", ""+ getFaulty());
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		boolean bucle = false;
		for(Junction it: itinerary){
			sb.append(it.getId());
			sb.append(",");
			bucle = true;
		}
		if(bucle) {
			sb.delete(sb.length() - 1, sb.length());
		}
	
		sb.append("]");
		out.put("Itinerary", sb.toString());
	}
}
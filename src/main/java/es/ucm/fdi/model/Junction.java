package es.ucm.fdi.model;
import java.util.*;

public class Junction {
	private String junctionId;
	public class IR{
		boolean light;
		ArrayList <Vehicle> vehicleListIR;
	}
	private Map <Road, IR> RoadQueue;
	private List <IR> IncomingRoadList;
	
}
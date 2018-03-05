package es.ucm.fdi.events;

import es.ucm.fdi.launcher.RoadMap;

public class NewRoadEvent extends Event{
	private String id;
	private String idJunctionIni;
	private String idJunctionDest;
	private int maxSpeed;
	private int length;
	public NewRoadEvent(int t, String i, String idJI, String idJD, int mS, int l) {
		time = t;
		id = i;
		idJunctionIni = idJI;
		idJunctionDest = idJD;
		maxSpeed = mS;
		length = l;
	}
	public void execute(RoadMap m) {
		
	}
}

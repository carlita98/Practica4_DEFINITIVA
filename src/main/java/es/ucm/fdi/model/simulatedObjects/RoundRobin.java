package es.ucm.fdi.model.simulatedObjects;

import java.util.Map;

import es.ucm.fdi.model.simulatedObjects.Junction.IR;

public class RoundRobin extends Junction{
	private int maxTimeSlice;
	private int minTimeSlice;
	public RoundRobin(String id, int maxTimeSlice, int minTimeSlice) {
		super(id);
		this.maxTimeSlice = maxTimeSlice;
		this.minTimeSlice =  minTimeSlice;
	}

	public class IrTime extends IR{
		int timeInterval = maxTimeSlice;
		int timeUnits;
	}
	
	public RoundRobin(String id) {
		super (id);
	}
	
	public void UpdatedLight() {
		
	}
	
	protected void fillReportDetails (Map <String, String> out) {
	
		
	}
}

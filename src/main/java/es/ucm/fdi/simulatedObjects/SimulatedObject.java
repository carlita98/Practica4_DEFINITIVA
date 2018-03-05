package es.ucm.fdi.simulatedObjects;

import java.util.Map;

public abstract class SimulatedObject {
	protected String Id;
	protected int time;
	public String getID() {
		return Id;
	}
	
	public void report (Map <String, String> out) {
		out.put("", getReportHeader());
		out.put("id", Id);
		out.put("time", " " + time);
		fillReportDetails (out);
	}
	protected abstract String getReportHeader();
	protected abstract void fillReportDetails (Map <String, String> out);
	public abstract void moveForward();


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + time;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulatedObject other = (SimulatedObject) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	
}

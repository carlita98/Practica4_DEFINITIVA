package es.ucm.fdi.ini;

import java.util.Map;

abstract public class SimulatedObject {
	protected String Id;
	public String getID() {
		return Id;
	}
	
	public void report (String id, String time, Map <String, String> out) {
		out.put("", getReportHeader());
		out.put("id", id);
		out.put("time", time);
		fillReportDetails (out);
	}
	protected abstract String getReportHeader();
	protected abstract void fillReportDetails (Map <String, String> out);
}

package es.ucm.fdi.model.trafficSimulator;

public class SimulatorException extends Exception{
	public SimulatorException() {
		
	}
	public SimulatorException (String s, Exception e) {
		super(s, e);
	}
}

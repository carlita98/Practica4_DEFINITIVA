package es.ucm.fdi.model.trafficSimulator;
/**
 * Exception used when problems happen into the Simulator
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
@SuppressWarnings("serial")
public class SimulatorException extends Exception{
	/**
	 * Constructor
	 */
	public SimulatorException() {	}
	/**
	 * Constructor, its enable the concatenation of Exceptions
	 * @param s
	 * @param e
	 */
	public SimulatorException (String s, Exception e) {
		super(s, e);
	}
}

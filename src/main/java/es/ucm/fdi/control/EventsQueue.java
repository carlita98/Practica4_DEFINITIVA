package es.ucm.fdi.control;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JTable;

import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.trafficSimulator.Simulator;
import es.ucm.fdi.model.trafficSimulator.Simulator.Listener;
import es.ucm.fdi.model.trafficSimulator.Simulator.UpdateEvent;

public class EventsQueue extends JTable implements Listener{

	static String[] columnNames = {"#", "Time", "Type"};
	static Object [][] data = {};
	private Simulator sim;

	public EventsQueue(Simulator sim) {
		super(data, columnNames);
		setFillsViewportHeight(true);
		sim.addSimulatorListener(this);
		this.sim = sim;
	}
	
	public void registered(UpdateEvent ue) {
		sim.getListeners().add(this);
	}
	public void reset(UpdateEvent ue) {
		data =  (Object[][]) new Object[ue.getEvenQueue().size()][columnNames.length];
		int counter = 0;
		for(Event e: ue.getEvenQueue()){
			Arrays.fill(data, counter);
			Arrays.fill(data, e.getTime());
			Arrays.fill(data, "");
		}
	}
	public void newEvent(UpdateEvent ue) {
		
	}
	public void advanced(UpdateEvent ue) {
		
	}
	public void error(UpdateEvent ue, String error) {}
}

package es.ucm.fdi.control;

import javax.swing.JTable;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class EventsQueue extends JTable{

	static String[] columnNames = {"#", "Time", "Type", "ad"};
	static Object [][] data = {};
	
	public EventsQueue(Simulator sim) {
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

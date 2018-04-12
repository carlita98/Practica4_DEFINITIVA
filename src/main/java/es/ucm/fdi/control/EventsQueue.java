package es.ucm.fdi.control;

import javax.swing.JTable;

public class EventsQueue extends JTable{

	static String[] columnNames = {"#", "Time", "Type", "ad"};
	static Object [][] data = {};
	
	public EventsQueue() {
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

package es.ucm.fdi.control;

import javax.swing.JTable;

public class VehiclesTable extends JTable{
	
	static String[] columnNames = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	static Object [][] data = {};
	public VehiclesTable() {

		
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

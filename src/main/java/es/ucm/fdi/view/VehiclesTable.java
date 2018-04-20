package es.ucm.fdi.view;

import javax.swing.JTable;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class VehiclesTable extends JTable{
	
	static String[] columnNames = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	static Object [][] data = {};

	public VehiclesTable() {

		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

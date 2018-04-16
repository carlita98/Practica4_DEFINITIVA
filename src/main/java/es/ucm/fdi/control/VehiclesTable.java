package es.ucm.fdi.control;

import javax.swing.JTable;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class VehiclesTable extends JTable{
	
	static String[] columnNames = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
	static Object [][] data = {};

	public VehiclesTable(Simulator sim) {

		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

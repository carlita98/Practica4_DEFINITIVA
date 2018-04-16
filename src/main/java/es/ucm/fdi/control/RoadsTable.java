package es.ucm.fdi.control;

import javax.swing.JTable;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class RoadsTable extends JTable{

	static String[] columnNames = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};
	static Object [][] data = {};

	public RoadsTable(Simulator sim) {
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

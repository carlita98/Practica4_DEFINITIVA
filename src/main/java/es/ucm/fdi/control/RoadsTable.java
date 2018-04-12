package es.ucm.fdi.control;

import javax.swing.JTable;

public class RoadsTable extends JTable{

	static String[] columnNames = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};
	static Object [][] data = {};
	public RoadsTable() {
		
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}

package es.ucm.fdi.control;

import javax.swing.JTable;

import es.ucm.fdi.model.trafficSimulator.Simulator;

public class JunctionsTable extends JTable{
	
	static String[] columnNames = {"ID", "Green", "Red"};
	static Object [][] data = {};

	public JunctionsTable() {

		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}


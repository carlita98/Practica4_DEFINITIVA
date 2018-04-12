package es.ucm.fdi.control;

import javax.swing.JTable;

public class JunctionsTable extends JTable{
	
	static String[] columnNames = {"ID", "Green", "Red"};
	static Object [][] data = {};
	
	public JunctionsTable() {
	
		super(data, columnNames);
		setFillsViewportHeight(true);
	}
}


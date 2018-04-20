package es.ucm.fdi.view;

import javax.swing.table.AbstractTableModel;

public class EventsTableModel extends AbstractTableModel{
	public Object[] columnNames = {"#", "Time", "Type",};
	public Object [][] data = {};
	@Override
	public int getRowCount() {
		return 0;
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}

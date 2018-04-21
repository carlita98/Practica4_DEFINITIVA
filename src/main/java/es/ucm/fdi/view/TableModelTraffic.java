package es.ucm.fdi.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableModelTraffic extends JPanel{
	private ListOfMapsTableModel tablemodel ;
	private JTable table;
	private String[] fieldNames;
	private List <? extends Describable> elements;
	
	public TableModelTraffic(String [] fieldNames, List <? extends Describable> elements) {
		this.fieldNames = fieldNames;
		this.elements =  elements;
		tablemodel = new ListOfMapsTableModel();
		table = new JTable(tablemodel);
		add(new JScrollPane( table,  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	
	public void setElements(List<? extends Describable> elements) {
		this.elements = elements;
	}

	public void updated () {
		tablemodel.updated();
	}
	private class ListOfMapsTableModel extends AbstractTableModel {

		@Override
		// fieldNames es un String[] con nombrs de col.
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}
		@Override
		// elements contiene la lista de elementos
		public int getRowCount() {
			return elements.size();
		}
		@Override
		public int getColumnCount() {
			return fieldNames.length;
		}
		@Override
		// ineficiente: ¿puedes mejorarlo?
		public Object getValueAt(int rowIndex, int columnIndex) {
			Map mapa = new HashMap<String, String>();
			elements.get(rowIndex).describe(mapa);
			return mapa.get(fieldNames[columnIndex]);
		}
		public void updated() {
			fireTableDataChanged();
		}
	}
}
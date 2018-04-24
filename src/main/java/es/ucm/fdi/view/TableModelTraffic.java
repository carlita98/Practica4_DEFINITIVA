package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class TableModelTraffic extends JPanel{
	private ListOfMapsTableModel tablemodel ;
	private JTable table;
	private String[] fieldNames;
	private List <? extends Describable> elements;
	
	public TableModelTraffic(String [] fieldNames, List <? extends Describable> elements) {
		super(new BorderLayout());
		this.fieldNames = fieldNames;
		this.elements =  elements;
		tablemodel = new ListOfMapsTableModel();
		table = new JTable(tablemodel);
		add(new JScrollPane (table), BorderLayout.CENTER);
	}
	
	public void setElements(List<? extends Describable> elements) {
		this.elements = elements;
	}

	public void updated () {
		tablemodel.updated();
	}
	private class ListOfMapsTableModel extends AbstractTableModel {


		// fieldNames es un String[] con nombrs de col.
		public String getColumnName(int columnIndex) {
			return fieldNames[columnIndex];
		}

		// elements contiene la lista de elementos
		public int getRowCount() {
			return elements.size();
		}

		public int getColumnCount() {
			return fieldNames.length;
		}

		// ineficiente: ¿puedes mejorarlo?
		public Object getValueAt(int rowIndex, int columnIndex) {
			Map mapa = new HashMap<String, String>();
			elements.get(rowIndex).describe(mapa);
			
			if(fieldNames[columnIndex].equals("#")){
				return rowIndex;
			}
			return mapa.get(fieldNames[columnIndex]);
		}
		public void updated() {
			fireTableDataChanged();
		}
	}
}

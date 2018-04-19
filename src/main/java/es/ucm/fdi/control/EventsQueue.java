package es.ucm.fdi.control;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.trafficSimulator.Simulator;
import es.ucm.fdi.model.trafficSimulator.Simulator.Listener;
import es.ucm.fdi.model.trafficSimulator.Simulator.UpdateEvent;

public class EventsQueue extends JTable {
	/*
	public static MyTableModel myData = new MyTableModel();
	
	public class MyTableModel extends AbstractTableModel{
		public Object[] columnNames = {"#", "Time", "Type"};
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
	private Simulator sim;
	public EventsQueue(Simulator sim) {
		super(myData);
		setFillsViewportHeight(true);
		sim.addSimulatorListener(new Listener() {
			public void registered(UpdateEvent ue) {}
			public void error(UpdateEvent ue, String s) {}
			public void reset(UpdateEvent ue) {update(ue);}
			public void newEvent(UpdateEvent ue) {update(ue);}
			public void advanced(UpdateEvent ue) {update(ue);}
		});
		this.sim = sim;
	}
	
	private void update(UpdateEvent ue) {
		Object [][]data =  (Object[][]) new Object[ue.getEventQueue().size()][getColumnCount()];
		int counter = 0;
		for(Event e: ue.getEventQueue()){
			Arrays.fill(data[counter], counter);
			Arrays.fill(data[counter], e.getTime());
			Arrays.fill(data[counter], "");
			counter++;
		}
		myData.fireTableDataChanged();
	}
	*/
}

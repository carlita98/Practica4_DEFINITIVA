package es.ucm.fdi.view.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.object.Junction;
import es.ucm.fdi.model.object.Road;
import es.ucm.fdi.model.object.Vehicle;

public class MyDialogWindow extends JFrame{
	
	private RoadMap rm;
	private DialogWindow dialog;
	private JTextArea report;
	
	List<String> vehicles;
	List<String> roads;
	List<String> junctions;
	
	public MyDialogWindow(RoadMap rm,JTextArea report) {
		super("Generate Reports");
		this.rm = rm;
		this.report = report;
		initGUI();
	}


	private void initGUI(){
		
		vehicles = new ArrayList<>();
		roads = new ArrayList<>();
		junctions = new ArrayList<>();
		
		for(Vehicle v : rm.getVehicles()){
			vehicles.add(v.getId());
		}
		
		for(Road r : rm.getRoads()){
			roads.add(r.getId());
		}
		
		for(Junction j : rm.getJunctions()){
			junctions.add(j.getId());
		}
		
		JPanel mainPanel = new JPanel();
		dialog = new DialogWindow(this);
		dialog.setData(vehicles, roads, junctions);
		
		JButton here = new JButton("here");
		here.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int status = dialog.open();
				if ( status == 0) {
				} else {
					for(String v : dialog.getSelectedVehicles()) {
						report.setText(rm.getVehicle(v).toString());
					}
					for(String r : dialog.getSelectedRoads()) {
						report.setText(rm.getRoad(r).toString());
					}
					for(String j : dialog.getSelectedJunctions()) {
						report.setText(rm.getJunction(j).toString());
					}
				}
			}
		});
		mainPanel.add(here);
		mainPanel.add(new JLabel("a dialog window is opened and the main window blocks."));
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}

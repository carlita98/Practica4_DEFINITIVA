package es.ucm.fdi.view.dialog;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import es.ucm.fdi.control.Controller;
import es.ucm.fdi.model.object.Junction;
import es.ucm.fdi.model.object.Road;
import es.ucm.fdi.model.object.SimulatedObject;
import es.ucm.fdi.model.object.Vehicle;
/**
 * Class that creates the dialog Window (in witch we can choose, 
 * witch report to write in the reportArea)
 * @author Carla Martínez y Beatriz Herguedas
 *
 */
public class MyDialogWindow extends JFrame{
	
	private Controller ctrl;
	private DialogWindow dialog;
	private OutputStream out;
	
	List<String> vehicles = new ArrayList<>();;
	List<String> roads = new ArrayList<>();;
	List<String> junctions = new ArrayList<>();;
	
	public MyDialogWindow(Controller ctrl, OutputStream out) {
		super("Generate Reports");
		this.ctrl = ctrl;
		this.out = out;
		initGUI();
	}
	
	/**
	 * Create and initialize the dialog of MyDialogWindow
	 */
	private void initGUI(){
		
		//Fill the three arrays with the objects id
		for(Vehicle v : ctrl.getSim().getRoadMap().getVehicles()){
			vehicles.add(v.getId());
		}
		
		for(Road r : ctrl.getSim().getRoadMap().getRoads()){
			roads.add(r.getId());
		}
		
		for(Junction j : ctrl.getSim().getRoadMap().getJunctions()){
			junctions.add(j.getId());
		}
		
		JPanel mainPanel = new JPanel();
		dialog = new DialogWindow(this);
		dialog.setData(vehicles, roads, junctions);
		
		int status = dialog.open();
		if ( status == 0) {
		} 
		//Add the selected objects into a List <SimulatedObject>
		else {
			List <SimulatedObject> l = new ArrayList<>();
			for(String v : dialog.getSelectedVehicles()) {
				l.add(ctrl.getSim().getRoadMap().getVehicle(v));
			}
			for(String r : dialog.getSelectedRoads()) {
				l.add(ctrl.getSim().getRoadMap().getRoad(r));
			}
			for(String j : dialog.getSelectedJunctions()) {
				l.add(ctrl.getSim().getRoadMap().getJunction(j));
			}
			//Generate report of the chosen list of SimulatedObject
			ctrl.getSim().generateReport(out, l);
		}
		this.setContentPane(mainPanel);
		this.pack();
	}
}

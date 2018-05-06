package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;

/**
 * Class that creates the PopMenu 
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class PopUpMenu{

	private JPanel _mainPanel = new JPanel();
	private JTextArea _editor;
	/**
	 * Internal enum that contains, the label of the different parts of the PopUpMenu and 
	 * the string with the templates for every event.
	 * @author Carla Martínez, Beatriz Herguedas
	 *
	 */
	private enum Template { 
		NewRRJunction("Add New RR Junction", "[new_junction]\ntime = \nid = \ntype = rr\nmax_time_slice = \nmin_time_slice = \n\n"),
		NewMCJunction("Add New MC Junction","[new_junction]\ntime = \nid = \ntype = mc\n\n"),
		NewJunction("Add New Junction","[new_junction]\ntime = \nid = \n\n"),
		NewDirtRoad("Add New Dirt Road","[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = dirt\n\n"),
		NewLanesRoad("Add New Lanes Road","[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = lanes\nlanes = \n\n"),
		NewRoad("Add New Road","[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \n\n"),
		NewBike("Add New Bike","[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = bike\n\n"),
		NewCar("Add New Car","[new_vehicle]\ntime = \nid = \nitinerary = \nmax_speed = \ntype = car\nresistance = \nfault_probability = \nmax_fault_duration = \nseed = \n\n"),
		NewVehicle("Add New Vehicle","[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \n\n"),
		MakeVehicleFaulty("Make Vehicle Faulty","[make_vehicle_faulty]\ntime = \nvehicles = \nduration = \n\n");

		private String option;
		private String text;
		public String getOption() {
			return option;
		}
		Template(String option, String text) {
			this.option = option;
			this.text = text;
		}
		public String toString() {
			return text;
		}
		};
	public JTextArea get_editor() {
		return _editor;
	}


	public void set_editor(JTextArea _editor) {
		this._editor = _editor;
	}


	public PopUpMenu() {
		addEditor();
	}
	/**
	 * Add the textArea and the menu to the mainPanel
	 */
	private void addEditor() {
		_mainPanel.add(new JLabel("Right click over the text-area to get the popup menu."),BorderLayout.PAGE_START);
		_editor = new JTextArea(40,30);
		
		//Creates and add the actions to the PopUpMenu
		JPopupMenu _editorPopupMenu = new JPopupMenu();
		
		JMenuItem exitOption = new JMenuItem("Exit");
		exitOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenuItem clearOption = new JMenuItem("Clear");
		clearOption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_editor.setText("");
			}
		});
		
		
		JMenu subMenu = new JMenu("Add Template");
		
		//Creates a Template Array 
		Template[]  templates = {
				Template.NewRRJunction,
				Template.NewMCJunction,
				Template.NewJunction,
				Template.NewDirtRoad,
				Template.NewLanesRoad,
				Template.NewRoad,
				Template.NewBike,
				Template.NewCar,
				Template.NewVehicle,
				Template.MakeVehicleFaulty};
		
		//Writes in the editor the selected template
		for (Template t : templates) {
			JMenuItem menuItem = new JMenuItem(t.getOption());
			menuItem.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					_editor.insert(t.toString(), _editor.getCaretPosition());
				}
				
			});
			subMenu.add(menuItem);
		}
		_editorPopupMenu.add(subMenu);
		_editorPopupMenu.addSeparator();
		_editorPopupMenu.add(exitOption);
		_editorPopupMenu.add(clearOption);
		
		//Add MouseListener to the editor
		_editor.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				showPopup(e);
			}
			public void mouseReleased(MouseEvent e) {
				showPopup(e);
			}
			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && _editorPopupMenu.isEnabled()) {
					_editorPopupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});

	}
}

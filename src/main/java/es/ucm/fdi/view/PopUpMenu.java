package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
public class PopUpMenu{

	private JPanel _mainPanel = new JPanel();
	private JTextArea _editor;
	private enum Template { 
		NewRRJunction("New RR Junction", "\n[new_junction]\ntime = \nid = \ntype = rr\nmax_time_slice = \nmin_time_slice = \n"),
		NewMCJunction("New MC Junction","\n[new_junction]\ntime = \nid = \ntype = mc\n"),
		NewJunction("New Junction","\n[new_junction]\ntime = \nid = \n"),
		NewDirtRoad("New Dirt Road","\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = dirt\n"),
		NewLanesRoad("New Lanes Road","\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \ntype = lanes\nlanes = \n"),
		NewRoad("New Road","\n[new_road]\ntime = \nid = \nsrc = \ndest = \nmax_speed = \nlength = \n"),
		NewBike("New Bike","\n[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \ntype = bike\n"),
		NewCar("New Car","\n[new_vehicle]\ntime = \nid = \nitinerary = \nmax_speed = \ntype = car\nresistance = \nfault_probability = \nmax_fault_duration = \nseed = \n"),
		NewVehicle("New Vehicle","\n[new_vehicle]\ntime = \nid = \nmax_speed = \nitinerary = \n"),
		MakeVehicleFaulty("Make Vehicle Faulty","\n[make_vehicle_faulty]\ntime = \nvehicles = \nduration = \n");

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
	
	private void addEditor() {
		_mainPanel.add(new JLabel("Right click over the text-area to get the popup menu."),BorderLayout.PAGE_START);
		_editor = new JTextArea(40,30);
		JPopupMenu _editorPopupMenu = new JPopupMenu();
		JMenuItem loadOption = new JMenuItem("Load");
		loadOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JMenuItem saveOption = new JMenuItem("Save");
		saveOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		JMenuItem clearOption = new JMenuItem("Clear");
		clearOption.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_editor.setText("");
			}
		});
		
		
		JMenu subMenu = new JMenu("Add Template");
		
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
		
		for (Template t : templates) {
			JMenuItem menuItem = new JMenuItem(t.getOption());
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					_editor.insert(t.toString(), _editor.getCaretPosition());
				}
			});
			subMenu.add(menuItem);
		}
		_editorPopupMenu.add(subMenu);
		_editorPopupMenu.addSeparator();
		_editorPopupMenu.add(loadOption);
		_editorPopupMenu.add(saveOption);
		_editorPopupMenu.add(clearOption);
		
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

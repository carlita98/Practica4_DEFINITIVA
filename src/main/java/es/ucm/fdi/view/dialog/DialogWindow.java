package es.ucm.fdi.view.dialog;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
/**
 * Creates a DialogWindow that will be used as an atribute in MyDialogWindow
 * @author Carla Martínez y Beatriz Herguedas
 *
 */
class DialogWindow extends JDialog {

	private static final long serialVersionUID = 1L;

	private MyListModel<String> vehicleModel = new MyListModel<>();
	private MyListModel<String> roadModel = new MyListModel<>();
	private MyListModel<String> junctionModel = new MyListModel<>();

	private int _status;
	private JList<String> vehicleList = new JList<>(vehicleModel);
	private JList<String> roadList = new JList<>(roadModel);
	private JList<String> junctionList = new JList<>(junctionModel);

	static final private char _clearSelectionKey = 'c';
	private Border _defaultBorder = BorderFactory.createLineBorder(Color.black, 2);

	public DialogWindow(Frame parent) {
		super(parent, true);
		initGUI();
	}

	/**
	 * Creates and initialize the Dialog
	 */
	private void initGUI() {

		_status = 0;

		setTitle("Generate Reports");
		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		JPanel vehiclePanel = new JPanel(new BorderLayout());
		JPanel roadPanel = new JPanel(new BorderLayout());
		JPanel junctionPanel = new JPanel(new BorderLayout());

		contentPanel.add(vehiclePanel);
		contentPanel.add(roadPanel);
		contentPanel.add(junctionPanel);

		vehiclePanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Vehicles", TitledBorder.LEFT, TitledBorder.TOP));
		roadPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Roads", TitledBorder.LEFT, TitledBorder.TOP));
		junctionPanel.setBorder(
				BorderFactory.createTitledBorder(_defaultBorder, "Junctions", TitledBorder.LEFT, TitledBorder.TOP));

		vehiclePanel.setMinimumSize(new Dimension(100, 100));
		roadPanel.setMinimumSize(new Dimension(100, 100));
		junctionPanel.setMinimumSize(new Dimension(100, 100));
		
		addCleanSelectionListner(vehicleList);
		addCleanSelectionListner(roadList);
		addCleanSelectionListner(junctionList);

		vehiclePanel.add(new JScrollPane(vehicleList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		roadPanel.add(new JScrollPane(roadList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		junctionPanel.add(new JScrollPane(junctionList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				_status = 0;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				_status = 1;
				DialogWindow.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		mainPanel.add(infoPanel, BorderLayout.PAGE_START);

		infoPanel.add(new JLabel("Select items for which you want to process."));
		infoPanel.add(new JLabel("Use '" + _clearSelectionKey + "' to deselect all."));
		infoPanel.add(new JLabel("Use Ctrl+A to select all"));
		infoPanel.add(new JLabel(" "));

		setContentPane(mainPanel);
		setMinimumSize(new Dimension(100, 100));
		setVisible(false);
	}

	private void addCleanSelectionListner(JList<?> list) {
		list.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == _clearSelectionKey) {
					list.clearSelection();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

	}

	public void setData(List<String> vehicle, List<String> road, List<String> junction) {
		vehicleModel.setList(vehicle);
		roadModel.setList(road);
		junctionModel.setList(junction);
	}

	public String[] getSelectedVehicles() {
		int[] indices = vehicleList.getSelectedIndices();
		String[] vehicles = new String[indices.length];
		for(int i=0; i < indices.length; i++) {
			vehicles[i] = vehicleModel.getElementAt(indices[i]);
		}
		return vehicles;
	}

	public String[] getSelectedRoads() {
		int[] indices = roadList.getSelectedIndices();
		String[] roads = new String[indices.length];
		for(int i=0; i < indices.length; i++) {
			roads[i] = roadModel.getElementAt(indices[i]);
		}
		return roads;
	}
	
	public String[] getSelectedJunctions() {
		int[] indices = junctionList.getSelectedIndices();
		String[] junctions = new String[indices.length];
		for(int i=0; i < indices.length; i++) {
			junctions[i] = junctionModel.getElementAt(indices[i]);
		}
		return junctions;
	}

	public int open() {
		setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}

}

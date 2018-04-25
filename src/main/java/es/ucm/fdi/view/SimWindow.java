package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.Simulator.EventType;
import es.ucm.fdi.model.Simulator.Listener;
import es.ucm.fdi.model.Simulator.UpdateEvent;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.util.MultiTreeMap;
import es.ucm.fdi.view.dialog.MyDialogWindow;
import es.ucm.fdi.view.graph.GraphLayout;

public class SimWindow extends JFrame implements Listener {
	// la vista usa el controlador
	private Controller ctrl;
	
	private GraphLayout graph;

	private JLabel downLabel;
	
	// Mensajes de error
	private JOptionPane showError;

	// Escribir informes
	private OutputStream out = new ByteArrayOutputStream();
	private OutputStream outReport = new ByteArrayOutputStream();
	
	// Paneles
	private JPanel supPanel;
	private JPanel infLeftPanel;
	private JPanel infRightPanel;
	private JPanel infPanel;
	private JSplitPane main;

	// Spinner
	private JSpinner steps;
	private JTextField time;
	private JLabel stepsLabel;
	private JLabel timeLabel;

	// Botones y barra
	private SimulatorAction load;
	private SimulatorAction save;
	private SimulatorAction clear;
	private SimulatorAction event;
	private SimulatorAction run;
	private SimulatorAction reset;
	private SimulatorAction generateReport;
	private SimulatorAction deleteReport;
	private SimulatorAction saveReport;
	private SimulatorAction exit;
	private JToolBar toolBar;
	private List<Event> events = new ArrayList<>();

	// Menu
	private JMenu menuFile;
	private JMenu menuSimulator;
	private JMenu menuReport;
	private JMenuBar menuBar;

	// Todo lo relativo a file
	private JFileChooser fc;
	private File currentFile;

	// Componentes de cada panel
	//PopupMenu
	private PopUpMenu eventsEditor = new PopUpMenu();
	private MyDialogWindow dialog;
	private JTextArea reportsArea; // zona de informes
	private TableModelTraffic eventsView; // cola de eventos
	private TableModelTraffic vehiclesTable; // tabla de vehiculos
	private TableModelTraffic roadsTable; // tabla de carreteras
	private TableModelTraffic junctionsTable; // tabla de cruces

	public SimWindow(Controller ctrl, String inFileName) throws FileNotFoundException, IOException {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;
		currentFile = inFileName != null ? new File(inFileName) : null;
		initGUI();
		ctrl.getSim().addSimulatorListener(this);
	}

	private void addPanel() throws FileNotFoundException, IOException {
		supPanel = new JPanel();
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		addEventsEditor();
		addEventsView();
		addReportsArea();
		supPanel.add(new JScrollPane(eventsEditor.get_editor()));
		supPanel.add(eventsView);
		supPanel.add(new JScrollPane(reportsArea));

		infLeftPanel = new JPanel();
		infLeftPanel.setLayout(new BoxLayout(infLeftPanel, BoxLayout.Y_AXIS));
		addVehicleTable();
		addRoadsTable();
		addJunctionsTable();
		infLeftPanel.add(vehiclesTable);
		infLeftPanel.add(roadsTable);
		infLeftPanel.add(junctionsTable);

		infRightPanel = new JPanel(new BorderLayout());
		graph = new GraphLayout(ctrl.getSim().getRoadMap());
		graph.generateGraph();
		infRightPanel.add(graph.get_graphComp());

		infPanel = new JPanel();
		infPanel.setLayout(new BoxLayout(infPanel, BoxLayout.X_AXIS));
		infPanel.add(infLeftPanel);
		infPanel.add(infRightPanel);

		main = new JSplitPane(JSplitPane.VERTICAL_SPLIT, supPanel, infPanel);
		add(main);
	}

	private void initGUI() throws FileNotFoundException, IOException {
		addBar();
		addMenuBar();
		add(downLabel = new JLabel(), BorderLayout.AFTER_LAST_LINE);
		fc = new JFileChooser();
		addPanel();
		setSize(1000, 1000);
		setVisible(true);
		main.setDividerLocation(.33);
	}

	private void addBar() {
		// instantiate actions
		load = new SimulatorAction(Command.Load.getName(), "open.png", "Abrir archivos", KeyEvent.VK_O, "control O", () -> {
			downLabel.setText(Command.Load.toString());
			load();
		});

		save = new SimulatorAction(Command.Save.getName(), "save.png", "Guardar fichero de eventos", KeyEvent.VK_S, "control S",
				() -> {
					downLabel.setText(Command.Save.toString());
					save(eventsEditor.get_editor());
				});

		clear = new SimulatorAction(Command.Clear.getName(), "clear.png", "Limpiar eventos", KeyEvent.VK_C, "control C", () -> {
			downLabel.setText(Command.Clear.toString());
			ctrl.setInputFile(null);
			eventsEditor.get_editor().setText(null);
			eventsView.setElements(Collections.emptyList());
			roadsTable.setElements(Collections.emptyList());
			vehiclesTable.setElements(Collections.emptyList());
			junctionsTable.setElements(Collections.emptyList());
		});

		event = new SimulatorAction(Command.Event.getName(), "events.png", "Insertar eventos", KeyEvent.VK_I, "control I", () -> {
				downLabel.setText(Command.Event.toString());
				ctrl.getSim().reset();
				ctrl.setInputFile(new ByteArrayInputStream(eventsEditor.get_editor().getText().getBytes()));
				ctrl.loadEvents();
		});

		run = new SimulatorAction(Command.Run.getName(), "play.png", "Ejecutar la simulación", KeyEvent.VK_E, "control E", () -> {
			downLabel.setText(Command.Run.toString());
			ctrl.getSim().execute((Integer) steps.getValue(), out);
		});

		reset = new SimulatorAction(Command.Reset.getName(), "reset.png", "Reiniciar", KeyEvent.VK_R, "control R", () -> {
			downLabel.setText(Command.Reset.toString());
			ctrl.getSim().reset();
		});

		steps = new JSpinner();
		SpinnerModel model = new SpinnerNumberModel(1, // initial value
				0, // min
				1000000000, // max
				1);
		steps.setModel(model);
		steps.setPreferredSize(new Dimension(100, 10));
		stepsLabel = new JLabel(" Steps: ");

		time = new JTextField();
		time.setPreferredSize(new Dimension(100, 10));
		time.setText("0");
		time.setEditable(false);
		timeLabel = new JLabel(" Time: ");

		generateReport = new SimulatorAction(Command.GenerateReport.getName(), "report.png", "Generar informes", KeyEvent.VK_G,
				"control G", () -> {
					downLabel.setText(Command.GenerateReport.toString());
					dialog = new MyDialogWindow (ctrl, outReport);
					reportsArea.setText(outReport.toString());
				});

		deleteReport = new SimulatorAction(Command.DeleteReport.getName(), "delete_report.png", "Eliminar informes", KeyEvent.VK_E,
				"control E", () -> {
					downLabel.setText(Command.DeleteReport.toString());
					reportsArea.setText(null);
				});

		saveReport = new SimulatorAction(Command.SaveReport.getName(), "save_report.png", "Guardar informe", KeyEvent.VK_M,
				"control M", () -> {
					downLabel.setText(Command.SaveReport.toString());
						save(reportsArea);
				});

		exit = new SimulatorAction(Command.Exit.getName(), "exit.png", "Salir de la aplicacion", KeyEvent.VK_X, "control X",
				() -> {
					downLabel.setText(Command.Exit.toString());
					System.exit(0);
					});

		toolBar = new JToolBar();
		toolBar.add(load);
		toolBar.add(save);
		toolBar.add(clear);
		toolBar.add(event);
		toolBar.add(run);
		toolBar.add(reset);
		toolBar.add(stepsLabel);
		toolBar.add(steps);
		toolBar.add(timeLabel);
		toolBar.add(time);

		JPanel aux2 = new JPanel();
		aux2.setPreferredSize(new Dimension(20, 1));
		toolBar.add(aux2);

		toolBar.add(generateReport);
		toolBar.add(deleteReport);
		toolBar.add(saveReport);
		toolBar.add(exit);
		JPanel aux = new JPanel();
		aux.setPreferredSize(new Dimension(700, 1));
		toolBar.add(aux);
		add(toolBar, BorderLayout.NORTH);

	}

	private void addMenuBar() {
		// add actions to menubar, and bar to window
		menuFile = new JMenu("File");
		menuFile.add(load);
		menuFile.add(save);
		menuFile.add(saveReport);
		menuFile.add(exit);

		menuSimulator = new JMenu("Simulator");
		menuSimulator.add(reset);
		menuSimulator.add(run);
		menuSimulator.add(event);

		menuReport = new JMenu("Report");
		menuReport.add(generateReport);
		menuReport.add(deleteReport);

		menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuSimulator);
		menuBar.add(menuReport);
		setJMenuBar(menuBar);
	}

	private void addEventsEditor() throws FileNotFoundException, IOException {
		eventsEditor.get_editor().setEditable(true);

		if (currentFile != null) {
			eventsEditor.get_editor().setText(readFile(currentFile));
			eventsEditor.get_editor().setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),
					"Events: " + currentFile.getName()));
		} else
			eventsEditor.get_editor().setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events"));
	}

	private void addReportsArea() {
		reportsArea = new JTextArea(40, 30);
		reportsArea.setEditable(false);
		reportsArea
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Reports"));
	}

	private void addEventsView() {
		String[] columnas = { "#", "Time", "Type" };
		eventsView = new TableModelTraffic(columnas, events);
		eventsView.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events Queue"));

	}

	private void addVehicleTable() {
		String[] columnas = { "ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary" };
		vehiclesTable = new TableModelTraffic(columnas, ctrl.getSim().getRoadMap().getVehiclesRO());
		vehiclesTable.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
	}

	private void addRoadsTable() {
		String[] columnas = { "ID", "Source", "Target", "Length", "Max Speed", "Vehicles" };
		roadsTable = new TableModelTraffic(columnas, ctrl.getSim().getRoadMap().getRoadsRO());
		roadsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
	}

	private void addJunctionsTable() {
		String[] columnas = { "ID", "Green", "Red" };
		junctionsTable = new TableModelTraffic(columnas, ctrl.getSim().getRoadMap().getJunctionsRO());
		junctionsTable.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junctions"));
	}

	public void registered(UpdateEvent ue) {
		// TODO Auto-generated method stub

	}

	public void reset(UpdateEvent ue) {
		events = ue.getEventQueue();
		eventsView.setElements(events);
		eventsView.updated();
		vehiclesTable.setElements(ctrl.getSim().getRoadMap().getVehiclesRO());
		vehiclesTable.updated();
		roadsTable.setElements(ctrl.getSim().getRoadMap().getRoadsRO());
		roadsTable.updated();
		junctionsTable.setElements(ctrl.getSim().getRoadMap().getJunctionsRO());
		junctionsTable.updated();
		time.setText("" + ctrl.getSim().getSimulatorTime());
		
		graph.setRm(ctrl.getSim().getRoadMap());
		graph.generateGraph();
	}

	public void newEvent(UpdateEvent ue) {
		events = ue.getEventQueue();
		eventsView.setElements(events);
		eventsView.updated();
		
		graph.setRm(ctrl.getSim().getRoadMap());
		graph.generateGraph();
	}

	public void advanced(UpdateEvent ue) {
		vehiclesTable.updated();
		roadsTable.updated();
		junctionsTable.updated();
		time.setText("" + ctrl.getSim().getSimulatorTime());
		graph.generateGraph();
	}

	public void error(UpdateEvent ue, String error) {
		showError.showMessageDialog(this, error);
		ctrl.getSim().reset();
		
		graph.setRm(ctrl.getSim().getRoadMap());
		graph.generateGraph();
	}

	public void load() {
		JFileChooser choose = new JFileChooser();
		FileNameExtensionFilter fil = new FileNameExtensionFilter("Files .ini", "ini");
		choose.setFileFilter(fil);

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentFile = fc.getSelectedFile();
			try {
				ctrl.setInputFile(new FileInputStream(currentFile));
				System.out.println("Loading: " + currentFile.getName());
				eventsEditor.get_editor().setText(readFile(currentFile));
				eventsEditor.get_editor().setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2),
						"Events: " + currentFile.getName()));
			} catch (Exception e) { 
			}
		} else {
			downLabel.setText("Load cancelled by user.");
		}
	}

	public void save(JTextArea area){
		JFileChooser choose = new JFileChooser();
		FileNameExtensionFilter fil = new FileNameExtensionFilter("Files .ini", "ini");
		choose.setFileFilter(fil);

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File outFile = fc.getSelectedFile();
			try {
				Files.write(outFile.toPath(), area.getText().getBytes("UTF-8"));
			} catch (Exception e) {		
			}
		} else {
			downLabel.setText("Save cancelled by user.");
		}
	}

	public String readFile(File file) throws FileNotFoundException, IOException {
		Ini read = new Ini();
		read.load(new FileInputStream(file));
		return read.toString();
	}


}

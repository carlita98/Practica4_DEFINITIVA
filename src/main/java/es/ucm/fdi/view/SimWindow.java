package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.Simulator.EventType;
import es.ucm.fdi.model.Simulator.Listener;
import es.ucm.fdi.model.Simulator.UpdateEvent;
import es.ucm.fdi.model.event.Event;
import es.ucm.fdi.view.dialog.MyDialogWindow;
import es.ucm.fdi.view.graph.GraphLayout;

public class SimWindow extends JFrame implements Listener {
	//The view uses controller and roadmap
	private Controller ctrl;
	
	//Sitios para controlar excepciones cargar y salvar 
	//Name columns for the tables 
	private static final String[] eventsViewColumns = { "#", "Time", "Type" };
	private static final String[] vehicleTableColumns = { "ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary" };
	private static final String[] roadTableColumns = { "ID", "Source", "Target", "Length", "Max Speed", "Vehicles" };
	private static final String[] junctionTableColumns = { "ID", "Green", "Red" };
	
	//Grap for the roadmap
	private GraphLayout graph;

	//JLabel placed in the south zone of the screen
	private JLabel downLabel;
	
	//Show the Error Message
	private JOptionPane showError;

	//Writes Reports
	//To call Controller Execute
	private OutputStream out = new ByteArrayOutputStream();
	//To generate the parcial reports
	private OutputStream outReport = new ByteArrayOutputStream();
	
	// Panel
	private JPanel supPanel = new JPanel ();
	private JPanel infLeftPanel = new JPanel ();
	private JPanel infRightPanel = new JPanel (new BorderLayout());
	private JPanel infPanel = new JPanel();
	private JSplitPane main;

	// Spinner and TextField
	private JSpinner steps =  new JSpinner();
	private JTextField time  = new JTextField();
	private JLabel stepsLabel =  new JLabel(" Steps: ");
	private JLabel timeLabel = new JLabel(" Time: ");

	//Buttoms, Menu and ToolBar
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
	private JToolBar toolBar  = new JToolBar();
	private List<Event> events = new ArrayList<>();
	private JMenu menuFile = new JMenu("File");
	private JMenu menuSimulator  = new JMenu("Simulator");
	private JMenu menuReport = new JMenu("Report");
	private JMenuBar menuBar = new JMenuBar();

	//FileChooser and File
	private JFileChooser fc = new JFileChooser();
	private File currentFile;

	//PopupMenu
	private PopUpMenu eventsEditor = new PopUpMenu();
	private JTextArea reportsArea; 
	private TableModelTraffic eventsView;
	private TableModelTraffic vehiclesTable;
	private TableModelTraffic roadsTable;
	private TableModelTraffic junctionsTable;

	public SimWindow(Controller ctrl, String inFileName) throws FileNotFoundException, IOException {
		super("Traffic Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;
		currentFile = inFileName != null ? new File(inFileName) : null;
		initGUI();
		ctrl.getSim().addSimulatorListener(this);
	}

	private void addPanel() throws FileNotFoundException, IOException {
		//Creates the main window distribution
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		addEventsEditor();
		addEventsView();
		addReportsArea();
		supPanel.add(new JScrollPane(eventsEditor.get_editor()));
		supPanel.add(eventsView);
		supPanel.add(new JScrollPane(reportsArea));

		infLeftPanel.setLayout(new BoxLayout(infLeftPanel, BoxLayout.Y_AXIS));
		addVehicleTable();
		addRoadsTable();
		addJunctionsTable();
		infLeftPanel.add(vehiclesTable);
		infLeftPanel.add(roadsTable);
		infLeftPanel.add(junctionsTable);

		graph = new GraphLayout(ctrl.getSim().getRoadMap());
		graph.generateGraph();
		infRightPanel.add(graph.get_graphComp());

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
		addPanel();
		setSize(1000, 1000);
		setVisible(true);
		main.setDividerLocation(.33);
	}

	private void addBar() {
		// instantiate actions
		load = new SimulatorAction(Command.Load.getName(), "open.png", "Open files", KeyEvent.VK_O, "control O", () -> {
			downLabel.setText(Command.Load.toString());
			load();
		});

		save = new SimulatorAction(Command.Save.getName(), "save.png", "Save Events file", KeyEvent.VK_S, "control S",
				() -> {
					downLabel.setText(Command.Save.toString());
					save(eventsEditor.get_editor());
				});

		clear = new SimulatorAction(Command.Clear.getName(), "clear.png", "Clear Events", KeyEvent.VK_C, "control C", () -> {
			downLabel.setText(Command.Clear.toString());
			eventsEditor.get_editor().setText(null);
		});

		event = new SimulatorAction(Command.Event.getName(), "events.png", "Insert Events", KeyEvent.VK_I, "control I", () -> {
				downLabel.setText(Command.Event.toString());
				ctrl.getSim().reset();
				ctrl.setInputFile(new ByteArrayInputStream(eventsEditor.get_editor().getText().getBytes()));
				ctrl.loadEvents();
		});

		run = new SimulatorAction(Command.Run.getName(), "play.png", "Execute the simulation", KeyEvent.VK_E, "control E", () -> {
			downLabel.setText(Command.Run.toString());
			ctrl.getSim().execute((Integer) steps.getValue(), out);
		});

		reset = new SimulatorAction(Command.Reset.getName(), "reset.png", "Reset", KeyEvent.VK_R, "control R", () -> {
			downLabel.setText(Command.Reset.toString());
			ctrl.getSim().reset();
		});

		SpinnerModel model = new SpinnerNumberModel(1,0,1000000000,1);
		steps.setModel(model);
		steps.setPreferredSize(new Dimension(100, 10));

		time.setPreferredSize(new Dimension(100, 10));
		time.setText("0");
		time.setEditable(false);

		generateReport = new SimulatorAction(Command.GenerateReport.getName(), "report.png", "Generar informes", KeyEvent.VK_G,
				"control G", () -> {
					downLabel.setText(Command.GenerateReport.toString());
					new MyDialogWindow (ctrl, outReport);
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

		//Add the action to the toolBar
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
		menuFile.add(load);
		menuFile.add(save);
		menuFile.add(saveReport);
		menuFile.add(exit);

		menuSimulator.add(reset);
		menuSimulator.add(run);
		menuSimulator.add(event);

		menuReport.add(generateReport);
		menuReport.add(deleteReport);

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
		eventsView = new TableModelTraffic(eventsViewColumns, events);
		eventsView.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events Queue"));
	}

	private void addVehicleTable() {
		vehiclesTable = new TableModelTraffic(vehicleTableColumns, ctrl.getSim().getRoadMap().getVehiclesRO());
		vehiclesTable.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
	}

	private void addRoadsTable() {
		roadsTable = new TableModelTraffic(roadTableColumns, ctrl.getSim().getRoadMap().getRoadsRO());
		roadsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
	}

	private void addJunctionsTable() {
		junctionsTable = new TableModelTraffic(junctionTableColumns, ctrl.getSim().getRoadMap().getJunctionsRO());
		junctionsTable.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junctions"));
	}

	public void registered(UpdateEvent ue) {
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
		
		graph.setRm(ctrl.getSim().getRoadMap());
		graph.generateGraph();
	}

	public void error(UpdateEvent ue, String error) {
		showError.showMessageDialog(this, error);
		ctrl.getSim().reset();
		
		graph.setRm(ctrl.getSim().getRoadMap());
		graph.generateGraph();
	}

	public void load()  {
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
				ctrl.getSim().fireUpdateEvent(EventType.ERROR, "There was a problem with file" + currentFile.getName());
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
				ctrl.getSim().fireUpdateEvent(EventType.ERROR, "There was a problem with file" + outFile.getName());
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

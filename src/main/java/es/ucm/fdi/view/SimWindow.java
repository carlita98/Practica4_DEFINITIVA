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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.model.Simulator.Listener;
import es.ucm.fdi.model.Simulator.UpdateEvent;
import es.ucm.fdi.model.event.Event;
public class SimWindow extends JFrame implements Listener {
	private Controller ctrl; // la vista usa el controlador 
	
	//Paneles
	private JPanel supPanel;
	private JPanel infLeftPanel;
	private JSplitPane main;
	
	//Spinner
	private JSpinner steps;
	private JTextField time;
	private JLabel stepsLabel;
	private JLabel timeLabel;
	
	//Botones y barra 
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
	private List<Event> events = new ArrayList <>();
	
	//Menu
	private JMenu menuFile;
	private JMenu menuSimulator;
	private JMenu menuReport;
	private JMenuBar menuBar;
	
	//Todo lo relativo a file
	private JFileChooser fc; 
	private File currentFile; 
	
	//Componentes de cada panle
	private JTextArea eventsEditor; // editor de eventos 
	private JTextArea reportsArea; // zona de informes 
	private TableModelTraffic eventsView; // cola de eventos 
	private TableModelTraffic vehiclesTable; // tabla de vehiculos 
	private TableModelTraffic roadsTable; // tabla de carreteras 
	private TableModelTraffic junctionsTable; // tabla de cruces 
	 
	public SimWindow(Controller ctrl,String inFileName) throws FileNotFoundException, IOException {   
		super("Traffic Simulator");    
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;    
		currentFile = inFileName != null ? new File(inFileName) : null; 
		//reportsOutputStream = new JTextAreaOutputStream(reportsArea,null); 
		//ctrl.setOutputStream(reportsOutputStream); // ver sección 8    
		initGUI();    
		ctrl.getSim().addSimulatorListener(this);
	}
	
	private void addPanel() throws FileNotFoundException, IOException {
		supPanel = new JPanel();
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		addEventsEditor();
		addEventsView();
		addReportsArea();
		supPanel.add(new JScrollPane(eventsEditor));
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
		
		main = new JSplitPane(JSplitPane.VERTICAL_SPLIT,supPanel, infLeftPanel);
		add(main);	
	}

	private void initGUI() throws FileNotFoundException, IOException {
		addBar();
		addMenuBar();
		fc = new JFileChooser();
		addPanel();
		setSize(1000, 1000);
		setVisible(true);
		main.setDividerLocation(.33);
	}
	
	private void addBar () {
		// instantiate actions
		load = new SimulatorAction(
				"Abrir", "open.png", "Abrir archivos",
				KeyEvent.VK_O, "control O", 
				()-> {try {load();} catch (IOException e) {
						e.printStackTrace();
					}});
		
		save = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_S, "control S", 
				()-> System.err.println("guardando..."));
		
		clear = new SimulatorAction(
				"Limpiar", "clear.png", "Limpiar eventos",
				KeyEvent.VK_C, "control C",
				()->{
					ctrl.setInputFile(null);
					eventsEditor.setText(null);
					eventsView.setElements(Collections.emptyList());
				});
		
		event = new SimulatorAction(
				"Insertar", "events.png", "Insertar eventos",
				KeyEvent.VK_I, "control I", 
				()->{
					try {
						ctrl.setInputFile(new ByteArrayInputStream(eventsEditor.getText().getBytes()));
						ctrl.loadEvents();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
		
		run = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar la simulación",
				KeyEvent.VK_E, "control E", 
				()->{
					ByteArrayOutputStream report = new ByteArrayOutputStream();
					ctrl.getSim().execute(1, report);
					reportsArea.setText(report.toString());
				});
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar",
				KeyEvent.VK_R, "control R", 
				()->System.err.println("reiniciando..."));
		
		steps = new JSpinner();
		((SpinnerNumberModel)steps.getModel()).setMinimum(0);
		steps.setPreferredSize(new Dimension(100,10));
		stepsLabel = new JLabel(" Steps: ");
		
		time = new JTextField();
		time.setPreferredSize(new Dimension (100, 10));
		time.setText("0");
		time.setEditable(false);
		timeLabel =  new JLabel(" Time: ");
		
		generateReport = new SimulatorAction(
				"Generar Informe", "report.png", "Generar informes",
				KeyEvent.VK_A, "control G", 
				()->System.err.println("generando informes..."));
		
		deleteReport = new SimulatorAction(
				"Eliminar Informes", "delete_report.png", "Eliminar informes",
				KeyEvent.VK_A, "control shift E", 
				()->System.err.println("eliminando informes..."));
		
		saveReport = new SimulatorAction(
				"Guardar Informes", "save_report.png", "Guardar informes",
				KeyEvent.VK_A, "control shift S", 
				()->System.err.println("guardando informe..."));
		
		exit = new SimulatorAction(
				"Salir", "exit.png", "Salir de la aplicacion",
				KeyEvent.VK_A, "control shift X", 
				()-> System.exit(0));
		
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
		toolBar.add(generateReport);
		toolBar.add(deleteReport);
		toolBar.add(saveReport);
		toolBar.add(exit);
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
		eventsEditor = new JTextArea(40,30);
		eventsEditor.setEditable(true);
	    eventsEditor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events"));
	    
	    if (currentFile != null) {      
	    	eventsEditor.setText(readFile(currentFile));      
	    }
	}
	private void addReportsArea() {
		reportsArea =new JTextArea(40,30);
		reportsArea.setEditable(false);
	    reportsArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Reports"));
	}
	
	private void addEventsView() {
		String[] columnas = {"#", "Time", "Type"};
		eventsView = new TableModelTraffic (columnas, events);
		eventsView.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Events Queue"));
		
	}

	private void addVehicleTable () {
		String []columnas = {"ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary"};
		vehiclesTable = new TableModelTraffic (columnas,ctrl.getSim().getM().getVehicles());
		vehiclesTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Vehicles"));
	}
	
	private void addRoadsTable () {
		String []columnas = {"ID", "Source", "Target", "Length", "Max Speed", "Vehicles"};
		roadsTable = new TableModelTraffic (columnas, ctrl.getSim().getM().getRoads());
		roadsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Roads"));
	}
	
	private void addJunctionsTable () {
		String []columnas = {"ID", "Green", "Red"};
		junctionsTable = new TableModelTraffic (columnas, ctrl.getSim().getM().getJunctions());
		junctionsTable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Junctions"));
	}
	@Override
	public void registered(UpdateEvent ue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset(UpdateEvent ue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newEvent(UpdateEvent ue) {
		events = ue.getEventQueue();
		eventsView.setElements(events);
		eventsView.updated();
	}

	@Override
	public void advanced(UpdateEvent ue) {
		vehiclesTable.updated();
		roadsTable.updated();
		junctionsTable.updated();
	}

	@Override
	public void error(UpdateEvent ue, String error) {
		// TODO Auto-generated method stub
		
	}
	
	public void load() throws FileNotFoundException, IOException {
	/*	File initial = new File("");
		if (initial.exists() && initial.isDirectory()) {
			fc.setCurrentDirectory(initial);		
		}*/
		
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION	) {
			currentFile = fc.getSelectedFile();
			ctrl.setInputFile(new FileInputStream(currentFile));
			System.out.println("Loading: "+ currentFile.getName());
			eventsEditor.setText(readFile(currentFile));  
		} 
		else{
			System.out.println("Load cancelled by user.");
		}
	}

	
	public String readFile(File file) throws FileNotFoundException, IOException {
		Ini read = new Ini ();
		read.load (new FileInputStream (file));
		return read.toString();
	}
}

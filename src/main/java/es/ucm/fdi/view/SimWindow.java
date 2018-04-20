package es.ucm.fdi.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import org.junit.runner.Describable;

import es.ucm.fdi.control.Controller;
import es.ucm.fdi.model.events.Event;
import es.ucm.fdi.model.trafficSimulator.Simulator.Listener;
import es.ucm.fdi.model.trafficSimulator.Simulator.UpdateEvent;

public class SimWindow extends JFrame implements Listener {
	private Controller ctrl; // la vista usa el controlador 
	
	//Botones y barra 
	private SimulatorAction load;
	private SimulatorAction save;
	private SimulatorAction clear;
	private SimulatorAction redirectOutput;
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
	private JTable eventsTable; // cola de eventos 
	private JTable vehiclesTable; // tabla de vehiculos 
	private JTable roadsTable; // tabla de carreteras 
	private JTable junctionsTable; // tabla de cruces 
	 
	public SimWindow(Controller ctrl,String inFileName) {   
		super("Traffic Simulator");    
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.ctrl = ctrl;    
		currentFile = inFileName != null ? new File(inFileName) : null;    
		//reportsOutputStream = new JTextAreaOutputStream(reportsArea,null); 
		//ctrl.setOutputStream(reportsOutputStream); // ver sección 8    
		initGUI();    
		ctrl.getSim().addSimulatorListener(this);
	}
	
	private void addSupPanel() {
		JPanel supPanel = new JPanel();
		supPanel.setLayout(new BoxLayout(supPanel, BoxLayout.X_AXIS));
		addEventsEditor(supPanel);
		addEventsView(supPanel);
		addReportsArea(supPanel);
		add(supPanel);
	}
	
	private void initGUI() {
		addBar();
		addMenuBar();
		fc = new JFileChooser();
		addSupPanel();
		setSize(1000, 1000);
		setVisible(true);
	}
	
	private void addBar () {
		// instantiate actions
		load = new SimulatorAction(
				"Abrir", "open.png", "Abrir archivos",
				KeyEvent.VK_O, "control O", 
				()-> System.err.println("abriendo..."));
		
		save = new SimulatorAction(
				"Guardar", "save.png", "Guardar cosas",
				KeyEvent.VK_S, "control S", 
				()-> System.err.println("guardando..."));
		
		clear = new SimulatorAction(
				"Limpiar", "clear.png", "Limpiar eventos",
				KeyEvent.VK_C, "control C",
				()->System.err.println("limpiando..."));
		
		redirectOutput = new SimulatorAction(
				"Insertar", "events.png", "Insertar eventos",
				KeyEvent.VK_I, "control I", 
				()->System.err.println("insertando..."));
		
		run = new SimulatorAction(
				"Ejecutar", "play.png", "Ejecutar la simulación",
				KeyEvent.VK_E, "control E", 
				()->System.err.println("ejecutando..."));
		
		reset = new SimulatorAction(
				"Reiniciar", "reset.png", "Reiniciar",
				KeyEvent.VK_R, "control R", 
				()->System.err.println("reiniciando..."));
		
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
		toolBar.add(redirectOutput);
		toolBar.add(run);
		toolBar.add(reset);
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
		menuSimulator.add(redirectOutput);
		
		menuReport = new JMenu("Report");
		menuReport.add(generateReport);
		menuReport.add(deleteReport);
		
		menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuSimulator);
		menuBar.add(menuReport);
		setJMenuBar(menuBar);
	}

	private void addEventsEditor(JPanel contentPanel_1) {
		eventsEditor = new JTextArea(40,30);
		eventsEditor.setLineWrap(true);
		eventsEditor.setWrapStyleWord(true);
	    //Border
	    Border b = BorderFactory.createLineBorder(Color.black, 2);
	    eventsEditor.setBorder(BorderFactory.createTitledBorder(b, "Events"));
	    contentPanel_1.add(eventsEditor);
	    contentPanel_1.add(new JScrollPane( eventsEditor,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}
	private void addReportsArea(JPanel contentPanel_3) {
		reportsArea =new JTextArea(40,30);
		reportsArea.setEditable(false);
		reportsArea.setLineWrap(true);
		reportsArea.setWrapStyleWord(true);
	    //Border
	    Border b = BorderFactory.createLineBorder(Color.black, 2);
	    reportsArea.setBorder(BorderFactory.createTitledBorder(b, "Reports"));
	    contentPanel_3.add(reportsArea);
	    contentPanel_3.add(new JScrollPane( reportsArea,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
	}
	
	private void addEventsView(JPanel contentPanel_2) {
		// Crea un JPanel para este componente    
		// Pon el borde en el JPanel    
		String[] columnas = {"#", "Time", "Type"};
		JPanel eventsView = new TableModelTraffic (columnas, events);
		//Border 
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		eventsTable.setBorder(BorderFactory.createTitledBorder(b, "Events Queue"));
		contentPanel_2.add(eventsTable);
		contentPanel_2.add(new JScrollPane( eventsTable,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advanced(UpdateEvent ue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(UpdateEvent ue, String error) {
		// TODO Auto-generated method stub
		
	}
}

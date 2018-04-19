package es.ucm.fdi.control;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.*;
import java.io.File;
import java.io.OutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import es.ucm.fdi.model.RoadMap.RoadMap;
import es.ucm.fdi.model.trafficSimulator.Simulator;

public class SimWindow extends JFrame{
	private Controller ctrl; // la vista usa el controlador 
	 
	//Paneles
	private JPanel mainPanel; 
	private JPanel panel1; 
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	
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
	 
	public SimWindow(/*Controller ctrl*/) {   
		super("Traffic Simulator");    
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		//this.ctrl = ctrl;    
		//currentFile = inFileName != null ? new File(inFileName) : null;    
		//reportsOutputStream = new JTextAreaOutputStream(reportsArea,null); 
		//ctrl.setOutputStream(reportsOutputStream); // ver sección 8    
		initGUI();    
		//model.addObserver(this); 
	}
	
	void initGUI() {
		addPanels();
		JSplitPane topSplit1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel1, panel2);
		JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,topSplit1, panel3);
		JSplitPane bottomupleftSplit = new JSplitPane (JSplitPane.VERTICAL_SPLIT, panel4, panel5);
		JSplitPane bottomleftSplit = new JSplitPane (JSplitPane.VERTICAL_SPLIT, bottomupleftSplit, panel6);
		JSplitPane bottomSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,bottomleftSplit, panel7);
		JSplitPane window = new JSplitPane (JSplitPane.VERTICAL_SPLIT, topSplit, bottomSplit);
		add(window);


		addBar();
		addMenuBar();
		fc = new JFileChooser();
		addEventsEditor(panel1);
		addEventsView(panel2);
		addReportsArea(panel3);
		setSize(1000, 1000);
		setVisible(true);
		

		//Los divider están mal a saber como se hacen
		window.setDividerLocation(.3f);
		topSplit.setDividerLocation(.6f);
		topSplit.setDividerLocation(.5f);
		bottomSplit.setDividerLocation(.5f);
		bottomleftSplit.setDividerLocation(.7f);
		bottomupleftSplit.setDividerLocation(.5f);

	}
	
	private void addPanels() {
		mainPanel = new JPanel ();
		add (mainPanel);
		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel4 = new JPanel();
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		panel5 = new JPanel();
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
		panel6 = new JPanel();
		panel6.setLayout(new BoxLayout(panel6, BoxLayout.Y_AXIS));
		panel7 = new JPanel();
		panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
		mainPanel.add(panel1, BorderLayout.CENTER);
		mainPanel.add(panel2, BorderLayout.CENTER);
		mainPanel.add(panel3, BorderLayout.CENTER);
		mainPanel.add(panel4, BorderLayout.CENTER);
		mainPanel.add(panel5, BorderLayout.CENTER);
		mainPanel.add(panel6, BorderLayout.CENTER);
		mainPanel.add(panel7, BorderLayout.CENTER);
		
		
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
		EventsTableModel eventsTableModel = new EventsTableModel();    
		eventsTable = new JTable(eventsTableModel); 
		//Border 
		 Border b = BorderFactory.createLineBorder(Color.black, 2);
		eventsTable.setBorder(BorderFactory.createTitledBorder(b, "Events Queue"));
		contentPanel_2.add(eventsTable);
		contentPanel_2.add(new JScrollPane( eventsTable,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS ,
	    		JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		
	}
}

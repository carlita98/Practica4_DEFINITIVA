package es.ucm.fdi.view.graph;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.object.*;

/**
 * Graphic component that designs a graph and generates it
 */
public class GraphLayout extends JFrame{
	
	public GraphComponent _graphComp = new GraphComponent();
	private RoadMap rm;
	
	/**
	 * Constructor of the class
	 * @param rm
	 * @throws HeadlessException
	 */
	public GraphLayout(RoadMap rm) throws HeadlessException {
		super();
		this.rm = rm;
	}

	public GraphComponent get_graphComp() {
		return _graphComp;
	}
	
	public void setRm(RoadMap rm) {
		this.rm = rm;
	}

	/**
	 * Generates a graph using the RoadMap
	 */
	public void generateGraph() {

		Graph g = new Graph();
		Map<Junction, Node> js = new HashMap<>();
		for (Junction j : rm.getJunctions()) {
			Node n = new Node(j.getId());
			js.put(j, n);
			//To convert Junction into Node in edges
			g.addNode(n);
		}
		for (Road r: rm.getRoadsRO()) {
			boolean green= false;
			if (r.getTarget().getIncomingRoadList().get(r.getTarget().getCurrentIncoming()) == r) {
				green = true;
			}
			Edge e = new Edge(r.getId(), js.get(r.getSource()), js.get(r.getTarget()), r.getLength(), green);	
			for(Vehicle v: r.getVehicleList().innerValues()) {
				e.addDot( new Dot(v.getId() , v.getRoadLocation()));
			}
			g.addEdge(e);
		}
		
		_graphComp.setGraph(g);

	}

}

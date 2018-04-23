package es.ucm.fdi.view.graphlayout;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import es.ucm.fdi.model.RoadMap;
import es.ucm.fdi.model.object.*;

public class GraphLayout extends JFrame{
	
	public GraphComponent _graphComp;
	private RoadMap rm;
	
	public void generateGraph() {

		Graph g =new Graph();
		Map<Junction, Node> js = new HashMap<>();
		for (Junction j : rm.getJunctions()) {
			Node n =new Node(j.getId());
			js.put(j, n);
			// <-- para convertir Junction a Node en aristas
			g.addNode(n);
		}
		for (Road r: rm.getRoadsRO()) {
			Edge e = new Edge(r.getId(), js.get(r.getSource()), js.get(r.getTarget()), r.getLength());	
			for(Vehicle v: rm.getVehiclesRO()) {
				e.addDot( new Dot(v.getId() , v.getRoadLocation()));
			}
			g.addEdge(e);
		}
		
		_graphComp.setGraph(g);
	}

}

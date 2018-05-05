package es.ucm.fdi.launcher;

import es.ucm.fdi.model.Simulator.Listener;
import es.ucm.fdi.model.Simulator.UpdateEvent;
/**
 * Listener of Batch mode
 * @author Carla Martínez, Beatriz Herguedas
 *
 */
public class BatchListener implements Listener{

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
		System.err.println(error);
	}

}

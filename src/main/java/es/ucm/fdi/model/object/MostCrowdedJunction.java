package es.ucm.fdi.model.object;

import java.util.Map.Entry;

import es.ucm.fdi.model.object.JunctionWithTimeSlice.IncomingRoadWithTimeSlice;

public class MostCrowdedJunction extends JunctionWithTimeSlice {

	public MostCrowdedJunction(String id, String string) {
		super(id, string);
	}

	public void addInRoadQueue(Road r) {
		incomingMap.put(r, new IncomingRoadWithTimeSlice(0, 0));
	}

	public void switchLights() {
            IncomingRoadWithTimeSlice ir = currentIR();
            if(ir.timeIsOver()){
                // red light
                updateCurrentIR(); // incomingRoad (index) changed
                ir = currentIR();
                ir.intervalTime = Math.max(ir.getNumberOfVehicles()/2, 1);
                ir.timeUnits = 0;
            }
	}
        
        private void updateCurrentIR(){
             int myBestRoad = (currentIncoming + 1) % incomingRoadList.size();
            for(int i = 0, j = myBestRoad; i < incomingRoadList.size()-1; i++, 
                    j = (j+1)% incomingRoadList.size()){
                if(incomingMap.get(incomingRoadList.get(myBestRoad)).getNumberOfVehicles()
                  == incomingMap.get(incomingRoadList.get(j)).getNumberOfVehicles() ){
                    myBestRoad = Math.min(myBestRoad, j);
                }
                else if(incomingMap.get(incomingRoadList.get(myBestRoad)).getNumberOfVehicles()
                  < incomingMap.get(incomingRoadList.get(j)).getNumberOfVehicles() ){
                     myBestRoad = j;
                }
            }
            currentIncoming = myBestRoad;
         }
}

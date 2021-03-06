/**
 * 
 */
package jModel;

import java.util.Collections;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * @author Chris West
 *
 */

public class Controller {
	
	public static int timestep = 0; 
	
	public Controller(){
	
	} 
	
@ScheduledMethod(start = 1, interval = 1)   //this is some Java-based stuff which schedules the start and interval of runs
public void step(){ //the step function

	
	Collections.shuffle(JModelBuilder.consumers);
	
	for (int i = 0; i<JModelBuilder.consumers.size();i++){  //for all consumers that have been set up NOTE: NOT SURE THIS IS PROPER CODE YET
		double endowment = JModelBuilder.consumers.get(i).getEndowment();  //extract the endowment of that consumer from the getEndowment function
		double maxUtility = JModelBuilder.consumers.get(i).getMaxUtility(); //extract the maximum utility of the consumer from the getMaxUtility function
	}
	
	JModelBuilder.auctioneer.get(0).calculateDemand();
	JModelBuilder.auctioneer.get(0).setNewPrices();
	
	
	
}

	public static int getStep(){
		return (int) RunEnvironment.getInstance().getCurrentSchedule().getTickCount();
	}
}
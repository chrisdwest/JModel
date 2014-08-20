/**
 * 
 */
package jModel;

import java.util.Collections;

import jModel.JModelBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * @author Chris West
 *
 */
public class Controller {
	
	public Controller(){
	
	} //PRETTY SURE THIS CODE IS NONSENSE AT THE MOMENT
	
	@ScheduledMethod(start = 1, interval = 1)   //this is some Java-based stuff which schedules the start and interval of runs
	public void step(){ //the step function
		
		Collections.shuffle(JModelBuilder.consumers);
		
		for (int i = 0; i<JModelBuilder.consumers.size();i++){  //for all consumers that have been set up NOTE: NOT SURE THIS IS PROPER CODE YET
			//double endowment = JModelBuilder.consumers.get(i).getEndowment();  //extract the endowment of that consumer from the getEndowment function
			//System.out.println(endowment);
			double maxUtility = JModelBuilder.consumers.get(i).getMaxUtility(); //extract the maximum utility of the consumer from the getMaxUtility function
		}
		
		JModelBuilder.auctioneer.get(0).calculateDemand();
		JModelBuilder.auctioneer.get(0).setNewPrices();
		
				
		
		
	}

}

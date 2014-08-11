/**
 * 
 */
package jModel;

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
		
		for (int i = 0; i<JModelBuilder.consumers.size();i++){  //for all consumers that have been set up NOTE: NOT SURE THIS IS PROPER CODE YET
			double endowment = JModelBuilder.consumers.get(i).getEndowment();  //extract the endowment of that consumer from the getEndowment function
			//System.out.println(endowment);
			double maxUtility = JModelBuilder.consumers.get(i).getMaxUtility(); //extract the maximum utility of the consumer from the getMaxUtility function
		}
		
		
		
		for (int i = 0; i<JModelBuilder.goods.size(); i++){ //for all goods that have been set up in the model
			
			//System.out.println(JModelBuilder.goods.get(i).getPrice());
			double newPrice = 15.0;  //declare a newprice of 15.
			JModelBuilder.goods.get(i).setPrice(newPrice);  //set all goods to a price of newPrice
			//System.out.println(JModelBuilder.goods.get(i).getPrice());
			//JModelBuilder.goods.get(i).getPrice();
			//System.out.println(0.0);
			}
		
		
		
	}

}

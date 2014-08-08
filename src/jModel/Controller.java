/**
 * 
 */
package jModel;

import jModel.JModelBuilder;
import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * @author Chris
 *
 */
public class Controller {
	
	public Controller(){
	
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	public void step(){
		
		for (int i = 0; i<JModelBuilder.consumers.size();i++){ 
			double endowment = JModelBuilder.consumers.get(i).getEndowment();
			//System.out.println(endowment);
			double maxUtility = JModelBuilder.consumers.get(i).getMaxUtility();
		}
		
		
		
		for (int i = 0; i<JModelBuilder.goods.size(); i++){
			
			//System.out.println(JModelBuilder.goods.get(i).getPrice());
			double newPrice = 15.0;
			JModelBuilder.goods.get(i).setPrice(newPrice);
			//System.out.println(JModelBuilder.goods.get(i).getPrice());
			//JModelBuilder.goods.get(i).getPrice();
			//ystem.out.println(0.0);
			}
		
		
		
	}

}

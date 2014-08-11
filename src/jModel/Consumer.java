/**
 * 
 */
package jModel;

import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;

//import repast.simphony.engine.schedule.ScheduledMethod;




/**
 * @author Chris West
 *
 */
public class Consumer {
	
	private int consumerIDNumber;  //holds consumer ID number
	//private ArrayList<Good> goodsOwned;
	private int[] preference; //holds preference array
	private ArrayList<Integer> goodsAllocated; //arraylist holds allocated good details (by good ID number)
	double totalEndowment; //holds total endowment of the consumer	 
	double maxUtility; //holds maximum utility of the consumer

	public Consumer(int consumerIDNumber, int[] preference, ArrayList<Integer> goodsAllocated){ //initialisation stuff
		this.consumerIDNumber = consumerIDNumber; 
		this.preference = preference;
		this.goodsAllocated = goodsAllocated;
		
	}
	
	public int getConsumerIDNumber(){
		return consumerIDNumber;
	}
	
	public int getPreference(){
		
		return preference[9]; //for some reason i have set this to get only the last (10th) item in the preference list??
	}
	
	public int getGoodID(){
		
		return goodsAllocated.get(0); //and for some reason i have set this to get the first item in the goods list???
	}
	
	private double endowment(ArrayList<Good> goods, ArrayList<Integer> goodsAllocated){ //function has all Goods in the goods arraylist pass in, and the goodsAllocated array of the consumer 
		
		totalEndowment = 0.0; //total endowment set to zero
		double goodPrice = 0; //double called goodPrice created an set to zero
		
		for (int i = 0; i<goodsAllocated.size(); i++){ //for all goods allocated to the consumer
			for(int j = 0; j<JModelBuilder.getGoodAmount();j++){ //for all the goods in the model
				int tmpGoodID = JModelBuilder.goods.get(j).getID(); //gets the ID of the j'th item in the 'goods' arraylist
				if (tmpGoodID == goodsAllocated.get(i)){ //if this ID matches the id of the ID of the good i that is allocated to the consumer
					goodPrice = JModelBuilder.goods.get(j).getPrice(); //then get the price of this good
				}
				
			}
			totalEndowment = totalEndowment + goodPrice; //add this price to the totalEndowment (after looping, totalEndowment will equal sum of prices of all goods allocated
			
		}
		
		//System.out.println(total_endowment);
		return totalEndowment;
	}
	
	private double maxUtility(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){  //passes arraylist of Goods, the consumer preferences, and the allocated goods
		
		double endowment = 0;
		
		
		
		double theoreticalMax = 0; 
		endowment = endowment(goods, goodsAllocated); //calls endowment function and sets 'endowment' to returned double
		
		for (int i = 0; i<=JModelBuilder.goodTypes; i++){ //for all good types
			//current doesn't do anything
		}
		
		
		System.out.println(endowment);  //think these 5 lines were just for error checking??
		int firstPref = preference[0];
		System.out.println(firstPref);
		double priceOfFirstPref = JModelBuilder.goodTypePrice.get(firstPref);
		System.out.println(priceOfFirstPref);
		
		return maxUtility;
	}
	
	/*private void changePrices(ArrayList<Good> goods){
		for (int i = 0; i<JModelBuilder.goods.size(); i++){
		JModelBuilder.goods.get(i).setPrice(newPrice);
		System.out.println(0.0);
		}
		System.out.println(1.0);
	}*/
		
	//@ScheduledMethod(start = 1, interval = 1)
	
	public double getMaxUtility(){
		maxUtility(JModelBuilder.goods, preference, goodsAllocated); //runs maxUtility
		return maxUtility;
	}
	
	public double getEndowment() {
		
		endowment(JModelBuilder.goods, goodsAllocated); //runs endowment
		
		//changePrices(JNEModelBuilder.goods);
		
		//System.out.println(total_endowment);
		
		return totalEndowment;
		//Good.setGoodPrices(JNEModelBuilder.goodTypePrice);
		
		
		//for (int i=0; i<JNEModelBuilder.getGoodAmount(); i++){
		//System.out.println(JNEModelBuilder.goods.get(0));
			//JNEModelBuilder.goods.get(0).setPrice();
		//	System.out.println(JNEModelBuilder.goods.get(i).getPrice());
			//a.setPrice();
		//}
	  
		//endowment(JNEModelBuilder.goods, goodsAllocated);
		//System.out.println(total_endowment);
	}
	
}

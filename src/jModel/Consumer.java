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
	
	private int consumerIDNumber;
	//private ArrayList<Good> goodsOwned;
	private int[] preference;
	private ArrayList<Integer> goodsAllocated;
	double totalEndowment;
	double maxUtility;

	public Consumer(int consumerIDNumber, int[] preference, ArrayList<Integer> goodsAllocated){
		this.consumerIDNumber = consumerIDNumber;
		this.preference = preference;
		this.goodsAllocated = goodsAllocated;
		
	}
	
	public int getConsumerIDNumber(){
		return consumerIDNumber;
	}
	
	public int getPreference(){
		
		return preference[9];
	}
	
	public int getGoodID(){
		
		return goodsAllocated.get(0);
	}
	
	private double endowment(ArrayList<Good> goods, ArrayList<Integer> goodsAllocated){
		
		totalEndowment = 0.0;
		double goodPrice = 0;
		
		for (int i = 0; i<goodsAllocated.size(); i++){
			for(int j = 0; j<JModelBuilder.getGoodAmount();j++){
				int tmpGoodID = JModelBuilder.goods.get(j).getID();
				if (tmpGoodID == goodsAllocated.get(i)){
					goodPrice = JModelBuilder.goods.get(j).getPrice();
				}
				
			}
			totalEndowment = totalEndowment + goodPrice;
			
		}
		
		//System.out.println(total_endowment);
		return totalEndowment;
	}
	
	private double maxUtility(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){
		
		double endowment = 0;
		
		
		
		double theoreticalMax = 0; 
		endowment = endowment(goods, goodsAllocated);
		
		for (int i = 0; i<=JModelBuilder.goodTypes; i++){
			
		}
		
		
		System.out.println(endowment);
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
		maxUtility(JModelBuilder.goods, preference, goodsAllocated);
		return maxUtility;
	}
	
	public double getEndowment() {
		
		endowment(JModelBuilder.goods, goodsAllocated);
		
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

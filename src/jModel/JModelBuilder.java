/**
 * 
 */
package jModel;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.HashMap;
//import java.util.List;
//import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.dataLoader.ContextBuilder;
//import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.random.RandomHelper;
import repast.simphony.util.SimUtilities;


/**
 * @author Chris West
 *
 */
public class JModelBuilder implements ContextBuilder<Object> {

	/* (non-Javadoc)
	 * @see repast.simphony.dataLoader.ContextBuilder#build(repast.simphony.context.Context)
	 */
	
	public static int goodAmount = 10;
	public static int goodTypes = 10;
	public static HashMap<Integer, Double> goodLookUp = new HashMap<Integer, Double>();
	public static HashMap<Integer, Double> goodTypePrice = new HashMap<Integer, Double>();
	public static ArrayList<Good> goods = new ArrayList<Good>();
	public static ArrayList<Consumer> consumers = new ArrayList<Consumer>();
	
	@Override
	public Context build(Context<Object> context) {
		context.setId("jNEModel");
		
		context.add(new Controller());
		
		
		ArrayList<Integer> goodTypeHolder = new ArrayList<Integer>();
		
		
		int total_consumers = 10;
				
		
		
		
		for (int i=0; i < goodTypes; i++){
			int goodType = i;
			goodTypeHolder.add(i);
			double price = RandomHelper.nextDoubleFromTo(0.0, 10.0);
			goodTypePrice.put(goodType, price);
			
		}
							
		ArrayList<Integer> goodsLeft = new ArrayList<Integer>();
		
		for (int i=0; i < goodAmount; i++){ 
			int goodID = i;
			goodsLeft.add(i);
			int goodTypeSelect = RandomHelper.nextIntFromTo(0,goodTypes-1);
			Good a = new Good(goodTypePrice.get(goodTypeSelect), goodTypeSelect, goodID);
			context.add(a);
			goods.add(a);
			goodLookUp.put(goodID,goodTypePrice.get(goodTypeSelect));
			//System.out.println(i);
			//System.out.println(goodTypePrice.get(goodTypeSelect));
		}
		
		
		
		
		
		int maxGoods = 1; //maxGoods multiplied by consumers should be <= goodAmount to ensure that all Consumers have at least 1 good.
		int minGoods = 1;
		
		for (int i=0; i < total_consumers; i++){
			SimUtilities.shuffle(goodTypeHolder,  RandomHelper.getUniform());
			int[] pref = new int[goodTypes];
			for (int j = 0; j<goodTypes; j++){
				pref[j] = goodTypeHolder.get(j); 
			}
			
			int goodsToAllocate = RandomHelper.nextIntFromTo(minGoods,maxGoods);
			
			ArrayList<Integer> goodsAllocated = new ArrayList<Integer>();
			
			for (int j = 0; j<goodsToAllocate; j++){
								
				int rand = RandomHelper.nextIntFromTo(0,goodsLeft.size()-1);
				
				goodsAllocated.add(goodsLeft.get(rand));
				
				goodsLeft.remove(rand);
			}
			//System.out.println(goodsAllocated);
			//System.out.println(goodTypeHolder);
			//System.out.println(goodsLeft);
			
			Consumer a = new Consumer(i, pref, goodsAllocated);
			context.add(a);
			consumers.add(a);
			//System.out.println(context.getAgentTypes());
		}
			
		
		
		
		return context;
		
	}
	
	public HashMap<Integer, Double> goodLookUp(){
		
		return goodLookUp;
	}
	
	public HashMap<Integer, Double> goodTypePrice(){
		return goodTypePrice;
	}
	
	public static int getGoodAmount() {
		return goodAmount;
	}
			
}



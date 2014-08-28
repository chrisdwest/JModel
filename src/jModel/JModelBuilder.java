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
	
	public static int goodAmount = 1000; //on set-up there are 10 goods in total
	public static int goodTypes = 10;  //on set-up there are 10 types of goods in total
	public static HashMap<Integer, Double> goodLookUp = new HashMap<Integer, Double>();  //declares a hashmap to allow the details of a specific good ID to be looked up
	public static HashMap<Integer, Double> goodTypePrice = new HashMap<Integer, Double>(); //declares a hashmap to allow the price of a good type to be looked up
	public static ArrayList<Good> goods = new ArrayList<Good>(); //an arraylist storing 'Good' objects
	public static ArrayList<Consumer> consumers = new ArrayList<Consumer>(); //an arraylist storing 'Consumer' objects
	public static ArrayList<Auctioneer> auctioneer = new ArrayList<Auctioneer>();
	public static ArrayList<Integer> goodTypeAllocated = new ArrayList<Integer>();
	//public static ArrayList<Integer> goodsAllocatedByType = new ArrayList<Integer>();
	
	@Override
	public Context build(Context<Object> context) {
		context.setId("JModel");
		
		context.add(new Controller());  //adds a new 'Controller' object to the context?
		Auctioneer a = new Auctioneer();
		context.add(a); //adds a new 'Auctioneer' object to the context
		auctioneer.add(a);//adds this Auctioneer object to a holding ArrayList
		
		ArrayList<Integer> goodTypeHolder = new ArrayList<Integer>(); //instantiates arraylist 'goodTypeHolder' of type int
		
		
		int total_consumers = 20; //the total number of consumers to create within the model
				
		
		
		
		for (int i=0; i < goodTypes; i++){  //this for loop assigns id numbers (between 0 and goodTypes-1) and prices to goods.
			int goodType = i;
			goodTypeHolder.add(i); //adds good number to goodTypeHolder arraylist
			double price = 1.0;//RandomHelper.nextDoubleFromTo(0.0, 10.0); //generates random price (double) between 0 and 10
			goodTypePrice.put(goodType, price); //adds this price within the goodTypePrice hashmap
			JModelBuilder.auctioneer.get(0).setPriceList(goodType, price);
		}
							
		ArrayList<Integer> goodsLeft = new ArrayList<Integer>(); //instantiates arraylist 'goodsLeft' 
		
		for (int i=0; i < goodAmount; i++){  //for all goods, this sets a price, type and ID number.
			int goodID = i;
			goodsLeft.add(i);  //adds value of i to the 'goodsLeft' arraylist (which is used later)
			int goodTypeSelect = RandomHelper.nextIntFromTo(0,goodTypes-1); //selects a good from id numbers 0 to goodTypes-1
			goodTypeAllocated.add(goodTypeSelect);
			Good b = new Good(goodTypePrice.get(goodTypeSelect), goodTypeSelect, goodID); //instantiates a new Good, with a price (based on the type), a type (assigned randomly from previous line), and a unique ID
			
			context.add(b); //adds this good to the 'context'
			goods.add(b); //adds this good to the arraylist 'goods'
			goodLookUp.put(goodID,goodTypePrice.get(goodTypeSelect)); //adds the goodID and the price to the 'goodLookUp' hashmap
			//System.out.println(i);
			//System.out.println(goodTypeSelect);
			//System.out.println(goodTypePrice.get(goodTypeSelect));
		}
		
		
		
		
		
		int maxGoods = 10; //maxGoods multiplied by consumers should be <= goodAmount to ensure that all Consumers have at least 1 good. THINK THIS COMMENT NEEDS TO BE IMPLEMENTED
		int minGoods = 10;
		
		for (int i=0; i < total_consumers; i++){ //NOTE: IT IS CURRENTLY POSSIBLE FOR CONSUMERS TO HAVE A PREFERENCE FOR GOODS WHICH ARE NOT IN THE 'goods' LIST (I.E. ARE NOT AVAILABLE).
			SimUtilities.shuffle(goodTypeHolder,  RandomHelper.getUniform()); //creates a random (shuffled) list of 'goodtypes'
			int[] pref = new int[goodTypes]; //instantiates an array to hold the list of preferences for good types for a consumer
			for (int j = 0; j<goodTypes; j++){ //for all 'goodtypes'
				pref[j] = goodTypeHolder.get(j);  //sets the random list of goodtypes to the preference list
			}
			
			int goodsToAllocate = RandomHelper.nextIntFromTo(minGoods,maxGoods); //sets the number of goods to allocate to each consumer
			
			ArrayList<Integer> goodsAllocated = new ArrayList<Integer>(); //instantiates new arraylist for holding allocated goods
			
			for (int j = 0; j<goodsToAllocate; j++){
								
				int rand = RandomHelper.nextIntFromTo(0,goodsLeft.size()-1); //creates random number between 0 and the number of goodsLeft-1
				
				goodsAllocated.add(goodsLeft.get(rand)); //adds a random good ID number to the goodsAllocated arraylist
				
				goodsLeft.remove(rand); //removes this good from the 'Goodsleft' list
			}
			//System.out.println(goodsAllocated);
			//System.out.println(goodTypeHolder);
			//System.out.println(goodsLeft);
			
			Consumer c = new Consumer(i, pref, goodsAllocated); //instantiates a new consumer with an id number (i), the preference list and a list of allocated goods)
			context.add(c); //adds this consumer to the context
			consumers.add(c);  //adds this consumer to the 'consumers' arraylist
			
			//double endowment = JModelBuilder.consumers.get(i).getEndowment();
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
	
	public static double getGoodTypePrice(int goodTypeSelect){
		return goodTypePrice.get(goodTypeSelect);
			}
	
	public static ArrayList<Integer> getGoodTypeAllocated(){
		return goodTypeAllocated;
	}
	
				
}



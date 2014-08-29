/**
 * 
 */
package jModel;


import java.util.ArrayList;
import java.util.Random;

import Jama.Matrix;





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
	private ArrayList<Integer> shoppingBasket = new ArrayList<Integer>(); //holds desired good details (good type numbers) to maximise utility

	private String[] firstName = {"Chris", "Simon", "Richard", "Elena", "Eric", "Aljona"};
	private String[] secondName = {"West", "Croft", "Taylor", "Dawkins", "Kemp-Benedict", "Karlysheva"};
	private String consumerName;
	
	public Consumer(int consumerIDNumber, int[] preference, ArrayList<Integer> goodsAllocated){ //initialisation stuff
		this.consumerIDNumber = consumerIDNumber; 
		this.preference = preference;
		this.goodsAllocated = goodsAllocated;
		int rnd1 = new Random().nextInt(firstName.length);
		int rnd2 = new Random().nextInt(secondName.length);
		this.consumerName = firstName[rnd1] + " " + secondName[rnd2];
		
	}
	
	public int getConsumerIDNumber(){
		return consumerIDNumber;
	}
	
	public int getNumberGoodsAllocated(){
		return goodsAllocated.size();
	}
	
	public int getPreference(){
		
		return preference[9]; //for some reason i have set this to get only the last (10th) item in the preference list??
	}
	
	public ArrayList<Integer> getGoodID(){
		
		return goodsAllocated; //and for some reason i have set this to get the first item in the goods list???
	}
	
	private double endowment(ArrayList<Good> goods, ArrayList<Integer> goodsAllocated){ //function has all Goods in the goods arraylist pass in, and the goodsAllocated array of the consumer 
		
		totalEndowment = 0.0; //total endowment set to zero
		double goodPrice = 0; //double called goodPrice created an set to zero
        ArrayList<Integer> type = new ArrayList<Integer>();
		
		for (int i = 0; i<goodsAllocated.size(); i++){ //for all goods allocated to the consumer
			for(int j = 0; j<JModelBuilder.getGoodAmount();j++){ //for all the goods in the model
				int tmpGoodID = JModelBuilder.goods.get(j).getID(); //gets the ID of the j'th item in the 'goods' arraylist
				if (tmpGoodID == goodsAllocated.get(i)){ //if this ID matches the id of the ID of the good i that is allocated to the consumer
					goodPrice = JModelBuilder.goods.get(j).getPrice(); //then get the price of this good
					type.add(JModelBuilder.goods.get(j).getType());
				}
				
			}
			totalEndowment = totalEndowment + goodPrice; //add this price to the totalEndowment (after looping, totalEndowment will equal sum of prices of all goods allocated
			
		}
		System.out.print("ConsumerName:");
		System.out.println(consumerName);
		//System.out.println(type);
		System.out.println(totalEndowment);
		return totalEndowment;
	}
	
	private double maxUtility(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){  //passes arraylist of Goods, the consumer preferences, and the allocated goods
		//System.out.print("ConsumerName:");
		//System.out.println(consumerName);
		//System.out.println(totalEndowment);
		//double endowment = 0;
		
		//double goodPrice = 0;
		double[] goodPrice = new double[preference.length];
		
		shoppingBasket.clear();
		
		double theoreticalMax = 0; 
		//endowment = endowment(goods, goodsAllocated); //calls endowment function and sets 'endowment' to returned double
		double endowment_remainder = totalEndowment;
		double[] endowment_rem_vector = new double[preference.length];
		
		for (int i = 0; i<preference.length; i++){
			goodPrice[i] = JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			endowment_rem_vector[i] = totalEndowment; 
		}
		
		Matrix goodPriceMat = new Matrix(goodPrice, preference.length);
		
		//int[] canAfford = Math.floor(endowment_rem_vector./goodPrice);
		
		/*for (int i = 0; i<preference.length; i++){ //in order of preference
			
			//goodPrice = JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			
			
			System.out.println(preference[i]);
			System.out.println(goodPrice);
			
			if (goodPrice<=endowment_remainder){  //if the goodPrice is less or equal to the amount of money they have left
				//System.out.println("Hooray - gimme gimme"); //i.e. I can afford that, and I want it now...
				
				if (JModelBuilder.getGoodTypeAllocated().contains(preference[i])){ //if the thing they want actually exists as a type in the 'pool' i.e. within 'goods' NOTE: THIS CURRENTLY ONLY COUNTS IF THEY EXIST, IT SHOULD ALSO PROBABLY SUM HOW MANY THERE ARE SO THAT IT CAN't EXCEED THIS NUMBER
				endowment_remainder = endowment_remainder - goodPrice; //deduct price from remaining endowment
				//System.out.println(endowment_remainder);				
						
				shoppingBasket.add(preference[i]);  //add the good type to the shopping list
								
				i--;
				}
			}
			
			
				
			
		}
		*/
		//System.out.println(shoppingBasket);
		
		//System.out.println(endowment);  //think these 5 lines were just for error checking??
		//int firstPref = preference[0];
		//System.out.println(firstPref);
		//double priceOfFirstPref = JModelBuilder.goodTypePrice.get(firstPref);
		//System.out.println(priceOfFirstPref);
		
		return maxUtility; //CURRENTLY THIS IS NOT UPDATED WITIN THIS FUNCTION - NEED TO DEAL WITH THIS
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
	
	public ArrayList<Integer> getShoppingBasket(){
		return shoppingBasket;
	}
	
}

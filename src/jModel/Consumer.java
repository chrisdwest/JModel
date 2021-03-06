/**
 * 
 */
package jModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import repast.simphony.engine.environment.RunEnvironment;

/**
 * @author Chris West
 *
 */
public class Consumer {
	
	private int consumerIDNumber;  //holds consumer ID number
	private int[] preference; //holds preference array
	private ArrayList<Integer> goodsAllocated; //arraylist holds allocated good details (by good ID number)
	double totalEndowment; //holds total endowment of the consumer	 
	double maxUtility; //holds maximum utility of the consumer
	private ArrayList<Integer> shoppingBasket = new ArrayList<Integer>(); //holds desired good details (good type numbers) to maximise utility
	private ArrayList<Integer> allSupplyIDs = new ArrayList<Integer>();
	private ArrayList<Integer> allSupplyTypes = new ArrayList<Integer>();
	private int[] supplyTypeCount = new int[(Integer) RunEnvironment.getInstance().getParameters().getValue("good_types")];
	private HashMap<Integer, Integer> supplyMap = new HashMap<Integer, Integer>();
	
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
		//System.out.println(goodsAllocated);
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
	
	public int[] getPreferenceList(){
		return preference;
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
					//System.out.println(goodPrice);
					type.add(JModelBuilder.goods.get(j).getType());
				}
				
			}
			totalEndowment = totalEndowment + goodPrice; //add this price to the totalEndowment (after looping, totalEndowment will equal sum of prices of all goods allocated
			//System.out.println(type);
			//System.out.println(totalEndowment);
		}
		
		return totalEndowment;
	}
	
	private double maxUtilityVer1(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){  //passes arraylist of Goods, the consumer preferences, and the allocated goods
			
		supplyMap.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
				
		for (int i = 0; i<supplyTypeCount.length;i++){
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
						
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<supplyTypeCount.length; i++){
		
		
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		
		
		supplyMap.put(i, supplyTypeCount[i]); 
		}
		
		//System.out.println(supplyMap);
		
	    double goodPrice = 0;
		//double[] goodPrice = new double[preference.length];
		
		shoppingBasket.clear();
		
		//endowment = endowment(goods, goodsAllocated); //calls endowment function and sets 'endowment' to returned double
		double endowment_remainder = totalEndowment;
		
		HashMap<Integer, Double> valueForMoney = new HashMap<Integer, Double>();
		ArrayList<Integer> Prefs = new ArrayList<Integer>();
		
		
		
		for (int i=0; i<preference.length;i++){
			Prefs.add(preference[i]);
			double score = preference.length - i;
			goodPrice = JModelBuilder.goodTypePrice.get(preference[i]);
					//JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			
			//System.out.println(goodPrice);
			if (supplyMap.get(preference[i])!=0){
			valueForMoney.put(preference[i], score/goodPrice);
			}
			else{
				valueForMoney.put(preference[i], -1.0);
			}
		}
		
		//System.out.println(valueForMoney);
		ArrayList<Integer> valuePrefs = new ArrayList<Integer>();
		
		for(int i = 0; i<preference.length;i++){
			double maxValueInMap =(Collections.max(valueForMoney.values()));
			//System.out.println(maxValueInMap);
			for(int j = 0; j<valueForMoney.size(); j++){
				//System.out.println(valueForMoney.get(j));
				
				if(valueForMoney.get(j)==maxValueInMap){
					if(valueForMoney.get(j)>=0.0){
					valuePrefs.add(j);
					}
					valueForMoney.put(j,-1.0);
				}	
			}
		}
		//System.out.println("PrefList:");
		//System.out.println(Prefs);
		
		//System.out.println("ValuePref:");
		//System.out.println(valuePrefs);
		
		for (int i = 0; i<valuePrefs.size();i++){
			goodPrice = JModelBuilder.goodTypePrice.get(valuePrefs.get(i));
			//System.out.println("Price & Endowment:");
			//System.out.println(goodPrice);
			//System.out.println(endowment_remainder);
			
			double result = Math.floor(endowment_remainder/goodPrice);
			//System.out.print("Wants:");
			//System.out.println(result);
			
			if(result>supplyMap.get(valuePrefs.get(i))){
				result = supplyMap.get(valuePrefs.get(i));
				//System.out.println(valuePrefs.get(i));
				//System.out.println(result);
			}
			//System.out.print("Can Have:");
			//System.out.println(result);
			double costOfPreference = result * goodPrice;
			//System.out.println(costOfPreference);
			endowment_remainder = endowment_remainder - costOfPreference;
			//System.out.println(endowment_remainder);
			for (int j=0; j<result;j++){
			shoppingBasket.add(valuePrefs.get(i));
			//System.out.println(valuePrefs.get(i));
			}
		}
		
		//System.out.println(shoppingBasket);
				
		return maxUtility; //CURRENTLY THIS IS NOT UPDATED WITIN THIS FUNCTION - NEED TO DEAL WITH THIS
	}	

	private double maxUtilityVer2(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){
		supplyMap.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
				
		for (int i = 0; i<supplyTypeCount.length;i++){
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
						
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<supplyTypeCount.length; i++){
				
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		supplyMap.put(i, supplyTypeCount[i]); 
		}
						
	    double goodPrice = 0;
				
		shoppingBasket.clear();
		
		double endowment_remainder = totalEndowment;
		
		HashMap<Integer, Double> goodScore = new HashMap<Integer, Double>();
		ArrayList<Integer> Prefs = new ArrayList<Integer>();
		
		
		for (int i=0; i<preference.length;i++){
			Prefs.add(preference[i]);
			double score = preference.length - i;
			goodPrice = JModelBuilder.goodTypePrice.get(preference[i]);
					
			if (supplyMap.get(preference[i])!=0){
			goodScore.put(preference[i], score);
			}
			else{
				goodScore.put(preference[i], -1.0);
			}
		}
				
		ArrayList<Integer> valuePrefs = new ArrayList<Integer>();
		
		for(int i = 0; i<preference.length;i++){
			double maxValueInMap =(Collections.max(goodScore.values()));
			
			for(int j = 0; j<goodScore.size(); j++){
								
				if(goodScore.get(j)==maxValueInMap){
					if(goodScore.get(j)>=0.0){
					valuePrefs.add(j);
					}
					goodScore.put(j,-1.0);
				}	
			}
		}
		
		//System.out.println("PrefList:");
				//System.out.println(Prefs);
				
				//System.out.println("ValuePref:");
				//System.out.println(valuePrefs);
				
		for (int i = 0; i<valuePrefs.size();i++){
			goodPrice = JModelBuilder.goodTypePrice.get(valuePrefs.get(i));
			
			
			double result = Math.floor(endowment_remainder/goodPrice);
			
			
			if(result>supplyMap.get(valuePrefs.get(i))){
				result = supplyMap.get(valuePrefs.get(i));
				
			}
			
			double costOfPreference = result * goodPrice;
			
			endowment_remainder = endowment_remainder - costOfPreference;
			
			for (int j=0; j<result;j++){
			shoppingBasket.add(valuePrefs.get(i));
			
			}
		}
		//System.out.println(shoppingBasket);				
		return maxUtility;
	}
	
	private double maxUtilityVer3(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){
		supplyMap.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
				
		for (int i = 0; i<supplyTypeCount.length;i++){
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
						
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<supplyTypeCount.length; i++){
				
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		supplyMap.put(i, supplyTypeCount[i]); 
		}
						
	    double goodPrice = 0;
				
		shoppingBasket.clear();
		
		double endowment_remainder = totalEndowment;
		
		//System.out.println(totalEndowment);
		
		HashMap<Integer, Double> goodScore = new HashMap<Integer, Double>();
		ArrayList<Integer> Prefs = new ArrayList<Integer>();
		
		double scoreSum = ((preference.length)/2.0) * (2.0+(preference.length) - 1.0);
		double[] moneyAvailable = new double[preference.length]; 
		
		for (int i=0; i<preference.length;i++){
			double score = preference.length - i;
			moneyAvailable[i] = totalEndowment*(score/scoreSum);
			//System.out.println(moneyAvailable[i]);
		}
		
		HashMap<Integer, Integer> tempSupplyMap = new HashMap<Integer, Integer>();
		tempSupplyMap.putAll(supplyMap);
		
		for (int i=0; i<preference.length;i++){
			goodPrice = JModelBuilder.goodTypePrice.get(preference[i]);
			double result = Math.floor(moneyAvailable[i]/goodPrice);
			tempSupplyMap.put(preference[i], tempSupplyMap.get(preference[i]) - (int)result);			
			//System.out.println(goodPrice);
			//System.out.println(result);
			//System.out.println(preference[i]);	
			if(result>supplyMap.get(preference[i])){
				result = supplyMap.get(preference[i]);
				tempSupplyMap.put(preference[i], 0);	
			}
			
			double costOfPreference = result * goodPrice;
			
			endowment_remainder = endowment_remainder - costOfPreference;
			
			for (int j=0; j<result;j++){
				shoppingBasket.add(preference[i]);
				
				}
		}
		
		//System.out.println(endowment_remainder);
		
		for (int i=0; i<preference.length;i++){
			
			double score = preference.length - i;
			goodPrice = JModelBuilder.goodTypePrice.get(preference[i]);
					
			if (tempSupplyMap.get(preference[i])!=0){
			goodScore.put(preference[i], score);
			}
			else{
				goodScore.put(preference[i], -1.0);
			}
		}
				
		ArrayList<Integer> valuePrefs = new ArrayList<Integer>();
		
		for(int i = 0; i<preference.length;i++){
			double maxValueInMap =(Collections.max(goodScore.values()));
			
			for(int j = 0; j<goodScore.size(); j++){
								
				if(goodScore.get(j)==maxValueInMap){
					if(goodScore.get(j)>=0.0){
					valuePrefs.add(j);
					}
					goodScore.put(j,-1.0);
				}	
			}
		}
				
		for (int i = 0; i<valuePrefs.size();i++){
			goodPrice = JModelBuilder.goodTypePrice.get(valuePrefs.get(i));
			
			//System.out.println(valuePrefs.get(i));	
			double result = Math.floor(endowment_remainder/goodPrice);
			//System.out.println(result);
			//System.out.println(tempSupplyMap.get(valuePrefs.get(i)));
			if(result>tempSupplyMap.get(valuePrefs.get(i))){
				result = tempSupplyMap.get(valuePrefs.get(i));
			}
			
			double costOfPreference = result * goodPrice;
			
			endowment_remainder = endowment_remainder - costOfPreference;
			
			for (int j=0; j<result;j++){
			shoppingBasket.add(valuePrefs.get(i));
			
			}
		}
		
		//System.out.println(shoppingBasket);			
		return maxUtility;
	}
	
	private double maxUtilityVer4(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){
		supplyMap.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
				
		for (int i = 0; i<supplyTypeCount.length;i++){
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
						
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<supplyTypeCount.length; i++){
				
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		supplyMap.put(i, supplyTypeCount[i]); 
		}
						
	    double goodPrice = 0;
				
		shoppingBasket.clear();
		
		double endowment_remainder = totalEndowment;
		
		HashMap<Integer, Double> valueForMoney = new HashMap<Integer, Double>();
		ArrayList<Integer> Prefs = new ArrayList<Integer>();
		
		
		
		for (int i=0; i<preference.length;i++){
			Prefs.add(preference[i]);
			double score = preference.length - i;
			goodPrice = JModelBuilder.goodTypePrice.get(preference[i]);
					//JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			
			//System.out.println(goodPrice);
			if (supplyMap.get(preference[i])!=0){
			valueForMoney.put(preference[i], score/goodPrice);
			}
			else{
				valueForMoney.put(preference[i], -1.0);
			}
		}
		
		//System.out.println(Prefs);
		//System.out.println(JModelBuilder.goodTypePrice);
		//System.out.println(valueForMoney);
		
		double valueSum = 0.0;
		double[] moneyAvailable = new double[preference.length]; 
		
		for (int i=0; i<valueForMoney.size();i++){
			if(valueForMoney.get(i)>=0.0){
			valueSum = valueSum + valueForMoney.get(i);
			}
		}
		//System.out.println(valueSum);
		//System.out.println(totalEndowment);
		
		for (int i=0; i<valueForMoney.size();i++){
			if(valueForMoney.get(i)>=0.0){
			moneyAvailable[i] = totalEndowment*(valueForMoney.get(i)/valueSum);
			}
			//System.out.println(moneyAvailable[i]);
		}
		
		//System.out.println(valueForMoney);
		ArrayList<Integer> valuePrefs = new ArrayList<Integer>();
		
		for(int i = 0; i<preference.length;i++){
			double maxValueInMap =(Collections.max(valueForMoney.values()));
			//System.out.println(maxValueInMap);
			for(int j = 0; j<valueForMoney.size(); j++){
				//System.out.println(valueForMoney.get(j));
				
				if(valueForMoney.get(j)==maxValueInMap){
					if(valueForMoney.get(j)>=0.0){
					valuePrefs.add(j);
					}
					valueForMoney.put(j,-1.0);
				}	
			}
		}
		
		
							
		HashMap<Integer, Integer> tempSupplyMap = new HashMap<Integer, Integer>();
		
		tempSupplyMap.putAll(supplyMap);
		//System.out.println(valuePrefs);
		//System.out.println(tempSupplyMap);
		for (int i=0; i<valuePrefs.size();i++){
			goodPrice = JModelBuilder.goodTypePrice.get(valuePrefs.get(i));
			
			double result = Math.floor(moneyAvailable[valuePrefs.get(i)]/goodPrice);
			//System.out.println(goodPrice);
			//System.out.println(moneyAvailable[valuePrefs.get(i)]);
			//System.out.println(result);
			tempSupplyMap.put(valuePrefs.get(i), tempSupplyMap.get(valuePrefs.get(i)) - (int)result);			
			//System.out.println(tempSupplyMap);
			//System.out.println(result);
			//System.out.println(preference[i]);	
			if(result>supplyMap.get(valuePrefs.get(i))){
				result = supplyMap.get(valuePrefs.get(i));
				tempSupplyMap.put(valuePrefs.get(i), 0);	
			}
			
			double costOfPreference = result * goodPrice;
			
			endowment_remainder = endowment_remainder - costOfPreference;
			
			for (int j=0; j<result;j++){
				shoppingBasket.add(valuePrefs.get(i));
				
				}
		}
		
		//System.out.println(valuePrefs);
		//System.out.println(tempSupplyMap);
		//System.out.println(shoppingBasket);
		
		for (int i = 0; i<valuePrefs.size();i++){
			goodPrice = JModelBuilder.goodTypePrice.get(valuePrefs.get(i));
			//System.out.println(endowment_remainder);
			//System.out.println(goodPrice);	
			double result = Math.floor(endowment_remainder/goodPrice);
			//System.out.println(valuePrefs.get(i));
			//System.out.println(result);
			//System.out.println(tempSupplyMap.get(valuePrefs.get(i)));
			if(result>tempSupplyMap.get(valuePrefs.get(i))){
				result = tempSupplyMap.get(valuePrefs.get(i));
			}
			//System.out.println(result);
			double costOfPreference = result * goodPrice;
			
			endowment_remainder = endowment_remainder - costOfPreference;
			
			for (int j=0; j<result;j++){
			shoppingBasket.add(valuePrefs.get(i));
			
			}
		}
		
		//System.out.println(shoppingBasket);			
		return maxUtility;
	}
	
	public double getMaxUtility(){
		maxUtilityVer4(JModelBuilder.goods, preference, goodsAllocated); //runs maxUtility (select correct version)
		return maxUtility;
	}
	
	public double getEndowment() {
		
		endowment(JModelBuilder.goods, goodsAllocated); //runs endowment			
		return totalEndowment;
		
	}
	
	public ArrayList<Integer> getShoppingBasket(){
		return shoppingBasket;
	}
	
}

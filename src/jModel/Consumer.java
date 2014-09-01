/**
 * 
 */
package jModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
	private int[] supplyTypeCount = new int[JModelBuilder.goodTypes];
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
		
		return totalEndowment;
	}
	
	private double maxUtility(ArrayList<Good> goods, int[] preference, ArrayList<Integer> goodsAllocated){  //passes arraylist of Goods, the consumer preferences, and the allocated goods
			
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
		//double[] goodPrice = new double[preference.length];
		
		shoppingBasket.clear();
		
		//endowment = endowment(goods, goodsAllocated); //calls endowment function and sets 'endowment' to returned double
		double endowment_remainder = totalEndowment;
		
		double minPrice=10000; //this isn't very satisfactory at the moment...
		for (int i=0; i<preference.length; i++){
			goodPrice = JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			if(minPrice>goodPrice){
				minPrice = goodPrice;
			}
		}
		
		while(endowment_remainder>=minPrice){
			double utilityMax = 0;
			int utilityMaxRef = 0;
			double utilityMaxAmount = 0;
			
		for (int i=0; i<preference.length;i++){
			goodPrice = JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			double result = Math.floor(endowment_remainder/goodPrice);
			if(result>supplyMap.get(preference[i])){
				result = supplyMap.get(preference[i]);
			}			
			double score = preference.length - i;
			double utility = result * score;
			
			if(utility>utilityMax){
			utilityMaxRef = i;
			utilityMaxAmount = result;
			utilityMax = utility;
			}
						
		}
		
		double costOfPreference = utilityMaxAmount * JModelBuilder.auctioneer.get(0).getPrice(preference[utilityMaxRef]);
				
		endowment_remainder = endowment_remainder - costOfPreference;
		
		
		for(int i = 0; i<utilityMaxAmount;i++){
			shoppingBasket.add(preference[utilityMaxRef]);
		}
		
		}
		//System.out.println(shoppingBasket);
		/*for (int i = 0; i<preference.length; i++){ //in order of preference
			
			//goodPrice = JModelBuilder.auctioneer.get(0).getPrice(preference[i]);
			
			
			System.out.println(preference[i]);
			System.out.println(goodPrice);
			
			if (goodPrice<=endowment_remainder){  //if the goodPrice is less or equal to the amount of money they have left
				
				if (JModelBuilder.getGoodTypeAllocated().contains(preference[i])){ //if the thing they want actually exists as a type in the 'pool' i.e. within 'goods' NOTE: THIS CURRENTLY ONLY COUNTS IF THEY EXIST, IT SHOULD ALSO PROBABLY SUM HOW MANY THERE ARE SO THAT IT CAN't EXCEED THIS NUMBER
				endowment_remainder = endowment_remainder - goodPrice; //deduct price from remaining endowment
				//System.out.println(endowment_remainder);				
						
				shoppingBasket.add(preference[i]);  //add the good type to the shopping list
								
				i--;
				}
			}						
		}
		*/
				
		return maxUtility; //CURRENTLY THIS IS NOT UPDATED WITIN THIS FUNCTION - NEED TO DEAL WITH THIS
	}	
		
	public double getMaxUtility(){
		maxUtility(JModelBuilder.goods, preference, goodsAllocated); //runs maxUtility
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

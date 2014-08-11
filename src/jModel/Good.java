/**
 * 
 */
package jModel;

//import java.util.ArrayList;
//import java.util.HashMap;

//import repast.simphony.engine.schedule.ScheduledMethod;

/**
 * @author Chris West
 *
 */
public class Good {
	
	private double price;   //holds the price of the good	
	private int goodIDNumber; //holds the ID of the good	
	private int goodType;	//holds the type of the good
	
		
	public Good(double price, int goodType, int goodIDNumber){
		this.price = price;  //sets the price(passed in to function when Good is initialised)
		this.goodType = goodType; //sets the goodType (passed in to function)
		this.goodIDNumber = goodIDNumber; //sets goodIDNumber (passed in to function)
	}
	
	public double getPrice() { //returns price
		return price;
	}
	
	
	
	/*public static void setGoodPrices(HashMap<Integer, Double> goodTypePrice){
		double oldPrice = 0;
		double newPrice = 0;
		for (int i = 0; i<goodTypePrice.size(); i++){
			oldPrice = goodTypePrice.get(i+1);
			newPrice = oldPrice + 1.0;
			goodTypePrice.put(i+1,newPrice);
		}
	}*/
	
	public void setPrice(double newPrice){ //sets new price
		//int type = getType();
		//System.out.println(this.price);
		this.price = newPrice;
		//System.out.println(this.price);
	}
			
	
	public int getType() {
		return goodType;
	}
	
	public int getID(){
		return goodIDNumber;
	}
	
	
	
	
	
	
	
	

}

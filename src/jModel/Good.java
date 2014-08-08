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
	
	private double price;
	private int goodIDNumber;
	private int goodType;
	
		
	public Good(double price, int goodType, int goodIDNumber){
		this.price = price;
		this.goodType = goodType;
		this.goodIDNumber = goodIDNumber;
	}
	
	public double getPrice() {
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
	
	public void setPrice(double newPrice){
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

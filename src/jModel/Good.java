/**
 * 
 */
package jModel;

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
	
	public void setPrice(double newPrice){ //sets new price
		this.price = newPrice;
	}
			
	
	public int getType() {
		return goodType;
	}
	
	public int getID(){
		return goodIDNumber;
	}
	
	
	
	
	
	
	
	
	

}

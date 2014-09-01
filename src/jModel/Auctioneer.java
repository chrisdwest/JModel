/**
 * 
 */
package jModel;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Chris West
 *
 */
public class Auctioneer {
	private ArrayList<Integer> allDemand = new ArrayList<Integer>();
	private ArrayList<Integer> allSupplyIDs = new ArrayList<Integer>();
	private ArrayList<Integer> allSupplyTypes = new ArrayList<Integer>();
	private int[] demandTypeCount = new int[JModelBuilder.goodTypes];
	private HashMap<Integer, Integer> demandMap = new HashMap<Integer, Integer>();
	private int[] supplyTypeCount = new int[JModelBuilder.goodTypes];
	private HashMap<Integer, Integer> supplyMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Double> priceList = new HashMap<Integer, Double>();
	File file = new File("test.txt");
	
	
		
	public Auctioneer(){ //initialisation stuff
				
	}
	
	public int getDemand1(){
		return demandMap.get(1);
	}	
		
	public void calculateDemand() {
		
		demandMap.clear();
		supplyMap.clear();
		allDemand.clear();
		allSupplyIDs.clear();
		allSupplyTypes.clear();
		int totalSupply = 0;
		int totalDemand = 0;
		
		PrintWriter output = null;
		try {
			output = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		output.print("Step: ");
		output.println(Controller.getStep());
		output.println("GoodType, Demand, Supply, Price");
		
		for (int i = 0; i<demandTypeCount.length;i++){
			demandTypeCount[i] = 0;
			supplyTypeCount[i] = 0;
		}
		
		for(int i = 0; i<JModelBuilder.consumers.size(); i++){
			allDemand.addAll(JModelBuilder.consumers.get(i).getShoppingBasket());
			allSupplyIDs.addAll(JModelBuilder.consumers.get(i).getGoodID());
		}
		
			
				
		for (int i = 0; i<allSupplyIDs.size(); i++){
			allSupplyTypes.add(JModelBuilder.goods.get(allSupplyIDs.get(i)).getType()); 
		}
		
		for(int i = 0; i<demandTypeCount.length; i++){
			for(int j = 0; j<allDemand.size();j++){
				if (allDemand.get(j) == i){
					demandTypeCount[i]++;
				}
				
			}
		
		demandMap.put(i, demandTypeCount[i]); 
		
		for(int j = 0; j<allSupplyTypes.size(); j++){
			if (allSupplyTypes.get(j) == i){
				supplyTypeCount[i]++;
			}
		}
		
		supplyMap.put(i, supplyTypeCount[i]); 
		
		output.print(i);
		output.print(",");
		output.print(demandMap.get(i));
		output.print(",");
		output.print(supplyMap.get(i));
		output.print(",");
		output.println(priceList.get(i));
		
				
		}
		
		for (int i=0;i<supplyMap.size();i++){
			totalSupply = totalSupply + supplyMap.get(i);
			totalDemand = totalDemand + demandMap.get(i);
		}
		System.out.println(totalSupply - totalDemand);
		
		output.println();
		
		output.close();
		
		
	}
	
	public void setNewPrices(){
		
		//double a=0;
		double[] b = new double[demandTypeCount.length];
		double c = 0;
		//double max = 0;
		priceList.clear();
		
		for (int i=0; i<demandTypeCount.length;i++){
			
			/* old method
			b[i] = (((double)demandMap.get(i) - (double)supplyMap.get(i)) / (double)supplyMap.get(i));
			*/
			
			//new method
			b[i] = ((double)demandMap.get(i) / (double)supplyMap.get(i));
			//c = (-0.5)*Math.exp(Math.log(0.5)*b[i])+1.25;
			c = Math.min((0.1*b[i])+0.9,1.1);
									
			
			
			JModelBuilder.goodTypePrice.put(i, JModelBuilder.goodTypePrice.get(i)*c);
			
			/*not needed for new method
			if(b[i]>max){
				max = b[i];
			}
			*/
		}
		/*OLD METHOD
		for (int i=0; i<demandTypeCount.length;i++){
			
			
			if(b[i]>0){
				b[i]=b[i]/max;
			}
		
			double c = Math.abs(b[i]);
			a = a+c;
			
		}
		
		for (int i=0; i<demandTypeCount.length;i++){
						
			JModelBuilder.goodTypePrice.put(i, Math.max(JModelBuilder.goodTypePrice.get(i) + ((b[i]/a)/* JModelBuilder.goodTypePrice.get(i)*//*),0.00001) );  
			
		}
	    */
				
		for (int i=0; i<JModelBuilder.goodAmount; i++){
			double price = JModelBuilder.goodTypePrice.get(JModelBuilder.goods.get(i).getType());
			JModelBuilder.goods.get(i).setPrice(price);
			priceList.put(JModelBuilder.goods.get(i).getType(), price);
		}
				
	}
	
	
	public double getPrice(int type){
		return priceList.get(type);
	}
	
	public void setPriceList(int type, double price){
		priceList.put(type, price);
	}
	
	public HashMap<Integer,Integer> getSupplyMap(){
		return supplyMap;
	}
	
	
}


